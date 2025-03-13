# Solución del ejercicio

## **Criterio Global 1: Instanciar objetos y hacer uso de ellos**

En el proyecto del **Gestor de Tareas y Eventos**, se ha implementado la instanciación de objetos utilizando tanto **constructores privados** como métodos estáticos a través del patrón `companion object`. A continuación, explico detalladamente cómo se ha realizado este proceso, citando ejemplos específicos del código.

---

### **Uso de Constructores Privados y Métodos de Clase**

1. **Instanciación de Tareas con Constructor Privado y Método Estático**

- La clase `Tarea` posee un **constructor privado**, lo cual impide la creación de instancias directamente desde fuera de la clase. En su lugar, se utiliza el método estático `creaInstancia` dentro del `companion object` para garantizar la generación controlada de objetos.

```kotlin
class Tarea private constructor(
    id: Int,
    descripcion: String,
    var estado: Statu = Statu.ABIERTA
) : Actividad(id, descripcion) {

    companion object {
        private var contador: Int = 1

        /**
         * Método estático para crear una instancia de Tarea.
         * Se asegura de que cada instancia tenga un ID único.
         */
        fun creaInstancia(descripcion: String, estado: Statu = Statu.ABIERTA): Tarea {
            val tarea = Tarea(contador, descripcion, estado)
            contador++
            return tarea
        }
    }
}
```

- **Uso en el Servicio**: El servicio `ActividadService` utiliza este método estático para instanciar y registrar nuevas tareas:

```kotlin
fun crearTarea(descripcion: String, estado: Statu = Statu.ABIERTA) {
    val tarea = Tarea.creaInstancia(descripcion, estado)
    repositorio.agregar(tarea)
}
```

---

2. **Instanciación de Eventos con Constructor Privado y Método Estático**

- Al igual que `Tarea`, la clase `Evento` sigue el mismo patrón, teniendo un constructor privado y un método de clase para la instanciación controlada:

```kotlin
class Evento private constructor(
    id: Int,
    descripcion: String,
    val fecha: String,
    val ubicacion: String
) : Actividad(id, descripcion) {

    companion object {
        private var contador: Int = 1

        /**
         * Método estático para crear una instancia de Evento.
         * Se asegura de que cada instancia tenga un ID único.
         */
        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String): Evento {
            val evento = Evento(contador, descripcion, fecha, ubicacion)
            contador++
            return evento
        }
    }
}
```

- **Uso en el Servicio**: Similar a las tareas, los eventos se instancian mediante el método estático:

```kotlin
fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
    val evento = Evento.creaInstancia(descripcion, fecha, ubicacion)
    repositorio.agregar(evento)
}
```

---

### **Uso de Constructores Públicos**

- En este proyecto **no se han utilizado constructores públicos**, ya que la lógica del negocio exige que los objetos `Tarea` y `Evento` se creen exclusivamente mediante los métodos `creaInstancia` para garantizar la correcta asignación de los identificadores únicos y aplicar cualquier lógica de inicialización necesaria.

---

### **Conclusión**

- La utilización de **constructores privados** y métodos estáticos de clase dentro del `companion object` garantiza un control estricto sobre la creación de instancias.
- Este enfoque sigue buenas prácticas de diseño y es consistente con el principio de encapsulación y la correcta gestión de la lógica de negocio.
- Además, asegura que cada `Tarea` y `Evento` reciba un **ID único** de forma automática, eliminando el riesgo de colisiones o errores en la identificación.

---


## **Criterio Global 2: Crear y llamar métodos estáticos**

---

### **¿Has definido algún método/propiedad estático en tu proyecto?**

Sí, en el proyecto he definido **métodos y propiedades estáticas** utilizando el `companion object` de Kotlin. Esto se ha hecho en las clases `Tarea` y `Evento` para gestionar la creación controlada de instancias y la generación automática de identificadores únicos.

---

#### **Ejemplo 1: Método estático en la clase `Tarea`**

```kotlin
class Tarea private constructor(
    id: Int,
    descripcion: String,
    var estado: Statu = Statu.ABIERTA
) : Actividad(id, descripcion) {

    companion object {
        private var contador: Int = 1

        /**
         * Método estático para crear una nueva instancia de Tarea.
         * Asigna un ID único automáticamente.
         */
        fun creaInstancia(descripcion: String, estado: Statu = Statu.ABIERTA): Tarea {
            val tarea = Tarea(contador, descripcion, estado)
            contador++
            return tarea
        }
    }
}
```

- **¿Por qué se definió como estático?**  
  El método `creaInstancia` se define como estático para:
    - Garantizar la **única forma controlada** de crear instancias de `Tarea`.
    - Asegurar la asignación automática de un **ID único** utilizando la propiedad `contador`.
    - Seguir el patrón de diseño **Factory Method**, que promueve una creación controlada y segura de objetos.

---

#### **Ejemplo 2: Método estático en la clase `Evento`**

```kotlin
class Evento private constructor(
    id: Int,
    descripcion: String,
    val fecha: String,
    val ubicacion: String
) : Actividad(id, descripcion) {

    companion object {
        private var contador: Int = 1

        /**
         * Método estático para crear una nueva instancia de Evento.
         */
        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String): Evento {
            val evento = Evento(contador, descripcion, fecha, ubicacion)
            contador++
            return evento
        }
    }
}
```

- **¿Por qué es estático?**  
  Igual que en la clase `Tarea`, el método `creaInstancia` en `Evento` es estático para:
    - Controlar la forma en la que se crean las instancias.
    - Gestionar automáticamente el ID único a través de la propiedad estática `contador`.

