package calculator;

import camp.nextstep.edu.missionutils.Console;

public class View {
    private final static String READ_STRING_EXPRESSION = "덧셈할 문자열을 입력해 주세요.\n";
    private final static String WRITE_RESULT_FORMAT = "결과 : %d\n";

    public static String readStringExpression() {
        try {
            System.out.print(READ_STRING_EXPRESSION);

            return Console.readLine();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void writeResult(int result) {
        System.out.printf((WRITE_RESULT_FORMAT), result);
    }

}
