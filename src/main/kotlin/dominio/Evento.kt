package org.practicatrim2.dominio

/**
 * Evento:
 * Hereda las propiedades de Actividad.
 * Posee una fecha de realización. No puede ser nula. Usa expresiones regulares para validar que la fecha sea válida (formato dd/MM/yyyy).
 * Posee una ubicación, representada mediante una cadena.
 * Posee un detalle que se genera dinámicamente: <id>+ " - " + <ubicacion> + " - " + <descripcion>.
 * Similar a Tarea en cuanto a que su único constructor es privado y dispone del método de clase creaInstancia para crear las instancias.
 * Sobreescribe toString. Muestra formateada toda la información de la tarea, en modo Evento=[nombreAtributo: <valorAtributo>, ...].
 * Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo
 *
 */

/**
 * Clase Evento que hereda de Actividad
 * @property fechaRealizacion Fecha de realización del evento
 * @property ubicacion Ubicación del evento
 * @constructor Crea una instancia de Evento
 * @param descripcion Descripción del evento
 * @param fechaRealizacion Fecha de realización del evento
 * @param ubicacion Ubicación del evento
 * @throws IllegalArgumentException Si la descripción está vacía
 *
 */
class Evento private constructor(descripcion:String, var ubicacion:String, var fechaRealizacion: String) : Actividad(descripcion) {

    init {
        //Fecha de realización del evento
        require(fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}"""))) { "Fecha de realización inválida" }
    }

    override val detalle: String
        get() = "$id - $ubicacion - $descripcion"

    //Sobreescribe el método toString
    override fun toString(): String {
        return "Evento=[id: $id, fechaCreacion: $fechaCreacion, descripcion: $descripcion, fechaRealizacion: $fechaRealizacion, ubicacion: $ubicacion]"
    }

    companion object {
        fun creaInstancia(descripcion: String, ubicacion: String, fechaRealizacion: String): Evento {
            return Evento(descripcion, ubicacion, fechaRealizacion)
        }
    }
}