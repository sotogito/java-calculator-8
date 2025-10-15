package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final DelimiterMatcher delimiterMatcher;

    public Calculator() {
        this.delimiterMatcher = new DelimiterMatcher();
    }


    public void calculate() {
        String stringExpression = View.readStringExpression();
        int result = getResult(stringExpression);

        View.writeResult(result);
    }

    private int getResult(String stringExpression) {
        if (stringExpression != null && stringExpression.isEmpty()) {
            return 0;
        }
        CalculatedValueDto calculatedValue = delimiterMatcher.match(stringExpression);
        stringExpression = calculatedValue.expression();

        validatePositiveNumber(stringExpression);
        List<Integer> numbers = getNumbers(calculatedValue);

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    private List<Integer> getNumbers(CalculatedValueDto calculatedValue) {
        List<Integer> numbers = new ArrayList<>();

        String delimiter = calculatedValue.delimiter();
        String stringExpression = calculatedValue.expression();

        String[] splitNumbers = stringExpression.split(delimiter);
        for (String split : splitNumbers) {
            try {
                numbers.add(Integer.parseInt(split));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        return numbers;
    }

    private void validatePositiveNumber(String stringExpression) {
        if (stringExpression.contains("-") || stringExpression.contains(".")) {
            throw new IllegalArgumentException();
        }
    }

}
