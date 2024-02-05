package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.SubscriptionRequest;
import ma.youcode.myrhbackendapi.dto.responses.SubscriptionResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.SubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController implements ControllerInterface<SubscriptionRequest, SubscriptionResponse, String> {

    private final SubscriptionService subscriptionService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> get(@PathVariable String id) {
        Optional<SubscriptionResponse> subscription = subscriptionService.find(id);
        assert subscription.isPresent();
        return new ResponseEntity<>(subscription.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionResponse>> getAll() {
        return new ResponseEntity<>(subscriptionService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<SubscriptionResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(subscriptionService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<SubscriptionResponse> create(@Valid @RequestBody SubscriptionRequest subscriptionRequest) {
        Optional<SubscriptionResponse> subscription = subscriptionService.create(subscriptionRequest);
        assert subscription.isPresent();
        return new ResponseEntity<>(subscription.get(), HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<SubscriptionResponse> update(@Valid @RequestBody SubscriptionRequest subscriptionRequest, @PathVariable String id) {
        Optional<SubscriptionResponse> subscription = subscriptionService.update(subscriptionRequest, id);
        assert subscription.isPresent();
        return new ResponseEntity<>(subscription.get(), HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable String id) {
        Map<String, String> messages = new HashMap<>();
        if (subscriptionService.destroy(id)) messages.put("message", "Subscription Deleted Successfully");
        else messages.put("message", "Subscription Couldn't be Deleted");
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
