import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimpleTest {

  @BeforeEach
  public void beforeEach() {
    Configuration.remote = System.getProperty("remote");
    Configuration.browserCapabilities.setCapability("enableVideo", true);
  }

  @Test
  public void test_1() {
    test_do();
  }

  @Test
  public void test_2() {
    test_do();
  }

  @Test
  public void test_3() {
    test_do();
  }

  @Test
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
  }

}
