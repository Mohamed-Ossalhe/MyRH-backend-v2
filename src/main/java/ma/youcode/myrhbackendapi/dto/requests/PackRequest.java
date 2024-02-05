package ma.youcode.myrhbackendapi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackRequest {
    private String name;
    private String description;
    private double price;
    private Integer numberOfOffers;
    private boolean isUnlimited;
    private boolean isRecommended;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
