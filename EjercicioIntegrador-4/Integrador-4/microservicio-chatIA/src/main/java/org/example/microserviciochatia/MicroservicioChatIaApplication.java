package org.example.microserviciochatia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
})
public class MicroservicioChatIaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioChatIaApplication.class, args);
    }

}
