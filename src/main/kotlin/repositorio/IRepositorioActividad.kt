package repositorio

import org.practicatrim2.dominio.Actividad

interface IRepositorioActividad {

    // Las interfaces de repositorios deben ser implementadas por clases concretas que interactúen con la base de datos, memoria, archivos.

    // Las clases repositorio deben ser inyectadas en las clases de servicios.

    // Los metodos de las clases repositorio suele ser CRUD (Create, Read, Update, Delete) y otros metodos necesarios para interactuar con la base de datos, memoria, archivos.

    fun agregarActividad(actividad: Actividad)
    fun eliminarActividad(actividad: Actividad)
    fun actualizarActividad(actividad: Actividad)
    fun obtenerActividades(): List<Actividad>
    fun obtenerActividadPorId(id: Int): Actividad?

}