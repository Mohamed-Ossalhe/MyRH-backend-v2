package ma.youcode.myrhbackendapi.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackResponse {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private Integer numberOfOffers;
    private boolean isUnlimited;
    private boolean isRecommended;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
