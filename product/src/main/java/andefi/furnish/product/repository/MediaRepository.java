package andefi.furnish.product.repository;

import andefi.furnish.product.model.EntityType;
import andefi.furnish.product.model.Media;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MediaRepository implements PanacheRepositoryBase<Media, UUID> {
  public List<Media> findByEntityIdAndEntityType(UUID entityId, EntityType entityType) {
    return find("entityId = ?1 and entityType = ?2", entityId, entityType).list();
  }
}
