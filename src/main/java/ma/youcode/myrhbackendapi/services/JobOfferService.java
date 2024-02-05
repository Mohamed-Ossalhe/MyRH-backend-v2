package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.JobOfferRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobOfferResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

import java.util.UUID;

public interface JobOfferService extends CrudInterface<JobOfferResponse, JobOfferRequest, UUID> {
}
