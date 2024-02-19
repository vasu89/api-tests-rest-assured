package training;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class APItest2 {
	
	@Test
	public void test1() throws IOException {
		String endpoint = "/endpoint";
		//GET
		given().when().get(endpoint).then().assertThat().statusCode(200).log().body();
		
		//GET with query paramjente
		given().queryParam("id", 2)
		.when().get(endpoint).then().assertThat().statusCode(200).log().body().and().log().headers()
		.body("records.size()", greaterThan(0));
		
		//POST
		File file = new File("/api-testing/testfile.json");
		String json = Files.readString(Paths.get(file.getAbsolutePath()));
		
		given().body(json).when().post(endpoint).then().assertThat().statusCode(200).log().body();
		
	}

}
