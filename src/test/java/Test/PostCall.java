package Test;
import static io.restassured.RestAssured.*;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import file.payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PostCall {
	
	@Test
	public void post()
	{
		RestAssured.baseURI="https://reqres.in/";
		
		String response=given().log().all().
				header("Content-type","application/json").when().body(payloads.post())
		.post("api/users").then().assertThat().
		statusCode(201).extract().response().asString();
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
		String name=js.getString("name");
		System.out.println(name);
				
	}
	
	//get call
	@Test
	public void get()
	{
		RestAssured.baseURI="https://reqres.in/";
		String response=given().log().all().when().get("api/users?page=2").then().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
		List<Integer>allid=js.getList("data.id");
		System.out.println(allid);
		
		// fetech id or name together
	}
	@Test
	public void getAll()
	{
		RestAssured.baseURI="https://reqres.in/";
		String response=given().log().all().when()
				.get("api/users?page=2").then().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(response);
		
		JsonPath js= new JsonPath(response);
		List<Map<String, Object>> users = js.getList("data");
		for(Map<String, Object>user :users)
		{
			int id=(int) user.get("id");
			String name=(String) user.get("first_name");
			System.out.println("ID: "+id+" "+name);
		}
	}

}
