import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.*;

public class СardDeliveryFormTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void happyPassCase() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//input[contains (@placeholder, 'Город' )]").setValue("Омск");
        $x("//label[contains(@data-test-id, 'agreement')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").sendKeys(Keys.COMMAND + "A");
        $x("//* [contains(@placeholder , 'Дата встречи')]").sendKeys(Keys.DELETE);
        $x("//* [contains(@placeholder , 'Дата встречи')]").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иванов Илья");
        $("[data-test-id='phone'] input").setValue("+79436578935");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(@class,'notification__content')]").should(appear, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(4,"dd.MM.yyyy") ), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    public void dropDownListCase() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $x("//input[contains (@placeholder, 'Город' )]").setValue("сан");
        $x("//* [contains(text() , 'Санкт-Петербург')]").click();
        $x("//span[contains(@class , 'input__icon')]").click();
        $x("//*[contains(@data-day, '1674687600000')]").click();
        $("[data-test-id='name'] input").setValue("Иванов Илья");
        $("[data-test-id='phone'] input").setValue("+79436578935");
        $x("//label[contains(@data-test-id, 'agreement')]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(@class,'notification__content')]").should(appear, Duration.ofMillis(15000));
        $x("//*[contains(@class,'notification__content')]").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(4,"dd.MM.yyyy")), Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }
}
