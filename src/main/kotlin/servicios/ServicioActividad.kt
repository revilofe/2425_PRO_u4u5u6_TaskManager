package org.practicatrim2.servicios

import org.practicatrim2.dominio.Actividad
import org.practicatrim2.dominio.Evento
import org.practicatrim2.dominio.Tarea
import repositorio.IRepositorioActividad

// La lógica de negocio debe depender de abstracciones (por ejemplo, interfaces de repositorios) y no de implementaciones concretas.


class ServicioActividad() : IServicioActividad {
    override fun crearActividad(descripcion: String):Actividad {
        return Tarea.creaInstancia(descripcion)
    }

    override fun crearActividad(descripcion: String, ubicacion: String, fechaRealizacion: String): Actividad {
        return Evento.creaInstancia(descripcion, ubicacion, fechaRealizacion)
    }

}
