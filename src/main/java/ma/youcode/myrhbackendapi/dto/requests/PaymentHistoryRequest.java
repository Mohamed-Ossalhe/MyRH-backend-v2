package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentHistoryRequest {
    private double amount;
    private String paymentMethod;
    private String transaction;
    private String paymentStatus;
    private String description;
    private String currency;
    private String receiptUrl;
    private String subscription;
}
