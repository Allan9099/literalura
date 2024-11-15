package com.alura.literalura.model;

import java.util.List;

public class RespuestaAPI {
    private List<Libro> resultados;

    public RespuestaAPI(){}

    public RespuestaAPI(List<Libro> resultados) {
        this.resultados = getResultados();
    }

    public List<Libro> getResultados() {
        return resultados;
    }

    public void setResultados(List<Libro> resultados) {
        this.resultados = resultados;
    }
}
