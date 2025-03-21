package org.practicatrim2

import org.practicatrim2.dominio.Actividad
import org.practicatrim2.presentacion.IUAplicacion
import org.practicatrim2.servicios.ServicioActividad
import org.practicatrim2.servicios.ServicioActividadFake

fun main() {
    val iu = IUAplicacion(ServicioActividadFake())
    iu.inicio()
}