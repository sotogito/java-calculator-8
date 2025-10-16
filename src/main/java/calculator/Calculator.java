package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final DelimiterResolver delimiterResolver;

    public Calculator() {
        this.delimiterResolver = new DelimiterResolver();
    }


    public Double calculate(String stringExpression) {
        if (stringExpression.isEmpty()) {
            return 0.0;
        }
        CalculatedValueDto calculatedValue = delimiterResolver.resolve(stringExpression);
        List<Double> numbers = getNumbers(calculatedValue);

        double result = numbers.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        validateValidNumber(result);

        return result;
    }

    private List<Double> getNumbers(CalculatedValueDto calculatedValue) {
        List<Double> numbers = new ArrayList<>();

        String delimiter = calculatedValue.delimiterRegex();
        String stringExpression = calculatedValue.expression();

        String[] splitNumbers = stringExpression.split(delimiter, -1);
        for (String split : splitNumbers) {
            try {
                double number = Double.parseDouble(split);
                validatePositiveNumber(number);

                numbers.add(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("잘못된 구분자를 사용했습니다.");
            }
        }
        return numbers;
    }

    private void validatePositiveNumber(double number) {
        if (number <= 0.0) {
            throw new IllegalArgumentException("양수만 계산 가능합니다.");
        }
    }

    private void validateValidNumber(double result) {
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new IllegalArgumentException("계산 가능한 범위를 초과했습니다.");
        }
    }

}
