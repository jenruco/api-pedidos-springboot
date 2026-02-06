package com.jenruco.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class PedidosApplication {

	private static final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
		log.info("Aplicación de Pedidos iniciada correctamente.");
	}

}
