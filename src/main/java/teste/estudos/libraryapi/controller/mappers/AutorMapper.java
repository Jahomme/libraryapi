package teste.estudos.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import teste.estudos.libraryapi.controller.dto.AutorDTO;
import teste.estudos.libraryapi.controller.dto.DetalhesAutorDTO;
import teste.estudos.libraryapi.model.Autor;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    DetalhesAutorDTO toDTO(Autor autor);
}
