package ma.youcode.myrhbackendapi.dto.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubscriptionResponse {
    private String subscriptionStatus;
    private String cancellationReason;
    private RecruiterResponse recruiter;
    private PackResponse pack;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
}
