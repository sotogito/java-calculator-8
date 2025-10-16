package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final DelimiterResolver delimiterResolver;

    public Calculator() {
        this.delimiterResolver = new DelimiterResolver();
    }


    public int calculate(String stringExpression) {
        if (stringExpression != null && stringExpression.isEmpty()) {
            return 0;
        }
        CalculatedValueDto calculatedValue = delimiterResolver.resolve(stringExpression);

        validatePositiveNumber(calculatedValue.expression());
        List<Integer> numbers = getNumbers(calculatedValue);

        return numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Integer> getNumbers(CalculatedValueDto calculatedValue) {
        List<Integer> numbers = new ArrayList<>();

        String delimiter = calculatedValue.delimiterRegex();
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
