package api;

import io.qameta.allure.Step;
import models.LoginRequestModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.Specification.*;

public class Authorization {

    @Step("Receive Authorization Data via API")
    public static LoginResponseModel getAuthData(String userName,String userPassword){
        LoginRequestModel requestModel = new LoginRequestModel();
        requestModel.setUserName(userName);
        requestModel.setPassword(userPassword);

        return given()
                .spec(requestSpec)
                .body(requestModel)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(responseSpec200)
                .extract().as(LoginResponseModel.class);
    }
}
