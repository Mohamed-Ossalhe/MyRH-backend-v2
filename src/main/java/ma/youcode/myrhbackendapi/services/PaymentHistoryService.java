package ma.youcode.myrhbackendapi.services;

import ma.youcode.myrhbackendapi.dto.requests.PaymentHistoryRequest;
import ma.youcode.myrhbackendapi.dto.responses.PaymentHistoryResponse;
import ma.youcode.myrhbackendapi.interfaces.CrudInterface;

public interface PaymentHistoryService extends CrudInterface<PaymentHistoryResponse, PaymentHistoryRequest, String> {
}
