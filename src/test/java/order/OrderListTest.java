package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import steps.OrderTestSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    private final OrderTestSteps steps = new OrderTestSteps();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяет, что API возвращает список заказов")
    public void testGetOrdersList() {
        Response response = steps.getOrdersList();
        response
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}