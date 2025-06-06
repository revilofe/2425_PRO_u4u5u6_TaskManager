# Actividad: Desarrollo de Proyecto Software en Kotlin

**ID actividad:** 2425_PRO_u4u5u6_taskManager

**Agrupamiento de la actividad**: Individual 

---

### Descripción:

Ejercicio simple de programación orientada a objetos en Kotlin, aplicando los principios SOLID y la separación en capas en una aplicación de consola para la gestión de tareas y eventos en proyectos colaborativos.

---

### **Ejercicio: Gestor de Tareas y Eventos para Proyectos Colaborativos**

#### **Contexto y Objetivo**

Desarrolla una aplicación de consola en Kotlin que permita gestionar **actividades** en un proyecto colaborativo. Estas actividades se dividen en dos tipos: **Tareas** y **Eventos**, por tanto ambas derivan de una superclase o interfaz denominada **Actividad**.

La aplicación debe seguir una **arquitectura en capas**, separando claramente:
- **La capa de presentación (UI):** se encarga de la interacción con el usuario a través de la consola.
- **La capa de lógica de aplicación:** gestiona la lógica de negocio (creación, almacenamiento y manejo de actividades). Esta capa conoce como realizar las operaciones relacionadas con la creación y consulta de actividades. Además conoce las entidades de dominio y se comunica con la capa de acceso a datos y debe depender de abstracciones, no de implementaciones concretas.
- **La capa de acceso a datos:** se encarga de almacenar y recuperar las actividades en memoria. En este caso, no se requiere persistencia en una base de datos, sino que se almacenarán en memoria. Por tanto esta capa conoce como almacenar y recuperar las actividades. Aunque en este ejercicio se puede utilizar un repositorio en memoria, se debe abstraer su acceso mediante interfaces, aplicando el principio de inversión de dependencias.

#### **Requerimientos Funcionales y No Funcionales**

1. **Arquitectura en Capas y Principio de Inversión de Dependencias**
   - La lógica de negocio debe depender de abstracciones (por ejemplo, interfaces de repositorios) y no de implementaciones concretas.
   - De igual forma, la comunicación entre la interfaz de usuario y la lógica de negocio debe estar claramente separada, por lo que se debe utilizar interfaces o abstracciones para comunicarse entre capas.

2. **Modelo de Dominio: Actividad, Tarea y Evento**
   - **`Actividad` (Superclase o Interfaz):**
     - Esta clase no se podrá instanciar, sino que será la superclase de `Tarea` y `Evento`.
     - Contendrá la lógica común a todas las actividades.
     - Posee un `id`. Se asigna valor automáticamente al crear la instancia. No puede ser nula. No se puede modificar.
     - Posee una `fechaCreacion`. Se asigna valor automáticamente al crear la instancia. No puede ser nula. No se puede modificar.
     - Posee una `descripcion`. No puede ser nula.
     - Debe incluir una propiedad, `detalle`, cuyo `get` retorne la concatenación del `id` y la `descripción`. Se verá así: `<id> + " - " + <descripción>`
   - **`Tarea`:**
     - Hereda las propiedades de `Actividad`.
     - Posee una propiedad `estado`, por defecto abierta. Toma valores de la enum class `Status` = {`ABIERTA`, `CERRADA`}
     - Su único constructor es `privado`. Se debe disponer de un método de clase (companion object) llamado `creaInstancia` para generar una nueva instancia.
     - Sobreescribe `toString`. Muestra formateada toda la información de la tarea, en modo `Tarea=[nombreAtributo: <valorAtributo>, ...]`.
     - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo
   - **`Evento`:**
     - Hereda las propiedades de Actividad.
     - Posee una `fecha` de realización. No puede ser nula. Usa expresiones regulares para validar que la fecha sea válida (formato `dd/MM/yyyy`).
     - Posee una `ubicación`, representada mediante una cadena.
     - Posee un `detalle` que se genera dinámicamente: `<id>+ " - " + <ubicacion> + " - " + <descripcion>`.
     - Similar a `Tarea` en cuanto a que su único constructor es privado y dispone del método de clase `creaInstancia` para crear las instancias.
     - Sobreescribe `toString`. Muestra formateada toda la información de la tarea, en modo `Evento=[nombreAtributo: <valorAtributo>, ...]`.
     - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo


