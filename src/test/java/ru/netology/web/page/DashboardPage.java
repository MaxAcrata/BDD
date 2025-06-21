package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Представляет дашборд после входа в систему, где отображаются карты и их балансы.
 */
public class DashboardPage {

    private static final String BALANCE_START = "баланс: ";
    private static final String BALANCE_FINISH = " р.";

    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");
    private final SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public DashboardPage() {
        heading.shouldBe(visible); // Проверка, что страница загружена
    }

    /**
     * Получает текущий баланс для заданной карты.
     */
    public int getCardBalance(DataHelper.CardInfo cardInfo) {
        String text = getCard(cardInfo).getText();
        return extractBalance(text);
    }

    /**
     * Инициирует перевод с выбранной карты.
     */
    public TransferPage selectCardTransfer(DataHelper.CardInfo cardInfo) {
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    /**
     * Обновляет страницу даш борда.
     */
    public void reloadDashBoardPage() {
        reloadButton.click();
        heading.shouldBe(visible);
    }

    /**
     * Проверяет, что баланс карты соответствует ожидаемому.
     */
    public void checkCardBalance(DataHelper.CardInfo cardInfo, int expectedBalance) {
        getCard(cardInfo)
                .shouldBe(visible)
                .shouldHave(text(BALANCE_START + expectedBalance + BALANCE_FINISH));
    }

    // ---------------------- Вспомогательные методы ----------------------

    /**
     * Возвращает элемент карты по её testId.
     */
    private SelenideElement getCard(DataHelper.CardInfo cardInfo) {
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getTestId()));
    }

    /**
     * Извлекает числовое значение баланса из текста карты.
     */
    private int extractBalance(String text) {
        int start = text.indexOf(BALANCE_START);
        int finish = text.indexOf(BALANCE_FINISH);
        String value = text.substring(start + BALANCE_START.length(), finish).trim();
        return Integer.parseInt(value);
    }
}
