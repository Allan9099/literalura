package com.alura.literalura.dto;

import com.alura.literalura.model.Autor;
import java.util.List;

public record LibroDTO(
        String titulo,
        List<Autor>autor,
        List<String> idioma,
        Double numeroDeDescargas
) {
}
