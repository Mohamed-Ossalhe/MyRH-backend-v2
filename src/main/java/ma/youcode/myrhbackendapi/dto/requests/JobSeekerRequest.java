package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobSeekerRequest extends UserRequest {
    private String identity;
    private MultipartFile resume;
}
