package calculator;

import java.util.ArrayList;
import java.util.List;

public enum BasicDelimiter {

    COMMA(","), COLON(":");

    private final String delimiter;

    BasicDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }


    public static String getDelimiterSplitRegexp() {
        List<String> regexps = new ArrayList<>();

        for (BasicDelimiter basicDelimiter : BasicDelimiter.values()) {
            regexps.add(basicDelimiter.delimiter);
        }
        return String.join("|", regexps);
    }

}
