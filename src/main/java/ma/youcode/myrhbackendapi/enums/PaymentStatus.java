package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCEEDED("succeeded"),
    PENDING("pending"),
    FAILED("failed");

    private final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
