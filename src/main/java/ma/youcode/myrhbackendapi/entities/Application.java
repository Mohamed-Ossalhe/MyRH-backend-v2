package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applications")
public class Application {
    @EmbeddedId
    private SeekerOfferId id;

    @Column(name = "motivation_letter")
    private String motivationLetter;

    @ManyToOne
    @MapsId("jobSeekerId")
    @JoinColumn(name = "seeker_id")
    private JobSeeker jobSeeker;

    @ManyToOne
    @MapsId("jobOfferId")
    @JoinColumn(name = "offer_id")
    private JobOffer jobOffer;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Application(SeekerOfferId id, String motivationLetter, JobSeeker jobSeeker, JobOffer jobOffer) {
        this.id = id;
        this.motivationLetter = motivationLetter;
        this.jobSeeker = jobSeeker;
        this.jobOffer = jobOffer;
    }
}