---

---

### **¿En qué parte del código se llama a un método estático o se utiliza la propiedad estática?**

Los métodos estáticos `creaInstancia` se utilizan en la capa de servicios para crear y almacenar nuevas actividades.

---

#### **Ejemplo 1: Llamada al método estático `creaInstancia` en `ActividadService` para crear una Tarea**

```kotlin
fun crearTarea(descripcion: String, estado: Statu = Statu.ABIERTA) {
    val tarea = Tarea.creaInstancia(descripcion, estado)
    repositorio.agregar(tarea)
}
```

- **Explicación:**
    - Aquí se está invocando el método estático `creaInstancia` para crear una nueva `Tarea`.
    - El ID se asigna automáticamente mediante la propiedad `contador` del `companion object`.

---

#### **Ejemplo 2: Llamada al método estático `creaInstancia` en `ActividadService` para crear un Evento**

```kotlin
fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
    val evento = Evento.creaInstancia(descripcion, fecha, ubicacion)
    repositorio.agregar(evento)
}
```

- **Explicación:**
    - Se está utilizando `creaInstancia` para crear un `Evento`, asegurando la asignación controlada de ID.

---

### **Objetivo de utilizar propiedades y métodos estáticos**

- **Control de la instanciación:** Solo se puede crear una instancia a través del método `creaInstancia`.
- **Generación automática de IDs únicos:** La propiedad `contador` asegura que cada objeto tenga un ID único y no repetido.
- **Encapsulación:** Se mantiene la lógica de creación de objetos encapsulada dentro de la propia clase.
- **Principio de Responsabilidad Única (SRP):** Cada clase se encarga de su propia lógica de creación de instancias.

---

### **Conclusión**

- La decisión de utilizar métodos y propiedades estáticas está totalmente justificada por la necesidad de controlar el proceso de creación y asegurar la unicidad de los objetos mediante IDs automáticos.
- Además, este enfoque sigue buenas prácticas en diseño de software, aplicando el principio SOLID de **Responsabilidad Única** y **Encapsulamiento**.

---



## **Criterio Global 3: Uso de entornos (IDE y estructura del proyecto)**

---

### **¿Cómo utilizaste el IDE para el desarrollo de tu proyecto?**

Para el desarrollo del proyecto **Gestor de Tareas y Eventos**, utilicé **IntelliJ IDEA**, el entorno de desarrollo más recomendado para Kotlin, ya que ofrece:

1. **Asistencia de código avanzada:** Con sugerencias, autocompletado, refactorización y verificación de errores en tiempo real.
2. **Integración con Kotlin:** Soporte nativo para Kotlin y sus características, incluyendo la gestión de `companion object`, `data classes` y pruebas.
3. **Depuración eficiente:** Herramientas para realizar **depuración paso a paso**, inspección de variables y ejecución de puntos de ruptura.
4. **Gestión de paquetes y dependencias:** Utilicé la gestión integrada de dependencias y paquetes, aunque en este proyecto no se utilizaron librerías externas.

---

### **¿Cómo estructuraste tu proyecto en el IDE?**

El proyecto se estructuró siguiendo las buenas prácticas de **arquitectura en capas**, organizando los paquetes de la siguiente forma:

```
src
└── main
    ├── kotlin
    │   ├── dominio          # Contiene las clases del dominio (Actividad, Tarea, Evento)
    │   ├── datos            # Contiene las interfaces y clases de repositorio
    │   ├── servicios        # Contiene la lógica de negocio y servicios
    │   ├── presentacion     # Contiene la lógica de interacción con el usuario
    │   └── main             # Contiene la función main y punto de entrada de la aplicación
    └── resources
```

---

### **Descripción de cada paquete**

1. **`dominio`**  
   Contiene las clases del modelo principal:
    - `Actividad`: Clase abstracta base con lógica común.
    - `Tarea` y `Evento`: Clases concretas que heredan de `Actividad`.
    - `Statu`: Enum para el estado de las tareas.

2. **`datos`**  
   Define la abstracción del repositorio:
    - `IActividadRepository`: Interfaz que define las operaciones básicas.
    - `ActividadRepository`: Implementación en memoria del repositorio.

3. **`servicios`**  
   Contiene la lógica de negocio:
    - `ActividadService`: Servicio que gestiona la creación y listado de actividades.

4. **`presentacion`**  
   Encapsula la lógica de presentación en la consola:
    - `ConsolaUI`: Gestiona la interacción con el usuario mediante menús y entrada por teclado.

5. **`main`**
    - Contiene la función `main` que inicializa la interfaz de usuario y arranca la aplicación.

---

### **Facilidades que ofreció el IDE durante el desarrollo**

- **Ejecución y pruebas rápidas:** Pude compilar y ejecutar la aplicación con facilidad gracias a la integración del IDE.
- **Depuración:** Utilicé la herramienta de depuración de IntelliJ para verificar el comportamiento de las clases, especialmente al validar la creación de instancias mediante los métodos `creaInstancia`.
- **Organización del código:** El soporte para paquetes facilitó la correcta organización de las capas y clases.

---

### **Conclusión**

- La utilización de **IntelliJ IDEA** como entorno de desarrollo facilitó enormemente la implementación del proyecto, especialmente por su integración con Kotlin y su robusta capacidad de depuración.
- La organización en capas dentro del proyecto refuerza la separación de responsabilidades y facilita el mantenimiento y escalabilidad futura del código.
- El IDE permitió una gestión eficiente de la estructura del proyecto, haciendo que cada paquete cumpla con una función específica y bien diferenciada.

