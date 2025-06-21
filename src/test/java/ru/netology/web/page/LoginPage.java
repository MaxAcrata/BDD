package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    // Поля ввода логина и пароля и кнопка входа
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    /**
     * Метод выполняет ввод логина и пароля и нажимает кнопку входа.
     * Возвращает страницу подтверждения (VerificationPage).
     *
     * @param info объект с логином и паролем
     * @return VerificationPage
     */
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }
}

