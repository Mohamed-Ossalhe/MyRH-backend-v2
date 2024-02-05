package ma.youcode.myrhbackendapi.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ma.youcode.myrhbackendapi.enums.Currency;

@Data
public class ChargeRequest {
    private String description;
    @NotNull(message = "amount cannot be null")
    @NotEmpty(message = "amount is required")
    private Long amount;
    @NotNull(message = "currency cannot be null")
    @NotEmpty(message = "currency is required")
    private Currency currency;
    @NotNull(message = "token cannot be null")
    @NotEmpty(message = "token is required")
    private String token;
    private String customer;
}
