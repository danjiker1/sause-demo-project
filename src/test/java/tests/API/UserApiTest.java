package tests.API;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("API Tests")
@Feature("User Management")
@Owner("Daniil")
@DisplayName("Тесты для API управления пользователями")
public class UserApiTest extends BaseApiTest {

    @Disabled
    @Test
    @DisplayName("Получение питомца по ID")
    @Description("Проверяем, что можем получить питомца с ID=1")
    void getPetById(){

        int petId=1;

        given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + petId)
                .then()
                .statusCode(200)
                .body("id", equalTo(petId))
                .body("petId", equalTo(1));
    }

}
