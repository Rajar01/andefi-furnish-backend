package andefi.furnish.order.repository;

import andefi.furnish.order.model.ShippingAddress;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ShippingAddressRepository implements PanacheRepositoryBase<ShippingAddress, UUID> {}