3. **Buenas Prácticas y Principios SOLID**
   - Haz uso del principio de **inversión de dependencias**: la lógica de negocio no debe depender de clases concretas para el almacenamiento de las actividades, sino de abstracciones. De igual forma la capa de presentación no debe depender de la lógica de negocio, sino de abstracciones.
   - Documenta y comenta el código de forma clara, y sobre todo aquellas aportaciones que no están indicadas en la descripción de la actividad.
   - No te olvides de los métodos de clase o estáticos (en Kotlin se implementan mediante *companion objects*). Asegúrate de que la creación de instancias de Evento y Tarea se realicen mediante el método de clase `creaInstancia`.

4. **Logica de presentación: Interfaz de Usuario (Consola)**
   - La capa de presentación debe comunicarse con la lógica de negocio a través de interfaces o abstracciones, a partir de las cuales debe invocar a los métodos del servicio para realizar las operaciones solicitadas por el usuario.
   - Por tanto, la capa de presentación no debe contener lógica de negocio, sino únicamente la interacción con el usuario.
   - La aplicación debe interactuar con el usuario a través de la consola, mostrando un menú que permita:
      - Crear una nueva actividad (seleccionando entre Tarea o Evento).
      - Listar todas las actividades registradas. Aplicando polimorfismo, se debe mostrar el detalle de cada actividad.
      - Listar todas las tareas registradas, pudiendo seleccionar entre estados `ABIERTA` o `CERRADA` o ambos.
   - La interface de usuario tiene que ser sencilla, mostrándo un menú con las opciones descritas en el punto anterior.

5. **Lógica de Aplicación: Service**
   - Implementa un servicio (por ejemplo, `ActividadService`) que gestione la creación, almacenamiento (en memoria) y consulta de actividades.
   - Este servicio debe depender de una interfaz de repositorio (por ejemplo, `IActividadRepository`), permitiendo cambiar la implementación del almacenamiento sin afectar la lógica de negocio.

6. **Lógica de acceso a datos: Repository **
   - Implementa la interfaz de repositorio `IActividadRepository`, mediante una clase repositorio (por ejemplo `ActividadRepository`) que permita almacenar y recuperar las actividades de memoria.
   - Asegúrate de que la lógica de negocio (Servicio) no dependa de esta implementación concreta, sino de una abstracción (interfaz de repositorio).

**Objetivo:**

- Demostrar comprensión de los fundamentos de POO mediante la instanciación y uso de objetos.
- Aplicar conceptos avanzados de POO como herencia, clases abstractas, e interfaces.
- Crear y usar clases que hagan uso de genéricos. 
- Aplicar principios SOLID.
- Hacer uso de las expresiones regulares.
- Integrar y utilizar librerías de clases externas para extender la funcionalidad del proyecto.
- Documentar y presentar el código de manera clara y comprensible.

**Trabajo a realizar:**

El trabajo a realizar es el que se deriva de la descripción. No obstante te listo algunas cosas a realizar, aunque **no es una lista exhaustiva de lo que debes hacer**:

1. **Definición de Clases y Estructura del Proyecto**
   - Crea un paquete (o módulo) para cada capa: 
     - `presentacion` para la interfaz de usuario.
     - `aplicacion` o `servicios` para la lógica de negocio.
     - `datos` para la implementación del repositorio (en memoria).
     - `dominio` para definir las clases `Actividad`, `Tarea` y `Evento`.

