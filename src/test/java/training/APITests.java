package training;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matcher;

import io.restassured.response.ValidatableResponse;
import models.Product;

import static io.restassured.RestAssured.given;

public class APITests {

	public APITests() {
		// TODO Auto-generated constructor stub
	}
	
	@Test
	public void getCategories() {
		String endpoint = "http://localhost:8888/api_testing/category/read.php";
		
		given()
			.when().get(endpoint)
			.then()
			.assertThat().statusCode(200);
	}
	
	@Test
	public void getProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/read.php";
		given()
			.when().get(endpoint)
			.then().assertThat()
			.statusCode(200)
			.log().headers()
				.header("Content-Type", equalTo("application/json; charset=UTF-8"))
				.body("records.size()", greaterThan(0))
				.body("records.id", everyItem(notNullValue()));
	}
	
	@Test
	public void getOneProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
				given().
					queryParam("id", 2).
				when().
					get(endpoint).
				then().assertThat()
				.statusCode(200)
				.body("id", equalTo("2"))
				.log().body();
	}
	
	@Test
	public void createProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/create.php";
		String body = """
			{
			    "name": "Water Bottle",
			    "description": "blue water bottle. Holds 64 ounces",
			    "price": 12,
			    "category_id": 3
			}""";
		ValidatableResponse response = 
				given()
				.body(body)
				.when()
				.post(endpoint)
				.then();
				response.log().body();
	}
	
	@Test
	public void updateProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/update.php";
		String body = """
				{
				"id": 19,
				"name": "Water Bottle",
			    "description": "blue water bottle. Holds 45 ounces",
			    "price": 16,
			    "category_id": 3
				}
				""";
		
		given()
			.body(body)
			.when().put(endpoint)
			.then().assertThat().statusCode(200).log().body();
	}
	
	@Test
	public void deleteProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/delete.php";
		String body = """
				{
				"id": 19
				}
				""";
		given()
			.body(body)
			.when().delete(endpoint)
			.then().assertThat().statusCode(200).and().log().body();
		
	}
	
	@Test
	public void createSerializableProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/create.php";
		Product product = new Product("LunchBox","rectangle shaped lunch box",20,3);
		given()
			.body(product)
			.when().post(endpoint)
			.then().assertThat().statusCode(201).log().body();
	}
	
	@Test
	public void getDeserializableProduct() {
		String endpoint = "http://localhost:8888/api_testing/product/read_one.php";
		
		Product expectedProduct = new Product(2,"Cross-Back Training Tank","The most awesome phone of 2013!",299.00,2,"Active Wear - Women");
		
		Product actualProduct = given().
			queryParam("id", 2).
		when().
			get(endpoint).as(Product.class);
		
		assertThat(expectedProduct, samePropertyValuesAs(actualProduct));
	}


}