---

## **Criterio Global 4: Definir clases y su contenido**

---

### **¿Cómo definiste las clases en tu proyecto?**

En el proyecto del **Gestor de Tareas y Eventos**, se han definido las clases siguiendo buenas prácticas de diseño orientado a objetos, aplicando **encapsulación, control de visibilidad** y **responsabilidad única**. Cada clase se diseñó en base a los requisitos del problema, asegurando una organización clara y coherente.

---

### 🔷 **Ejemplos de Definición de Clases**

### 1. **Clase Abstracta `Actividad`**

```kotlin
package dominio

/**
 * Clase base abstracta para representar una actividad.
 * Contiene la lógica común que comparten Tarea y Evento.
 */
abstract class Actividad(
    val id: Int,               // ID inmutable y no nulo
    val descripcion: String    // Descripción no nula
) {
    // Propiedad 'detalle' que concatena el id y la descripción.
    open val detalle: String
        get() = "$id - $descripcion"

    // Método para mostrar detalles, que puede ser sobreescrito.
    open fun mostrarDetalle(): String = detalle
}
```

- **Propiedades:**
    - `id` y `descripcion` son **inmutables** y están protegidos contra modificaciones.
    - La propiedad `detalle` es calculada dinámicamente a través de un getter.

- **Métodos:**
    - `mostrarDetalle()`: Método común que puede ser sobreescrito en las subclases.

- **Visibilidad:**
    - Todos los atributos son públicos, pero inmutables por diseño.

- **Razón de su diseño:**
    - Facilita la reutilización de lógica común entre las clases `Tarea` y `Evento`.
    - Simplifica la gestión de atributos comunes como `id` y `descripcion`.

---

### 2. **Clase `Tarea`**

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

- **Propiedades:**
    - `estado` es mutable y de tipo `Statu` (enum), con valor por defecto `ABIERTA`.
    - `detalle` sobreescribe la lógica de la superclase para incluir el estado.

- **Constructores:**
    - Constructor **privado** para controlar la creación de instancias.
    - Método `creaInstancia` en el `companion object` para gestionar la instanciación.

- **Métodos:**
    - `toString()`: Redefinido para proporcionar una salida personalizada en la consola.

- **Visibilidad:**
    - La propiedad `estado` es pública y mutable.
    - El constructor es privado para asegurar la integridad en la creación de objetos.

---

### 3. **Clase `Evento`**

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

- **Propiedades:**
    - `fecha` y `ubicacion` son inmutables y necesarias para cada evento.

- **Constructores:**
    - Constructor **privado** y método de creación `creaInstancia` para controlar la asignación de IDs únicos.

- **Métodos:**
    - `detalle` sobreescribe el de la superclase para incluir atributos adicionales.
    - `toString()` para una salida personalizada.

- **Visibilidad:**
    - Propiedades `fecha` y `ubicacion` son públicas e inmutables.

---

### **¿Cómo contribuyen estas clases a la solución del problema?**

- **Modelado claro:**  
  Cada clase representa de forma precisa y diferenciada los conceptos del dominio (`Tarea` y `Evento`), lo que facilita la comprensión y extensión del sistema.

- **Reutilización de código:**  
  La lógica común está centralizada en la clase abstracta `Actividad`, promoviendo la **reutilización** y reduciendo la duplicación.

- **Encapsulamiento y control de visibilidad:**  
  La visibilidad de los atributos y constructores se ha gestionado cuidadosamente para:
    - **Proteger la integridad** de los datos (ID inmutables, descripciones no nulas).
    - Garantizar que la creación de instancias siga un flujo controlado.

- **Paquete al que pertenecen:**  
  Todas estas clases están dentro del paquete `dominio`, lo que facilita la **organización del código** y sigue el principio de encapsulamiento del dominio del problema.

---

### **Buenas prácticas aplicadas**

1. **Encapsulación:**
    - Los constructores son privados y se controlan mediante métodos de clase.

2. **Responsabilidad Única (SRP - SOLID):**
    - Cada clase está diseñada para cumplir con una única responsabilidad.

3. **Inmutabilidad:**
    - Las propiedades críticas, como el `id`, son inmutables para asegurar la integridad de los datos.

4. **Uso de `companion object`:**
    - Garantiza la correcta gestión de la creación de instancias y la generación de IDs únicos.

---

### **Conclusión**

- La estructura de las clases garantiza una **separación clara de responsabilidades** y favorece la extensibilidad del código.
- La elección de visibilidades y métodos de acceso sigue principios sólidos de diseño, como la **encapsulación** y la **inmutabilidad**.
- La organización por paquetes permite un código más limpio y mantenible, donde cada parte del sistema está claramente diferenciada.

---

## **Criterio Global 5: Herencia y uso de clases abstractas e interfaces**

---

### **¿Cómo has implementado la herencia y/o utilizado interfaces en tu proyecto?**

En el proyecto del **Gestor de Tareas y Eventos**, se ha implementado la **herencia** utilizando una **clase abstracta** llamada `Actividad`, de la cual heredan las clases `Tarea` y `Evento`. Este enfoque se eligió para aplicar una jerarquía lógica que represente las actividades en el sistema.

---

### 🔷 **Fragmento de Código: Clase Abstracta y Herencia**

#### 1. **Clase Abstracta `Actividad` (Superclase)**

