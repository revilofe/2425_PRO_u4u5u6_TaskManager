package org.practicatrim2.dominio


/**
 * Tarea:
 * Hereda las propiedades de Actividad.
 * Posee una propiedad estado, por defecto abierta. Toma valores de la enum class Status = {ABIERTA, CERRADA}
 * Su único constructor es privado. Se debe disponer de un método de clase (companion object) llamado creaInstancia para generar una nueva instancia.
 * Sobreescribe toString. Muestra formateada toda la información de la tarea, en modo Tarea=[nombreAtributo: <valorAtributo>, ...].
 * Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo
 */


/**
 * Clase Tarea que hereda de Actividad
 * @property estado Estado de la tarea
 * @constructor Crea una instancia de Tarea
 * @param descripcion Descripción de la tarea
 * @throws IllegalArgumentException Si la descripción está vacía
 */
class Tarea private constructor(descripcion: String, var estado: Status = Status.ABIERTA) : Actividad(descripcion){

    //sobreescritura de la propiedad detalle
    override val detalle: String
        get() = "$id - $descripcion"

    //Sobreescribe el método toString
    override fun toString(): String {
        return "Tarea=[id: $id, fechaCreacion: $fechaCreacion, descripcion: $descripcion, estado: $estado]"
    }

    companion object{
        fun creaInstancia(descripcion: String): Tarea{
            return Tarea(descripcion)
        }
    }

}


