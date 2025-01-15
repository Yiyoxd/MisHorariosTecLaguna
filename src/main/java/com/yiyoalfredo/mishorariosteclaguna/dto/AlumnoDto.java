package com.yiyoalfredo.mishorariosteclaguna.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;

import java.util.List;
import java.util.Objects;

public class AlumnoDto {
    private final String nombre;
    private final String numControl;
    private final int creditos;
    private final List<MateriaDto> materiasCursadas;

    @JsonCreator
    public AlumnoDto(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("numControl") String numControl,
            @JsonProperty("creditos") int creditos,
            @JsonProperty("materiasCursadas") List<MateriaDto> materiasCursadas) {
        this.nombre = nombre;
        this.numControl = numControl;
        this.creditos = creditos;
        this.materiasCursadas = materiasCursadas;
    }

    public AlumnoDto(Alumno alumno) {
        this (
            alumno.getNombre(),
            alumno.getNumControl(),
            alumno.getCreditosTotales(),
            alumno.getMateriasCursadas().stream().map(MateriaDto::new).toList()
        );
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumControl() {
        return numControl;
    }

    public int getCreditos() {
        return creditos;
    }

    public List<MateriaDto> getMateriasCursadas() {
        return materiasCursadas;
    }
}
