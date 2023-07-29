package bg.sdcf.repository;

import bg.sdcf.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, String> {

   Optional<UserRole> findByAuthority(String authority);

}