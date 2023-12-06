package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement code = Selenide.$("[data-test-id=code] input");
    private final SelenideElement verifyButton = Selenide.$("[data-test-id=action-verify]");
    public VerificationPage() {

        code.shouldBe(Condition.visible);
    }
    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        code.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}