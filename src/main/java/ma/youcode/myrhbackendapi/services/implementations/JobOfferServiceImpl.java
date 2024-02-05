package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.JobOfferRequest;
import ma.youcode.myrhbackendapi.dto.responses.JobOfferResponse;
import ma.youcode.myrhbackendapi.entities.JobOffer;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.Subscription;
import ma.youcode.myrhbackendapi.enums.SubscriptionStatus;
import ma.youcode.myrhbackendapi.exceptions.InActiveSubscriptionException;
import ma.youcode.myrhbackendapi.exceptions.NotAllowedToCreateOffersException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.exceptions.UnverifiedUserException;
import ma.youcode.myrhbackendapi.repositories.JobOfferRepository;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.repositories.SubscriptionRepository;
import ma.youcode.myrhbackendapi.services.JobOfferService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final RecruiterRepository recruiterRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper mapper;

    @Override
    public List<JobOfferResponse> getAll() {
        List<JobOffer> jobOffers = jobOfferRepository.findAll();
        if (jobOffers.isEmpty()) throw new ResourceNotFoundException("No Job Offers Found");
        return jobOffers.stream().map(jobOffer -> mapper.map(jobOffer, JobOfferResponse.class)).toList();
    }

    @Override
    public Page<JobOfferResponse> getAll(Pageable pageable) {
        Page<JobOffer> jobOfferPage = jobOfferRepository.findAll(pageable);
        if (jobOfferPage.isEmpty()) throw new ResourceNotFoundException("No Job Offers Found");
        return jobOfferPage.map(jobOffer -> mapper.map(jobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> find(UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        return Optional.of(mapper.map(jobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> create(JobOfferRequest jobOfferRequest) {
        Recruiter recruiter = recruiterRepository.findRecruiterByEmail(jobOfferRequest.getRecruiter())
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with email: " + jobOfferRequest.getRecruiter()));
        if (!recruiter.isVerified()) throw new UnverifiedUserException("unverified");

        Subscription subscription = subscriptionRepository.findSubscriptionByRecruiter(recruiter)
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found for recruiter: " + jobOfferRequest.getRecruiter()));

        if (subscription.getSubscriptionStatus() != SubscriptionStatus.ACTIVE) throw new InActiveSubscriptionException("Your Subscription is Not Active, Please Reactivate");

        if (!subscription.getPack().isUnlimited() && !recruiterCanCreateMoreOffers(recruiter, subscription)) throw new NotAllowedToCreateOffersException("Not Allowed to Create More Offers");

        JobOffer jobOffer = mapper.map(jobOfferRequest, JobOffer.class);
        jobOffer.setRecruiter(recruiter);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        return Optional.of(mapper.map(savedJobOffer, JobOfferResponse.class));
    }

    @Override
    public Optional<JobOfferResponse> update(JobOfferRequest jobOfferRequest, UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        jobOfferRequest.setId(String.valueOf(jobOffer.getId()));
        JobOffer jobOfferToUpdate = mapper.map(jobOfferRequest, JobOffer.class);
        JobOffer savedJobOffer = jobOfferRepository.save(jobOfferToUpdate);
        return Optional.of(mapper.map(savedJobOffer, JobOfferResponse.class));
    }

    @Override
    public boolean destroy(UUID id) {
        JobOffer jobOffer = jobOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Job Offer Found with ID: " + id));
        jobOfferRepository.delete(jobOffer);
        return true;
    }

    public boolean recruiterCanCreateMoreOffers(Recruiter recruiter, Subscription subscription) {
        Integer count = jobOfferRepository.countJobOffersByRecruiter(recruiter);
        System.out.println(count);
        return jobOfferRepository.countJobOffersByRecruiter(recruiter) < subscription.getPack().getNumberOfOffers();
    }
}
