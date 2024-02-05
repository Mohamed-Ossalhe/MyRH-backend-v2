package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PackRepository extends JpaRepository<Pack, UUID> {
    public Optional<Pack> findByNameAndPrice(String name, double price);
}
