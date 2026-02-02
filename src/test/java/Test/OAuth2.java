package Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class OAuth2 {
	
	public static void main(String[] args) {
		//get the token from auth server
		
		Response response=RestAssured.given().contentType(ContentType.URLENC)
		.formParam("grant_type", "client_Credentails")
		.formParam("client_id", "client_id")
		.formParam("client_secert", "clientsceret")
		.post("https://auth.example.com/oauth/token");
		String access_token=response.jsonPath().getString("access_token");
		
		//step 2 use this token in secured api call
		RestAssured.given().header("Authrization","bearer" +access_token)
		.body("abc").when().log().all()
		.post("abc").then().assertThat()
		.statusCode(201).extract().response().toString();
		
	}

}
