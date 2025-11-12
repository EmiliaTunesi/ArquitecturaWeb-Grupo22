package org.example.microserviciomonopatin.dto.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinRequestDTO {

    private Double latitudActual;
    private Double longitudActual;

}
