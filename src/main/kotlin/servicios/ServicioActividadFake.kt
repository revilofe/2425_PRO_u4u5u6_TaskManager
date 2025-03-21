package org.practicatrim2.servicios

import org.practicatrim2.dominio.Actividad
import org.practicatrim2.dominio.Evento
import org.practicatrim2.dominio.Tarea

class ServicioActividadFake : IServicioActividad {
    override fun crearActividad(descripcion: String): Actividad {
        return Tarea.creaInstancia("fake")
    }

    override fun crearActividad(descripcion: String, ubicacion: String, fechaRealizacion: String): Actividad {
        return Evento.creaInstancia("fake", "fake", "01/01/2021")
    }
}