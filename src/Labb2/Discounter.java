package Labb2;

import java.math.BigDecimal;

public interface Discounter {

    BigDecimal applyDiscount(BigDecimal amount);

    static Discounter noDiscount() {return amount -> amount.multiply(BigDecimal.valueOf(1));}

    static Discounter christmasDiscounter() {
        return amount -> amount.multiply(BigDecimal.valueOf(0.9));
    }

    static Discounter easterDiscounter() {
        return amount -> amount.multiply(BigDecimal.valueOf(0.8));
    }

}
