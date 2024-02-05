package ma.youcode.myrhbackendapi.dto.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class RecruiterRequest extends UserRequest implements Serializable {
    private String address;

    private boolean isVerified = false;

    private MultipartFile image;
}
