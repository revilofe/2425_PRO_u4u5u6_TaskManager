package org.practicatrim2.repositorio

import org.practicatrim2.dominio.Actividad
import repositorio.IRepositorioActividad

/**
 * Repositorio de actividades en memoria. Implementa la interfaz IRepositorioActividad, almacenando las actividades en memoria.
 */
class RepositorioActividadMemoria : IRepositorioActividad {
    private val actividades = mutableListOf<Actividad>()

    override fun agregarActividad(actividad: Actividad) {
        actividades.add(actividad)
    }

    override fun eliminarActividad(actividad: Actividad) {
        actividades.remove(actividad)
    }

    override fun actualizarActividad(actividad: Actividad) {
        val indice = actividades.indexOfFirst { it.id == actividad.id }
        if (indice != -1) {
            actividades[indice] = actividad
        }
    }

    override fun obtenerActividades(): List<Actividad> {
        return actividades.toList()
    }

    override fun obtenerActividadPorId(id: Int): Actividad? {
        return actividades.find { it.id == id }
    }

}