package andefi.furnish.model;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "accounts", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})
@UserDefinition
public class Account {
    @Id
    @GeneratedValue
    private UUID id;
    @Email()
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @Username
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Password
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Roles
    private String role = "user";
    @Column(name = "verified_at")
    private Instant verifiedAt;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
