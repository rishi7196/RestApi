package Test;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

public class ValidateApiResponse {

	@Test
	public void Extract() {
		RestAssured.baseURI = "https://reqres.in";

		String res = given().log().all().header("ContentType", "application/json")
				.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa").when().get("api/users?page=2").then()
				.assertThat().statusCode(200).extract().asString();
		System.out.println(res);

	}

}
