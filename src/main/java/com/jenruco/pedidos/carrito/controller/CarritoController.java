package com.jenruco.pedidos.carrito.controller;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.carrito.dto.AgregarCarritoReqDto;
import com.jenruco.pedidos.carrito.dto.CarritoItemResDto;
import com.jenruco.pedidos.carrito.dto.CarritoReqDto;
import com.jenruco.pedidos.carrito.service.CarritoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private static final Logger log = LoggerFactory.getLogger(PedidosApplication.class);
    
    @Autowired
    private final CarritoService carritoService;

    /**
     * Obtiene el carrito de compras de un usuario específico.
     * 
     * @param request {@link CarritoReqDto} objeto que contiene el ID del usuario
     * @return {@link ResponseEntity} con el {@link List} de {@link CarritoItemResDto} con los items del carrito
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<CarritoItemResDto>> getCarritoPorUsuario(@PathVariable Long usuarioId) {
        CarritoReqDto request = new CarritoReqDto();
        request.setUsuarioId(usuarioId);
        List<CarritoItemResDto> carritoItems = carritoService.getCarritoPorUsuario(request);
        return ResponseEntity.ok(carritoItems);
    }

    /**
     * Agrega uno o varios items al carrito de un usuario.
     * Si el usuario no tiene carrito, crea uno nuevo automáticamente.
     * 
     * @param request {@link AgregarCarritoReqDto} objeto con el ID del usuario e items a agregar
     * @return {@link ResponseEntity} con el {@link Boolean} true si se agregaron los items correctamente
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @PostMapping("/agregar")
    public ResponseEntity<Boolean> agregarItemAlCarrito(@Valid @RequestBody AgregarCarritoReqDto request) {
        Boolean resultado = carritoService.agregarItemAlCarrito(request);
        return ResponseEntity.ok(resultado);
    }
    
}
