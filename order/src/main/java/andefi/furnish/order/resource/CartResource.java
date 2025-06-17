package andefi.furnish.order.resource;

import andefi.furnish.order.model.Cart;
import andefi.furnish.order.payload.*;
import andefi.furnish.order.service.CartService;
import andefi.furnish.product.model.Category;
import andefi.furnish.product.model.Product;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

@Path("/api/carts")
@RequestScoped
public class CartResource {
  @Inject CartService cartService;

  @Inject
  @Claim(standard = Claims.email)
  String accountEmail;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Authenticated
  @Transactional
  public Response addProductsIntoCart(@Valid AddProductsToCartRequestBody payload) {
    cartService.addProductsIntoCart(accountEmail, payload.getProductId(), payload.getQuantity());

    return Response.ok().build();
  }

  @GET
  @Authenticated
  @Produces(MediaType.APPLICATION_JSON)
  public Response getCartItemsInCart() {
    Optional<Cart> cart = cartService.getCartByAccountEmail(accountEmail);

    CartResponse response = new CartResponse();

    cart.ifPresent(
        c -> {
          List<CartItemResponse> cartItems =
              cartService.getCartItemsInCart(accountEmail).stream()
                  .map(
                      ci -> {
                        Product product = ci.getProduct();

                        String media =
                            !product.getMedia().isEmpty()
                                ? product.getMedia().getFirst().getUrl()
                                : null;

                        Set<String> productCategories =
                            product.getCategories().stream()
                                .map(Category::getName)
                                .collect(Collectors.toSet());

                        CartItemResponse cartItem = new CartItemResponse();
                        cartItem.setId(ci.getId());
                        cartItem.setProductId(product.getId());
                        cartItem.setProductName(product.getName());
                        cartItem.setProductCategories(productCategories);
                        cartItem.setProductPrice(product.getPrice());
                        cartItem.setQuantity(ci.getQuantity());
                        cartItem.setDiscountPercentage(product.getDiscountPercentage());
                        cartItem.setMedia(media);

                        return cartItem;
                      })
                  .toList();

          response.setId(c.getId());
          response.setCartItems(cartItems);
        });

    return Response.ok(response).build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Authenticated
  @Transactional
  public Response updateCartItemQuantityInCart(
      @Valid UpdateCartItemQuantityInCartRequestBody payload) {
    cartService.updateCartItemQuantityInCart(
        accountEmail, payload.getCartItemId(), payload.getQuantity());
    return Response.ok().build();
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Authenticated
  @Transactional
  public Response removeCartItemInCart(@Valid RemoveCartItemInCartRequestBody payload) {
    cartService.removeCartItemInCart(accountEmail, payload.getCartItemId());

    return Response.ok().build();
  }
}
