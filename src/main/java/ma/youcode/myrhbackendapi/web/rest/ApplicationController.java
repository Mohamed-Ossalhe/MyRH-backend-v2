package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.ApplicationRequest;
import ma.youcode.myrhbackendapi.dto.responses.ApplicationResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController implements ControllerInterface<ApplicationRequest, ApplicationResponse, UUID> {

    private final ApplicationService applicationService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> get(@PathVariable UUID id) {
        return null;
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<ApplicationResponse>> getAll() {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ApplicationResponse>> getAllPaginated(Pageable pageable) {
        return null;
    }


    @Override
    @PostMapping("/create")
    public ResponseEntity<ApplicationResponse> create(@Valid @ModelAttribute ApplicationRequest applicationRequest) {
        Optional<ApplicationResponse> applicationResponse = applicationService.create(applicationRequest);
        assert applicationResponse.isPresent();
        return new ResponseEntity<>(applicationResponse.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<ApplicationResponse> update(@Valid @RequestBody ApplicationRequest applicationRequest, @PathVariable UUID id) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable UUID id) {
        return null;
    }
}
