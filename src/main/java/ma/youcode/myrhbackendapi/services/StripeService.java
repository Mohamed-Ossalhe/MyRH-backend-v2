package ma.youcode.myrhbackendapi.services;

import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import ma.youcode.myrhbackendapi.dto.requests.ChargeRequest;

public interface StripeService {
    public void init();

    public Charge charge(ChargeRequest request);

    public ChargeCreateParams createChargeParams(ChargeRequest request);
}
