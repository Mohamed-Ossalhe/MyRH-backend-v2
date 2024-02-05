package ma.youcode.myrhbackendapi.enums;

import lombok.Getter;

@Getter
public enum Access {
    USER("user"),
    RECRUITER("recruiter"),
    AGENT("agent");

    private final String access;

    Access(String access) {
        this.access = access;
    }
}