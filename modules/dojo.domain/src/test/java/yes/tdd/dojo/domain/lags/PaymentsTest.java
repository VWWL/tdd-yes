package yes.tdd.dojo.domain.lags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentsTest {
    @Test
    void should_be_able_to_calculate_the_best_advice_giving_by_orders() {
        Payments payments = Payments.create(
            Order.of("AF514", 0, 5, 10),
            Order.of("C05", 3, 7, 14),
            Order.of("AF515", 5, 9, 7),
            Order.of("BA01", 6, 9, 8)
        );
        assertEquals(payments.findBest(), "The best choices is: AF514 BA01. The gain is 18");
    }
}