package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.JobOfferRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobOfferResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.JobOfferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/joboffers")
public class JobOfferController implements ControllerInterface<JobOfferRequest, JobOfferResponse, UUID> {

    private final JobOfferService jobOfferService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferResponse> get(@PathVariable UUID id) {
        Optional<JobOfferResponse> jobOffer = jobOfferService.find(id);
        assert jobOffer.isPresent();
        return new ResponseEntity<>(jobOffer.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<JobOfferResponse>> getAll() {
        return new ResponseEntity<>(jobOfferService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<JobOfferResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(jobOfferService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<JobOfferResponse> create(@Valid @RequestBody JobOfferRequest jobOfferRequest) {
        Optional<JobOfferResponse> jobOffer = jobOfferService.create(jobOfferRequest);
        assert jobOffer.isPresent();
        return new ResponseEntity<>(jobOffer.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<JobOfferResponse> update(@Valid @RequestBody JobOfferRequest jobOfferRequest, @PathVariable UUID id) {
        Optional<JobOfferResponse> jobOffer = jobOfferService.update(jobOfferRequest, id);
        assert jobOffer.isPresent();
        return new ResponseEntity<>(jobOffer.get(), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable UUID id) {
        Map<String, String> messages = new HashMap<>();
        if (jobOfferService.destroy(id)) messages.put("message", "Job Offer Deleted Successfully");
        else messages.put("message", "Job Offer couldn't be Deleted");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
