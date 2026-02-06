package com.jenruco.pedidos.rol.service;

import com.jenruco.pedidos.rol.dto.RolReqDto;
import com.jenruco.pedidos.rol.dto.RolResDto;

public interface RolService {

    public RolResDto getRolByNombre(RolReqDto rolReq);
}
