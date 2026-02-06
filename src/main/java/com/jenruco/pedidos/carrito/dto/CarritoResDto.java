package com.jenruco.pedidos.carrito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResDto {
    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;
}
