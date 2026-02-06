package com.jenruco.pedidos.usuario.controller;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.usuario.dto.LoginReqDto;
import com.jenruco.pedidos.usuario.dto.LoginResDto;
import com.jenruco.pedidos.usuario.dto.UsuarioReqDto;
import com.jenruco.pedidos.usuario.dto.UsuarioResDto;
import com.jenruco.pedidos.usuario.service.UsuarioService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(PedidosApplication.class);
    private final UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<List<UsuarioResDto>> getUsuarios() {
        List<UsuarioResDto> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResDto> postClientes(@Valid @RequestBody UsuarioReqDto usuarioReq) {
        UsuarioResDto nuevoUsuario = usuarioService.crearUsuario(usuarioReq);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReq) {
        LoginResDto response = usuarioService.login(loginReq);
        return ResponseEntity.ok(response);
    }
    
}