2. **Implementación del Modelo de Dominio**
   - Define la superclase o interfaz `Actividad` que incluya:
     - Una propiedad común `detalle`.
     - Resto de atributos.
     - Cualquier lógica que consideres común a Tareas y Eventos.
   - Implementa **Tarea** y **Evento**:
     - **Campos inmutables:** El campo **id** debe generarse automáticamente (puedes utilizar una variable estática en cada clase para llevar el contador).
     - **Constructores privados:** Asegúrate de que los constructores sean privados y se acceda a ellos únicamente mediante el método de clase `creaInstancia` en el *companion object*.
     - **Propiedad `detalle`:** Si tiene implementación distinta a la de por defecto.
     - **Método `toString`:** Sobreescribe el método `toString` para mostrar toda la información de la tarea o evento.
     - Cualquier otro atributo o método que se haya indicado en la descripción, o que consideres necesario. En este caso, no olvides comentarlo.

3. **Desarrollo de la Lógica de Aplicación**
   - Implementa un servicio (`ActividadService`) que:
     - Utilice una interfaz de repositorio (`IActividadRepository`) para almacenar y recuperar actividades.
     - Permita la creación de nuevas actividades mediante métodos que invoquen `creaInstancia` de cada clase.
   - Recuerda aplicar el **principio de inversión de dependencias**: el servicio debe depender de la abstracción, no de una implementación concreta del repositorio.

4. **Interfaz de Usuario (Consola)**
   - Crea una interfaz de usuario sencilla que muestre un menú:
     - **Opción 1:** Crear nueva actividad (se debe preguntar al usuario si desea crear una Tarea o un Evento, luego solicitar los datos requeridos).
     - **Opción 2:** Listar todas las actividades, mostrando el detalle de cada una.
     - **Opción 3:** Listar las tareas, pudiendo seleccionar entre estados `ABIERTA` o `CERRADA` o ambos.
   - La capa de presentación debe invocar los métodos del servicio para realizar las operaciones solicitadas. No debe contener lógica de negocio y no debe depender de implementaciones concretas de la lógica de aplicación, sino de abstracciones.

5. **Documentación y Comentarios**
   - Asegúrate de comentar el código, explicando las decisiones de diseño, la aplicación de los principios SOLID (especialmente la inversión de dependencias) y el funcionamiento general del sistema.

6. **Prueba y Depuración:** Realiza pruebas para asegurarte de que tu aplicación funciona como se espera y depura cualquier error encontrado.

7. **Contesta a las preguntas** Ver el punto **Preguntas para la Evaluación**

### Recursos

- Apuntes dados en clase sobre programación orientada a objetos, Kotlin, uso de IDEs, y manejo de librerías.
- Recursos vistos en clase, incluyendo ejemplos de código, documentación de Kotlin, y guías de uso de librerías.

### Evaluación y calificación

**RA y CE evaluados**: Resultados de Aprendizaje 2, 4, 6, 7 y Criterios de Evaluación asociados.

**Conlleva presentación**: SI

**Rúbrica**: Más adelante se enviará o mostrará la rúbrica de esta práctica.

### Entrega

> Es condición indispensable que el código entregado cumpla con los estándares de calidad y buenas prácticas de programación, además de los requisitos solicitados. Cumplir con los estándares de calidad implica que el código debe estar correctamente indentado y comentado. Por supuesto, el código debe ser funcional y no debe contener errores.
> **La entrega tiene que cumplir las condiciones de entrega para poder ser calificada. En caso de no cumplirlas podría calificarse como no entregada.**

- **Conlleva la entrega de URL a repositorio:** El contenido se entregará en un repositorio GitHub. 
- **Respuestas a las preguntas:** Deben contestarse, de manera clara y detallada en este fichero, README.md
- **MUY IMPORTANTE!!** Incluir un subapartado ("Respuestas a las preguntas planteadas") dónde se resuelvan las preguntas de evaluación que os realizamos a continuación. De forma clara y detallada, incluyendo obligatoriamente enlaces a **fragmentos de código** que justifica vuestra respuesta.

# Preguntas para la Evaluación

