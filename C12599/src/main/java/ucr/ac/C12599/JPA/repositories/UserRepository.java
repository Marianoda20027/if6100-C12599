package ucr.ac.C12599.JPA.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucr.ac.C12599.JPA.entities.userEntity;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<userEntity, UUID> {
}