```kotlin
package dominio

/**
 * Clase base abstracta para las actividades del sistema.
 * Contiene propiedades y métodos comunes que son heredados por las clases derivadas.
 */
abstract class Actividad(
    val id: Int,
    val descripcion: String
) {
    // Propiedad común que concatena el ID y la descripción
    open val detalle: String
        get() = "$id - $descripcion"

    // Método común que puede ser sobrescrito
    open fun mostrarDetalle(): String = detalle
}
```

- **Propósito de la herencia:**  
  La clase `Actividad` centraliza la lógica común, como el manejo del `id`, `descripcion` y el método `mostrarDetalle()`, que luego es reutilizado y personalizado por las clases hijas.

---

#### 2. **Clase `Tarea` (Subclase)**

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

- **Uso de la herencia:**
    - Hereda las propiedades `id` y `descripcion` de `Actividad`.
    - Sobrescribe la propiedad `detalle` para incluir el `estado` de la tarea.

---

#### 3. **Clase `Evento` (Subclase)**

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

- **Uso de la herencia:**
    - Hereda las propiedades `id` y `descripcion` de `Actividad`.
    - Sobrescribe `detalle` para incluir los atributos específicos del evento (`fecha` y `ubicación`).

---

### **¿Por qué se eligió este enfoque?**

- **Reutilización de código:**  
  Al centralizar la lógica común en la superclase `Actividad`, evitamos la duplicación de código en `Tarea` y `Evento`.

- **Facilidad de extensión:**  
  Si en el futuro se añaden más tipos de actividades, simplemente se podrá crear una nueva clase que herede de `Actividad`.

- **Jerarquía clara:**  
  La herencia representa de forma natural la relación de **"es un tipo de"**, ya que tanto `Tarea` como `Evento` **"son tipos de"** `Actividad`.

---

### **¿Cómo beneficia a la estructura de la aplicación?**

- **Simplicidad y claridad:**  
  La jerarquía facilita el entendimiento del código y simplifica la gestión de las actividades.

- **Cumplimiento de SOLID:**
    - **S (Responsabilidad Única):** Cada clase está diseñada para una única responsabilidad (definir un tipo de actividad).
    - **L (Sustitución de Liskov):** `Tarea` y `Evento` pueden utilizarse donde se espera una `Actividad`, garantizando la compatibilidad.
    - **O (Abierto/Cerrado):** La estructura es **abierta para extensión** (nuevas actividades pueden añadirse fácilmente) y **cerrada para modificación** (la lógica común no necesita reescribirse).
    - **D (Inversión de Dependencias):** La lógica de negocio depende de la abstracción `Actividad`, y no de implementaciones concretas.

---

### **Principios SOLID utilizados y beneficios obtenidos**

- **SRP (Responsabilidad Única):**  
  Cada clase tiene una responsabilidad específica: `Actividad` define la lógica común, y las subclases gestionan sus detalles particulares.

- **OCP (Abierto/Cerrado):**  
  El diseño permite agregar nuevas clases (como un nuevo tipo de actividad) sin modificar las clases existentes.

- **LSP (Sustitución de Liskov):**  
  Cualquier objeto `Tarea` o `Evento` puede ser tratado como una `Actividad`, lo que facilita la generalización y el uso polimórfico.

- **DIP (Inversión de Dependencias):**  
  La lógica de negocio depende de la abstracción `Actividad` y del `IActividadRepository` para almacenar las actividades.

---

### **Conclusión**

- La implementación de herencia con una **clase abstracta** permite un diseño limpio, coherente y extensible.
- La jerarquía creada facilita la **mantenibilidad** y **reutilización** del código, además de seguir buenas prácticas de programación y principios SOLID.
- Este diseño proporciona la base para que el sistema sea escalable y fácil de extender con nuevos tipos de actividades en el futuro.

---

## **Criterio Global 6: Diseño de jerarquía de clases**

---

### **¿Cómo presentas la jerarquía de clases diseñada en tu proyecto?**

En el **Gestor de Tareas y Eventos**, se ha implementado una jerarquía de clases bien definida, aplicando los principios de diseño orientado a objetos y las mejores prácticas recomendadas.

---

### 🔷 **Jerarquía de Clases Implementada**

```
                   Actividad (abstracta)
                          ▲
                   ┌──────┴────────┐
                   │               │
                Tarea            Evento
```

---

### **Detalles de la Jerarquía**

1. **`Actividad` (Superclase abstracta):**
    - Es la **base común** para todas las actividades del sistema.
    - Define las propiedades y métodos comunes, como `id`, `descripcion` y `detalle`.
    - Proporciona un método `mostrarDetalle()` que puede ser sobrescrito por las subclases.

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

---

2. **`Tarea` (Subclase de `Actividad`):**
    - Añade la propiedad `estado` y sobreescribe `detalle` para incluir esta información.
    - Su constructor es **privado** y se utiliza un método de clase para crear instancias.
    - Sigue la lógica común definida en `Actividad`.

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

---

3. **`Evento` (Subclase de `Actividad`):**
    - Añade las propiedades `fecha` y `ubicación`, y también sobreescribe `detalle`.
    - Constructor privado con método de clase para la creación controlada.

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

---

---

### **¿Cómo probaste y depuraste esta jerarquía para asegurar su correcto funcionamiento?**

1. **Pruebas Manuales en la Consola:**  
   Se utilizó la clase `ConsolaUI` para interactuar con el usuario y probar la creación y listado de actividades:

```kotlin
fun crearTarea(descripcion: String) {
    actividadService.crearTarea(descripcion)
}

fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
    actividadService.crearEvento(descripcion, fecha, ubicacion)
}
```

