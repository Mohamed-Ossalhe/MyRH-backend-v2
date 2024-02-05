package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.myrhbackendapi.enums.SubscriptionStatus;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recruiters")
public class Recruiter extends User {
    private String Address;
    private String image;

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY)
    private List<JobOffer> jobOffers;
}
