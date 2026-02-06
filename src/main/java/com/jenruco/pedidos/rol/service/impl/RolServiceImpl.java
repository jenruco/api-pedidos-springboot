package com.jenruco.pedidos.rol.service.impl;

import com.jenruco.pedidos.carrito.dto.AgregarCarritoReqDto;
import com.jenruco.pedidos.rol.dto.RolReqDto;
import com.jenruco.pedidos.rol.dto.RolResDto;
import com.jenruco.pedidos.rol.entity.Rol;
import com.jenruco.pedidos.rol.repository.RolRepository;
import com.jenruco.pedidos.rol.service.RolService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final ModelMapper modelMapper;

    /**
     * Obtiene un rol por su nombre.
     * 
     * @param request {@link RolReqDto} objeto con el nombre del rol a buscar
     * @return {@link RolResDto} con los datos del rol encontrado
     * 
     * @author Henry Pérez 
     * @version 1.0
     * @since 05-02-2026
     * 
     */
    @Override
    public RolResDto getRolByNombre(RolReqDto rolReq) {
         Optional<Rol> rol = rolRepository.findByNombre(rolReq.getNombre());
         return rol.map(r -> modelMapper.map(r, RolResDto.class))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rol no encontrado"));
    }
}
