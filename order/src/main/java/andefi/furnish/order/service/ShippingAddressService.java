package andefi.furnish.order.service;

import andefi.furnish.order.model.ShippingAddress;
import andefi.furnish.order.repository.ShippingAddressRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ShippingAddressService {
  @Inject ShippingAddressRepository shippingAddressRepository;

  public Optional<ShippingAddress> getShippingAddressById(UUID id) {
    return shippingAddressRepository.findByIdOptional(id);
  }
}
