package zerobase.commerce.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.commerce.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

}
