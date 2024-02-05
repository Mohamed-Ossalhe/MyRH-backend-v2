package ma.youcode.myrhbackendapi.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeekerOfferId implements Serializable {
    @Column(name = "seeker_id")
    private UUID jobSeekerId;
    @Column(name = "offer_id")
    private UUID jobOfferId;
}
