package com.jenruco.pedidos.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    private String token;
    private String email;
    private String mensaje;
}
