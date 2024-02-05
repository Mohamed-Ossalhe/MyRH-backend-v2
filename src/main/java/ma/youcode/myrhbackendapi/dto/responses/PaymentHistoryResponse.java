package ma.youcode.myrhbackendapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.youcode.myrhbackendapi.enums.PaymentStatus;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryResponse {
    private UUID id;
    private double amount;
    private String paymentMethod;
    private String transaction;
    private PaymentStatus paymentStatus;
    private String description;
    private String currency;
    private String receiptUrl;
}
