package ma.youcode.myrhbackendapi.enums;


import lombok.Getter;

@Getter
public enum Currency {
    USD("usd"),
    MAD("mad"),
    EUR("eur");

    private String currency;

    Currency(String currency) {
        this.currency = currency;
    }
}
