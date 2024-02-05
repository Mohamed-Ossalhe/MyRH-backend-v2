package ma.youcode.myrhbackendapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.entities.Application;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.enums.OfferStatus;

import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobOfferResponse {
    private UUID id;
    private String title;
    private String description;
    private String profile;

    private String address;
    private String educationalLevel;

    private double salary;

    private OfferStatus status;

    private RecruiterResponse recruiter;

//    private List<ApplicationResponse> applications;
}
