package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierTestSteps;
import utils.RandomDataGenerator;

import static org.apache.http.HttpStatus.*;

public class CourierCreationTest {
    private CourierTestSteps steps;
    private Courier courier;
    private String courierId;

    @Before
    public void setUp() {
        steps = new CourierTestSteps();
        courier = RandomDataGenerator.createRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверяет, что курьер может быть успешно создан при корректных данных")
    public void testSuccessfulCourierCreation() {
        Response response = steps.createCourier(courier);
        steps.verifyCourierCreatedSuccessfully(response);

        Response loginResponse = steps.loginCourier(courier);
        courierId = loginResponse.jsonPath().getString("id");
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Проверяет, что нельзя создать двух одинаковых курьеров")
    public void testDuplicateCourierCreation() {
        steps.createCourier(courier);
        Response loginResponse = steps.loginCourier(courier);
        courierId = loginResponse.jsonPath().getString("id");

        Response duplicateResponse = steps.createCourier(courier);
        steps.verifyCourierCreationError(duplicateResponse, SC_CONFLICT, "Этот логин уже используется. Попробуйте другой.");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверяет, что нельзя создать курьера без указания логина")
    public void testCourierCreationWithoutLogin() {
        courier.setLogin(null);
        Response response = steps.createCourier(courier);
        steps.verifyCourierCreationError(response, SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи");
    }


}