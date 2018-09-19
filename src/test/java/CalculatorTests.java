import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class CalculatorTests {
    private Calculator calculator = new Calculator(10, 20);

    @Test
    public void shouldCreateCalculatorInstance() {
        assertNotNull(calculator);
    }

    @Test
    public void shouldReturnCorrectSum() {
        assertEquals(30, calculator.calculateSum());
    }
}
