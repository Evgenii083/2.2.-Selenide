import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryFormTest {

    @Test
    public void happyPassCase() {
        Configuration.holdBrowserOpen = true;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        open("http://0.0.0.0:9999");
        $x("//input[contains (@placeholder, 'Город' )]").setValue("Омск");
        $x("//label[contains(@data-test-id, 'agreement')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").sendKeys(Keys.COMMAND+"A");
        $x("//* [contains(@placeholder , 'Дата встречи')]").sendKeys(Keys.DELETE);
        $x("//* [contains(@placeholder , 'Дата встречи')]").setValue(currentDate.plusDays(4).format(formatter));
        $("[data-test-id='name'] input").setValue("Иванов Илья");
        $("[data-test-id='phone'] input").setValue("+79436578935");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(@class,'notification__content')]").should(appear, Duration.ofMillis(15000));
    }
}
