package com.arquitecturaweb.ejercicioIntegrador3.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(@Qualifier("CSVReader") CSVReader csvReader) {
        return args -> {
            csvReader.populateDB();
        };
    }
}
