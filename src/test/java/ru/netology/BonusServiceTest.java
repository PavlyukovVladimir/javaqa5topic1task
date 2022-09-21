package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BonusServiceTest {
    private BonusService service;

    /** Метод генерирует последовательность тестовых данных,
     * <p> для параметрического теста */
    public static Stream<Arguments> testDataProvider() {
        return Stream.of(
                arguments("Нижняя граница, для зарегистрированных пользователей", 0L, true, 0L),
                arguments("Нижняя граница + 1, для зарегистрированных пользователей", 1L, true, 0L),

                arguments("Значение из интервала, для зарегистрированных пользователей", 10_000L, true, 300L),

                arguments("Ниже верхней границы, для зарегистрированных пользователей", 16_666L, true, 499L),
                arguments("Выше верхней границы, для зарегистрированных пользователей", 16_667L, true, 500L),

                arguments("Нижняя граница, для не зарегистрированных пользователей", 0L, false, 0L),
                arguments("Нижняя граница + 1, для не зарегистрированных пользователей", 1L, false, 0L),

                arguments("Значение из интервала, для не зарегистрированных пользователей", 10_000L, true, 100L),

                arguments("Верхняя граница - 1, для не зарегистрированных пользователей", 49_999L, false, 500L),
                arguments("Верхняя граница, для не зарегистрированных пользователей", 50_000L, false, 500L),
                arguments("Верхняя граница + 1, для не зарегистрированных пользователей", 50_001L, false, 500L)
        );
    }

    @BeforeEach
    void setUp(){
        // перед каждым тестом создаем тестируемый объект
        service = new BonusService();
    }

    @DisplayName("Параметризованный тест")
    @ParameterizedTest(name = "{index} - {0} registered is {2}, amount is {1}")
    @MethodSource("testDataProvider")
    void test(String description, long amount, boolean isRegistered, long expected) {

        // вызываем целевой метод:
        long actual = service.calculate(amount, isRegistered);

        // производим проверку (сравниваем ожидаемый и фактический):
        assertEquals(expected, actual);
    }
}