package dbConnection;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import file.payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class PostCallValidation {

	@Test
	public void post() throws SQLException {
		RestAssured.baseURI = "https://reqres.in/";
		String response = given().log().all().header("x-api-key", "reqres_5e5feddb161f432298669b3dc4a852aa")
				.header("Content-type", "application/json").when().body(payloads.post()).post("api/users").then()
				.assertThat().statusCode(201).extract().response().asString();
		System.out.println(response);
		
		 // 2. Read API response
        JsonPath js = new JsonPath(response);
        String apiId = js.getString("id");
        String apiName = js.getString("name");
        String apiJob = js.getString("job");

        // 3. Connect to PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "postgres";
        String password = "rishi7196";

        try (Connection con =
                DriverManager.getConnection(url, user, password)) {

            // 4. Save API response in DB
            String insert = """
                INSERT INTO api_post_users (id, name, job)
                VALUES (?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                SET name = EXCLUDED.name,
                    job = EXCLUDED.job
                """;

            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setString(1, apiId);
                ps.setString(2, apiName);
                ps.setString(3, apiJob);
                ps.executeUpdate();
            }

            // 5. Fetch DB record and validate
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT id, name FROM api_post_users WHERE id = ?")) {

                ps.setString(1, apiId);
                           try (ResultSet rs = ps.executeQuery()) {
                    assertTrue(rs.next(), "Record not found in DB");

                    assertEquals(rs.getString("id"), apiId, "ID mismatch");
                    assertEquals(rs.getString("name"), apiName,
                            "Name mismatch");

                    System.out.println("PASS: ID and Name matched!");
                }
            }
        }
    }
}
	