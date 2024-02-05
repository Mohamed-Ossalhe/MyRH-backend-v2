package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, UUID> {
}
