package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromInput = $("[data-test-id='from'] input");
    private final SelenideElement errorMassage = $("[data-test-id='error-notification'] ,notification__content");

    public TransferPage() {
        SelenideElement transferHead = $(byText("Пополнение карты"));
        transferHead.shouldBe(visible);

    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardinfo) {
      makeTransfer(amountToTransfer, cardinfo);
      return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }
    public void findErrorMessage(String expectedText) {
        errorMassage.should(Condition.and("Проверка сообщения об ошибке", Condition.text(expectedText), Condition.visible),
        Duration.ofSeconds(15));
    }

   }
