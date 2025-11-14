package teste.estudos.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record DetalhesAutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {
}
