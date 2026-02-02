package Test;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.List;


public class NestedArray {
	
	@Test
	public void nested()
	{
		
		RestAssured.baseURI="https://reqres.in/";
		String res=given().log().all().header("Content-type","application/json")
		.when().get("/api/users?page=2")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(res);
				
		JsonPath js= new JsonPath(res);
		int str=js.get("total_pages");
		System.out.println(str);
		String id=js.getString("data.find{it.id==7}");
		String id8=js.getString("data.find{it.id==8}");
		System.out.println(id);
		System.out.println(id8);
				
	}

}
