package andefi.furnish.order.repository;

import andefi.furnish.order.model.Cart;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class CartRepository implements PanacheRepositoryBase<Cart, UUID> {}
