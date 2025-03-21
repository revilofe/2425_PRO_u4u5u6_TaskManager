package org.practicatrim2.presentacion

import org.practicatrim2.dominio.Actividad
import org.practicatrim2.servicios.IServicioActividad
import org.practicatrim2.servicios.ServicioActividad

//  - De igual forma, la comunicación entre la interfaz de usuario y la lógica de negocio debe estar claramente separada, por lo que se debe utilizar interfaces o abstracciones para comunicarse entre capas.

/*
  - Crea una interfaz de usuario sencilla que muestre un menú:
     - **Opción 1:** Crear nueva actividad (se debe preguntar al usuario si desea crear una Tarea o un Evento, luego solicitar los datos requeridos).
     - **Opción 2:** Listar todas las actividades, mostrando el detalle de cada una.
     - **Opción 3:** Listar las tareas, pudiendo seleccionar entre estados `ABIERTA` o `CERRADA` o ambos.
   - La capa de presentación debe invocar los métodos del servicio para realizar las operaciones solicitadas. No debe contener lógica de negocio y no debe depender de implementaciones concretas de la lógica de aplicación, sino de abstracciones.

 */

class IUAplicacion(var servicioActividad: IServicioActividad) {


    fun inicio() {
        menu()
    }

    private fun menu() {
        var seguir = true
        do {
            println("1. Crear nueva actividad")
            println("2. Listar todas las actividades")
            println("3. Listar las tareas")
            println("4. Salir")
            println("Ingrese una opción:")
            val opcion = readLine()
            when (opcion) {
                "1" ->  {
                    var actividad = crearActividad()
                    if (actividad != null) {
                        println("Actividad creada: $actividad")
                    }
                }

                "2" -> listarActividades()
                "3" -> listarTareas()
                "4" -> seguir = false
                else -> println("Opción inválida")
            }
        } while (seguir)
        return
    }

    private fun listarTareas() {
        TODO("Not yet implemented")
    }

    private fun listarActividades() {
        TODO("Not yet implemented")
    }


    private fun crearActividad(): Actividad? {
        var actividad: Actividad? = null
        // - La capa de presentación debe invocar los métodos del servicio para realizar las operaciones solicitadas. No debe actividadcontener lógica de negocio y no debe depender de implementaciones concretas de la lógica de aplicación, sino de abstracciones.

        // Solicitar al usuario si desea crear una Tarea o un Evento
        do {
            println("¿Qué tipo de actividad desea crear?")
            println("1. Tarea")
            println("2. Evento")
            println("Ingrese una opción:")
            val opcion = readLine()
            when (opcion) {
                "1" -> actividad = crearTarea()
                "2" -> actividad = crearEvento()
                else -> println("Opción inválida")
            }
        } while (opcion != "1" && opcion != "2")
        return actividad
    }

    private fun crearTarea() : Actividad? {
        //Pregunto por la información necesario para tarea y la creo. Si es blanco, vuelvo a pedir
        var descripcion = ""
        do{
            println("Ingrese la descripción de la tarea:")
            descripcion = readln()
            if (descripcion.isNullOrBlank()) {
                println("La descripción no puede estar vacía")
            }
        } while (descripcion.isNullOrBlank())

        return servicioActividad.crearActividad(descripcion)

    }

    private fun crearEvento(): Actividad? {
        // Solicitar al usuario la información necesaria para crear un evento. Si descripción o ubicación son blancos, volver a pedir. Ademas,comprobar que la fecha sea válida.
        var descripcion = ""
        var fechaRealizacion = ""
        var ubicacion = ""


        do{
            println("Ingrese la descripción del evento:")
            descripcion = readln()
            if (descripcion.isNullOrBlank()) {
                println("La descripción no puede estar vacía")
            }
            println("Ingrese la ubicación del evento:")
            ubicacion = readln()
            if (ubicacion.isNullOrBlank()) {
                println("La ubicación no puede estar vacía")
            }
            println("Ingrese la fecha de realización del evento (dd/MM/yyyy):")
            fechaRealizacion = readln()
            val fechaValida = fechaRealizacion.matches(Regex("""\d{2}/\d{2}/\d{4}"""))
            if (!fechaValida) {
                println("Fecha de realización inválida")
            }
        } while (descripcion.isNullOrBlank() || ubicacion.isNullOrBlank() || !fechaValida)
        return servicioActividad.crearActividad(descripcion, ubicacion, fechaRealizacion)
    }


}



