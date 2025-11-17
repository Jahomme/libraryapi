package teste.estudos.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import teste.estudos.libraryapi.controller.dto.CadastroLivroDTO;
import teste.estudos.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import teste.estudos.libraryapi.model.Livro;
import teste.estudos.libraryapi.repository.AutorRepository;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);


    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
