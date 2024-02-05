package ma.youcode.myrhbackendapi.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.PackRequest;
import ma.youcode.myrhbackendapi.dto.responses.PackResponse;
import ma.youcode.myrhbackendapi.interfaces.ControllerInterface;
import ma.youcode.myrhbackendapi.services.PackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/packs")
public class PackController implements ControllerInterface<PackRequest, PackResponse, String> {

    private final PackService packService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PackResponse> get(@PathVariable String id) {
        Optional<PackResponse> packResponse = packService.find(id);
        assert packResponse.isPresent();
        return new ResponseEntity<>(packResponse.get(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<PackResponse>> getAll() {
        return new ResponseEntity<>(packService.getAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PackResponse>> getAllPaginated(Pageable pageable) {
        return new ResponseEntity<>(packService.getAll(pageable), HttpStatus.OK);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<PackResponse> create(@Valid @RequestBody PackRequest packRequest) {
        return null;
    }

    @Override
    @PatchMapping("/{id}/update")
    public ResponseEntity<PackResponse> update(@Valid @RequestBody PackRequest packRequest, @PathVariable String id) {
        return null;
    }

    @Override
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Map<String, String>> destroy(@PathVariable String id) {
        return null;
    }
}
