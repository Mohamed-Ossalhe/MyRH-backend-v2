package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RecruiterRequest;
import ma.youcode.myrhbackendapi.dto.requests.VerificationCodeRequest;
import ma.youcode.myrhbackendapi.dto.responses.RecruiterResponse;
import ma.youcode.myrhbackendapi.dto.responses.VerificationCodeResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.RecruiterService;
import ma.youcode.myrhbackendapi.services.VerificationCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recruiters")
public class RecruiterController implements ControllerInterface<RecruiterRequest, RecruiterResponse, UUID> {

    private final RecruiterService recruiterService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponse> get(@PathVariable UUID id) {
        Optional<RecruiterResponse> recruiter = recruiterService.find(id);
        assert recruiter.isPresent();
        return new ResponseEntity<>(recruiter.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<RecruiterResponse>> getAll() {
        return new ResponseEntity<>(recruiterService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<RecruiterResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(recruiterService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<RecruiterResponse> create(@Valid @ModelAttribute RecruiterRequest recruiterRequest) {
        Optional<RecruiterResponse> recruiter = recruiterService.create(recruiterRequest);
        assert recruiter.isPresent();
        return new ResponseEntity<>(recruiter.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<RecruiterResponse> update(@Valid @RequestBody RecruiterRequest recruiterRequest, @PathVariable UUID id) {
        Optional<RecruiterResponse> recruiter = recruiterService.update(recruiterRequest, id);
        assert recruiter.isPresent();
        return new ResponseEntity<>(recruiter.get(), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable UUID id) {
        Map<String, String> messages = new HashMap<>();
        if (recruiterService.destroy(id)) messages.put("message", "Recruiter Deleted Successfully");
        else messages.put("message", "Recruiter couldn't be deleted");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
