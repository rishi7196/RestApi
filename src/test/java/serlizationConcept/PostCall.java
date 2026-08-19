package serlizationConcept;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import utlities.CommaonUtils;




public class PostCall {
	UserRequest userrequest = new UserRequest();
	
	@Test
	public void post()
	{
		RestAssured.baseURI="https://reqres.in";
		userrequest.setJob(CommaonUtils.getJobName());
		userrequest.setName(CommaonUtils.getRandomName());
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
		userrequest.setJob(CommaonUtils.getJobName());
		userrequest.setName(CommaonUtils.getRandomName());
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
