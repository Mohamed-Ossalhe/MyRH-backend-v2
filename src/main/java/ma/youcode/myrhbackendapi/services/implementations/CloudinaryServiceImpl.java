package ma.youcode.myrhbackendapi.services.implementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            File convertedFile = convertMultipartFileToFile(file);
            Map uploadedFile = cloudinary.uploader().upload(convertedFile, ObjectUtils.emptyMap());
            return uploadedFile.get("url").toString();
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public File convertMultipartFileToFile(MultipartFile file) throws Exception{
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();
        return convertedFile;
    }
}
