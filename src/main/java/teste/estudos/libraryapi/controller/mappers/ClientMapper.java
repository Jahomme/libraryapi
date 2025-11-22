package teste.estudos.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import teste.estudos.libraryapi.controller.dto.ClientDTO;
import teste.estudos.libraryapi.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);

    ClientDTO toDTO(Client client);
}
