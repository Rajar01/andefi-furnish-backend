package andefi.furnish.product.service;

import andefi.furnish.product.model.EntityType;
import andefi.furnish.product.model.Media;
import andefi.furnish.product.repository.MediaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MediaService {
  @Inject MediaRepository mediaRepository;

  public List<Media> getProductMedia(UUID id) {
    return mediaRepository.findByEntityIdAndEntityType(id, EntityType.PRODUCT);
  }

  public List<Media> getReviewMedia(UUID id) {
    return mediaRepository.findByEntityIdAndEntityType(id, EntityType.REVIEW);
  }
}
