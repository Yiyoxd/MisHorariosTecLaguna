package com.yiyoalfredo.mishorariosteclaguna.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiyoalfredo.mishorariosteclaguna.service.AlumnoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Alumno {
    private String nombre;
    private String numControl;
    private Carrera carrera;
    private List<Materia> materiasCursadas;
    private List<Materia> maateriasFaltantes;
    private final int creditosTotales;

    public Alumno() {
        this.materiasCursadas = new ArrayList<>();
        creditosTotales = 0;
    }

    @JsonCreator
    public Alumno(
            @JsonProperty("nombre") String nombre,
            @JsonProperty("numControl") String numControl,
            @JsonProperty("carrera") Carrera carrera,
            @JsonProperty("materiasCursadas") List<Materia> materiasCursadas) {
        this.nombre = nombre;
        this.numControl = numControl;
        this.carrera = carrera;
        this.materiasCursadas = materiasCursadas == null ? new ArrayList<>() : materiasCursadas;
        maateriasFaltantes = AlumnoService.getListMateriasFaltantes(this);
        creditosTotales = AlumnoService.getCreditos(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public List<Materia> getMateriasCursadas() {
        return materiasCursadas;
    }

    public List<Materia> getMateriasCursadasCopy() {
        return new ArrayList<>(materiasCursadas);
    }

    public void setMateriasCursadas(List<Materia> materiasCursadas) {
        this.materiasCursadas = materiasCursadas == null ? new ArrayList<>() : materiasCursadas;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public List<Materia> getMateriasFaltantes() {
        return maateriasFaltantes;
    }

    public List<Materia> getMateriasFaltantesCopy() {
        return new ArrayList<>(maateriasFaltantes);
    }

    @Override
    public String toString() {
        return numControl + " " + nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Alumno other)) return false;
        return Objects.equals(numControl, other.numControl) &&
                Objects.equals(nombre, other.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numControl, nombre);
    }

    public int getCreditosTotales() {
        return creditosTotales;
    }
}
