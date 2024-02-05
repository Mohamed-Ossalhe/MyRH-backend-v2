package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.JobSeekerRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobSeekerResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.JobSeekerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobseekers")
public class JobSeekerController implements ControllerInterface<JobSeekerRequest, JobSeekerResponse, UUID> {

    private final JobSeekerService jobSeekerService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerResponse> get(@PathVariable UUID id) {
        Optional<JobSeekerResponse> jobSeeker = jobSeekerService.find(id);
        assert jobSeeker.isPresent();
        return new ResponseEntity<>(jobSeeker.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<JobSeekerResponse>> getAll() {
        return new ResponseEntity<>(jobSeekerService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<JobSeekerResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(jobSeekerService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<JobSeekerResponse> create(@Valid @RequestBody JobSeekerRequest jobSeekerRequest) {
        Optional<JobSeekerResponse> jobSeeker = jobSeekerService.create(jobSeekerRequest);
        assert jobSeeker.isPresent();
        return new ResponseEntity<>(jobSeeker.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<JobSeekerResponse> update(@Valid @RequestBody JobSeekerRequest jobSeekerRequest, @PathVariable UUID id) {
        Optional<JobSeekerResponse> jobSeeker = jobSeekerService.update(jobSeekerRequest, id);
        assert jobSeeker.isPresent();
        return new ResponseEntity<>(jobSeeker.get(), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable UUID id) {
        Map<String, String> messages = new HashMap<>();
        if (jobSeekerService.destroy(id)) messages.put("message", "JobSeeker Deleted Successfully");
        else messages.put("message", "JobSeeker Couldn't be Deleted");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
