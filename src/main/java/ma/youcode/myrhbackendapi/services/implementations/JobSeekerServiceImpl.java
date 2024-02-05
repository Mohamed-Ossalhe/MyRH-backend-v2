package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.JobSeekerRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobSeekerResponse;
import ma.youcode.myrhbackendapi.entities.JobSeeker;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.JobSeekerRepository;
import ma.youcode.myrhbackendapi.services.JobSeekerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;
    private final ModelMapper mapper;

    @Override
    public List<JobSeekerResponse> getAll() {
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();
        if (jobSeekerList.isEmpty()) throw new ResourceNotFoundException("No Job Seekers Found");
        return jobSeekerList.stream().map(jobSeeker -> mapper.map(jobSeeker, JobSeekerResponse.class)).toList();
    }

    @Override
    public Page<JobSeekerResponse> getAll(Pageable pageable) {
        Page<JobSeeker> jobSeekerPage = jobSeekerRepository.findAll(pageable);
        if (jobSeekerPage.isEmpty()) throw new ResourceNotFoundException("No Job Seekers Found");
        return jobSeekerPage.map(jobSeeker -> mapper.map(jobSeeker, JobSeekerResponse.class));
    }

    @Override
    public Optional<JobSeekerResponse> find(UUID id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No JobSeeker Found with ID: " + id));
        return Optional.of(mapper.map(jobSeeker, JobSeekerResponse.class));
    }

    @Override
    public Optional<JobSeekerResponse> create(JobSeekerRequest jobSeekerRequest) {
        JobSeeker jobSeeker = mapper.map(jobSeekerRequest, JobSeeker.class);
        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);
        return Optional.of(mapper.map(savedJobSeeker, JobSeekerResponse.class));
    }

    @Override
    public Optional<JobSeekerResponse> update(JobSeekerRequest jobSeekerRequest, UUID id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No JobSeeker Found with ID: " + id));
        jobSeekerRequest.setId(jobSeeker.getId());
        JobSeeker jobSeekerToUpdate = mapper.map(jobSeekerRequest, JobSeeker.class);
        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeekerToUpdate);
        return Optional.of(mapper.map(savedJobSeeker, JobSeekerResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No JobSeeker Found with ID: " + id));
        jobSeekerRepository.delete(jobSeeker);
        return true;
    }
}
