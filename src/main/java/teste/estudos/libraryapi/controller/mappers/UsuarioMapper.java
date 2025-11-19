package teste.estudos.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import teste.estudos.libraryapi.controller.dto.UsuarioDTO;
import teste.estudos.libraryapi.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario autor);
}
