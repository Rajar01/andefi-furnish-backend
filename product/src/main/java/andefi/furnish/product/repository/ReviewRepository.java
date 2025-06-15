package andefi.furnish.product.repository;

import andefi.furnish.product.model.Review;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ReviewRepository implements PanacheRepositoryBase<Review, UUID> {}
