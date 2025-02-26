package org.practicatrim2

/*
A continuación, te presento la **solución completa** en Kotlin, organizada en las distintas capas (dominio, servicios, datos y presentación). Cada bloque de código incluye comentarios explicativos y se ha aplicado el principio de inversión de dependencias, junto con buenas prácticas y principios SOLID. Revisa la solución y dime si es correcta antes de continuar con la siguiente fase (la explicación detallada o ajustes que consideres).

---

*/

// ====================
// PAQUETE: dominio
// ====================
package dominio


/**
 * Interfaz o clase abstracta que define la actividad.
 * Contiene los campos comunes y la lógica para el getter 'detalle'
 */
abstract class Actividad(
    val id: Int,               // id inmutable y no nulo
    val descripcion: String    // descripción no nula
) {
    // Propiedad 'detalle' que concatena el id y la descripción
    open val detalle: String
        get() = "$id - $descripcion"

    // Método para mostrar detalles (opcional, se puede usar toString)
    open fun mostrarDetalle(): String = detalle
}

/**
 * Enum para el estado de una Tarea
 */
enum class Statu {
    ABIERTA, CERRADA
}

/**
 * Clase Tarea que hereda de Actividad.
 * - El constructor es privado y se instancia mediante creaInstancia.
 * - Tiene un atributo 'estado' que por defecto es ABIERTA.
 */
class Tarea private constructor(
    id: Int,
    descripcion: String,
    var estado: Statu = Statu.ABIERTA
) : Actividad(id, descripcion) {

    // Se sobreescribe la propiedad 'detalle' heredada, reutilizando la lógica de la superclase.
    override val detalle: String
        get() = super.detalle + " [Estado: $estado]"

    // Se sobreescribe toString para mostrar sus detalles.
    override fun toString(): String {
        return "Tarea -> $detalle"
    }

    // Companion object para controlar la creación de instancias.
    companion object {
        // Variable estática para generar ids únicos.
        private var contador: Int = 1

        /**
         * Método para crear una instancia de Tarea.
         * Se encarga de asignar un id único.
         */
        fun creaInstancia(descripcion: String, estado: Statu = Statu.ABIERTA): Tarea {
            val tarea = Tarea(contador, descripcion, estado)
            contador++
            return tarea
        }
    }
}

/**
 * Clase Evento que hereda de Actividad.
 * - Incluye atributos propios: fecha y ubicacion.
 * - El constructor es privado y se instancia mediante creaInstancia.
 */
class Evento private constructor(
    id: Int,
    descripcion: String,
    val fecha: String,      // Para simplicidad, fecha como String (podría ser LocalDate)
    val ubicacion: String
) : Actividad(id, descripcion) {

    // Se sobreescribe la propiedad 'detalle' para incluir fecha y ubicación
    override val detalle: String
        get() = super.detalle + " [Fecha: $fecha, Ubicación: $ubicacion]"

    // Se sobreescribe toString para mostrar sus detalles.
    override fun toString(): String {
        return "Evento -> $detalle"
    }

    // Companion object para controlar la creación de instancias.
    companion object {
        // Variable estática para generar ids únicos.
        private var contador: Int = 1

        /**
         * Método para crear una instancia de Evento.
         * Se encarga de asignar un id único.
         */
        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String): Evento {
            val evento = Evento(contador, descripcion, fecha, ubicacion)
            contador++
            return evento
        }
    }
}

// ====================
// PAQUETE: datos
// ====================

package datos

import dominio.Actividad

/**
 * Interfaz del repositorio de Actividades.
 * Se usa para aplicar el principio de inversión de dependencias.
 */
interface IActividadRepository {
    fun agregar(actividad: Actividad)
    fun obtenerTodas(): List<Actividad>
}

/**
 * Implementación en memoria del repositorio de Actividades.
 * Es una clase sencilla para almacenar las actividades en una lista.
 */
class ActividadRepository : IActividadRepository {
    private val actividades = mutableListOf<Actividad>()

    override fun agregar(actividad: Actividad) {
        actividades.add(actividad)
    }

    override fun obtenerTodas(): List<Actividad> = actividades.toList()
}

// ====================
// PAQUETE: servicios
// ====================

package servicios

import datos.IActividadRepository
import dominio.Actividad
import dominio.Tarea
import dominio.Evento
import dominio.Statu

/**
 * Servicio que gestiona las actividades.
 * Depende de la abstracción IActividadRepository para almacenar y recuperar actividades.
 */
class ActividadService(private val repositorio: IActividadRepository) {

    /**
     * Crea y agrega una nueva Tarea.
     */
    fun crearTarea(descripcion: String, estado: Statu = Statu.ABIERTA) {
        val tarea = Tarea.creaInstancia(descripcion, estado)
        repositorio.agregar(tarea)
    }

