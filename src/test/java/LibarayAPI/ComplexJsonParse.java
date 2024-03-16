import io.restassured.path.json.JsonPath;
public class ComplexJsonParse {

    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.coursesDetails());
        //System.out.println(js.prettyPrint());
//1. Print No of courses returned by API
        int coursesCount = js.getInt("courses.size()");
        System.out.println("coursesCount-> "+coursesCount);
//2. Print Purchase Amount
        int parchmentAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("parchmentAmount-> "+parchmentAmount);
//4. Print All course titles and their respective Prices
        for(int i=0; i<coursesCount; i++){
            String courseTitle = js.get("courses["+i+"].title");
            int coursePrices = js.getInt("courses["+i+"].price");
            System.out.println("courseTitle-> "+courseTitle+" & its Prices-> "+ coursePrices);
            //System.out.println("courseTitle-> "+coursePrices);
        }
//5. Print no of copies sold by RPA Course
        for(int i=0; i<coursesCount; i++){
            String courseTitle = js.get("courses["+i+"].title");
            if(courseTitle.equals("RPA")){
                int CourseSoldCount = js.get("courses["+i+"].copies");
                System.out.println("rpaCourseSoldCount-> "+CourseSoldCount);
                break;
            }
        }

//6. Verify if Sum of all Course prices matches with Purchase Amount
        int sumOfallCourses=0;
        for(int i=0; i<coursesCount; i++){
            sumOfallCourses+= js.getInt("courses["+i+"].price")*js.getInt("courses["+i+"].copies");
        }

        if(sumOfallCourses==parchmentAmount){
            System.out.println("Sum of all Course prices matches with Purchase Amount sumOfallCourses "+sumOfallCourses+" = parchmentAmount "+parchmentAmount);
        }
        else System.out.println("Sum of all Course prices does not match with Purchase Amount sumOfallCourses "+sumOfallCourses+" = parchmentAmount "+parchmentAmount);
    }
}
