package apiTests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;


public class testForSweatBand {

	public testForSweatBand() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void createProduct() throws IOException {
		String endpoint = "http://localhost:8888/api_testing/product/create.php";

		File file = new File("src/test/resources/sweatband.json");
        String json = Files.readString(Paths.get(file.getAbsolutePath()));
        
        
				given()
				.body(json)
				.when().post(endpoint)
				.then().assertThat().statusCode(201).log().body();
	
	}
	
	@Test
	public void updateProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/update.php";
		
		
	}

}
