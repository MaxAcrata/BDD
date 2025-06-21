package ru.netology.web.data;

import lombok.Value;

/**
 * Вспомогательный класс для генерации тестовых данных:
 * - логины, пароли, карты, суммы перевода
 */
public class DataHelper {

    private DataHelper() {
    }

    /**
     * Возвращает корректную пару логина и пароля
     */
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    /**
     * Возвращает код подтверждения для 2-х факторной аутентификации
     */
    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    /**
     * Возвращает данные первой карты
     */
    public static CardInfo getFirstCardInfo() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    /**
     * Возвращает данные второй карты
     */
    public static CardInfo getSecondCardInfo() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    /**
     * Генерирует валидную сумму для перевода (например, 1/10 баланса)
     */
    public static int generateValidAmount(int balance) {
        return Math.max(1, Math.abs(balance) / 10); // минимум 1 рубль
    }

    /**
     * Генерирует невалидную сумму (больше текущего баланса)
     */
    public static int generateInvalidAmount(int balance) {
        return Math.abs(balance) + 1;
    }

    /**
     * Генерирует фиксированную сумму перевода (пример)
     */
    public static TransferAmount getFixedTransferAmount() {
        return new TransferAmount(100);
    }

    // --- Модели данных ---

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String testId; // используется для идентификации в DOM
    }

    @Value
    public static class TransferAmount {
        int amount;
    }
}
