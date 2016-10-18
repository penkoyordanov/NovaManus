package helpers;

import com.jayway.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.Common.Browser;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class GenerateRanomAddresses {
    private WebDriver driver;
    private int initialCommentsCount;
    @BeforeMethod
    public void setUp() {
        Browser.initChrome();
        this.driver = Browser.eDriver();
        driver.get("http://www.mapcrunch.com/");
        driver.findElement(By.id("go-button")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.findElement(By.id("address")).getText());
    }
    @Test public void
    content_type_with_charset_returns_the_content_type_with_the_given_charset() {
        // When
        final String contentType = ContentType.JSON.withCharset("UTF-8");

        // Then
        assertThat(contentType, equalTo("application/json; charset=UTF-8"));
        System.out.println(ContentType.JSON.toString());
    }

    @Test public void
    content_type_with_java_charset_returns_the_content_type_with_the_given_charset() {
        // When
        final String contentType = ContentType.JSON.withCharset(Charset.forName("ISO-8859-1"));

        // Then
        assertThat(contentType, equalTo("application/json; charset=ISO-8859-1"));
    }

    @Test public void
    content_type_matches_expected_content_type_using_ignore_case() {
        // Given
        final String expected = "appliCatIon/JSON";

        // When
        boolean matches = ContentType.JSON.matches(expected);

        // Then
        assertThat(matches, is(true));
    }

    @Test public void
    content_type_doesnt_match_when_expected_content_type_is_not_equal_to_actual() {
        // Given
        final String expected = "application/json2";

        // When
        boolean matches = ContentType.JSON.matches(expected);

        // Then
        assertThat(matches, is(false));
    }

    @Test public void
    content_type_doesnt_match_when_expected_content_type_is_null() {
        // Given
        final String expected = null;

        // When
        boolean matches = ContentType.JSON.matches(expected);

        // Then
        assertThat(matches, is(false));
    }
   /* @Test
    public void getAdress() throws InterruptedException {




        eDriver.get("http://www.mapcrunch.com/");
       *//* eDriver.findElement(By.id("options-button")).click();
        eDriver.findElement(By.linkText("Europe")).click();
        Thread.sleep(1000);
        eDriver.findElement(By.id("cities")).click();
        Thread.sleep(1000);
        eDriver.findElement(By.id("options-button")).click();
        Thread.sleep(1000);
        eDriver.findElement(By.id("go-button")).click();
        System.out.println(eDriver.findElement(By.id("info-address-first")).getText());*//*
        eDriver.findElement(By.id("go-button")).click();
        Thread.sleep(2000);
        System.out.println(eDriver.findElement(By.id("address")).getText());


        *//*for (int i = 0; i < 100; i++) {
            Thread.sleep(2000);
            eDriver.findElement(By.id("go-button")).click();
            Thread.sleep(2000);
            System.out.println(eDriver.findElement(By.id("address")).getText());
        }*//*


    }*/
}
