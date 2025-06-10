package andefi.furnish.payload;

import jakarta.validation.constraints.NotBlank;

public class RequestPasswordResetRequestBody {
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public String getEmail() {
        return email;
    }
}
