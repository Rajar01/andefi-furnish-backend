package andefi.furnish.order.service;

import andefi.furnish.account.service.AccountService;
import andefi.furnish.order.model.Cart;
import andefi.furnish.order.model.CartItem;
import andefi.furnish.order.repository.CartRepository;
import andefi.furnish.product.model.Media;
import andefi.furnish.product.model.Product;
import andefi.furnish.product.service.MediaService;
import andefi.furnish.product.service.ProductService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class CartService {
  @Inject ProductService productService;
  @Inject AccountService accountService;
  @Inject MediaService mediaService;

  @Inject CartRepository cartRepository;

  public void addProductsIntoCart(String email, UUID productId, Long quantity) {
    // Get or create cart for the associated account
    Cart cart =
        this.getCartByAccountEmail(email)
            .orElseGet(
                () -> {
                  Cart c = new Cart();
                  c.setAccount(accountService.getAccountByEmail(email));
                  return c;
                });

    // Get cart items inside cart
    List<CartItem> cartItems = cart.getCartItems();

    // Add product to cart
    Product product = productService.getProductById(productId);
    CartItem cartItem = new CartItem();
    cartItem.setCart(cart);
    cartItem.setProduct(product);
    cartItem.setQuantity(quantity);
    cartItems.add(cartItem);
    cart.setCartItems(cartItems);

    cartRepository.persist(cart);
  }

  public Optional<Cart> getCartByAccountEmail(String email) {
    return cartRepository.find("account.email", email).firstResultOptional();
  }

  public List<CartItem> getCartItemsInCart(String email) {
    Optional<Cart> cart = this.getCartByAccountEmail(email);

    cart.ifPresent(
        c -> {
          c.getCartItems()
              .forEach(
                  ci -> {
                    Product product = ci.getProduct();
                    List<Media> media = mediaService.getProductMedia(product.getId());
                    ci.getProduct().setMedia(media);
                  });
        });

    return cart.isPresent() ? cart.get().getCartItems() : new ArrayList<>();
  }

  public void updateCartItemQuantityInCart(String email, UUID cartItemId, Long quantity) {
    Cart cart = this.getCartByAccountEmail(email).orElseThrow(NotFoundException::new);

    AtomicBoolean isUpdated = new AtomicBoolean(false);

    cart.getCartItems()
        .forEach(
            it -> {
              if (it.getId().equals(cartItemId)) {
                it.setQuantity(quantity);
                isUpdated.set(true);
              }
            });

    if (!isUpdated.get()) {
      throw new BadRequestException();
    }

    cartRepository.persist(cart);
  }

  public void removeCartItemInCart(String email, UUID cartItemId) {
    Cart cart = this.getCartByAccountEmail(email).orElseThrow(NotFoundException::new);

    boolean isRemoved = cart.getCartItems().removeIf(it -> it.getId().equals(cartItemId));

    if (!isRemoved) {
      throw new BadRequestException();
    }

    cartRepository.persist(cart);
  }
}
