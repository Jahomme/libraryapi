package teste.estudos.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "Autor")
public record AutorDTO(
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 100, message = "Campos fora do tamanho padrão!")
        String nome,
        @NotNull(message = "campo obrigatório!")
        @Past(message = "Não pode ser uma data futura!")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 50, message = "Campos fora do tamanho padrão!")
        String nacionalidade) {
}
