package com.jenruco.pedidos.rol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolReqDto {
    @NotBlank
    @Size(max = 50)
    private String nombre;
}
