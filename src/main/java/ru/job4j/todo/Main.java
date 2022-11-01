package ru.job4j.todo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

@SpringBootApplication
public class Main {

    /**
     * SessionFactory - это конфигуратор.
     * Создается один раз, через фабрику,
     * на все приложение. Метод configure
     * читает файл hibernate.cfg.xml и
     * выполняет инициализация пула и кешей.
     * SessionFactory необходим для получения
     * объекта Session - позволяет записать,
     * удалить и прочитать данные из БД.
     * Загружаем обьект через Spring Context.
     * @return SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sf() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();
        return sf;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("Go to http://localhost:8080/all");
    }

}
