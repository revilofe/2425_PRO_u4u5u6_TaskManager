package org.practicatrim2.dominio

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


/**
 * **`Actividad` (Superclase o Interfaz):**
 *      - Esta clase no se podrá instanciar, sino que será la superclase de `Tarea` y `Evento`.
 *      - Contendrá la lógica común a todas las actividades.
 *      - Posee un `id`. Se asigna valor automáticamente al crear la instancia. No puede ser nula. No se puede modificar.
 *      - Posee una `fechaCreacion`. Se asigna valor automáticamente al crear la instancia. No puede ser nula. No se puede modificar.
 *      - Posee una `descripcion`. No puede ser nula.
 *      - Debe incluir una propiedad, `detalle`, cuyo `get` retorne la concatenación del `id` y la `descripción`. Se verá así: `<id> + " - " + <descripción>`
 */

abstract class Actividad(val descripcion: String) {
    companion object {
        private var contador = 0
            get() = ++field
    }

    val id: Int
    val fechaCreacion: String
    abstract val detalle: String

    init {
        require(descripcion.isNotBlank()) { "Descripcion cannot be empty" }
        id = Actividad.contador
        fechaCreacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

}

