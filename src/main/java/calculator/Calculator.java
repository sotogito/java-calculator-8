package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    private static final Pattern CUSTOM_DELIMITER_PATTERN = Pattern.compile("^//(.)\\\\n(.*)$");
    private static final List<String> BASIC_DELIMITERS = List.of(",", ";");
    private static final String ESCAPE_CHARACTER = "\\";
    private static final List<String> META_CHARACTERS = new ArrayList<>(
            List.of("*", "^", "$", ".", "+", "?", "|", "\\", "[", "]", "{", "}", "(", ")"));

    public void calculate() {
        String stringExpression = View.readStringExpression();
        int result = getResult(stringExpression);

        View.writeResult(result);
    }

    private int getResult(String stringExpression) {
        if (stringExpression != null && stringExpression.isEmpty()) {
            return 0;
        }
        List<String> delimiters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        Matcher matcher = CUSTOM_DELIMITER_PATTERN.matcher(stringExpression);

        if (matcher.matches()) {
            String customDelimiter = matcher.group(1);
            stringExpression = matcher.group(2);

            if (META_CHARACTERS.contains(customDelimiter)) {
                customDelimiter = ESCAPE_CHARACTER + customDelimiter;
            }
            delimiters = List.of(customDelimiter);
        } else {
            delimiters = List.of(",", ";");
        }

        if (stringExpression.contains("-") || stringExpression.contains(".")) {
            throw new IllegalArgumentException();
        }

        String[] splitNumbers = stringExpression.split(String.join("|", delimiters));
        for (String split : splitNumbers) {
            try {
                numbers.add(Integer.parseInt(split));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

}
