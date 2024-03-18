# Diplom_3
# Тестирование веб-приложения [Stellar Burgers](https://stellarburgers.nomoreparties.site/)

## **Задание**
* Опиши элементы, которые будешь использовать в тестах, с помощью Page Object.
* Протестируй функциональность в Google Chrome и Яндекс.Браузере. 
* Подключи Allure-отчёт.

## **Регистрация:**
*  Проверь успешную регистрацию.
* Ошибку для некорректного пароля. Минимальный пароль — шесть символов.

## **Вход**
* Вход по кнопке «Войти в аккаунт» на главной,
*  Вход через кнопку «Личный кабинет»,
*  Вход через кнопку в форме регистрации,
* Вход через кнопку в форме восстановления пароля.

## **Переход в личный кабинет**
* Проверь переход по клику на «Личный кабинет».

## **Переход из личного кабинета в конструктор**
* Проверь переход по клику на «Конструктор» и на логотип Stellar Burgers.

## **Выход из аккаунта**
* Проверь выход по кнопке «Выйти» в личном кабинете.

## **Раздел «Конструктор»**
* Проверь, что работают переходы к разделам: «Булки», «Соусы»,«Начинки».

## Технологии
* Java 11
* JUnit 4.13.2
* Selenium 4.16.1
* Maven 3.9.5
* Allure (JUnit 4) 2.15.0
* REST Assured 4.4.0
* Gson 2.8.9
* Webdrivermanager 5.6.2

## Запуск проекта

Ввести в консоли Run Anything (дважды нажав Ctrl): `mvn clean test`