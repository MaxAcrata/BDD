package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.netology.web.data.DataHelper.generateInvalidAmount;
import static ru.netology.web.data.DataHelper.generateValidAmount;

public class MoneyTransferTest {
    DashboardPage dashboardPage;
    DataHelper.CardInfo firstCardInfo;
    DataHelper.CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    /**
     * Подготовка: логин, верификация и получение текущих балансов карт.
     */
    @BeforeEach
    void setup() {
        // Логинимся и подтверждаем код
        dashboardPage = open("http://localhost:9999", LoginPage.class)
                .validLogin(DataHelper.getAuthInfo())
                .validVerification(DataHelper.getVerificationCode());

        // Получаем данные по картам
        firstCardInfo = DataHelper.getFirstCardInfo();
        secondCardInfo = DataHelper.getSecondCardInfo();

        // Получаем текущие балансы
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    /**
     * Тест: перевод валидной суммы с первой карты на вторую.
     */
    @Test
    void transferFromFirstToSecond() {
        var amount = generateValidAmount(firstCardBalance);
        var expectedFirstBalance = firstCardBalance - amount;
        var expectedSecondBalance = secondCardBalance + amount;

        // Выбираем карту-получателя и выполняем перевод
        var transferPage = dashboardPage.selectCardTransfer(secondCardInfo);
        dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);

        // Обновляем страницу и проверяем балансы
        dashboardPage.reloadDashBoardPage();
        assertAll(
                () -> dashboardPage.checkCardBalance(firstCardInfo, expectedFirstBalance),
                () -> dashboardPage.checkCardBalance(secondCardInfo, expectedSecondBalance)
        );
    }

    /**
     * Тест: попытка перевода суммы, превышающей баланс — ожидаем ошибку.
     */
    @Test
    void shouldGetErrorMessageIfAmountMoreThanBalance() {
        var amount = generateInvalidAmount(secondCardBalance);

        // Переход на форму перевода и ввод данных
        var transferPage = dashboardPage.selectCardTransfer(firstCardInfo);
        transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);

        // Проверка сообщения об ошибке и отсутствие изменения баланса
        assertAll(
                () -> transferPage.findErrorMessage("Выполнена попытка переводв суммы, превышающей остаток на карте списания"),
                () -> dashboardPage.reloadDashBoardPage(),
                () -> dashboardPage.checkCardBalance(firstCardInfo, firstCardBalance),
                () -> dashboardPage.checkCardBalance(secondCardInfo, secondCardBalance)
        );
    }
}
