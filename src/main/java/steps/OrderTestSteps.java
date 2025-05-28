package steps;

import api.ScooterApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static org.hamcrest.Matchers.notNullValue;

public class OrderTestSteps {
    private final ScooterApiClient apiClient = new ScooterApiClient();

    @Step("Создать новый заказ")
    public Response createOrder(Order order) {
        return apiClient.createOrder(order);
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return apiClient.getOrdersList();
    }

    @Step("Отменить заказ")
    public void cancelOrder(String track) {
        apiClient.cancelOrder(track);
    }

    @Step("Проверить наличие номера заказа")
    public void verifyOrderTrackExists(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

}