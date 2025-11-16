#  Proyecto Appf1 - App de Fórmula 1

Esta es una aplicación de Android sobre la Fórmula 1, creada en Kotlin para la asignatura de App Móviles.

La app muestra información sobre la temporada de F1, incluyendo un calendario de carreras, las clasificaciones de pilotos y equipos, y una pantalla de "Fantasy". Todos los datos se guardan en una base de datos local **Room** para que la app funcione sin internet.

##  Características Principales

* Pantalla de Inicio: Muestra la próxima carrera de la temporada.
* Calendario de Carreras: Una lista (`RecyclerView`) con todas las carreras del año.
* Detalle de Carrera: Al pulsar una carrera, se abre una pantalla con una **barra superior que se colapsa** y muestra la imagen del circuito. También tiene un botón para **abrir la ubicación en Google Maps**.
* Resultados:Una pantalla con 3 pestañas (Carreras pasadas, Pilotos, Equipos).
* Clasificación por Puntos: Las listas de Pilotos y Equipos están **ordenadas por puntos** (de mayor a menor).
* Pantalla Fantasy: Una sección interactiva donde puedes elegir un piloto (y ves su foto) y asignarle puntos con una barra (`SeekBar`).
* Ajustes:
    * Permite guardar un apodo.
    * **¡Selector de Idioma!** Un `Spinner` permite cambiar el idioma de toda la app entre Español, Inglés, o usar el del sistema.

##  Tecnologías Usadas

* Kotlin: El lenguaje principal de la app.
* Base de Datos Room: Para guardar todas las carreras, pilotos y equipos en el teléfono.
* MVVM (ViewModel): Se usa un `RaceViewModel` para conectar la base de datos con las pantallas.
* ViewBinding: Para conectar el código Kotlin con los XML 
* RecyclerView y CardView: Para mostrar las listas.
* Coil: Para cargar las fotos de los pilotos y coches desde internet.
* Material Design: Para los botones, las barras, los menús y el diseño general.
* SharedPreferences: Para guardar el apodo y el idioma seleccionado.

##  Cómo Funciona

La app tiene 9 Activities en total. La `MainActivity` es la principal y la primera que se abre.

1.  Carga de Datos:** Al abrir `MainActivity` por primera vez, la función `preloadData` revisa si la base de datos está vacía. Si lo está, la rellena con todos los datos de carreras, pilotos (con sus puntos y fotos) y equipos.
2.  Navegación:** Un `BottomNavigationView` (el menú de abajo) permite navegar entre las 5 Activities principales (`Main`, `Schedule`, `Results`, `Fantasy`, `Settings`).
3.  Selector de Idioma:** Se creó una `BaseActivity.kt` de la que heredan todas las demás. Esta clase revisa la preferencia de idioma guardada en `SharedPreferences` *antes* de que se cree la pantalla, y fuerza el idioma (español o inglés) en toda la app.
