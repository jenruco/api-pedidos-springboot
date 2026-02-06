package com.jenruco.pedidos.rol.service;

import com.jenruco.pedidos.rol.dto.RolReqDto;
import com.jenruco.pedidos.rol.dto.RolResDto;
import com.jenruco.pedidos.rol.entity.Rol;
import com.jenruco.pedidos.rol.repository.RolRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    public RolResDto getRolByNombre(RolReqDto rolReq) {
         Optional<Rol> rol = rolRepository.findByNombre(rolReq.getNombre());
         return rol.map(r -> modelMapper.map(r, RolResDto.class))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }
}
