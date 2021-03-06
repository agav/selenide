package integration;

import com.codeborne.selenide.ex.DialogTextMismatch;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.confirm;
import static com.codeborne.selenide.WebDriverRunner.supportsModalDialogs;
import static org.junit.Assert.fail;

public class AlertTest extends IntegrationTest {
  @Before
  public void openTestPage() {
    openFile("page_with_alerts.html");
  }

  @Test
  public void canSubmitAlertDialog() {
    $(By.name("username")).val("Greg");
    $(byValue("Alert button")).click();
    confirm("Are you sure, Greg?");
    $("#message").shouldHave(text("Hello, Greg!"));
    $("#container").shouldBe(empty);
  }

  @Test
  public void selenideChecksDialogText() {
    $(By.name("username")).val("Gregg");
    $(byValue("Alert button")).click();
    try {
      confirm("Good bye, Greg!");
    }
    catch (DialogTextMismatch expected) {
      return;
    }
    if (supportsModalDialogs()) {
      fail("Should throw DialogTextMismatch for mismatching text");
    }
  }
}
