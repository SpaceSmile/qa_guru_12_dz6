package ru.kinopoisk;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchFilmsTests {

    @ValueSource(strings = {
            "The Batman",
            "Spider-Man"
    })

    @ParameterizedTest(name = "Проверка поиска фильмов на сатйе kinopoisk по слову {0}")
    void kinoSearchTest(String testData) {
        Selenide.open("https://www.kinopoisk.ru");
        $("[name=kp_query]").setValue(testData);
        $("button[type='submit']").click();
        $$(".search_results")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }
    @CsvSource(value = {
            "The Batman, Бэтмен 2022",
            "Spider-Man, Человек-паук: Нет пути домой 2021"
    })
    @ParameterizedTest(name = "Проверка поиска фильмов на сатйе kinopoisk по слову {0}, ожидаем результат: {1}")
    void kinoSearchComplexTest(String testData) {
        Selenide.open("https://www.kinopoisk.ru");
        $("[name=kp_query]").setValue(testData);
        $("button[type='submit']").click();
        $$(".search_results")
                .find(Condition.text(testData))
                .shouldBe(visible);
    }
    static Stream<Arguments> methodSourceKinoSearchComplexTest() {
        return Stream.of(
                Arguments.of("The Batman", "Бэтмен 2022"),
                Arguments.of("Spider-Man", "Человек-паук: Нет пути домой 2021")
        );
    }

    @MethodSource()
    @ParameterizedTest
    void methodSourceKinoSearchComplexTest(String testData, String result) {
        Selenide.open("https://www.kinopoisk.ru");
        $("[name=kp_query]").setValue(testData);
        $("button[type='submit']").click();
        $$(".search_results")
                .find(Condition.text(result))
                .shouldBe(visible);
    }

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }
}
