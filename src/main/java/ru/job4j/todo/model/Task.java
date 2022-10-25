package ru.job4j.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity /* указывает что модель можно сохранить в БД */
@Table(name = "tasks") /* указывает на таблицу(схему) в бд*/
@Data
public class Task {
    @Id /* указывает какое поле в модели первичный ключ */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());
    private Boolean done = false;
}
