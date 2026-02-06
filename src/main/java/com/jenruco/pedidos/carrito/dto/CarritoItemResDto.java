package com.jenruco.pedidos.carrito.dto;

import com.jenruco.pedidos.producto.dto.ProductoResDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoItemResDto {
    private Long id;
    private CarritoResDto carrito;
    private ProductoResDto producto;
    private Integer cantidad;
}
