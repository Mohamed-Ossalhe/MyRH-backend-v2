package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;
import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.JobSeeker;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;

@Data
public class ApplicationRequest {
    private SeekerOfferId id;

    private String motivationLetter;

    private JobSeekerRequest jobSeeker;

    private JobOfferRequest jobOffer;
}
