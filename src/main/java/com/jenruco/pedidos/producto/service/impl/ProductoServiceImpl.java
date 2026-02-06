package com.jenruco.pedidos.producto.service.impl;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.carrito.dto.AgregarCarritoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoResDto;
import com.jenruco.pedidos.producto.entity.Producto;
import com.jenruco.pedidos.producto.repository.ProductoRepository;
import com.jenruco.pedidos.producto.service.ProductoService;
import com.jenruco.pedidos.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

    /**
     * Obtiene la lista de productos disponibles en el sistema.
     * 
     * @return {@link List} de {@link ProductoResDto} con los productos disponibles
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public List<ProductoResDto> getProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(producto -> modelMapper.map(producto, ProductoResDto.class))
                .toList();
    }

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param producto {@link ProductoReqDto} objeto con los datos del producto a crear
     * @return {@link ProductoResDto} con los datos del producto creado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public ProductoResDto crearProducto(ProductoReqDto producto) {
        Producto nuevoProducto = modelMapper.map(producto, Producto.class);
        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return modelMapper.map(productoGuardado, ProductoResDto.class);
    }
}
