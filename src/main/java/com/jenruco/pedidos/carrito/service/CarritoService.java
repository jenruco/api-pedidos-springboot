package com.jenruco.pedidos.carrito.service;

import com.jenruco.pedidos.carrito.dto.AgregarCarritoReqDto;
import com.jenruco.pedidos.carrito.dto.CarritoItemResDto;
import com.jenruco.pedidos.carrito.dto.CarritoReqDto;

import java.util.List;

public interface CarritoService {

    public List<CarritoItemResDto> getCarritoPorUsuario(CarritoReqDto request);
    public Boolean agregarItemAlCarrito(AgregarCarritoReqDto request);
}
