package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum OfferStatus {
    ACCEPTED("accepted"),
    REFUSED("refused"),
    PENDING("pending");

    private final String status;

    OfferStatus(String status) {
        this.status = status;
    }
}
