package com.jenruco.pedidos.producto.controller;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.producto.dto.ProductoReqDto;
import com.jenruco.pedidos.producto.dto.ProductoResDto;
import com.jenruco.pedidos.producto.service.ProductoService;
import lombok.RequiredArgsConstructor;
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
    private final ProductoService productoService;
    @GetMapping
    public ResponseEntity<List<ProductoResDto>> getUsuarios() {
        List<ProductoResDto> usuarios = productoService.getProductos();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<ProductoResDto> postClientes(@Valid @RequestBody ProductoReqDto usuarioReq) {
        ProductoResDto nuevoUsuario = productoService.crearProducto(usuarioReq);
        return ResponseEntity.ok(nuevoUsuario);
    }
    
}
