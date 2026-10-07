# Sistema de Operaciones y Planificación de Rutas para Micros (Java)

Aplicación local desarrollada en **Java** para la gestión operativa, planificación de itinerarios y análisis de conexiones de transporte terrestre. Implementa un modelo de datos estructurado basado en **Teoría de Grafos** y TDAs propios para optimizar la asignación de flota y el cálculo de trayectos.

## 🌟 Características Principales

* **Planificación de Rutas con Grafos (DFS):**
  * Representación del grafo de terminales mediante un patrón **Singleton** centralizado.
  * Búsqueda en profundidad (DFS) para encontrar todas las rutas posibles entre dos terminales especificando un límite máximo de paradas.
  * Identificación automática de terminales desconectadas o rutas no utilizadas.
* **Gestión Eficiente de Flota & Unidades:**
  * Uso de `LinkedList` para la administración dinámica de viajes asignados a cada micro.
  * Mapeo rápido mediante Dictionarios para asociar patentes e identificadores únicos con el historial operativo de la unidad.
  * Garantía de unicidad en conexiones y registros mediante colecciones de tipo `Set`.
* **Módulo de Prioridad & Métricas Operativas:**
  * Reportes de utilización de unidades, terminales con mayor tráfico de salidas/llegadas y análisis de rutas más/menos concurridas.

## 🛠️ Tecnologías y Estructuras

* **Lenguaje:** Java 17+
* **Patrones & Estructuras de Datos:** Singleton Pattern, Graphs (Adjacency Matrix/List), Depth-First Search (DFS), `LinkedList`, `Set`, `Map`, `Graph`.

## 📂 Estructura del Proyecto

```text
.
├── src/
│   ├── models/        # Entidades (Micro, Terminal, Viaje).
│   ├── TDAs/          # Definicion e implementacion de tipos de datos abstractos.
│   ├── controllers/   # Lógica de grafos, algoritmos DFS, micros, viajes, y terminales
│   ├── exception/     # Exceptions personalizadas para cada regla de negocio.
│   └── main.java
└── README.md
```

## Authors
- [Gianluca Lavinia - Lu. 1214519](https://www.github.com/Gian-Tuka)
- [Nicolas Zuccarello - Lu. 1215660](https://www.github.com/NicolasdeZucca)
