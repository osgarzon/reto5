package com.reto5.reto5;

import com.reto5.controlador.ProductoControlador;
import com.reto5.modelo.ProductoRepositorio;
import com.reto5.vista.GUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("com.reto5.modelo")
@EnableJdbcRepositories("com.reto5.modelo")
public class Reto5Application {

    @Autowired
    ProductoRepositorio repositorio;
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            GUI vista = new GUI();
            ProductoControlador controlador = new ProductoControlador(repositorio, vista);
        };
    }

}
