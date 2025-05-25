package steps;

import api.ScooterApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;

import static org.hamcrest.Matchers.*;

public class CourierTestSteps {
    private final ScooterApiClient apiClient = new ScooterApiClient();

    @Step("Создать нового курьера")
    public Response createCourier(Courier courier) {
        return apiClient.createCourier(courier);
    }

    @Step("Авторизоваться курьером")
    public Response loginCourier(Courier courier) {
        return apiClient.loginCourier(courier);
    }

    @Step("Удалить курьера")
    public void deleteCourier(String id) {
        apiClient.deleteCourier(id);
    }

    @Step("Проверить успешное создание курьера")
    public void verifyCourierCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Step("Проверить ошибку при создании курьера")
    public void verifyCourierCreationError(Response response, int statusCode, String errorMessage) {
        response.then()
                .statusCode(statusCode)
                .body("message", equalTo(errorMessage));
    }
}