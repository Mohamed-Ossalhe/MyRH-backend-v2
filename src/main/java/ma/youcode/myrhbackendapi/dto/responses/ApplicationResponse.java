package ma.youcode.myrhbackendapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.JobSeeker;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private SeekerOfferId id;

    private String motivationLetter;

    private JobSeekerResponse jobSeeker;

    private JobOfferResponse jobOffer;
}
