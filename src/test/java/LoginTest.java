import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    private Courier courier;

    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    private int courierId;

    @Before
    @Step("Создание курьера")
    public void setUp() {
        courier = Courier.generateCourier();
        client.createCourier(courier);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        var creds = CourierCredentials.fromCourier(courier);
        client.logIn(creds);
        if(courierId > 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccessfully(response);
        }
    }


    @Test
    @DisplayName("Успешная авторизацию курьера")
    public void testSuccessfullyLogin() {
        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    public void testLoginWithoutOneParameter() {
        var creds = CourierCredentials.fromCourier(courier);
        creds.setPassword("");
        ValidatableResponse loginResponse = client.logIn(creds);
        String message = check.loginFailedWithoutOneParameter(loginResponse);
        assert message.contains("Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Авторизация курьера под несуществующим логином")
    public void testLoginForNonExistentUser() {
        var creds = CourierCredentials.fromCourier(courier);
        System.out.println(creds.getLogin());
        creds.setLogin(creds.getLogin() + RandomStringUtils.randomAlphanumeric(10));
        ValidatableResponse loginResponse = client.logIn(creds);
        String message = check.testLoginFailedForNonExistentUser(loginResponse);
        assert message.contains("Учетная запись не найдена");
    }




}