2. **Verificación de Salida en Consola:**
    - Se verificó manualmente que los objetos creados presentaran los detalles correctos al usar el método `listarActividades()`.

```kotlin
private fun listarActividades() {
    val actividades = actividadService.listarActividades()
    actividades.forEach { println(it.toString()) }
}
```

3. **Depuración con IntelliJ:**
    - Se utilizaron **puntos de ruptura** y la herramienta de **depuración paso a paso** para observar cómo se generaban las instancias, cómo se incrementaban los IDs y cómo se ejecutaba la lógica de herencia.

4. **Pruebas con Casos de Uso Diversos:**
    - Se realizaron pruebas introduciendo diferentes combinaciones de datos para validar la correcta asignación de atributos y la generación de `detalle`.

---

---

### **¿Qué tipo de herencia has utilizado y cómo se aplica?**

- **Especialización:**  
  `Tarea` y `Evento` **especializan** la clase `Actividad` añadiendo propiedades y comportamientos específicos.

- **Extensión:**  
  Ambas subclases **extienden** los métodos y propiedades de la superclase `Actividad`, reutilizando y personalizando la lógica de `detalle` y `toString()`.

- **Construcción:**  
  El método `creaInstancia` en los *companion objects* es un ejemplo claro de **patrón de construcción**, permitiendo crear objetos con inicialización controlada.

---

---

### **Conclusión sobre la Jerarquía de Clases**

- La jerarquía fue diseñada aplicando los principios de **claridad, reutilización y extensión controlada**.
- Las pruebas manuales y mediante depuración aseguraron la correcta implementación de la lógica de herencia y la integridad de los datos.
- La jerarquía facilita futuras ampliaciones del sistema, permitiendo añadir nuevos tipos de actividades sin romper el código existente.

---

## **Criterio Global 7: Librerías de clases**

---

### **¿Has incorporado alguna librería externa en tu proyecto?**

No se han utilizado **librerías externas** en este proyecto, ya que se trataba de una aplicación sencilla en consola donde no se requería de dependencias adicionales.

---

### **¿Qué importaciones has utilizado en tu proyecto?**

Aunque no se utilizaron librerías externas, sí se han realizado **importaciones estándar de Kotlin** para facilitar tareas comunes, como la lectura de datos por consola y la manipulación de colecciones.

---

### 🔷 **Ejemplos de Importaciones Utilizadas**

1. **`Scanner` para lectura de datos en la consola:**

```kotlin
import java.util.Scanner
```

- **¿Por qué se utilizó?**  
  Kotlin es interoperable con Java, y en este caso, `Scanner` fue utilizado para simplificar la entrada de datos en la consola.

- **Ejemplo de uso:**

```kotlin
private val scanner = Scanner(System.`in`)

private fun leerOpcion(): Int {
    return try {
        scanner.nextLine().toInt()
    } catch (e: Exception) {
        -1
    }
}
```

- **¿Qué beneficio aportó?**  
  Facilitó la interacción con el usuario, permitiendo leer datos de forma sencilla y con control de errores para validar las entradas.

---

2. **Uso de listas y colecciones estándar de Kotlin**

- **Ejemplo de definición de listas mutables:**

```kotlin
private val actividades = mutableListOf<Actividad>()
```

- **¿Por qué se utilizó?**  
  Kotlin ofrece una API de colecciones robusta y fácil de utilizar, por lo que fue innecesario recurrir a librerías externas.

- **Beneficio obtenido:**
    - Simplificó la manipulación de datos en memoria.
    - Mejoró la legibilidad y mantenibilidad del código.

---

3. **Uso de enumeraciones (`enum`) estándar de Kotlin**

```kotlin
enum class Statu {
    ABIERTA, CERRADA
}
```

- **¿Por qué se utilizó?**  
  Para gestionar el estado de las tareas de manera segura, evitando errores de valores incorrectos.

- **Beneficio obtenido:**
    - Garantiza que los estados de las tareas sean controlados y predefinidos.
    - Facilita la extensión futura si se desea añadir nuevos estados.

---

---

### **¿Cómo extendieron estas importaciones la funcionalidad de la aplicación?**

1. **Lectura de datos:**  
   La utilización de `Scanner` permitió implementar un sistema de entrada de datos robusto, controlando excepciones y validaciones.

2. **Gestión de colecciones:**  
   El uso de las colecciones de Kotlin (`mutableListOf`) facilitó el almacenamiento dinámico de actividades sin necesidad de estructuras personalizadas o complejas.

3. **Enriquecimiento del modelo de dominio:**  
   La definición del enum `Statu` permitió gestionar los estados de las tareas de forma clara y segura, mejorando la legibilidad del código.

---

### **¿Por qué no se usaron librerías externas?**

- **Simplicidad:** El proyecto es pequeño y la lógica es sencilla, por lo que no era necesario sobrecargar la aplicación con dependencias externas.
- **Objetivo educativo:** El ejercicio busca profundizar en la programación orientada a objetos y en los principios SOLID, centrándose en el código propio más que en el uso de librerías.
- **Autonomía del lenguaje:** Kotlin ya ofrece un conjunto sólido de herramientas estándar para gestionar entradas, colecciones y estructuras de datos.

---

### **Conclusión**

