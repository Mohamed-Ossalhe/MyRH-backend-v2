package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, UUID> {
    public Integer countJobOffersByRecruiter(Recruiter recruiter);
}
