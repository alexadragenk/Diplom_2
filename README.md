# Diplom_2
## Технологии, используемые в проекте
* Java 11
* JUnit 4.13.1
* maven 3.9.0
* rest-assured 5.2.0
* allure 2.22.1
* allure-maven 2.10.0
* maven-surefire-plugin 2.22.2
* gson 2.8.9

## Запуск тестов
Запуск тестов:
```
mvn clean test
```
Генерация html отчета:
```
mvn allure:report
```
Просмотр отчета:
```
mvn allure:serve
```
## Описание
Тесты HTTP API приложения заказа бургеров.
Allure отчет добавлен в репозиторий.