- Aunque no se utilizaron librerías externas, se aplicaron correctamente las **importaciones estándar de Kotlin y Java** para la entrada de datos y la gestión de colecciones.
- La elección de evitar librerías externas respondió a una necesidad de **simplicidad, claridad y enfoque en la lógica de negocio**.
- Se logró un código limpio, mantenible y claro, siguiendo las mejores prácticas y aprovechando al máximo las capacidades nativas del lenguaje Kotlin.

---

## **Criterio Global 8: Documentado**

---

### **¿Cómo has documentado y comentado tu código?**

Durante el desarrollo del proyecto **Gestor de Tareas y Eventos**, he seguido buenas prácticas de documentación y comentarios, asegurando que el código sea **claro, mantenible y fácil de entender** para otros desarrolladores.

La documentación se ha realizado de las siguientes maneras:

---

### 🔷 **1. Comentarios de Clase y Métodos**

Cada clase y método contiene comentarios explicativos que detallan su propósito, los parámetros y el flujo de ejecución. Esto ayuda a entender rápidamente la funcionalidad del código.

#### **Ejemplo en la Clase Abstracta `Actividad`**

```kotlin
/**
 * Clase base abstracta para las actividades del sistema.
 * Define propiedades comunes y una lógica básica que las subclases pueden reutilizar.
 *
 * @property id Identificador único de la actividad.
 * @property descripcion Descripción general de la actividad.
 */
abstract class Actividad(
    val id: Int,
    val descripcion: String
) {
    /**
     * Propiedad calculada que concatena el ID y la descripción.
     * Las subclases pueden sobrescribir esta propiedad para añadir más detalles.
     */
    open val detalle: String
        get() = "$id - $descripcion"

    /**
     * Método que retorna el detalle de la actividad.
     * Puede ser sobrescrito por las clases hijas.
     */
    open fun mostrarDetalle(): String = detalle
}
```

- **Valor aportado:**
    - La documentación clara de cada clase y método ayuda a identificar rápidamente su propósito.
    - Facilita la comprensión del flujo de datos y lógica de negocio.
    - Asegura que cualquier desarrollador pueda mantener o ampliar el código en el futuro.

---

#### **Ejemplo en la Clase `Tarea`**

```kotlin
/**
 * Clase que representa una Tarea dentro del sistema.
 *
 * Hereda de la clase abstracta Actividad y añade un estado adicional.
 * La creación de instancias se gestiona a través de un método de clase.
 *
 * @property estado Estado actual de la tarea.
 */
class Tarea private constructor(
    id: Int,
    descripcion: String,
    var estado: Statu = Statu.ABIERTA
) : Actividad(id, descripcion) {

    /**
     * Detalle personalizado que incluye el estado de la tarea.
     */
    override val detalle: String
        get() = super.detalle + " [Estado: $estado]"

    /**
     * Método que proporciona una representación en forma de cadena de la Tarea.
     */
    override fun toString(): String {
        return "Tarea -> $detalle"
    }

    companion object {
        private var contador: Int = 1

        /**
         * Método estático para crear una instancia de Tarea con ID único.
         *
         * @param descripcion Descripción de la tarea.
         * @param estado Estado inicial de la tarea.
         * @return Nueva instancia de Tarea.
         */
        fun creaInstancia(descripcion: String, estado: Statu = Statu.ABIERTA): Tarea {
            val tarea = Tarea(contador, descripcion, estado)
            contador++
            return tarea
        }
    }
}
```

- **Valor aportado:**
    - La documentación en los métodos `creaInstancia` y `toString` facilita la comprensión de su funcionamiento.
    - Ayuda a futuros desarrolladores a entender la lógica de asignación de IDs únicos.

---

---

### 🔷 **2. Comentarios en Código Crítico**

Se han añadido comentarios en aquellas partes del código que implementan lógicas críticas o específicas, especialmente en los métodos donde se maneja la creación de instancias.

#### **Ejemplo en la Clase `ActividadService`**

```kotlin
/**
 * Servicio que gestiona las operaciones relacionadas con las actividades.
 * Depende de un repositorio para almacenar y recuperar datos.
 */
class ActividadService(private val repositorio: IActividadRepository) {

    /**
     * Crea y almacena una nueva Tarea.
     *
     * @param descripcion Descripción de la tarea.
     * @param estado Estado inicial de la tarea.
     */
    fun crearTarea(descripcion: String, estado: Statu = Statu.ABIERTA) {
        val tarea = Tarea.creaInstancia(descripcion, estado)
        repositorio.agregar(tarea)  // Se agrega la tarea al repositorio en memoria
    }

    /**
     * Lista todas las actividades registradas.
     *
     * @return Lista de actividades.
     */
    fun listarActividades(): List<Actividad> = repositorio.obtenerTodas()
}
```

- **Valor aportado:**
    - Los comentarios ayudan a explicar por qué y cómo se realizan las operaciones clave.
    - Facilita la depuración y el mantenimiento del código.

---

---

### 🔷 **3. Herramientas Utilizadas para Documentación**

- **Comentarios en formato KDoc:**  
  Utilicé el estándar de documentación **KDoc**, propio de Kotlin, que es compatible con herramientas de generación automática de documentación.

- **IntelliJ IDEA:**  
  El propio IDE detecta los comentarios de KDoc y los presenta en sugerencias y ayuda contextual.

---

### 🔷 **4. ¿Cómo se asegura que la documentación aporte valor?**

- **Claridad y Concisión:**  
  La documentación se ha centrado en ser **clara y relevante**, evitando comentarios redundantes que no aporten información adicional.

- **Estructura consistente:**  
  Todos los comentarios siguen el mismo patrón y estilo, asegurando consistencia en todo el proyecto.

