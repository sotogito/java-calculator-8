package calculator;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private final DelimiterResolver delimiterResolver;

    public Calculator() {
        this.delimiterResolver = new DelimiterResolver();
    }


    public Double calculate(String stringExpression) {
        if (stringExpression != null && stringExpression.isEmpty()) {
            return 0.0;
        }
        CalculatedValueDto calculatedValue = delimiterResolver.resolve(stringExpression);
//        validatePositiveNumber(calculatedValue);

        List<Double> numbers = getNumbers(calculatedValue);

        return numbers.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private List<Double> getNumbers(CalculatedValueDto calculatedValue) {
        List<Double> numbers = new ArrayList<>();

        String delimiter = calculatedValue.delimiterRegex();
        String stringExpression = calculatedValue.expression();

        String[] splitNumbers = stringExpression.split(delimiter);
        for (String split : splitNumbers) {
            try {
//                numbers.add(Double.parseDouble(split));
                double number = Double.parseDouble(split);
                if (number == 0.0) {
                    throw new IllegalArgumentException("양수만 계산 가능합니다.");
                }
                numbers.add(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("잘못된 구분자를 사용했습니다.");
            }
        }
        return numbers;
    }

    private void validatePositiveNumber(CalculatedValueDto calculatedValue) {
        if (calculatedValue.isCustomDelimiter()) {
            String customDelimiter = calculatedValue.delimiterRegex();
            if (customDelimiter.equals("-")) {
                return;
            }
        }
        String expression = calculatedValue.expression();
        if (expression.contains("-")) {
            throw new IllegalArgumentException("양수만 계산 가능합니다.");
        }
    }

}
