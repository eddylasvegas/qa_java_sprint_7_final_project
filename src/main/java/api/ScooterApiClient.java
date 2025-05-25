package api;

import io.restassured.response.Response;
import models.Courier;
import models.Order;

import static io.restassured.RestAssured.given;

public class ScooterApiClient {
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String ORDER_PATH = "/api/v1/orders";

    public Response createCourier(Courier courier) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body(courier)
                .post(COURIER_PATH);
    }

    public Response loginCourier(Courier courier) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body(courier)
                .post(COURIER_PATH + "/login");
    }

    public Response deleteCourier(String id) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .delete(COURIER_PATH + "/" + id);
    }

    public Response createOrder(Order order) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body(order)
                .post(ORDER_PATH);
    }

    public Response getOrdersList() {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .get(ORDER_PATH);
    }

    public Response cancelOrder(String track) {
        return given()
                .spec(ApiConfig.getDefaultRequestSpec())
                .body("{\"track\": \"" + track + "\"}")
                .put(ORDER_PATH + "/cancel");
    }
}