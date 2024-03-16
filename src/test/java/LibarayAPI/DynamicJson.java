import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI="http://216.10.245.166";
        String addBookResponce=given().log().all().header("content-type","application/json")
                .body(Payload.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = ReusableMethods.js(addBookResponce);
        String bookID=js.get("ID");
        System.out.println(bookID);

    }
    @DataProvider(name = "BooksData")
    public Object [][] getData(){
       return new Object [][] {{"gscasa","6734"},{"asdve","8785"},{"werer","2345"}};
    }
}
