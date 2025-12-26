package io.github.ltreba.libraryapi.controller.mappers;

import io.github.ltreba.libraryapi.controller.dto.AutorDTO;
import io.github.ltreba.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
