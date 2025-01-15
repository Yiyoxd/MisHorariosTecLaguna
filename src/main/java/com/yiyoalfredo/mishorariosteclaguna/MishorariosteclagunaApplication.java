package com.yiyoalfredo.mishorariosteclaguna;

import com.yiyoalfredo.mishorariosteclaguna.excepcion.LoginException;
import com.yiyoalfredo.mishorariosteclaguna.model.Alumno;
import com.yiyoalfredo.mishorariosteclaguna.model.MateriaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.BusquedaHorario;
import com.yiyoalfredo.mishorariosteclaguna.service.KardexParser;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MishorariosteclagunaApplication {

    public static void main(String[] args) {
        Alumno alumno;
        //SpringApplication.run(MishorariosteclagunaApplication.class, args);
        try {
            alumno = new KardexParser().parseKardexFromWeb("MATRICULA", "PASSWORD");
        } catch (LoginException e) {
            System.out.println("Credenciales incorrectas");
            return;
        }
        BusquedaHorario busquedaHorario = new BusquedaHorario(alumno);
        List<List<MateriaHorario>> horarios = busquedaHorario.posiblesHorarios(0, 36, 0, 20);
        System.out.println(horarios.size());
    }
}
