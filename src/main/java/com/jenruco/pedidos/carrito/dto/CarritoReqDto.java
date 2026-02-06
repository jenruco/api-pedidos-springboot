package com.jenruco.pedidos.carrito.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoReqDto {

    @NotNull(message = "El usuarioId no puede ser nulo")
    private Long usuarioId;
}
