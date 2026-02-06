package com.jenruco.pedidos.usuario.service;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.usuario.dto.LoginReqDto;
import com.jenruco.pedidos.usuario.dto.LoginResDto;
import com.jenruco.pedidos.usuario.dto.UsuarioReqDto;
import com.jenruco.pedidos.usuario.dto.UsuarioResDto;
import com.jenruco.pedidos.usuario.entity.Usuario;
import com.jenruco.pedidos.usuario.repository.UsuarioRepository;
import com.jenruco.pedidos.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

    public List<UsuarioResDto> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResDto.class))
                .toList();
    }

    public UsuarioResDto crearUsuario(UsuarioReqDto usuario) {
        Usuario nuevoUsuario = modelMapper.map(usuario, Usuario.class);
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return modelMapper.map(usuarioGuardado, UsuarioResDto.class);
    }

    public LoginResDto login(LoginReqDto loginReq) {
        // Busca el usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginReq.getEmail());

        if (usuarioOpt.isEmpty()) {
            log.warn("Intento de login fallido: usuario no encontrado - {}", loginReq.getEmail());
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email o contraseña incorrectos"
            );
        }

        Usuario usuario = usuarioOpt.get();

        // Verifica la contraseña
        if (!usuario.getPassword().equals(loginReq.getPassword())) {
            log.warn("Intento de login fallido: contraseña incorrecta - {}", loginReq.getEmail());
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Email o contraseña incorrectos"
            );
        }

        // Genera JWT token
        String token = jwtUtil.generateToken(usuario.getEmail());

        return new LoginResDto(token, usuario.getEmail(), "Login exitoso");
    }
}