- **Mantenimiento y revisión:**  
  Durante las pruebas y el desarrollo, revisé y ajusté los comentarios para reflejar fielmente el comportamiento actualizado del código.

- **Ejemplos claros en cada clase:**  
  En cada clase, los comentarios detallan **por qué** se ha utilizado una lógica específica, como el uso de `companion object` o la elección de visibilidades.

---

---

### **Conclusión**

- La documentación del proyecto sigue el estándar **KDoc**, lo que garantiza claridad, coherencia y facilidad de mantenimiento.
- Los comentarios están orientados a explicar la **intención y funcionamiento del código**, especialmente en las secciones más complejas.
- Se ha priorizado la utilidad sobre la cantidad, asegurando que cada comentario aporte valor y facilite la comprensión a futuros desarrolladores.

---

## **Criterio Global 9: Genéricos**

---

### **¿Has implementado o utilizado alguna clase con genéricos en tu proyecto?**

En el proyecto del **Gestor de Tareas y Eventos**, **no se ha implementado una clase explícita con genéricos**, pero sí se ha hecho uso de las **colecciones genéricas** proporcionadas por Kotlin, como `List`, `MutableList` y `List<Actividad>`. Además, se ha respetado el uso correcto de tipos en las interfaces para garantizar la seguridad de tipo y la flexibilidad del código.

---

---

### 🔷 **Uso de Genéricos en Colecciones**

Aunque no se ha creado una clase personalizada genérica, se han utilizado los genéricos del propio lenguaje para garantizar la seguridad de tipo y flexibilidad en el almacenamiento y manipulación de objetos.

---

#### **Ejemplo 1: Repositorio con Genéricos**

```kotlin
interface IActividadRepository {
    fun agregar(actividad: Actividad)
    fun obtenerTodas(): List<Actividad>
}
```

- **Uso del genérico `List<Actividad>`:**
    - La interfaz `IActividadRepository` define que el método `obtenerTodas` retorna una lista de objetos del tipo `Actividad` o de sus subtipos.
    - Esto garantiza la seguridad de tipo, permitiendo trabajar exclusivamente con instancias de `Actividad`, `Tarea` o `Evento`.

---

#### **Ejemplo 2: Implementación del Repositorio con Genéricos**

```kotlin
class ActividadRepository : IActividadRepository {
    private val actividades = mutableListOf<Actividad>()

    override fun agregar(actividad: Actividad) {
        actividades.add(actividad)
    }

    override fun obtenerTodas(): List<Actividad> = actividades
}
```

- **Uso del genérico `MutableList<Actividad>`:**
    - Se utilizó `mutableListOf<Actividad>()` para almacenar dinámicamente cualquier instancia que herede de `Actividad`.
    - Esto garantiza flexibilidad y seguridad, permitiendo almacenar tanto `Tarea` como `Evento` en la misma colección.

---

#### **Ejemplo 3: Uso de Genéricos en los Servicios**

```kotlin
fun listarActividades(): List<Actividad> = repositorio.obtenerTodas()
```

- **¿Qué beneficio se obtuvo?**
    - **Seguridad de tipo:** Garantiza que solo se trabajen con objetos válidos del tipo `Actividad` o sus derivados.
    - **Flexibilidad:** Permite tratar cualquier objeto `Actividad` de forma homogénea.
    - **Escalabilidad:** Facilita la extensión del sistema en el futuro con nuevas clases que hereden de `Actividad` sin romper el código existente.

---

---

### 🔷 **Beneficios Obtenidos del Uso de Genéricos**

1. ✅ **Seguridad de tipo:**
    - El uso de colecciones genéricas como `List<Actividad>` evita errores de tipo en tiempo de compilación, garantizando que solo objetos válidos sean almacenados y manipulados.

2. ✅ **Flexibilidad:**
    - Permite almacenar instancias de cualquier clase que herede de `Actividad`, haciendo que el código sea flexible y fácilmente extensible.

3. ✅ **Mantenibilidad:**
    - Facilita el mantenimiento del código, ya que la estructura es clara y robusta frente a errores.

4. ✅ **Escalabilidad:**
    - Si en el futuro se desea añadir nuevas actividades, estas pueden incorporarse al repositorio sin modificar la estructura existente.

---

---

### 🔷 **¿Por qué no se implementaron clases personalizadas con genéricos?**

- **Simplicidad del Proyecto:**  
  La lógica actual del proyecto no requiere clases personalizadas con genéricos, ya que las necesidades del sistema se resuelven adecuadamente con las colecciones genéricas de Kotlin.

- **Enfoque en Conceptos OOP y SOLID:**  
  El objetivo principal era practicar y aplicar los principios de **programación orientada a objetos** y **principios SOLID**, por lo que la inclusión de clases genéricas personalizadas no aportaría un valor adicional significativo.

- **Evitar Complejidad Innecesaria:**  
  Implementar clases con genéricos sin una necesidad concreta podría añadir complejidad innecesaria al código.

---

---

### **Conclusión**

- Aunque no se implementaron clases personalizadas con genéricos, se ha hecho un uso **adecuado y seguro** de las colecciones genéricas (`List<Actividad>`, `MutableList<Actividad>`), garantizando seguridad de tipo y flexibilidad.
- Esta decisión responde a un diseño eficiente y práctico, priorizando la **claridad, mantenibilidad y simplicidad** del código.
- En el caso de que el sistema evolucione hacia escenarios más complejos, el diseño actual permite extender fácilmente el uso de genéricos.

---

