import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SimpleTest {

  @BeforeAll
  public static void beforeAll() {
    Configuration.remote = System.getProperty("remote", "http://localhost:4444/wd/hub");
    Configuration.browserCapabilities.setCapability("enableVideo", true);
  }

  @BeforeEach
  public void beforeEach(TestInfo testInfo) {
    Configuration.browserCapabilities.setCapability("name", testInfo.getDisplayName());
  }

  @Test
  public void test_1() {
    test_do();
  }

  //@Test
  public void test_2() {
    test_do();
  }

  //@Test
  public void test_3() {
    test_do();
  }

  //@Test
  public void test_4() {
    test_do();
  }

  public static void test_do() {
    open("http://ya.ru");
    sleep(5000);
    $("#text").shouldBe(visible).setValue("selenoid");
    sleep(5000);
    $(byText("Найти")).parent().shouldBe(visible).click();
    sleep(5000);
    $("div.content").shouldBe(visible);
    sleep(5000);
    screenshot("screen1");
    getWebDriver().manage().window().maximize();
    screenshot("screen2");
  }

  @AfterEach
  public void afterEach() {
    step("video");
    String sessionId = getSessionId();
    System.out.println(sessionId);
    closeWebDriver();
    //attachAllureVideo(sessionId);
    attachS3Video(sessionId);
  }

  @Step
  public void attachS3Video(String sessionId) {

  }

  @Step
  public void attachAllureVideo(String sessionId) {
    try {
      System.out.println("attachAllureVideo attachment");
      String selenoidUrl = Configuration.remote.replace("/wd/hub", "");
      URL videoUrl = new URL(selenoidUrl + "/video/" + sessionId + ".mp4");
      step("Video link: " + videoUrl.toString());
      InputStream is = getSelenoidVideo(videoUrl);
      addAttachment("Video", "video/mp4", is, "mp4");
      System.out.println("attachAllureVideo attachment 2");
      deleteSelenoidVideo(videoUrl);
    } catch (Exception e) {
      System.out.println("attachAllureVideo exception");
      e.printStackTrace();
    }
  }

  @Step
  public InputStream getSelenoidVideo(URL url) {
    int lastSize = 0;
    int exit = 2;
    for (int i = 0; i < 20; i++) {
      try {
        int size = Integer.parseInt(url.openConnection().getHeaderField("Content-Length"));
        System.out.println("Content-Length: " + size);
        System.out.println("i: " + i);
        if (size > lastSize) {
          lastSize = size;
          Thread.sleep(1500);
        } else if (size == lastSize) {
          System.out.println("Content-Length: " + size);
          System.out.println("exit: " + exit);
          exit--;
          Thread.sleep(1000);
        }
        if (exit < 0) {
          System.out.println("video ok!");
          return url.openStream();
        }
      } catch (Exception e) {
        System.out.println("getSelenoidVideo exception: " + e.getMessage());
        //e.printStackTrace();
      }
    }

    return null;
  }

  @Step
  public void deleteSelenoidVideo(URL url) {
    try {
      HttpURLConnection deleteConn = (HttpURLConnection) url.openConnection();
      deleteConn.setDoOutput(true);
      deleteConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      deleteConn.setRequestMethod("DELETE");
      deleteConn.connect();
      System.out.println("deleteSelenoidVideo response");
      System.out.println(deleteConn.getResponseCode());
      System.out.println(deleteConn.getResponseMessage());
      deleteConn.disconnect();
    } catch (IOException e) {
      System.out.println("deleteSelenoidVideo exception");
      e.printStackTrace();
    }
  }

  public String getSessionId() {
    return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
  }

}
