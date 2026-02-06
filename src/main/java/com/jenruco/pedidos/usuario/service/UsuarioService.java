package com.jenruco.pedidos.usuario.service;

import com.jenruco.pedidos.usuario.dto.LoginReqDto;
import com.jenruco.pedidos.usuario.dto.LoginResDto;
import com.jenruco.pedidos.usuario.dto.UsuarioReqDto;
import com.jenruco.pedidos.usuario.dto.UsuarioResDto;
import java.util.List;

public interface UsuarioService {

    public List<UsuarioResDto> getUsuarios();
    public UsuarioResDto crearUsuario(UsuarioReqDto usuario);
    public LoginResDto login(LoginReqDto loginReq);
}
