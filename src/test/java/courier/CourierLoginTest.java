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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private CourierTestSteps steps;
    private Courier courier;
    private String courierId;

    @Before
    public void setUp() {
        steps = new CourierTestSteps();
        courier = RandomDataGenerator.createRandomCourier();
        steps.createCourier(courier);
        Response loginResponse = steps.loginCourier(courier);
        courierId = loginResponse.jsonPath().getString("id");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяет, что курьер может авторизоваться с корректными данными")
    public void testSuccessfulCourierLogin() {
        Response response = steps.loginCourier(courier);
        response.then().statusCode(SC_OK);
        String actualId = response.jsonPath().getString("id");
        assertThat("ID курьера должен быть не пустым", actualId, notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверяет, что система возвращает ошибку при неверном пароле")
    public void testLoginWithWrongPassword() {
        Courier wrongPasswordCourier = new Courier(courier.getLogin(), "wrong_password", null);
        Response response = steps.loginCourier(wrongPasswordCourier);
        steps.verifyCourierCreationError(response, SC_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Проверяет, что система возвращает ошибку при неверном логине")
    public void testLoginWithWrongLogin() {
        Courier wrongLoginCourier = new Courier("nonexistent_login", courier.getPassword(), null);
        Response response = steps.loginCourier(wrongLoginCourier);
        steps.verifyCourierCreationError(response, SC_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверяет, что система возвращает ошибку при отсутствии пароля")
    public void testLoginWithoutPassword() {
        Courier noPasswordCourier = new Courier(courier.getLogin(), "", null);
        Response response = steps.loginCourier(noPasswordCourier);
        steps.verifyCourierCreationError(response, SC_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверяет, что система возвращает ошибку при отсутствии логина")
    public void testLoginWithoutLogin() {
        Courier noLoginCourier = new Courier("", courier.getPassword(), null);
        Response response = steps.loginCourier(noLoginCourier);
        steps.verifyCourierCreationError(response, SC_BAD_REQUEST, "Недостаточно данных для входа");
    }
}