package com.jenruco.pedidos.producto.service;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.producto.dto.ProductoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoResDto;
import com.jenruco.pedidos.producto.entity.Producto;
import com.jenruco.pedidos.producto.repository.ProductoRepository;
import com.jenruco.pedidos.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

    public List<ProductoResDto> getProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoResDto.class))
                .toList();
    }

    public ProductoResDto crearProducto(ProductoReqDto producto) {
        Producto nuevoProducto = modelMapper.map(producto, Producto.class);
        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return modelMapper.map(productoGuardado, ProductoResDto.class);
    }
}
