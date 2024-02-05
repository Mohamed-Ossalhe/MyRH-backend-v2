package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.ApplicationRequest;
import ma.youcode.myrhbackendapi.dto.responses.ApplicationResponse;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

public interface ApplicationService extends CrudInterface<ApplicationResponse, ApplicationRequest, SeekerOfferId> {
}
