package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

import javax.persistence.*;
import java.util.TimeZone;

@Entity
@Table(name = "todo_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String name;
    private String login;
    private String password;
    private String zone = TimeZone.getDefault().getID();

}
