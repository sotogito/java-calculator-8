package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DelimiterResolverTest {
    private DelimiterResolver delimiterResolver = new DelimiterResolver();

    @Test
    void 기본_구분자_사용() {
        String stringExpression = "1,2:3";

        CalculatedValueDto actual = delimiterResolver.resolve(stringExpression);
        CalculatedValueDto expected = new CalculatedValueDto(
                false,
                ",|:",
                "1,2:3"
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 커스텀_구분자_사용() {
        String stringExpression = "//@\\n1@2@3";

        CalculatedValueDto actual = delimiterResolver.resolve(stringExpression);
        CalculatedValueDto expected = new CalculatedValueDto(
                true,
                "@",
                "1@2@3"
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("커스텀 구분자로 Length가 2이상인 문자열을 사용할 경우 커스텀 구분자 정규식에 매치되지 않음")
    void 커스텀_구분자_문자열_사용() {
        String stringExpression = "//%^\\n1%^2";

        CalculatedValueDto actual = delimiterResolver.resolve(stringExpression);
        CalculatedValueDto expected = new CalculatedValueDto(
                false,
                ",|:",
                "//%^\\n1%^2"
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("메타 문자 사용시 이스케이프 문자 처리")
    void 커스텀_구분자_메타_문자_사용() {
        String stringExpression = "//^\\n1^2^3";

        CalculatedValueDto actual = delimiterResolver.resolve(stringExpression);
        CalculatedValueDto expected = new CalculatedValueDto(
                true,
                "\\^",
                "1^2^3"
        );

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "//1\\n213",
            "//.\\n1.2.3"
    })
    void 커스텀_구분자_사용_불가_문자_사용_예외_테스트(String input) {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> delimiterResolver.resolve(input))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

}
