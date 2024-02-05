package ma.youcode.myrhbackendapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CloudinaryService {
    public String uploadFile(MultipartFile file);
    public File convertMultipartFileToFile(MultipartFile file) throws Exception;
}
