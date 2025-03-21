package org.practicatrim2.servicios

import org.practicatrim2.dominio.Actividad

interface IServicioActividad {
    fun crearActividad(descripcion: String): Actividad
    fun crearActividad(descripcion: String, ubicacion: String, fechaRealizacion: String): Actividad
}