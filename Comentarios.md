
# CRITERIO GLOBAL 1

Posible respuesta

Se ha implementado la instanciación de objetos utilizando tanto constructores privados como métodos estáticos a través del patrón companion object.  Esto nos garantiza un control estricto sobre la creación de instancias. Nos aseguremos por ejemplo que cada Tarea y Evento reciba un ID único de forma automática, eliminando el riesgo de colisiones o errores en la identificación. Y ejemplos.


El metodo estatico para crear tareas y eventos se usa en .... para crear instancias y cumplir lo dicho arriba.


El resto de tareas tienen constructores publicos, que se usan normalmente para crear las clases de servicio y repositorio, previas a la inyección en otras clases. Estas clases se instancias en ....... y se le pasan los parametros necesarios para ello.




# CRITERIO GLOBAL 2


Posible respuesta

Se utilizia metodos y propiedades estaticas,  para gestionar la creación controlada de instancias y la generación automática de identificadores únicos.

el método creaInstancia en Evento es estático para:
- Controlar la forma en la que se crean las instancias.
- Gestionar automáticamente el ID único a través de la propiedad estática contador.

Tambien tenemos un contador statico que va alimentando los ids que se van generando.


Esta práctica nos asegura mayor encapsulación, teniendo lógica de creación de objetos encasulada dentro de la propia clase.  Ademas principios de responsabildiad única, ya que la clase se encarga de su propia cración de objetos.


# CRITERIO GLOBAL 3


Posible respuesta

Para el desarrollo del proyecto Gestor de Tareas y Eventos, utilicé IntelliJ IDEA, el entorno de desarrollo más recomendado para Kotlin, ya que ofrece:

Asistencia de código avanzada: Con sugerencias, autocompletado, refactorización y verificación de errores en tiempo real.
Integración con Kotlin: Soporte nativo para Kotlin y sus características, incluyendo la gestión de companion object, data classes y pruebas.
Depuración eficiente: Herramientas para realizar depuración paso a paso, inspección de variables y ejecución de puntos de ruptura.
Gestión de paquetes y dependencias: Utilicé la gestión integrada de dependencias y paquetes, aunque en este proyecto no se utilizaron librerías externas.


El proyecto se estructuró siguiendo las buenas prácticas de arquitectura en capas, organizando los paquetes de la siguiente forma:

src
└── main
├── kotlin
│   ├── dominio          # Contiene las clases del dominio (Actividad, Tarea, Evento)
│   ├── datos            # Contiene las interfaces y clases de repositorio
│   ├── servicios        # Contiene la lógica de negocio y servicios
│   ├── presentacion     # Contiene la lógica de interacción con el usuario
│   └── main             # Contiene la función main y punto de entrada de la aplicación
└── resources

Podemos describir cada paquete y contenido.


# CRITERIO GLOBAL 4

Posible respuesta

Las clases encapsulan la información de las actividades, eventos y tareas. Estos son objetos que nos encontramos en el mundo real.

Actividad es una abstracción de eventos y tareas, y por tanto mantiene toda la información común.

Propiedades como estado, que toma los valores de la enum clas STATUS.

Se han tenido en cuenta la inmutabilidad de las propiedades, y la visibilidad

Algunas propiedades tienen restricciones, por ejemplo no ser nulas, ni repeticas. Tener valores por defecto...


Para usar principios de responsabilidad única, existen otras clases que tienen que ver con
- los servicios que aprota la aplicación
- la logica de acceso a datos.
- la interación con el usuario

Esto nos ha permitido tambien una organización en paquetes.



# CRITERIO GLOBAL 5


Posible respuesta

Puedes enfocar la respusta tanto desde el punto de vista de actividaes, tareas y evento. O desde el punto de vista de la interfaz de repositorio y su implmentación. Y la interface de servicios y su implementación.

En el proyecto del Gestorr de tareas y eventos, se ha implementado la herencia utilizando una clase abstracta, Actividad, de la cual heredan las clases Tarea y Evento. Se ha hecho asi para aplicar una jerarquía lógica que represente las actividades en el sistema. Actividad se podrá utililizar en cualquier sitio dónde quereamos utilizar tarea o evento. Ademas nos permitira utilizar otros tipos de actividades si fuera necesario.  Y ejemplos.



En cuanto al puntos de vista de la interfaz de repositorio y su implmentación. Y la interface de servicios y su implementación.

El servicio ServicioActividad implementará la interfaz de Servicios: IServicioActividad. Esta capa tendra el comportamiento de todas las funcionalidades que se le pueden pedir a mi aplicación relacionadas con la actividad. En este caso es añadir una actividad y listar una actividad.

El respositorio RepositorioActividad  implementará la interfaz de Repositorio: IRespositorioActividad. Esta capa se ocupa del comportamiento de la capa que gestiona los datos... los metodos relacionados con crear, leer,  modificar , elimnar los datos.  Por tanto se puede decir que solo tendria 4 metodos para crear, leer,  modificar , elimnar los datos, en este caso actividades. 

En este caso al ser una plicación muy simple el comportamiento de IRepositorio e IServicio suele ser muy parecido, pero en aplicaiones grandes IRepositorio se queda en los metodos de crear, leer,  modificar , elimnar. Mientras que IServicio crecera en función de las funcionalidaes de la aplicación.


Nos permite reutilizar codigo, nos permite extender facilmente y es una jerarquia de clases clara.


En cuanto a cumplimiento de SOLID, por ejemplo solo apolicado a actividad, tarea, evento.

