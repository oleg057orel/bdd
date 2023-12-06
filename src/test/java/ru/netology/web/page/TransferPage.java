package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = Selenide.$(Selectors.byText("Пополнить"));
    private final SelenideElement amountInput = Selenide.$(".money-input input");
    private final SelenideElement fromInput = Selenide.$(".card-input input");
    private final SelenideElement transferHead = Selenide.$("h1").shouldHave(Condition.exactText("Пополнение карты"));
    private final SelenideElement errorMessage = Selenide.$("[data-test-id='error-notification']");

    public TransferPage() {
        transferHead.shouldBe(visible);
    }

    public DashboardPage transferValidSum(String amount, DataHelper.CardInfo cardInfo) {
        transfer(amount, cardInfo);
        return new DashboardPage();
    }

    public void transfer(String amount, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amount);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public void errorMessage(String expectedText) {
        errorMessage.shouldBe(visible).shouldHave(text(expectedText), Duration.ofSeconds(15));
    }
}