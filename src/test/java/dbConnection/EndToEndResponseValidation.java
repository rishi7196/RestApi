package dbConnection;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class EndToEndResponseValidation {

	@Test
	public void dbValidation() throws SQLException {
		RestAssured.baseURI = "https://reqres.in";
		String res = given().log().all().header("ContentType", "application/json")
				.header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa").when().get("api/users?page=2").then()
				.assertThat().statusCode(200).extract().asString();
		System.out.println(res);

		 JsonPath js = new JsonPath(res);

	        // Validate ID 7
	        int id = js.getInt("data.find{it.id==7}.id");
	        String name = js.getString("data.find{it.id==7}.first_name");

	        // 2. Database connection
	        String url = "jdbc:postgresql://localhost:5432/testdb";
	        String user = "postgres";
	        String password = "rishi7196";

	        try (Connection con =
	                DriverManager.getConnection(url, user, password)) {

	            	    String query = "SELECT id, first_name FROM api_users WHERE id = ?";

	        	    try (PreparedStatement ps = con.prepareStatement(query)) {
	        	        ps.setInt(1, id);

	        	        try (ResultSet rs = ps.executeQuery()) {
	        	            assertTrue(rs.next(), "Record not found in DB");

	        	            assertEquals(rs.getInt("id"), id, "ID mismatch");
	        	            assertEquals(rs.getString("first_name"), name,
	        	                    "First name mismatch");

	        	            System.out.println("PASS: ID and First Name matched");
	        	        }
	        	    }
	        	}
	}
}