Si no sabes cómo conseguir los enlaces a fragmentos de código, puedes revisar la documentación oficial de GitHub: [Como se consiguen los enlaces a fragmentos de código?](https://docs.github.com/es/get-started/writing-on-github/working-with-advanced-formatting/creating-a-permanent-link-to-a-code-snippet)

Este conjunto de preguntas está diseñado para ayudarte a reflexionar sobre cómo has aplicado los criterios de evaluación en tu proyecto. Al responderlas, **asegúrate de hacer referencia y enlazar al código relevante** en tu `README.md`, facilitando así la evaluación de tu trabajo.

#### **Criterio global 1: Instancia objetos y hacer uso de ellos**
- **(2.a, 2.b, 2.c, 2.d, 2.f, 2.h, 4.e, 4.f)**: Describe cómo has instanciado y utilizado los objetos en tu proyecto. ¿Cómo has aplicado los constructores públicos y constructores privados? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código donde usas constructores publicos y privados y les pasas parámetros.  

#### **Criterio global 2: Crear y llamar métodos estáticos**
- **(4.h)**: ¿Has definido algún método/propiedad estático en tu proyecto? ¿Cuál era el objetivo y por qué consideraste que debía ser estático en lugar de un método/propiedad de instancia? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.
- **(2.e)**: ¿En qué parte del código se llama a un método estático o se utiliza la propiedad estática? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.

#### **Criterio global 3: Uso de entornos**
- **(2.i)**: ¿Cómo utilizaste el IDE para el desarrollo de tu proyecto? Describe muy brevemente la estructura de tu proyecto.

#### **Criterio global 4: Definir clases y su contenido**
- **(4.a, 4.b, 4.c, 4.d, 4.g)**: Explica sobre un fragmento enlazado a tu código, cómo definiste las clases en tu proyecto, es decir como identificaste las de propiedades, métodos y constructores y modificadores del control de acceso a métodos y propiedades, para representar al objeto del mundo real. ¿Cómo contribuyen estas clases a la solución del problema que tu aplicación aborda, es decir a que paquete pertenecen y porque?

#### **Criterio global 5: Herencia y uso de clases abstractas e interfaces**
- **(4.g, 7.a, 7.b, 7.c, 7.i, 7.j)**: Explica sobre un fragmento enlazado a tu código cómo has implementado la herencia y/o utilizado interfaces en tu proyecto. ¿Por qué elegiste este enfoque y cómo beneficia a la estructura de tu aplicación? ¿De qué manera has utilizado los principios SOLID para mejorar el diseño de tu proyecto? Mostrando tu código, contesta qué principios has utilizado y qué beneficio has obtenido. Recuerda los que hay, porque seguro que has utilizado más de uno además de la inversión de dependencias.

#### **Criterio global 6: Diseño de jerarquía de clases**
- **(7.d, 7.e, 7.f, 7.g)**: Presenta la jerarquía de clases que diseñaste, proporciona obligatoriamente ejemplos específicos de fragmentos de tu código. ¿Cómo probaste y depuraste esta jerarquía para asegurar su correcto funcionamiento? ¿Qué tipo de herencia has utilizado: Especificación, Especialización, Extensión, Construcción?

#### **Criterio global 7: Librerías de clases**
- **(2.g, 4.i)**: Describe cualquier librería externa que hayas incorporado en tu proyecto. Sino, explica cualquier importación que hayas hecho. Explica cómo y por qué las elegiste, y cómo las incorporaste en tu proyecto. ¿Cómo extendió la funcionalidad de tu aplicación? Proporciona ejemplos específicos de su uso en tu proyecto.

#### **Criterio global 8: Documentado**
- **(7.h)**: Muestra enlaces a fragmentos de tu código en donde se vena ejemplos de cómo has documentado y comentado tu código. ¿Que herramientas has utilizado? ¿Cómo aseguras que tu documentación aporte valor para la comprensión, mantenimiento y depuración del código?

#### **Criterio global 9: Genéricos**
- **(6.f)**: Muestra enlaces a fragmentos de tu código en el que se vean ejemplos de cómo has implementado/usado una clase con genéricos. ¿Qué beneficio has obtenido?

#### **Criterio global 10: Expresiones Regulares**
- **(6.g)**: Muestra enlaces a fragmentos de tu código en el que se vean ejemplos de cómo has utilizado las expresiones regulares. ¿Qué beneficio has obtenido?