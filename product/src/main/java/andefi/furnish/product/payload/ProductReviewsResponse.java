package andefi.furnish.product.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductReviewsResponse {
  @JsonProperty private UUID id;
  @JsonProperty private String content;
  @JsonProperty private float rating;
  @JsonProperty private String author;
  @JsonProperty private List<String> media = new ArrayList<>();

  public ProductReviewsResponse() {}

  public ProductReviewsResponse(
      UUID id, String content, float rating, String author, List<String> media) {
    this.id = id;
    this.content = content;
    this.rating = rating;
    this.author = author;
    this.media = media;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setMedia(List<String> media) {
    this.media = media;
  }
}
