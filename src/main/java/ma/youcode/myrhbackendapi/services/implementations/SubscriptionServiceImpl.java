package ma.youcode.myrhbackendapi.services.implementations;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.PaymentHistoryRequest;
import ma.youcode.myrhbackendapi.dto.requests.SubscriptionRequest;
import ma.youcode.myrhbackendapi.dto.responses.SubscriptionResponse;
import ma.youcode.myrhbackendapi.entities.Pack;
import ma.youcode.myrhbackendapi.entities.Recruiter;
import ma.youcode.myrhbackendapi.entities.Subscription;
import ma.youcode.myrhbackendapi.enums.SubscriptionStatus;
import ma.youcode.myrhbackendapi.exceptions.InActiveSubscriptionException;
import ma.youcode.myrhbackendapi.exceptions.ResourceAlreadyExistException;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.JobOfferRepository;
import ma.youcode.myrhbackendapi.repositories.PackRepository;
import ma.youcode.myrhbackendapi.repositories.RecruiterRepository;
import ma.youcode.myrhbackendapi.repositories.SubscriptionRepository;
import ma.youcode.myrhbackendapi.services.PaymentHistoryService;
import ma.youcode.myrhbackendapi.services.StripeService;
import ma.youcode.myrhbackendapi.services.SubscriptionService;
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
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final RecruiterRepository recruiterRepository;
    private final JobOfferRepository jobOfferRepository;
    private final PackRepository packRepository;
    private final StripeService stripeService;
    private final PaymentHistoryService paymentHistoryService;
    private final ModelMapper mapper;

    @Override
    public List<SubscriptionResponse> getAll() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.isEmpty()) throw new ResourceNotFoundException("No Subscriptions Found");
        return subscriptions.stream().map(subscription -> mapper.map(subscription, SubscriptionResponse.class)).toList();
    }

    @Override
    public Page<SubscriptionResponse> getAll(Pageable pageable) {
        Page<Subscription> subscriptions = subscriptionRepository.findAll(pageable);
        if (subscriptions.isEmpty()) throw new ResourceNotFoundException("No Subscriptions Found");
        return subscriptions.map(subscription -> mapper.map(subscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> find(String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        return Optional.of(mapper.map(subscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> create(SubscriptionRequest subscriptionRequest) {
        Recruiter recruiter = recruiterRepository.findRecruiterByEmail(subscriptionRequest.getRecruiter())
                .orElseThrow(() -> new ResourceNotFoundException("No Recruiter Found with email: " + subscriptionRequest.getRecruiter()));
        Pack pack = packRepository.findById(Utils.pareseStringToUUID(subscriptionRequest.getPack()))
                .orElseThrow(() -> new ResourceNotFoundException("No Pack Found with ID: " + subscriptionRequest.getPack()));

        Optional<Subscription> subscription = subscriptionRepository.findSubscriptionByRecruiter(recruiter);
        if (subscription.isPresent()) {
            System.out.println("total: " + !recruiterCanCreateMoreOffers(recruiter, subscription.get()));
            System.out.println("status: " + (subscription.get().getSubscriptionStatus() == SubscriptionStatus.ACTIVE));
            if (subscription.get().getSubscriptionStatus() == SubscriptionStatus.ACTIVE && !recruiterCanCreateMoreOffers(recruiter, subscription.get())) {
                subscription.get().setSubscriptionStatus(SubscriptionStatus.IN_ACTIVE);
                subscriptionRepository.save(subscription.get());
                throw new InActiveSubscriptionException("Your Pack is InActive, please Renew it");
            }else {
                throw new ResourceAlreadyExistException("You Already have a Subscription in this Plan, with Total Offers: " + subscription.get().getPack().getNumberOfOffers());
            }
        }

        Charge charge = stripeService.charge(subscriptionRequest.getChargeRequest());

        System.out.println(charge.toString());

        Subscription subscriptionToSave = mapper.map(subscriptionRequest, Subscription.class);
        subscriptionToSave.setPack(pack);
        subscriptionToSave.setRecruiter(recruiter);
        Subscription savedSubscription = subscriptionRepository.save(subscriptionToSave);

        PaymentHistoryRequest paymentHistoryRequest = PaymentHistoryRequest.builder()
                .amount((double) charge.getAmount() / 100)
                .currency(charge.getCurrency())
                .description(charge.getDescription())
                .paymentMethod(charge.getPaymentMethod())
                .paymentStatus(charge.getStatus().toUpperCase())
                .receiptUrl(charge.getReceiptUrl())
                .transaction(charge.getBalanceTransaction())
                .subscription(savedSubscription.getId().toString())
                .receiptUrl(charge.getReceiptUrl())
                .build();

        paymentHistoryService.create(paymentHistoryRequest);

        return Optional.of(mapper.map(savedSubscription, SubscriptionResponse.class));
    }

    @Override
    public Optional<SubscriptionResponse> update(SubscriptionRequest subscriptionRequest, String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        Subscription subscriptionToUpdate = mapper.map(subscriptionRequest, Subscription.class);
        subscriptionToUpdate.setId(subscription.getId());
        Subscription savedSubscription = subscriptionRepository.save(subscriptionToUpdate);
        return Optional.of(mapper.map(savedSubscription, SubscriptionResponse.class));
    }

    @Override
    public boolean destroy(String id) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + id));
        subscriptionRepository.delete(subscription);
        return true;
    }

    public boolean recruiterCanCreateMoreOffers(Recruiter recruiter, Subscription subscription) {
        Integer count = jobOfferRepository.countJobOffersByRecruiter(recruiter);
        System.out.println(count);
        return jobOfferRepository.countJobOffersByRecruiter(recruiter) < subscription.getPack().getNumberOfOffers();
    }
}
