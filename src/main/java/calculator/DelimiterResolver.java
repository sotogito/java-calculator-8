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
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(stringExpression);

        if (matcher.matches()) {
            String customDelimiter = matcher.group(1);
            stringExpression = matcher.group(2);

            validateDelimiterNotNumber(customDelimiter);
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

}
