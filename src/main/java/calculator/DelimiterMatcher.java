package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DelimiterMatcher {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.)\\\\n(.*)$");
    private static final List<String> BASIC_DELIMITERS = List.of(",", ";");
    private static final String ESCAPE_CHARACTER = "\\";
    private static final List<String> META_CHARACTERS = new ArrayList<>(
            List.of("*", "^", "$", ".", "+", "?", "|", "\\", "[", "]", "{", "}", "(", ")"));


    public CalculatedValueDto match(String stringExpression) {
        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(stringExpression);

        if (matcher.matches()) {
            String customDelimiter = matcher.group(1);
            stringExpression = matcher.group(2);

            if (META_CHARACTERS.contains(customDelimiter)) {
                customDelimiter = getEscapedMetaDelimiter(customDelimiter);
            }

            return new CalculatedValueDto(customDelimiter, stringExpression);
        }
        String basicDelimiter = String.join("|", BASIC_DELIMITERS);
        return new CalculatedValueDto(basicDelimiter, stringExpression);
    }

    private String getEscapedMetaDelimiter(String metaCustomDelimiter) {
        return ESCAPE_CHARACTER + metaCustomDelimiter;
    }

}
