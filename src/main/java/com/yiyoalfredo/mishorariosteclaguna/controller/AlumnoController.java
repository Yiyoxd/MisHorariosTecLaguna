package com.yiyoalfredo.mishorariosteclaguna.controller;

import com.yiyoalfredo.mishorariosteclaguna.dto.AlumnoDto;
import com.yiyoalfredo.mishorariosteclaguna.dto.CredencialesDTO;
import com.yiyoalfredo.mishorariosteclaguna.dto.MateriaDto;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.AlumnoService;
import com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @PostMapping("/login")
    public AlumnoDto obtenerAlumno(@RequestBody CredencialesDTO credenciales) {
        return alumnoService.cargarAlumnoDtoDesdeCredenciales(credenciales);
    }

    @PostMapping("/materias-cursadas")
    public List<MateriaDto> obtenerMateriasCursadas(@RequestBody CredencialesDTO credenciales) {
        AlumnoDto alumnoDto = alumnoService.cargarAlumnoDtoDesdeCredenciales(credenciales);
        return alumnoDto.getMateriasCursadas();
    }

    @PostMapping("/materias-faltantes")
    public List<MateriaDto> obtenerMateriasFaltantes(@RequestBody CredencialesDTO credenciales) {
        Alumno alumno = alumnoService.cargarAlumnoDesdeCredenciales(credenciales);
        return alumnoService.getMateriasFaltantesDto(alumno);
    }

    @PostMapping("horarios-disponibles")
    public List<List<MateriaHorario>> obtenerHorariosDisponibles(@RequestBody CredencialesDTO credenciales) {
        Alumno alumno = alumnoService.cargarAlumnoDesdeCredenciales(credenciales);
        BusquedaHorario service = new BusquedaHorario(alumno);
        return service.posiblesHorarios(1, 10);
    }
}
