package Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import org.testng.annotations.Test;

public class SchemaValidation {
	
	@Test
	public void getSchema()
	{
		RestAssured.baseURI="https://reqres.in/";
		given().log().all()
		.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa")
		.when().get("api/users?page=2").then().assertThat()
		.statusCode(200).body(matchesJsonSchemaInClasspath("Schema.json"));
	
		
	}

}
