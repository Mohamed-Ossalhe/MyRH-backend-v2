package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.RecruiterRequest;
import ma.youcode.myrhbackendapi.dto.responses.RecruiterResponse;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.exceptions.SomethingWentWrongException;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.services.CloudinaryService;
import ma.youcode.myrhbackendapi.services.RecruiterService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RecruiterServiceImpl implements RecruiterService {

    private final RecruiterRepository recruiterRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper mapper;

    @Override
    public List<RecruiterResponse> getAll() {
        List<Recruiter> recruiters = recruiterRepository.findAll();
        if (recruiters.isEmpty())
            throw new ResourceNotFoundException("No Recruiters Found");
        return recruiters.stream().map(recruiter -> mapper.map(recruiter, RecruiterResponse.class)).toList();
    }

    @Override
    public Page<RecruiterResponse> getAll(Pageable pageable) {
        Page<Recruiter> recruiterPage = recruiterRepository.findAll(pageable);
        if (recruiterPage.isEmpty())
            throw new ResourceNotFoundException("No Recruiters Found");
        return recruiterPage.map(recruiter -> mapper.map(recruiter, RecruiterResponse.class));
    }

    @Override
    public Optional<RecruiterResponse> find(UUID id) {
        Optional<Recruiter> recruiter = recruiterRepository.findById(id);
        if (recruiter.isEmpty())
            throw new ResourceNotFoundException("No Recruiter Found with ID: " + id);
        return Optional.of(mapper.map(recruiter.get(), RecruiterResponse.class));
    }

    @Override
    public Optional<RecruiterResponse> create(RecruiterRequest recruiterRequest) {
        try {
            // upload image to cloudinary
            CompletableFuture<String> futureImageUrl = CompletableFuture.supplyAsync(() -> cloudinaryService.uploadFile(recruiterRequest.getImage()));
            // create recruiter
            Recruiter recruiter = mapper.map(recruiterRequest, Recruiter.class);
            recruiter.setImage(futureImageUrl.get());
            Recruiter savedRecruiter = recruiterRepository.save(recruiter);
            return Optional.of(mapper.map(savedRecruiter, RecruiterResponse.class));
        }catch (Exception e) {
            throw new SomethingWentWrongException("Something went wrong while uploading image to cloudinary");
        }
    }

    @Override
    public Optional<RecruiterResponse> update(RecruiterRequest recruiterRequest, UUID id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + id));

        recruiterRequest.setId(recruiter.getId());
        Recruiter mappedRecruiter = mapper.map(recruiterRequest, Recruiter.class);
        Recruiter updatedRecruiter = recruiterRepository.save(mappedRecruiter);
        return Optional.of(mapper.map(updatedRecruiter, RecruiterResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with ID: " + id));
        recruiterRepository.delete(recruiter);
        return true;
    }

    @Override
    public Recruiter findRecruiterByEmail(String email) {
        return recruiterRepository.findRecruiterByEmail(email)
                .orElse(null);
    }
}
