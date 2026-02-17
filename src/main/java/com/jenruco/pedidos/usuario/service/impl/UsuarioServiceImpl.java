package com.jenruco.pedidos.usuario.service.impl;

import com.jenruco.pedidos.PedidosApplication;
import com.jenruco.pedidos.rol.dto.RolReqDto;
import com.jenruco.pedidos.rol.dto.RolResDto;
import com.jenruco.pedidos.usuario.dto.LoginReqDto;
import com.jenruco.pedidos.usuario.dto.LoginResDto;
import com.jenruco.pedidos.usuario.dto.UsuarioReqDto;
import com.jenruco.pedidos.usuario.dto.UsuarioResDto;
import com.jenruco.pedidos.usuario.entity.Usuario;
import com.jenruco.pedidos.usuario.repository.UsuarioRepository;
import com.jenruco.pedidos.usuario.service.UsuarioService;
import com.jenruco.pedidos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(PedidosApplication.class);

    /**
     * Obtiene la lista de usuarios registrados en el sistema.
     * 
     * @return {@link List} de {@link UsuarioResDto} con los usuarios registrados
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public List<UsuarioResDto> getUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResDto.class))
                .toList();
    }

    @Override
    public UsuarioResDto getUsuarioById(Long id) {
        if(id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El identificador del usuario es obligatorio");
        }
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return modelMapper.map(usuario, UsuarioResDto.class);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * 
     * @param usuario {@link UsuarioReqDto} objeto con los datos del usuario a crear
     * @return {@link UsuarioResDto} con los datos del usuario creado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public UsuarioResDto crearUsuario(UsuarioReqDto usuario) {
        Usuario nuevoUsuario = modelMapper.map(usuario, Usuario.class);
        if (nuevoUsuario.getPassword() != null) {
            nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        }
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return modelMapper.map(usuarioGuardado, UsuarioResDto.class);
    }

    @Override
    public UsuarioResDto editarUsuario(Long id, UsuarioReqDto usuario) {
        if(id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El identificador del usuario es obligatorio");
        }
        Usuario usuariodb = usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario a editar"));

        usuariodb.setNombre(usuario.getNombre());
        usuariodb.setRolId(usuario.getRolId());
        
        Usuario usuarioGuardado = usuarioRepository.save(usuariodb);
        return modelMapper.map(usuarioGuardado, UsuarioResDto.class);
    }

    /**
     * Inicia sesión para un usuario existente y genera un token JWT.
     * 
     * @param loginReq {@link LoginReqDto} objeto con los datos del usuario a loguear
     * @return {@link LoginResDto} con el token JWT generado y otros datos del usuario
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
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

        // Verifica la contraseña usando PasswordEncoder (BCrypt)
        if (!passwordEncoder.matches(loginReq.getPassword(), usuario.getPassword())) {
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
