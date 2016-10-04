package helpers;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by penko.yordanov on 31-May-16.
 */
public class TestDataFaker {
    private static Faker faker = new Faker(new Locale("en"));

    private static String getTitle() {
        return faker.lorem().sentence();
    }
    private static String getDescription() {
        return faker.lorem().sentence(10);
    }


    private static String getPrice() {
        return Integer.toString(faker.number().numberBetween(1,2000000));
    }

    private static String getCategory(){
        String[] categories={"Sell","Wanted","Swap","Give away","Rent"};
        return categories[faker.number().numberBetween(0,4)];
    }

    private static String getcondition(){
        String[] conditions={"New","Used"};
        return conditions[faker.number().numberBetween(0,1)];
    }

    private static String getEmail(String firstName, String lastName){
      return firstName.toLowerCase()+"."+lastName.toLowerCase()+"@yahoo.com";
    }

    private static String getFirstName(){
        return faker.name().firstName();
    }

    private static String getLastName(){
        return faker.name().lastName();
    }

    private static String getGender(){
        String[] gender={"Male","Female"};
        return gender[faker.number().numberBetween(0,1)];
    }

    private static String getPhone(){
        return Long.toString(faker.number().randomNumber(10,true));
    }
    private static String getAddress(){
        int num=faker.number().numberBetween(1,196);
        return "bul. \"Aleksandar Stamboliyski\" "+num+", Sofia, Bulgaria";
    }

    private static String getBirthDate(){
        Random random = new Random();
        int minDay = (int) LocalDate.of(1940, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2015, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return randomBirthDate.toString();
    }

    public static String getChatMessage(){
        return faker.lorem().sentence();
    }

    public static Map<String, String> getAdValues() {
        Map<String, String> returnValue = new HashMap<>();
        returnValue.put("title", getTitle());
        returnValue.put("category",getCategory());
        returnValue.put("description",getDescription());
        returnValue.put("price",getPrice());
        returnValue.put("condition",getcondition());
        return returnValue;

    }

    public static Map<String, String> getReistrationFormValues() {
        Map<String, String> returnValue = new HashMap<>();
        String firstName=getFirstName();
        String lastName=getLastName();
//        returnValue.put("firstName", "John");
        returnValue.put("firstName", firstName);
        returnValue.put("lastName",lastName);
        returnValue.put("gender",getGender());
        returnValue.put("birth",getBirthDate());
        returnValue.put("phone",getPhone());
        returnValue.put("email",getEmail(firstName,lastName));
        returnValue.put("address",getAddress());
        return returnValue;

    }

    public static void main(String[] args) {
        System.out.println(getLastName());
    }
}
