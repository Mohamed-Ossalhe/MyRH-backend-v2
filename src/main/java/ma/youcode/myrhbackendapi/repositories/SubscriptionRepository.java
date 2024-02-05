package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Pack;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.Subscription;
import ma.youcode.myrhbackendapi.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    public Optional<Subscription> findSubscriptionByRecruiterAndSubscriptionStatus(Recruiter recruiter, SubscriptionStatus subscriptionStatus);
    public Optional<Subscription> findSubscriptionByRecruiter(Recruiter recruiter);
}
