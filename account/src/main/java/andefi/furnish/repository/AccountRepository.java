package andefi.furnish.repository;

import andefi.furnish.model.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, UUID> {
    public Optional<Account> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public Optional<Account> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }
}