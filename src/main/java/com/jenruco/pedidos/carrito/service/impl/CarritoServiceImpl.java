package com.jenruco.pedidos.carrito.service.impl;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.carrito.dto.AgregarCarritoReqDto;
import com.jenruco.pedidos.carrito.dto.CarritoItemReqDto;
import com.jenruco.pedidos.carrito.dto.CarritoItemResDto;
import com.jenruco.pedidos.carrito.dto.CarritoReqDto;
import com.jenruco.pedidos.carrito.entity.Carrito;
import com.jenruco.pedidos.carrito.entity.CarritoItem;
import com.jenruco.pedidos.carrito.repository.CarritoItemRepository;
import com.jenruco.pedidos.carrito.repository.CarritoRepository;
import com.jenruco.pedidos.carrito.service.CarritoService;
import com.jenruco.pedidos.producto.entity.Producto;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementación del servicio de carrito de compras.
 * Maneja la lógica de negocio relacionada con los carritos y sus items.
 * 
 * @author Aplicación de Pedidos
 * @version 1.0
 * 
 */
@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoItemRepository carritoItemRepository;
    private final ModelMapper modelMapper;
    private final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

    /**
     * Obtiene el carrito de compras de un usuario específico.
     * 
     * @param request {@link CarritoReqDto} objeto que contiene el ID del usuario
     * @return {@link List} de {@link CarritoItemResDto} con los items del carrito
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public List<CarritoItemResDto> getCarritoPorUsuario(CarritoReqDto request) {
        Carrito carrito = carritoRepository.findByUsuarioId(request.getUsuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado para el usuario"));

        List<CarritoItem> items = carritoItemRepository.findByCarritoId(carrito.getId());
        
        return items.stream()
                .map(item -> modelMapper.map(item, CarritoItemResDto.class))
                .toList();
    }

    /**
     * Agrega uno o varios items al carrito de un usuario.
     * Si el usuario no tiene carrito, crea uno nuevo automáticamente.
     * 
     * @param request {@link AgregarCarritoReqDto} objeto con el ID del usuario e items a agregar
     * @return {@link Boolean} true si se agregaron los items correctamente
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public Boolean agregarItemAlCarrito(AgregarCarritoReqDto request) {
        // Busca el carrito existente o crea uno nuevo si no existe
        Carrito carrito = carritoRepository.findByUsuarioId(request.getUsuarioId())
                    .orElseGet(() -> {
                        Carrito nuevoCarrito = new Carrito();
                        nuevoCarrito.setUsuarioId(request.getUsuarioId());
                        return carritoRepository.save(nuevoCarrito);
                    }
                );
        
        for (CarritoItemReqDto itemDto : request.getItems()) {
            CarritoItem item = modelMapper.map(itemDto, CarritoItem.class);
            item.setCarrito(carrito);
            Producto producto = new Producto();
            producto.setId(itemDto.getProductoId());
            item.setProducto(producto);
            carritoItemRepository.save(item);
        }
        return true;
    }
}
