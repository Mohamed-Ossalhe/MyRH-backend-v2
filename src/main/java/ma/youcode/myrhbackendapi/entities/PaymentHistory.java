package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.youcode.myrhbackendapi.enums.PaymentStatus;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private double amount;
    private String paymentMethod;
    private String transaction;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String currency;
    private String description;
    private String receiptUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
