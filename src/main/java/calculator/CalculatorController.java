package calculator;

public class CalculatorController {
    private final Calculator calculator;

    public CalculatorController() {
        this.calculator = new Calculator();
    }


    public void calculate() {
        String stringExpression = View.readStringExpression();
        double result = calculator.calculate(stringExpression);

        View.writeResult(result);
    }

}
