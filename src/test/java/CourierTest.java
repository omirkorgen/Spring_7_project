import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.courier.Courier;
import org.example.courier.CourierChecks;
import org.example.courier.CourierClient;
import org.example.courier.CourierCredentials;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();
    private int courierId;

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if(courierId > 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccessfully(response);
        }
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCreateCourier() {
        var courier = Courier.generateCourier();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void testCreationFails() {
        var courier = Courier.generateCourier();
        courier.setPassword(null);

        ValidatableResponse createResponce = client.createCourier(courier);
        String message = check.creationFailed(createResponce);
        assert message.contains("Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void testCreateTwoIdenticalCouriers(){
        var courier = Courier.generateCourier();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        ValidatableResponse createResponse2 = client.createCourier(courier);
        String message = check.creationTwoIdenticalCouriers(createResponse2);
        assert message.contains("Этот логин уже используется");

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }



}
