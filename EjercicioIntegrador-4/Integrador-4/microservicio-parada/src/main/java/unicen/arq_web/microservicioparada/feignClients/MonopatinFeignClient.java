package unicen.arq_web.microservicioparada.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import unicen.arq_web.microservicioparada.models.Monopatin;

@FeignClient(name="microservicio-monopatin", url="http://localhost:8084/api/monopoatines")
public interface MonopatinFeignClient {

    @GetMapping
    Monopatin getById(@RequestParam("id") Long id);
}
