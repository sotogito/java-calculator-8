package calculator;

import camp.nextstep.edu.missionutils.Console;
import java.text.DecimalFormat;

public class View {
    private final static String READ_STRING_EXPRESSION = "덧셈할 문자열을 입력해 주세요.\n";
    private final static String WRITE_RESULT_FORMAT = "결과 : ";
    private final static String DECIMAL_FORMAT = "#.######";

    public static String readStringExpression() {
        try {
            System.out.print(READ_STRING_EXPRESSION);

            return Console.readLine().trim();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    public static void writeResult(double result) {
        DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);

        System.out.println(WRITE_RESULT_FORMAT + df.format(result));
    }

}
