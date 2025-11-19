package teste.estudos.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank
        String login,
        @NotBlank
        String senha,
        List<String> roles
        ) {
}
