A continuación, te presento la **explicación detallada** del código y una **guía de evaluación** para que el profesor pueda calificar el ejercicio de forma sencilla y objetiva.

---

## Explicación del Código

### 1. Dominio (Paquete `dominio`)

- **Clase `Actividad` (abstracta):**  
  Define los atributos comunes (*id* y *descripción*) y la propiedad `detalle`, que concatena estos valores. Esto permite centralizar la lógica común para ambas subclases, facilitando la reutilización de código.
  ```kotlin
  abstract class Actividad(
      val id: Int,
      val descripcion: String
  ) {
      open val detalle: String
          get() = "$id - $descripcion"
  
      open fun mostrarDetalle(): String = detalle
  }
  ```

- **Enum `Statu`:**  
  Define los estados posibles para una *Tarea*.
  ```kotlin
  enum class Statu {
      ABIERTA, CERRADA
  }
  ```

- **Clase `Tarea`:**  
  Hereda de `Actividad` y añade el atributo `estado`, con un valor por defecto de `ABIERTA`.
    - El constructor es **privado**; la instancia se crea mediante el método `creaInstancia` del *companion object*, que también se encarga de generar un *id* único.
    - Se sobreescribe la propiedad `detalle` para incluir el estado y el método `toString` para presentar sus detalles.
  ```kotlin
  class Tarea private constructor(
      id: Int,
      descripcion: String,
      var estado: Statu = Statu.ABIERTA
  ) : Actividad(id, descripcion) {
  
      override val detalle: String
          get() = super.detalle + " [Estado: $estado]"
  
      override fun toString(): String {
          return "Tarea -> $detalle"
      }
  
      companion object {
          private var contador: Int = 1
  
          fun creaInstancia(descripcion: String, estado: Statu = Statu.ABIERTA): Tarea {
              val tarea = Tarea(contador, descripcion, estado)
              contador++
              return tarea
          }
      }
  }
  ```

- **Clase `Evento`:**  
  Similar a `Tarea`, pero con atributos propios como `fecha` y `ubicacion`.
    - El constructor es privado y se utiliza `creaInstancia` para crear objetos, asignando automáticamente el *id*.
    - Se sobreescribe `detalle` y `toString` para incorporar la información adicional.
  ```kotlin
  class Evento private constructor(
      id: Int,
      descripcion: String,
      val fecha: String,
      val ubicacion: String
  ) : Actividad(id, descripcion) {
  
      override val detalle: String
          get() = super.detalle + " [Fecha: $fecha, Ubicación: $ubicacion]"
  
      override fun toString(): String {
          return "Evento -> $detalle"
      }
  
      companion object {
          private var contador: Int = 1
  
          fun creaInstancia(descripcion: String, fecha: String, ubicacion: String): Evento {
              val evento = Evento(contador, descripcion, fecha, ubicacion)
              contador++
              return evento
          }
      }
  }
  ```

### 2. Datos (Paquete `datos`)

- **Interfaz `IActividadRepository`:**  
  Define la abstracción para almacenar y recuperar actividades, aplicando el principio de inversión de dependencias.
  ```kotlin
  interface IActividadRepository {
      fun agregar(actividad: Actividad)
      fun obtenerTodas(): List<Actividad>
  }
  ```

- **Implementación `ActividadRepository`:**  
  Una versión en memoria del repositorio, que utiliza una lista mutable para guardar las actividades.
  ```kotlin
  class ActividadRepository : IActividadRepository {
      private val actividades = mutableListOf<Actividad>()
  
      override fun agregar(actividad: Actividad) {
          actividades.add(actividad)
      }
  
      override fun obtenerTodas(): List<Actividad> = actividades
  }
  ```

### 3. Servicios (Paquete `servicios`)

- **Clase `ActividadService`:**  
  Contiene la lógica de negocio para crear y listar actividades.
    - Utiliza **inyección de dependencias**: el servicio recibe en su constructor una instancia de `IActividadRepository`, lo que permite cambiar la implementación del repositorio sin modificar la lógica de negocio.
    - Dispone de métodos para crear tareas y eventos, invocando los métodos `creaInstancia` de cada clase.
  ```kotlin
  class ActividadService(private val repositorio: IActividadRepository) {
  
      fun crearTarea(descripcion: String, estado: Statu = Statu.ABIERTA) {
          val tarea = Tarea.creaInstancia(descripcion, estado)
          repositorio.agregar(tarea)
      }
  
      fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
          val evento = Evento.creaInstancia(descripcion, fecha, ubicacion)
          repositorio.agregar(evento)
      }
  
      fun listarActividades(): List<Actividad> = repositorio.obtenerTodas()
  }
  ```

