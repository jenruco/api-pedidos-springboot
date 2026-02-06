package com.jenruco.pedidos.usuario.controller;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.usuario.dto.LoginReqDto;
import com.jenruco.pedidos.usuario.dto.LoginResDto;
import com.jenruco.pedidos.usuario.dto.UsuarioReqDto;
import com.jenruco.pedidos.usuario.dto.UsuarioResDto;
import com.jenruco.pedidos.usuario.service.UsuarioService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(PedidosApplication.class);
    
    @Autowired
    private final UsuarioService usuarioService;

    /**
     * Obtiene la lista de usuarios registrados en el sistema.
     * 
     * @return {@link ResponseEntity} con {@link List} de {@link UsuarioResDto} con los usuarios registrados
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResDto>> getUsuarios() {
        List<UsuarioResDto> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario {@link UsuarioReqDto} objeto con los datos del usuario a crear
     * @return {@link ResponseEntity} con {@link UsuarioResDto} con los datos del usuario creado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @PostMapping
    public ResponseEntity<UsuarioResDto> postClientes(@Valid @RequestBody UsuarioReqDto usuarioReq) {
        UsuarioResDto nuevoUsuario = usuarioService.crearUsuario(usuarioReq);
        return ResponseEntity.ok(nuevoUsuario);
    }

    /**
     * Inicia sesión para un usuario existente y genera un token JWT.
     * 
     * @param loginReq {@link LoginReqDto} objeto con los datos del usuario a loguear
     * @return {@link ResponseEntity} con {@link LoginResDto} con el token JWT generado y otros datos del usuario
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@Valid @RequestBody LoginReqDto loginReq) {
        LoginResDto response = usuarioService.login(loginReq);
        return ResponseEntity.ok(response);
    }
    
}
