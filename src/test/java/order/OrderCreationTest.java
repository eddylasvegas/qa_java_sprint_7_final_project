package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Order;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderTestSteps;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final OrderTestSteps steps = new OrderTestSteps();
    private final List<String> colors;
    private Response orderResponse;

    public OrderCreationTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвета самоката: {0}")
    public static Object[][] getColorData() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of()}
        };
    }

    @Test
    @DisplayName("Создание заказа с разными цветами самоката")
    @Description("Проверяет возможность создания заказа с различными вариантами выбора цвета")
    public void testOrderCreationWithDifferentColors() {
        Order order = new Order(
                "Иван",
                "Иванов",
                "ул. Ленина, 1",
                "1",
                "+79998887766",
                5,
                "2024-12-31",
                "Комментарий",
                colors
        );

        orderResponse = steps.createOrder(order);
        steps.verifyOrderTrackExists(orderResponse);
    }

    @After
    public void tearDown() {
        if (orderResponse != null) {
            String trackNumber = orderResponse.jsonPath().getString("track");
            if (trackNumber != null) {
                steps.cancelOrder(trackNumber);
            }
        }
    }
}