package entity.p05BillsPaymentSystem;

import jakarta.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetail{
    private CardType cardType;
    private Integer expiration_month;
    private Integer expiration_year;

    public CreditCard() {
    }

    @Enumerated(EnumType.STRING)
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public Integer getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(Integer expiration_month) {
        this.expiration_month = expiration_month;
    }

    @Column(name = "expiration_year")
    public Integer getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(Integer expiration_year) {
        this.expiration_year = expiration_year;
    }
}
