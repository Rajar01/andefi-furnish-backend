package andefi.furnish.account.payload;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequestBody {
  @NotBlank(message = "Email cannot be blank")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  private String password;

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }
}
