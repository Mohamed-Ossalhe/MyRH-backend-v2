package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.JobSeekerRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobSeekerResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

import java.util.UUID;

public interface JobSeekerService extends CrudInterface<JobSeekerResponse, JobSeekerRequest, UUID> {
}
