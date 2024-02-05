package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.PackRequest;
import ma.youcode.myrhbackendapi.dto.responses.PackResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

import java.util.List;

public interface PackService extends CrudInterface<PackResponse, PackRequest, String> {
}
