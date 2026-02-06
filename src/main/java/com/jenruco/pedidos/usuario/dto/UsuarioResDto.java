package com.jenruco.pedidos.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResDto {
    private Long id;
    private String nombre;
    private String email;
    private Integer rolId;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
