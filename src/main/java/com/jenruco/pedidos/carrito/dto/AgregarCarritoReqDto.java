package com.jenruco.pedidos.carrito.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgregarCarritoReqDto {

    @NotNull(message = "El usuarioId no puede ser nulo")
    private Long usuarioId;

    private List<CarritoItemReqDto> items;
}
