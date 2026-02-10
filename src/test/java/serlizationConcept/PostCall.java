package serlizationConcept;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;




public class PostCall {
	UserRequest userrequest = new UserRequest();
	
	@Test
	public void post()
	{
		RestAssured.baseURI="https://reqres.in";
		UserRequest request= new UserRequest();
		userrequest.setName("john");
		userrequest.setJob("QA Engineer");
		String res=given().log().all().contentType(ContentType.JSON)
				.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa")

		.body(userrequest).when().post("/api/users")
		.then().assertThat().statusCode(201).extract().response().asString();
		System.out.println(res);		
	}
	// extract id via extract method
	
	@Test
	public void getId()
	{
		RestAssured.baseURI="https://reqres.in";
		userrequest.setJob("SDET");
		userrequest.setName("priya kumar");
		String response=given().log().all().contentType(ContentType.JSON)
		.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa")
		.body(userrequest).when().post("api/users").then().assertThat().statusCode(201)
		.extract().response().asString();
		JsonPath js= new JsonPath(response);
		String ids=js.getString("id");
		System.out.println("ID is "+ids);
		// validate dynamic id in json response
		Assert.assertNotNull(ids,"ID should not be null");
		
	}

}
