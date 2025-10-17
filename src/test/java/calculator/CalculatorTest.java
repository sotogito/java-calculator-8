package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class CalculatorTest {
    private Calculator calculator = new Calculator();


    @Test
    void 공백_수식_0_반환() {
        String stringExpression = "";

        double actual = calculator.calculate(stringExpression);
        double expected = 0;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 기본_구분자_사용() {
        String stringExpression = "1,2:3";

        double actual = calculator.calculate(stringExpression);
        double expected = 6;

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 커스텀_구분자_사용() {
        String stringExpression = "//-\\n1-2-3";

        double actual = calculator.calculate(stringExpression);
        double expected = 6;

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//*\\n1*2*3",
            "//^\\n1^2^3",
            "//\\\\n1\\2\\3",
    })
    void 커스텀_구분자_메타_문자_사용(String input) {
        double actual = calculator.calculate(input);
        double expected = 6;

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "0",
            "-1,2,3",
            "//#\\n1#-2"
    })
    void 양수가_아닌_수_수식_예외_테스트(String input) {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> calculator.calculate(input))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            ",1:2,3",
            "1:2:"
    })
    void 앞_뒤_구분자_사용_예외_테스트(String input) {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> calculator.calculate(input))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("Length가 2 이상인 문자 사용")
    void 커스텀_문자_문자열_사용_예외_테스트() {
        String stringExpression = "//@#\n1@#2";

        assertSimpleTest(() ->
                assertThatThrownBy(() -> calculator.calculate(stringExpression))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("NaN, Infinite")
    void 계산_가능한_범위_초과_결과값_예외_테스트() {
        String stringExpression = "1" + Double.MAX_VALUE;

        assertSimpleTest(() ->
                assertThatThrownBy(() -> calculator.calculate(stringExpression))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

}
