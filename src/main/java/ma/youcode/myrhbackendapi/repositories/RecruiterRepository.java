package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {
    public Optional<Recruiter> findRecruiterByEmail(String email);
}
