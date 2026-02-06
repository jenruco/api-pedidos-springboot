package com.jenruco.pedidos.producto.service;

import com.jenruco.pedidos.producto.dto.ProductoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoResDto;
import java.util.List;

public interface ProductoService {

    public List<ProductoResDto> getProductos();
    public ProductoResDto crearProducto(ProductoReqDto producto);
}
