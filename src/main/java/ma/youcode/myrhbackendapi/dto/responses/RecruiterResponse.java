package ma.youcode.myrhbackendapi.dto.responses;

import lombok.*;
import ma.youcode.myrhbackendapi.entities.JobOffer;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecruiterResponse extends UserResponse {
    private String address;
    private String image;
//    private List<JobOffer> jobOffers;
}
