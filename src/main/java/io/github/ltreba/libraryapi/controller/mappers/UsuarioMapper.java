package io.github.ltreba.libraryapi.controller.mappers;

import io.github.ltreba.libraryapi.controller.dto.UsuarioDTO;
import io.github.ltreba.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO usuarioDTO);
    UsuarioDTO toDto(Usuario usuario);
}
