package com.jenruco.pedidos.producto.controller;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.producto.dto.ProductoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoResDto;
import com.jenruco.pedidos.producto.service.ProductoService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private static final Logger log = LoggerFactory.getLogger(PedidosApplication.class);
    
    @Autowired
    private final ProductoService productoService;
    
    /**
     * Obtiene la lista de productos disponibles en el sistema.
     * 
     * @return {@link ResponseEntity} con {@link List} de {@link ProductoResDto} con los productos disponibles
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @GetMapping
    public ResponseEntity<List<ProductoResDto>> getUsuarios() {
        List<ProductoResDto> usuarios = productoService.getProductos();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Crea un nuevo producto en el sistema.
     * 
     * @param producto {@link ProductoReqDto} objeto con los datos del producto a crear
     * @return {@link ResponseEntity} con el {@link ProductoResDto} con los datos del producto creado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @PostMapping
    public ResponseEntity<ProductoResDto> postClientes(@Valid @RequestBody ProductoReqDto usuarioReq) {
        ProductoResDto nuevoUsuario = productoService.crearProducto(usuarioReq);
        return ResponseEntity.ok(nuevoUsuario);
    }
    
}
