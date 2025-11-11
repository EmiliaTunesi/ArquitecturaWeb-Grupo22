package org.example.microservicioadministrador.util;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.entity.tipoTarifa;
import org.example.microservicioadministrador.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Component("CSVReader")
public class CSVReader {

    @Autowired
    private TarifaRepository tarifaRepository;


    private Iterable<CSVRecord> getData(String archivo) throws IOException {
        String path = "src/main/resources/" + archivo;
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());

        return csvParser.getRecords();
    }

    public void populateDB() throws Exception{
        try{
            insertTarifas();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertTarifas() throws Exception{
        for (CSVRecord row : getData("tarifa.csv")) {
            Tarifa tarifa = new Tarifa();

            tarifa.setNombre(row.get("nombre"));
            tarifa.setPrecio_min(Double.parseDouble(row.get("precio_min")));
            tarifa.setTipo(tipoTarifa.valueOf(row.get("tipo").trim().toUpperCase()));
            tarifa.setTiempoEspera(Integer.parseInt(row.get("tiempoEspera")));
            tarifa.setVigenteDesde(LocalDate.parse(row.get("vigenteDesde")));

            String hasta = row.get("vigenteHasta");
            if (hasta != null && !hasta.isBlank()) {
                tarifa.setVigenteHasta(LocalDate.parse(hasta));
            } else {
                tarifa.setVigenteHasta(null);
            }
            tarifaRepository.save(tarifa);
        }
    }
}
