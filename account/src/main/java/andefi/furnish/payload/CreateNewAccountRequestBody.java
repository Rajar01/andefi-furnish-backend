package andefi.furnish.payload;

import jakarta.validation.constraints.NotBlank;

public class CreateNewAccountRequestBody {
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private String role = "user";

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
