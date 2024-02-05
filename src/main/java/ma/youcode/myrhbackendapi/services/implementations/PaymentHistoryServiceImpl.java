package ma.youcode.myrhbackendapi.services.implementations;

import lombok.RequiredArgsConstructor;
import ma.youcode.myrhbackendapi.dto.requests.PaymentHistoryRequest;
import ma.youcode.myrhbackendapi.dto.responses.PaymentHistoryResponse;
import ma.youcode.myrhbackendapi.entities.PaymentHistory;
import ma.youcode.myrhbackendapi.entities.Subscription;
import ma.youcode.myrhbackendapi.exceptions.ResourceNotFoundException;
import ma.youcode.myrhbackendapi.repositories.PaymentHistoryRepository;
import ma.youcode.myrhbackendapi.repositories.SubscriptionRepository;
import ma.youcode.myrhbackendapi.services.PaymentHistoryService;
import ma.youcode.myrhbackendapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper mapper;

    @Override
    public List<PaymentHistoryResponse> getAll() {
        List<PaymentHistory> paymentHistories = paymentHistoryRepository.findAll();
        if (paymentHistories.isEmpty()) throw new ResourceNotFoundException("No Payments History Found");
        return paymentHistories.stream().map(paymentHistory -> mapper.map(paymentHistory, PaymentHistoryResponse.class)).toList();
    }

    @Override
    public Page<PaymentHistoryResponse> getAll(Pageable pageable) {
        Page<PaymentHistory> paymentHistories = paymentHistoryRepository.findAll(pageable);
        if (paymentHistories.isEmpty()) throw new ResourceNotFoundException("No Payments History Found");
        return paymentHistories.map(paymentHistory -> mapper.map(paymentHistory, PaymentHistoryResponse.class));
    }

    @Override
    public Optional<PaymentHistoryResponse> find(String id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Payment History Found with ID: " + id));
        return Optional.of(mapper.map(paymentHistory, PaymentHistoryResponse.class));
    }

    @Override
    public Optional<PaymentHistoryResponse> create(PaymentHistoryRequest paymentHistoryRequest) {
        Subscription subscription = subscriptionRepository.findById(Utils.pareseStringToUUID(paymentHistoryRequest.getSubscription()))
                .orElseThrow(() -> new ResourceNotFoundException("No Subscription Found with ID: " + paymentHistoryRequest.getSubscription()));
        PaymentHistory paymentHistoryToSave = mapper.map(paymentHistoryRequest, PaymentHistory.class);
        paymentHistoryToSave.setSubscription(subscription);
        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistoryToSave);
        return Optional.of(mapper.map(savedPaymentHistory, PaymentHistoryResponse.class));
    }

    @Override
    public Optional<PaymentHistoryResponse> update(PaymentHistoryRequest paymentHistoryRequest, String id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Payment History Found with ID: " + id));
        PaymentHistory paymentHistoryToSave = mapper.map(paymentHistoryRequest, PaymentHistory.class);
        paymentHistoryToSave.setId(paymentHistory.getId());
        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(paymentHistoryToSave);
        return Optional.of(mapper.map(savedPaymentHistory, PaymentHistoryResponse.class));
    }

    @Override
    public boolean destroy(String id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(Utils.pareseStringToUUID(id))
                .orElseThrow(() -> new ResourceNotFoundException("No Payment History Found with ID: " + id));
        paymentHistoryRepository.delete(paymentHistory);
        return true;
    }
}
