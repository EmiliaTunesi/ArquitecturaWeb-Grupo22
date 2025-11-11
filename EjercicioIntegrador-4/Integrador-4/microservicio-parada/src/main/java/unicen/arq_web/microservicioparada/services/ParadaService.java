package unicen.arq_web.microservicioparada.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unicen.arq_web.microservicioparada.entities.Parada;
import unicen.arq_web.microservicioparada.feignClients.MonopatinFeignClient;
import unicen.arq_web.microservicioparada.models.Monopatin;
import unicen.arq_web.microservicioparada.models.ParadaDto;
import unicen.arq_web.microservicioparada.repositories.ParadaRepository;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class ParadaService {

    @Autowired
    private ParadaRepository pr;
    @Autowired
    private MonopatinFeignClient fcMonop;

    public ParadaDto getById(Integer id){ //Devuelve (si existe) una parada con ese id
        if (pr.findById(id).isPresent()) {
            Parada salida = this.pr.findById(id).get();
            ParadaDto salidaDto = new ParadaDto(salida);
            return salidaDto;
        }else{
            throw new RuntimeException("No existe parada con el id: " + id);
        }
    }

    public ArrayList<ParadaDto> getAll(){ //Devuelve todas las paradas
        ArrayList<Parada> listaIntermedia = this.pr.findAll();
        ArrayList<ParadaDto> listaFinal = new ArrayList<>();
        for(Parada parada : listaIntermedia) {
            ParadaDto dto = new ParadaDto(parada);
            listaFinal.add(dto);
        }
        return listaFinal;
    }

    public ParadaDto update(Integer id, Parada reemplazo) {
        if (pr.findById(id).isPresent()) {
            Parada salida = this.pr.save(reemplazo);
            ParadaDto salidaDto = new ParadaDto(salida);
            return salidaDto;
        }else{
            throw new RuntimeException("No existe parada con el id: " + id);
        }
    }

    public void delete(Integer id){ this.pr.deleteById(id); }


    public ParadaDto add(Parada p) {
        Parada salida = this.pr.save(p);
        ParadaDto salidaDto = new ParadaDto(salida);
        return salidaDto;
    }

    public ParadaDto estacionar(Integer idParada, Long idMonopatin) {
        Monopatin m = this.fcMonop.getMonopatinById(idMonopatin);
        if (m != null) {
            pr.findById(idParada).ifPresent(p -> {
                Long idMonop = m.getId();
                p.agregarMonopatin(idMonop);
                pr.save(p);
            });

            if (pr.findById(idParada).isPresent()) {
                Parada actualizada = pr.findById(idParada).get();
                return new ParadaDto(actualizada);
            } else {
                throw new RuntimeException("No existe parada con el id: " + idParada);
            }
        }else{
            throw new RuntimeException("No existe monopatin con el id: " + idMonopatin);
        }
    }

    public boolean quitarMonopatin(Integer idParada, Long idMonopatin) {
        Monopatin m = this.fcMonop.getMonopatinById(idMonopatin);
        if (m != null) {
            pr.findById(idParada).ifPresent(p -> {
                Long idMonop = m.getId();
                p.quitarMonopatin(idMonop);
                pr.save(p);
            });
            return pr.findById(idParada).isEmpty();
        }
        else  {
            throw new RuntimeException("No existe monopatin con el id: " + idMonopatin);
        }
    }


}
