package ru.kinopoisk;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
            "JUnit, Человек-паук: Нет пути домой 2021"
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

    @AfterEach
    void close() {
        Selenide.closeWebDriver();
    }
}
