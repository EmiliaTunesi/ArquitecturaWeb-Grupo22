package unicen.arq_web.microservicioparada.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import unicen.arq_web.microservicioparada.models.Monopatin;

@FeignClient(name="microservicio-monopatin", url="http://localhost:8084/api/monopatines")
public interface MonopatinFeignClient {

    @GetMapping("/{id}")
    Monopatin getMonopatinById(@PathVariable Long id);
}
