package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, UUID> {
    public Optional<JobSeeker> findJobSeekerByIdentity(String identity);
}