S (Responsabilidad Única): Cada clase está diseñada para una única responsabilidad (definir un tipo de actividad).
L (Sustitución de Liskov): Tarea y Evento pueden utilizarse donde se espera una Actividad, garantizando la compatibilidad.
O (Abierto/Cerrado): La estructura es abierta para extensión (nuevas actividades pueden añadirse fácilmente) y cerrada para modificación (la lógica común no necesita reescribirse).
D (Segregación de interfaces): Las funcionalidades están separadas claramente según necesidades. Por ejemplo  usamos interfaces para repositorio y servicio, separando según responsabilidaes.  
I Inversión de Dependencias): La lógica de negocio depende de la abstracción Actividad, y no de implementaciones concretas. Tambien usamos interfaces para repositorio y servicio, dependiendo de las abstraciones.

En cuanto al puntos de vista de la interfaz de repositorio y su implmentación. Y la interface de servicios y su implementación.




# CRITERIO GLOBAL 6

Posible respuesta

Igualmente, puedes enfocar la respusta tanto desde el punto de vista de actividaes, tareas y evento. O desde el punto de vista de la interfaz de repositorio y su implmentación. Y la interface de servicios y su implementación. . O desde el punto de vista de la interfaz de repositorio y su implmentación. Y la interface de servicios y su implementación.


Desde el punto de vista de actividaes, tareas y evento... es una jerarquiza bien definidos, aplicando los principios de diseño orientado a objetos y las mejores prácticas recomendadas.

Actividad (Superclase abstracta): Es la base común para todas las actividades del sistema.
Define las propiedades y métodos comunes, como id, descripcion y detalle.
Proporciona una propiedad detalleque puede ser sobrescrito por las subclases....

Tambien sobre evento y tarea.

Probe y depuere la jerarquia haciendo uso de ella durante la cración de actividades, en el servicio que las crea. Tambien mostrando la información, para ver como se usa el polimorfismo. Se realizaron pruebas introduciendo diferentes combinaciones de datos para validar la correcta asignación de atributos y la generación de detalle.

Se usan este tipo de herencia

Extensión:
Ambas subclases extienden los métodos y propiedades de la superclase Actividad, reutilizando y personalizando la lógica de detalle y toString().

La jerarquía creada facilita futuras ampliaciones del sistema, permitiendo añadir nuevos tipos de actividades sin romper el código existente

# CRITERIO GLOBAL 7

Posible respuesta

El proyecto es pequeño y la lógica es sencilla, por lo que no era necesario sobrecargar la aplicación con dependencias externas.

Por tanto no se han utilizado librerías externas en este proyecto. Pero sí se han realizado importaciones estándar de Kotlin para facilitar tareas comunes, como la lectura de datos por consola y la manipulación de colecciones.... o cualquier otra cosa. Porque importarse se importa lo que tu has creado si has crado paquetes.

Por ejemplo Scanner para lectura de datos en la consola, (Y ejemplos) utilizado para simplificar la entrada de datos en la consola. Facilitó la interacción con el usuario, permitiendo leer datos de forma sencilla y con control de errores para validar las entradas.


# CRITERIO GLOBAL 8

Posible respuesta

he seguido buenas prácticas de documentación y comentarios, asegurando que el código sea claro, mantenible y fácil de entender para otros desarrolladores.

En cuanto a la comentarios, cada clase y método contiene comentarios explicativos que detallan su propósito, los parámetros y el flujo de ejecución. Esto ayuda a entender rápidamente la funcionalidad del código. Y ejemplos.

La documentación de una clase, método, etc ayuda a identificar su propósito. Facilita entender el flujo de datos y lógica de negocio, y lo mas importante asegura que cualquier desarrollador pueda mantener o ampliar el código en el futuro.

En cuanto a la documentación, utilicé el estándar de documentación KDoc, propio de Kotlin, que es compatible con herramientas de generación automática de documentación. Y ejemplos

La documentación clara y relevante, evitando comentarios que no aporten. Al seguir todos los comentarios el mismo patrón, aseguran consistencia en todo el proyecto.

He añadido ejemplos en algunas clase, con comentarios detallando por qué se ha utilizado una lógica específica, como el uso de companion object. Y ejemplos

# CRITERIO GLOBAL 9

Posible respuesta

No se ha implementado una clase explícita con genéricos, pero sí se ha hecho uso de las colecciones genéricas que Kotlin trae por defecto, como List, MutableList y List<Actividad>. Además, se ha respetado el uso correcto de tipos para asegurar la flexibilidad del código.


Y ejemplos

El uso de colecciones genéricas como List<Actividad> evita errores de tipo en tiempo de compilación, garantizando que solo objetos válidos sean almacenados y manipulados: Tareas y Eventos.

Con eso hacemos  el código sea flexible y fácilmente extensible. Facilita el mantenimiento del código.

Por ejemplo, si en el futuro se desea añadir nuevas actividades, estas pueden incorporarse al repositorio sin modificar la estructura existente.



# CRITERIO GLOBAL 10

Posible respuesta

Se ha definido una expresión regular en el servicio que gestiona la creación de eventos. Con la validación me aseguro que fecha que ha introducido el usuario siga el formato YYYY-MM-DD. Asi evitamos errores entrada y se mantiene la consistencia de los datos.

Y ejemplos y explicación de loq ue se ha hecho.

Se decide validar la fecha por matenimiiilidad del código, asegurando un formato de fecha consistente y estandar. Facilita el mantenimiento del código, ya que reduce el código de comprobación, resultado al final mas sencillo. 



