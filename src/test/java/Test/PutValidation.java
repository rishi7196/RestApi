package Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class PutValidation {

	@Test
	public void put() {
		RestAssured.baseURI = "https://reqres.in/";
		String res = given().log().all()
				.header("Content-type", "application/json")
				.when().get("/api/users?page=2")
				.then().assertThat().
				statusCode(200).body("page", equalTo(2))
				.extract().response().asString();
		System.out.println(res);
	}

}
