package ma.youcode.myrhbackendapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_seekers")
public class JobSeeker extends User {
    @Column(name = "identity_num",nullable = false, unique = true)
    private String identity;
    private String resume;

    @OneToMany(mappedBy = "jobSeeker", fetch = FetchType.LAZY)
    private List<Application> applications;
}
