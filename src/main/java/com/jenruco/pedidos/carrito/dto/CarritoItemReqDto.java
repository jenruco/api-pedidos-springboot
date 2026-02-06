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
public class CarritoItemReqDto {

    @NotNull(message = "El id del carrito es obligatorio")
    @Positive(message = "El id del carrito debe ser un número positivo")
    private Long carritoId;

    @NotNull(message = "El id del producto es obligatorio")
    @Positive(message = "El id del producto debe ser un número positivo")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
}
