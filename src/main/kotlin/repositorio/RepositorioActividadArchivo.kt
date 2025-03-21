package org.practicatrim2.repositorio

import org.practicatrim2.dominio.Actividad
import repositorio.IRepositorioActividad

/**
 * Repositorio de actividades que implementa la interfaz IRepositorioActividad. Almacena las actividades en un archivo.
 */
class RepositorioActividadArchivo : IRepositorioActividad {
    // Este repostiorio almacenara las actividades en archivos
    override fun agregarActividad(actividad: Actividad) {
        // Agrega la actividad al archivo

    }

    override fun eliminarActividad(actividad: Actividad) {
        TODO("Not yet implemented")
    }

    override fun actualizarActividad(actividad: Actividad) {
        TODO("Not yet implemented")
    }

    override fun obtenerActividades(): List<Actividad> {
        TODO("Not yet implemented")
    }

    override fun obtenerActividadPorId(id: Int): Actividad? {
        TODO("Not yet implemented")
    }
}