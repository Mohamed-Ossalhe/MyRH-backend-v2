package ma.youcode.myrhbackendapi.services.implementations;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Source;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentSourceCollectionCreateParams;
import com.stripe.param.SourceCreateParams;
import jakarta.annotation.PostConstruct;
import ma.youcode.myrhbackendapi.dto.requests.ChargeRequest;
import ma.youcode.myrhbackendapi.exceptions.CustomStripeException;
import ma.youcode.myrhbackendapi.services.StripeService;
import ma.youcode.myrhbackendapi.utils.Env;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link StripeService} interface for handling payments with the Stripe API.
 * This service provides methods for charging users based on the provided {@link ChargeRequest}.
 *
 * @author Mohamed OSSALHE
 */
@Service
public class StripeServiceImpl implements StripeService {

    /**
     * initialize stripe api secret key on bean initialization
     */
    @Override
    @PostConstruct
    public void init() {
        Stripe.apiKey = Env.get("STRIPE_API_KEY");
    }

    /**
     * creates the Charge object used to request payment, and the Confirmation of the PaymentIntent
     * @param request The {@link ChargeRequest} containing details for the charge operation
     * @return object representing the result of the charge operation
     * @throws {@link CustomStripeException} If an error occurs during the Stripe API call.
     */
    @Override
    public Charge charge(ChargeRequest request) {
        try {
            ChargeCreateParams params = createChargeParams(request);
            return Charge.create(params);
        }catch(StripeException exception) {
            throw new CustomStripeException(exception.getMessage());
        }
    }

    /**
     * Creates a {@link ChargeCreateParams} Object for the Stripe charge based on the provided {@link ChargeRequest}.
     *
     * @param request The {@link ChargeRequest} containing details for the charge operation.
     * @return A {@link ChargeCreateParams} for the Stripe charge.
     */
    @Override
    public ChargeCreateParams createChargeParams(ChargeRequest request) {
        return ChargeCreateParams.builder()
                .setAmount(request.getAmount() * 100)
                .setCurrency(request.getCurrency().toString())
                .setDescription(request.getDescription())
                .setSource(request.getToken())
                .build();
    }
}
