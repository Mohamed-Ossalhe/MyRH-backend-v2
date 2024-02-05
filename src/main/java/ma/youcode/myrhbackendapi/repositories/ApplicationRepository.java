package ma.youcode.myrhbackendapi.repositories;

import ma.youcode.myrhbackendapi.entities.Application;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, SeekerOfferId> {
}
