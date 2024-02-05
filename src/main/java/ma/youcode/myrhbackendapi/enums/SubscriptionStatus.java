package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum SubscriptionStatus {
    ACTIVE("active"),
    IN_ACTIVE("inactive");

    private final String status;

    SubscriptionStatus(String status) {
        this.status = status;
    }
}