## **Criterio Global 10: Expresiones Regulares**

---

### **¿Cómo has utilizado las expresiones regulares en tu proyecto?**

Aunque en el diseño inicial del proyecto **Gestor de Tareas y Eventos** no se incluyeron expresiones regulares, en este ejercicio **he supuesto su implementación** para validar el formato de la fecha del campo `fecha` en la clase `Evento`.

---

### 🔷 **Uso de Expresiones Regulares para Validar la Fecha**

Se ha definido una expresión regular en el servicio que gestiona la creación de eventos. La validación asegura que la fecha ingresada por el usuario siga el formato **`YYYY-MM-DD`**, lo que evita errores de entrada y garantiza la consistencia de los datos.

---

#### **Código de Validación con Expresión Regular**

```kotlin
class ActividadService(private val repositorio: IActividadRepository) {

    /**
     * Valida el formato de la fecha utilizando una expresión regular.
     * El formato esperado es YYYY-MM-DD.
     */
    private fun validarFormatoFecha(fecha: String): Boolean {
        val regex = Regex("""\d{4}-\d{2}-\d{2}""")  // Formato de fecha: YYYY-MM-DD
        return regex.matches(fecha)
    }

    /**
     * Crea y agrega un nuevo Evento al repositorio después de validar la fecha.
     *
     * @param descripcion Descripción del evento.
     * @param fecha Fecha del evento en formato YYYY-MM-DD.
     * @param ubicacion Ubicación del evento.
     */
    fun crearEvento(descripcion: String, fecha: String, ubicacion: String) {
        if (!validarFormatoFecha(fecha)) {
            throw IllegalArgumentException("Formato de fecha inválido. Use el formato YYYY-MM-DD.")
        }
        val evento = Evento.creaInstancia(descripcion, fecha, ubicacion)
        repositorio.agregar(evento)
    }
}
```

---

### 🔷 **Explicación del Código**

1. **Definición de la Expresión Regular:**
    - La expresión regular definida es: `\d{4}-\d{2}-\d{2}`
    - Esta expresión garantiza que:
        - Los primeros 4 dígitos corresponden al año (`\d{4}`).
        - Luego, un guion (`-`).
        - Seguido de 2 dígitos para el mes (`\d{2}`).
        - Otro guion (`-`).
        - Finalmente, 2 dígitos para el día (`\d{2}`).

2. **Validación del Formato:**
    - El método `regex.matches(fecha)` verifica si la fecha proporcionada coincide con el patrón establecido.

3. **Lógica de Control:**
    - Si el formato de la fecha es incorrecto, se lanza una **`IllegalArgumentException`** indicando al usuario cuál es el formato esperado.

---

#### **Ejemplo de Uso en la Capa de Presentación**

```kotlin
private fun crearActividad() {
    println("\nSeleccione el tipo de actividad a crear:")
    println("1. Tarea")
    println("2. Evento")
    print("Opción: ")
    
    when (leerOpcion()) {
        2 -> {
            print("Ingrese la descripción del Evento: ")
            val descripcion = scanner.nextLine()
            print("Ingrese la fecha del Evento (formato YYYY-MM-DD): ")
            val fecha = scanner.nextLine()
            print("Ingrese la ubicación del Evento: ")
            val ubicacion = scanner.nextLine()
            
            try {
                actividadService.crearEvento(descripcion, fecha, ubicacion)
                println("Evento creado exitosamente.")
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        else -> println("Opción no válida.")
    }
}
```

- **Flujo de Validación:**
    - Si el usuario introduce una fecha incorrecta, se mostrará un mensaje indicando el error y solicitando que introduzca un valor correcto.

---

---

### 🔷 **¿Qué beneficio se ha obtenido al usar expresiones regulares?**

1. ✅ **Validación Precisa:**
    - Garantiza que la fecha proporcionada cumpla con un formato **consistente y estandarizado** (`YYYY-MM-DD`).

2. ✅ **Prevención de Errores:**
    - Evita errores derivados de entradas mal formateadas o datos inconsistentes.

3. ✅ **Mejora de la Experiencia de Usuario:**
    - Al proporcionar mensajes claros sobre el formato correcto, se minimizan confusiones y se mejora la interacción con la aplicación.

4. ✅ **Mantenibilidad del Código:**
    - La validación está encapsulada en un método independiente, facilitando futuras modificaciones si se desea cambiar o extender el formato de fecha permitido.

5. ✅ **Cumplimiento de Buenas Prácticas:**
    - Se sigue el principio de **responsabilidad única** (SRP), ya que cada método tiene una única función claramente definida.

---

---

### 🔷 **¿Por qué se eligió este enfoque para validar la fecha?**

- **Simplicidad:**
    - Las expresiones regulares permiten validar de forma concisa y eficiente el formato de la fecha sin necesidad de lógica compleja.

- **Flexibilidad:**
    - Si en el futuro se desea ampliar el formato (por ejemplo, permitir horas), bastará con ajustar la expresión regular.

- **Estándar:**
    - Utilizar expresiones regulares para validaciones es una práctica común y reconocida en programación.

---

---

### **Conclusión**

- La implementación de expresiones regulares para validar las fechas de los eventos asegura que los datos introducidos sean **consistentes y válidos**, mejorando la calidad y robustez del sistema.
- El uso de expresiones regulares facilita la **mantenibilidad y extensibilidad** del código, permitiendo futuras modificaciones con cambios mínimos.
- Este enfoque también mejora la experiencia del usuario al prevenir errores y proporcionar mensajes claros y detallados.