    /**
     * Crea y agrega un nuevo Evento.
     */
    fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
        val evento = Evento.creaInstancia(descripcion, fecha, ubicacion)
        repositorio.agregar(evento)
    }

    /**
     * Retorna la lista de todas las actividades registradas.
     */
    fun listarActividades(): List<Actividad> = repositorio.obtenerTodas()
}

// ====================
// PAQUETE: presentacion
// ====================

package presentacion

import servicios.ActividadService
import datos.ActividadRepository
import dominio.Statu
import java.util.Scanner

/**
 * Clase que gestiona la interacción con el usuario a través de la consola.
 * Separa la lógica de presentación de la lógica de negocio.
 */
class ConsolaUI {

    // Se utiliza un Scanner para leer la entrada del usuario.
    private val scanner = Scanner(System.`in`)

    // Instancia del servicio, inyectando la dependencia a través de la interfaz.
    private val actividadService = ActividadService(ActividadRepository())

    /**
     * Método principal que muestra el menú y procesa las opciones del usuario.
     */
    fun iniciar() {
        var salir = false
        while (!salir) {
            mostrarMenu()
            when (leerOpcion()) {
                1 -> crearActividad()
                2 -> listarActividades()
                3 -> {
                    println("Saliendo de la aplicación. ¡Hasta luego!")
                    salir = true
                }
                else -> println("Opción no válida. Intente nuevamente.")
            }
        }
    }

    /**
     * Muestra el menú principal.
     */
    private fun mostrarMenu() {
        println("\n----- Gestor de Actividades -----")
        println("1. Crear nueva actividad")
        println("2. Listar actividades")
        println("3. Salir")
        print("Seleccione una opción: ")
    }

    /**
     * Lee y retorna la opción ingresada por el usuario.
     */
    private fun leerOpcion(): Int {
        return try {
            scanner.nextLine().toInt()
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * Procesa la creación de una actividad, preguntando si es Tarea o Evento.
     */
    private fun crearActividad() {
        println("\nSeleccione el tipo de actividad a crear:")
        println("1. Tarea")
        println("2. Evento")
        print("Opción: ")
        when (leerOpcion()) {
            1 -> {
                print("Ingrese la descripción de la Tarea: ")
                val descripcion = scanner.nextLine()
                // Se puede pedir al usuario definir el estado, pero se usa por defecto ABIERTA
                actividadService.crearTarea(descripcion, Statu.ABIERTA)
                println("Tarea creada exitosamente.")
            }
            2 -> {
                print("Ingrese la descripción del Evento: ")
                val descripcion = scanner.nextLine()
                print("Ingrese la fecha del Evento (ej: 2025-03-15): ")
                val fecha = scanner.nextLine()
                print("Ingrese la ubicación del Evento: ")
                val ubicacion = scanner.nextLine()
                actividadService.crearEvento(descripcion, fecha, ubicacion)
                println("Evento creado exitosamente.")
            }
            else -> println("Opción no válida.")
        }
    }

    /**
     * Lista todas las actividades registradas.
     */
    private fun listarActividades() {
        println("\n--- Lista de Actividades ---")
        val actividades = actividadService.listarActividades()
        if (actividades.isEmpty()) {
            println("No hay actividades registradas.")
        } else {
            actividades.forEach { println(it.toString()) }
        }
    }
}

// ====================
// FUNCIÓN MAIN
// ====================

package main

import presentacion.ConsolaUI

/**
 * Función main que inicia la aplicación.
 */
fun main() {
    val consolaUI = ConsolaUI()
    consolaUI.iniciar() //Inyectar servicio
}
/*

### Explicación General:

1. **Dominio:**
- Se define la abstracción `Actividad` con sus campos inmutables y la propiedad `detalle` que concatena el id y la descripción.
- `Tarea` y `Evento` heredan de `Actividad`, implementando constructores privados y el patrón *companion object* para crear instancias con un id único.
- Se incluye un enum `Statu` para el estado de la tarea.

2. **Datos:**
- Se crea la interfaz `IActividadRepository` que define los métodos de agregar y obtener actividades.
- `ActividadRepository` es una implementación en memoria de esta interfaz.

3. **Servicios:**
- `ActividadService` contiene la lógica de negocio para crear y listar actividades. Depende de la abstracción `IActividadRepository`, lo que permite aplicar la inversión de dependencias.

4. **Presentación:**
- `ConsolaUI` es la capa de interacción con el usuario. Muestra un menú, procesa entradas y comunica las peticiones a `ActividadService`.

5. **Función Main:**
- Se instancia la interfaz de usuario y se inicia la aplicación de consola.

*/