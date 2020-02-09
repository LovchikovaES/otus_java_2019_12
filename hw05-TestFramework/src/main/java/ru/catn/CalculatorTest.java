package ru.catn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class CalculatorTest {

    Calculator calc = new Calculator();

    @Before
    public void beforeFirst() {
        System.out.println("beforeFirst() has called.");
    }

    @Before
    public void beforeSecond() {
        System.out.println("beforeSecond() has called.");
    }

    @Before
    public void beforeThird() {
        System.out.println("beforeThird() has called.");
    }

    @After
    public void tearDown() {
        System.out.println("Test finished.");
    }

    @Test
    public void shouldReturn100OfMultiply10And10() {
        Assertions.assertEquals(100, calc.multiply(10, 10));
    }

    @Test
    public void shouldReturn200OfMultiply10And30() {
        Assertions.assertEquals(200, calc.multiply(10, 30));
    }

    @Test
    public void shouldThrowDivideByZero() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calc.divide(10, 0);
            }
        });
    }

    @Test
    public void shouldReturn2OfDivide100Into50() {
        Assertions.assertEquals(2, calc.divide(100,50));
    }
}
