package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelimiterResolver {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.)\\\\n(.*)$");
    private static final String ESCAPE_CHARACTER = "\\";
    private static final List<String> META_CHARACTERS = new ArrayList<>(
            List.of("*", "^", "$", ".", "+", "?", "|", "\\", "[", "]", "{", "}", "(", ")"));


    public CalculatedValueDto resolve(String stringExpression) {
        Matcher customDelimiterMatcher = CUSTOM_DELIMITER_PATTERN.matcher(stringExpression);

        if (customDelimiterMatcher.matches()) {
            String customDelimiter = customDelimiterMatcher.group(1);
            stringExpression = customDelimiterMatcher.group(2);

            validateDelimiterNotNumber(customDelimiter);
            validateDelimiterNotDecimalPoint(customDelimiter);
            if (META_CHARACTERS.contains(customDelimiter)) {
                customDelimiter = getEscapedMetaDelimiter(customDelimiter);
            }

            return new CalculatedValueDto(true, customDelimiter, stringExpression);
        }
        String basicDelimiter = BasicDelimiter.getDelimiterSplitRegex();
        return new CalculatedValueDto(false, basicDelimiter, stringExpression);
    }

    private String getEscapedMetaDelimiter(String metaCustomDelimiter) {
        return ESCAPE_CHARACTER + metaCustomDelimiter;
    }

    private void validateDelimiterNotNumber(String delimiterRegex) {
        if (delimiterRegex.matches("\\d+")) {
            throw new IllegalArgumentException("커스텀 구분자로 숫자를 사용할 수 없습니다.");
        }
    }

    private void validateDelimiterNotDecimalPoint(String delimiterRegex) {
        if (delimiterRegex.equals(".")) {
            throw new IllegalArgumentException("커스텀 구분자로 소수점을 사용할 수 없습니다.");
        }
    }

}
