package unicen.arq_web.microservicioparada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicroservicioParadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicioParadaApplication.class, args);
    }

}
