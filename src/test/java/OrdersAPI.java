import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrdersAPI {

    private final String ORDERS = "/orders";
    private final String INGREDIENTS = "/ingredients";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Orders order, String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .baseUri(Config.BASE_URL)
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all();
    }

    @Step("Получить заказ")
    public ValidatableResponse getOrder(String token) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .baseUri(Config.BASE_URL)
                .get(ORDERS)
                .then().log().all();
    }

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredients() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(Config.BASE_URL)
                .get(INGREDIENTS)
                .then().log().all();
    }

}
