import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    public void happyPassCase() {
        $x("//input[contains (@placeholder, 'Город' )]").setValue("Омск");
        $x("//label[contains(@data-test-id, 'agreement')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").click();
        $x("//* [contains(@placeholder , 'Дата встречи')]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $x("//* [contains(@placeholder , 'Дата встречи')]").setValue(generateDate(4, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Иванов Илья");
        $("[data-test-id='phone'] input").setValue("+79436578935");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(@class,'notification__content')]").should(appear, Duration.ofMillis(15000));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(4, "dd.MM.yyyy")), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    public void dropDownListCase() {
        int currentDate = Integer.parseInt(generateDate(3, "MM"));
        int planingDate = Integer.parseInt(generateDate(7, "MM"));
        int planingDay = Integer.parseInt(generateDate(7, "dd"));
        $x("//input[contains (@placeholder, 'Город' )]").setValue("сан");
        $x("//* [contains(text() , 'Санкт-Петербург')]").click();
        $x("//span[contains(@class , 'input__icon')]").click();
        if (planingDate > currentDate) {
            $x("//div[(@data-step='1')]").click();
        }
        $x(String.format("//td[contains(text(),'%s')]", planingDay)).click();
        $("[data-test-id='name'] input").setValue("Иванов Илья");
        $("[data-test-id='phone'] input").setValue("+79436578935");
        $x("//label[contains(@data-test-id, 'agreement')]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(@class,'notification__content')]").should(appear, Duration.ofMillis(15000));
        $x("//*[contains(@class,'notification__content')]")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + generateDate(7, "dd.MM.yyyy")),
                        Duration.ofSeconds(15)).shouldBe(Condition.visible);

    }
}
