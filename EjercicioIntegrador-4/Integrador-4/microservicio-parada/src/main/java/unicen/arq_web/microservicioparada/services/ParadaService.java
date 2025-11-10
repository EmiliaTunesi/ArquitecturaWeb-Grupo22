package unicen.arq_web.microservicioparada.services;

import org.springframework.stereotype.Service;
import unicen.arq_web.microservicioparada.entities.Parada;
import unicen.arq_web.microservicioparada.models.ParadaDto;
import unicen.arq_web.microservicioparada.repositories.ParadaRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ParadaService {
    private ParadaRepository pr;

    public ParadaDto getById(Integer id){
        if (pr.findById(id).isPresent()) {
            Parada salida = this.pr.findById(id).get();
            ParadaDto salidaDto = new ParadaDto(salida);
            return salidaDto;
        }else{
            throw new RuntimeException("No existe parada con el id: " + id);
        }
    }

    public ArrayList<ParadaDto> getAll(){
        ArrayList<Parada> listaIntermedia = this.pr.findAll();
        ArrayList<ParadaDto> listaFinal = new ArrayList<>();
        for(Parada parada : listaIntermedia) {
            ParadaDto dto = new ParadaDto(parada);
            listaFinal.add(dto);
        }
        return listaFinal;
    }

    public ParadaDto update(Integer id, Parada p) {
        if (pr.findById(id).isPresent()) {
            Parada salida = this.pr.save(p).get();
            ParadaDto salidaDto = new ParadaDto(salida);
            return salidaDto;
        }else{
            throw new RuntimeException("No existe parada con el id: " + id);
        }
    }

    public void delete(Integer id){ this.pr.deleteById(id); }


    public ParadaDto add(Parada p) {
        Optional<Parada> salida = this.pr.save(p);
        ParadaDto salidaDto = new ParadaDto(salida.get());
        return salidaDto;
    }



}
