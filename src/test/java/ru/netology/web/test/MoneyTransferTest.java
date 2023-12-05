package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.Page.DashboardPage;
import ru.netology.web.Page.LoginPage;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    CardInfo firstCardInfo;
    CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    @Test
    void shouldTransferMoneyFromFirstCard() {
        var amount = generateValidAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
        dashboardPage = transferPage.transferValidSum(valueOf(amount), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance((firstCardInfo));
        var actualBalanceSecondCard = dashboardPage.getCardBalance((secondCardInfo));
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyFromSecondCard() {
        var amount = generateValidAmount(secondCardBalance);
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transferPage.transferValidSum(valueOf(amount), secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.getCardBalance((firstCardInfo));
        var actualBalanceSecondCard = dashboardPage.getCardBalance((secondCardInfo));
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

}