### 4. Presentación (Paquete `presentacion`)

- **Clase `ConsolaUI`:**  
  Maneja la interacción con el usuario a través de la consola.
    - Muestra un menú con opciones para crear actividades o listar las registradas.
    - Separa claramente la lógica de presentación de la lógica de negocio, invocando a `ActividadService` para procesar las peticiones del usuario.
  ```kotlin
  class ConsolaUI {
  
      private val scanner = Scanner(System.`in`)
      private val actividadService = ActividadService(ActividadRepository())
  
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
  
      private fun mostrarMenu() {
          println("\n----- Gestor de Actividades -----")
          println("1. Crear nueva actividad")
          println("2. Listar actividades")
          println("3. Salir")
          print("Seleccione una opción: ")
      }
  
      private fun leerOpcion(): Int {
          return try {
              scanner.nextLine().toInt()
          } catch (e: Exception) {
              -1
          }
      }
  
      private fun crearActividad() {
          println("\nSeleccione el tipo de actividad a crear:")
          println("1. Tarea")
          println("2. Evento")
          print("Opción: ")
          when (leerOpcion()) {
              1 -> {
                  print("Ingrese la descripción de la Tarea: ")
                  val descripcion = scanner.nextLine()
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
  ```

### 5. Función `main` (Paquete `main`)

- La función `main` es el punto de entrada de la aplicación, donde se instancia la interfaz de usuario (`ConsolaUI`) y se llama al método `iniciar()`.
  ```kotlin
  fun main() {
      val consolaUI = ConsolaUI()
      consolaUI.iniciar()
  }
  ```

---

## Guía de Evaluación para el Profesor

Para evaluar este ejercicio, se puede utilizar la siguiente lista de criterios:

### **1. Modelo de Dominio (Actividades)**
- **Definición de la superclase o interfaz `Actividad`:**
    - Se verifica que *id* y *descripción* sean inmutables y no nulos.
    - La propiedad `detalle` concatena correctamente *id* y *descripción*.

- **Implementación de `Tarea` y `Evento`:**
    - Ambos tienen constructores privados y el método `creaInstancia` en su *companion object*.
    - *Tarea* incluye el atributo `estado` con un valor por defecto, y sobreescribe `toString` para mostrar detalles.
    - *Evento* incluye atributos adicionales (`fecha` y `ubicacion`) y sobreescribe `toString` para mostrar sus detalles.

### **2. Principios SOLID y Buenas Prácticas**
- **Inversión de Dependencias:**
    - La capa de lógica (servicios) depende de la abstracción `IActividadRepository` y no de la implementación concreta.
- **Uso de Métodos Estáticos:**
    - La creación de instancias se gestiona mediante métodos de clase en los *companion objects*.
- **Documentación y Comentarios:**
    - Cada clase y método están debidamente comentados, explicando la finalidad y la lógica implementada.

### **3. Arquitectura en Capas y Separación de Responsabilidades**
- **Capa de Presentación (UI):**
    - La clase `ConsolaUI` gestiona la interacción con el usuario de forma independiente de la lógica de negocio.
- **Capa de Lógica de Aplicación (Servicios):**
    - `ActividadService` centraliza la creación y consulta de actividades.
- **Capa de Datos:**
    - Se utiliza una implementación en memoria (`ActividadRepository`) que cumple con la interfaz `IActividadRepository`.

### **4. Interacción y Flujo de la Aplicación**
- **Interfaz de Usuario:**
    - La aplicación presenta un menú que permite crear una nueva actividad (Tarea o Evento) y listar todas las actividades.
    - Se validan las entradas del usuario y se gestionan opciones no válidas.

### **5. Aspectos Adicionales**
- **Correcta Generación de IDs:**
    - Los *ids* se generan de forma automática y única para cada instancia.
- **Aplicación de Comentarios y Estilo de Código:**
    - Se debe verificar que el código esté claro, bien estructurado y siga las buenas prácticas de programación en Kotlin.

---

Esta solución y la guía de evaluación aseguran que se hayan contemplado los **resultados de aprendizaje** y los **criterios de evaluación** definidos, además de integrar una arquitectura en capas y aplicar los principios SOLID (especialmente la inversión de dependencias).

¿Te parece correcta la solución y la guía de evaluación? ¿Deseas algún ajuste o comentario adicional?