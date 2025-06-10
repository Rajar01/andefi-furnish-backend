package andefi.furnish.payload;

import jakarta.validation.constraints.NotBlank;

public class PasswordResetRequestBody {
    @NotBlank(message = "Password cannot be blank")
    private String password;

    public String getPassword() {
        return password;
    }
}
