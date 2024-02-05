package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "packs")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private double price;
    private Integer numberOfOffers;
    private boolean isUnlimited;
    private boolean isRecommended;

    @OneToMany(mappedBy = "pack", fetch = FetchType.LAZY)
    private List<Subscription> subscriptions;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
