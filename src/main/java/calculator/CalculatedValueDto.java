package calculator;

public record CalculatedValueDto(
        boolean isCustomDelimiter,
        String delimiterRegex,
        String expression
) {
}
