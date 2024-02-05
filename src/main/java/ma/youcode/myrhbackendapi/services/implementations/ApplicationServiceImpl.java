package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.ApplicationRequest;
import ma.youcode.myrhbackendapi.dto.responses.ApplicationResponse;
import ma.youcode.myrhbackendapi.entities.Application;
import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.JobSeeker;
import ma.youcode.myrhbackendapi.entities.embeddable.SeekerOfferId;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.ApplicationRepository;
import ma.youcode.myrhbackendapi.repositories.JobOfferRepository;
import ma.youcode.myrhbackendapi.repositories.JobSeekerRepository;
import ma.youcode.myrhbackendapi.services.ApplicationService;
import ma.youcode.myrhbackendapi.services.CloudinaryService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobSeekerRepository jobSeekerRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ApplicationRepository applicationRepository;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper mapper;

    @Override
    public List<ApplicationResponse> getAll() {
        return null;
    }

    @Override
    public Page<ApplicationResponse> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<ApplicationResponse> find(SeekerOfferId seekerOfferId) {
        return Optional.empty();
    }

    @Override
    public Optional<ApplicationResponse> create(ApplicationRequest applicationRequest) {
        // TODO: check if jobSeeker exist
        Optional<JobSeeker> jobSeeker = jobSeekerRepository.findJobSeekerByIdentity(applicationRequest.getJobSeeker().getIdentity());
        if (jobSeeker.isEmpty()) {
            jobSeeker = Optional.of(mapper.map(applicationRequest.getJobSeeker(), JobSeeker.class));
        }
        // TODO: check if job offer exist
        JobOffer jobOffer = jobOfferRepository.findById(Utils.pareseStringToUUID(applicationRequest.getJobOffer().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + applicationRequest.getJobOffer().getId()));
        // TODO: Check if application already exist
        SeekerOfferId seekerOfferId = new SeekerOfferId(jobSeeker.get().getId(), jobOffer.getId());
        if (applicationRepository.findById(seekerOfferId).isPresent()) throw new ResourceAlreadyExistException("You Have already applied to this Offer");
        // TODO: upload file to cloudinary
        String resumeUrl = cloudinaryService.uploadFile(applicationRequest.getJobSeeker().getResume());
        // TODO: save jobseeker new info
        jobSeeker.get().setResume(resumeUrl);
        jobSeeker = Optional.of(jobSeekerRepository.save(jobSeeker.get()));
        // TODO: insert new Application
        Application application = new Application(seekerOfferId, applicationRequest.getMotivationLetter(), jobSeeker.get(), jobOffer);
        Application savedApplication = applicationRepository.save(application);
        return Optional.of(mapper.map(savedApplication, ApplicationResponse.class));
    }

    @Override
    public Optional<ApplicationResponse> update(ApplicationRequest applicationRequest, SeekerOfferId seekerOfferId) {
        return Optional.empty();
    }

    @Override
    public boolean destroy(SeekerOfferId seekerOfferId) {
        return false;
    }
}
