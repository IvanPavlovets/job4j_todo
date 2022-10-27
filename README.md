# job4j_todo

 - [О проекте]()
 - [Технологии]() 
 - [Запуск приложения]() 
 - [Как использовать]()  

О проекте
=
Сервис для отслеживания задач.<br>

Технологии
=
![https://www.oracle.com/java/](https://img.shields.io/badge/Java-12-orange)
![https://spring.io/blog/2022/08/18/spring-boot-2-7-3-available-now](https://img.shields.io/badge/Spring%20Boot-2.7.3-green)
![https://getbootstrap.com/](https://img.shields.io/badge/Bootstrap-style-blueviolet)
![https://www.thymeleaf.org/](https://img.shields.io/badge/Thymeleaf-3.0.15-darkgreen)
![https://www.postgresql.org/](https://img.shields.io/badge/PostgreSQL-42.4.2-informational)
![https://www.h2database.com/html/main.html](https://img.shields.io/badge/-H2-2.1.214-blueviolet)
![https://www.liquibase.org/](https://img.shields.io/badge/-Liquibase-4.15.0-blue)
![](https://img.shields.io/badge/JUnit-4.13.2-yellowgreen)
![https://site.mockito.org/](https://img.shields.io/badge/-Mockito-4.0.0-brightgreen)
![https://checkstyle.sourceforge.io/](https://img.shields.io/badge/-checkstyle-3.1.2-lightgrey)

Запуск приложения
=
1. Создать бд:<br>

create database todo;<br>

2. Запуск приложения с maven. Перейдите в корень проекта через командную строку и выполните команды:<br>

mvn clean install<br>
mvn spring-boot:run<br>

Как использовать
=
<h3>Виды приложения:</h3><br>

<h4>1. Главная страница со списком всех заданий.</h4><br>

![Image of all](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/all.png)<br>
 В таблице отображаются **имя**, **дата создания** и **состояние** (выполнено или нет)<br>
 В навигационной панели страницы присутствуют три ссылки: **Все**, **Выполненные**, **Новые**.
 Внизу таблицы находиться кнопка **"Добавить задание"**.<br> 
 ___

<h4>2. Форма добавления нового задания.</h4><br>

![Image of addTask](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/addTask.png)<br>
 При нажатии на кнопку **"Добавить задание"** мы попадаем на форму нового задания.<br>
 После заполнения и нажатия **сохранить** формируется новое задание.<br>
 ___
 
<h4>3. Вкладка "Выполненные".</h4><br>

![Image of done](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/done.png)<br>
 Отображает все **выполненные** задачи.
 ___
 
<h4>4. Вкладка "Новые".</h4><br>

![Image of notDone](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/notDone.png)<br>
 Отображает все **невыполненные** задачи.
___

<h4>5. Страница подробного описания задачи.</h4><br>

![Image of detailed](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/detailed.png)<br>
Помимо подробного описания на странице присутствуют три кнопки: **Завершить", **Редактировать**, **Удалить**.<br>
**Завершить** - переводит задачу в состояние **выполнено**.<br>
**Удалить** - удаляет задачу из таблицы и переходит на список **всех заданий**.<br>
**Редактировать** - переходит на форму редактирования описания задачи.<br>
___

<h4>6. Форма редактирования задачи.</h4><br>

![Image of edit](https://github.com/IvanPavlovets/job4j_todo/blob/master/images/edit.png)<br>
___


