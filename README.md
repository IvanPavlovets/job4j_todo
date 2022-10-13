![](https://img.shields.io/badge/Java-%3E%3D%208-orange)
![](https://img.shields.io/badge/Maven-3-red)
![](https://img.shields.io/badge/Spring%20boot-%202.5.2-green)
![](https://img.shields.io/badge/-Bootstrap-blueviolet)
![](https://img.shields.io/badge/-Thymeleaf-darkgreen)
![](https://img.shields.io/badge/PostgreSQL-%3E%3D%209-informational)
![](https://img.shields.io/badge/-JDBC-blue)
![](https://img.shields.io/badge/-H2%20-blueviolet)
![](https://img.shields.io/badge/-Liquibase-blue)
![](https://img.shields.io/badge/JUnit-%3E%3D%204-yellowgreen)
![](https://img.shields.io/badge/-Mockito-brightgreen)
![](https://img.shields.io/badge/-checkstyle-lightgrey)

# job4j_todo

 - [О проекте]()
 - [Технологии]() 
 - [Как использовать]()  

О проекте
=
Сервис для отслеживания задач.<br>

Технологии
=
 * Frontend - **HTML**, **CSS**, **BOOTSTRAP**, **Thymeleaf**;
 * Backend - **Java 12**, **JDBC**, **Spring Boot**;
 * Сборщик проектов - **Maven**;
 * СУБД - **PostgreSQL**, **H2**;
 * библиотека для управления обновлений схем БД - **Liquibase**;
 * библиотека для модульного тестирования - **JUnit**;
 * библиотеки для тестирования - **JUnit**, **Mockito**;
 * Инструмент анализа стиля кода - **Checkstyle**;

Как использовать
=
 Виды.<br>
    - Страница со списком всех заданий. В таблице отображаем имя, дату создания и состояние (выполнено или нет)<br>
    - На странице со списком добавить кнопку "Добавить задание".<br> 
    - На странице со списком добавьте три ссылки: Все, Выполненные, Новые. При перевода по ссылкам в таблице нужно отображать: все задания, только выполненные или только новые.<br>
    - При клике на задание переходим на страницу с подробным описанием.<br>
    - На странице с подробным описанием добавить кнопки: Выполнено, Отредактировать, Удалить.<br>
    - Если нажали на кнокпу выполнить, то задание переводиться в состояние выполнено.<br>
    - Кнопка редактировать переводит пользователя на отдельную страницу для редактирования.<br>
    - Кнопка удалить, удаляет задание и переходит на список всех заданий.<br>


 Приложение должно иметь три слоя: Контроллеры, Сервисы, Персистенции.<br>
    - Объект SessionFactory создается один раз в классе Main с аннотацией @Bean. По аналогии с loadDabaseSource в проекте "Работа мечты".<br>
    - Объект TaskStore принимает параметр SessionFactory через конструктор.<br>
