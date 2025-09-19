# 🚀 Launch CameraX: Registro de Usuarios con OCR

[![Estado de la Build](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/tu-usuario/launch_camerax)
[![Licencia](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blueviolet.svg)](https://kotlinlang.org)

Proyecto de demostración para Android que simplifica el proceso de registro de usuarios mediante el escaneo de documentos de identidad, utilizando **Jetpack CameraX**, **reconocimiento de texto (OCR)** y una implementación basada en **Arquitectura Limpia (Clean Architecture)**.

# Screenshot

| document scanning with camera x | Updating missing fields for registration | Log in and navigate to the profile screen |
|---------------------------------|------------------------------------------|-------------------------------------------|
| <img width="300" src="https://github.com/user-attachments/assets/712d664f-7ab1-43b2-856e-a3607b3b11bd" />|<img width="300" src="https://github.com/user-attachments/assets/60290069-17bf-4869-a148-5faf0c067ab8"/>|<img width="300" src="https://github.com/user-attachments/assets/61e71bd0-c57e-4c9a-abe9-770f1fdd336b"/>|

---

## ✨ Características Principales

* **Vista de Cámara en Tiempo Real:** Interfaz fluida y eficiente para el escaneo de documentos usando Jetpack CameraX.
* **Reconocimiento de Texto (OCR):** Detección y extracción automática de datos clave (nombre, número de ID, etc.) del documento.
* **Autocompletado de Formulario:** La información extraída rellena automáticamente los campos del formulario de registro.
* **UI Moderna y Reactiva:** Interfaz de usuario construida enteramente con **Jetpack Compose** y los lineamientos de Material 3.
* **Arquitectura Escalable:** Código desacoplado, mantenible y testeable gracias a la implementación de Clean Architecture.

---

## 🏛️ Arquitectura y Patrones de Diseño

Este proyecto sigue los principios de **Clean Architecture** para separar las responsabilidades del software en distintas capas, logrando un sistema más robusto y fácil de mantener.

```
┌──────────────────────────┐
│      Presentation        │ (Jetpack Compose, ViewModel, UI State)
├──────────────────────────┤
│        Domain            │ (Use Cases, Entities) - Lógica de negocio pura
├──────────────────────────┤
│         Data             │ (Repository, Data Sources: ML Kit, Room)
└──────────────────────────┘
```

### Capas

* ** capa de presentación (Presentation):** Construida con **Jetpack Compose** y **ViewModels (MVVM)**. Se encarga de mostrar los datos en la UI y manejar las interacciones del usuario. La comunicación con el ViewModel se realiza a través de flujos de estado (StateFlows) para representar el estado de la UI (ej. `Loading`, `Success`, `Error`).
* ** capa de dominio (Domain):** Contiene la lógica de negocio central y es independiente de cualquier framework. Aquí se definen los **Casos de Uso** (Use Cases) que orquestan el flujo de datos entre la presentación y la capa de datos.
* ** capa de datos (Data):** Proporciona los datos a la aplicación a través del **Patrón Repositorio (Repository Pattern)**. Este repositorio abstrae el origen de los datos, ya sea un servicio de OCR (como ML Kit), una base de datos local o una API remota.

### Patrones de Diseño Utilizados

* **MVVM (Model-View-ViewModel):** Para separar la lógica de la UI de su estado.
* **Patrón Repositorio:** Para abstraer las fuentes de datos.
* **Inyección de Dependencias (DI):** Se utiliza **Hilt** para proveer las dependencias a lo largo de la aplicación, facilitando el desacoplamiento y las pruebas.
* **Patrón State:** Para gestionar el estado de la UI de forma predecible y segura en Jetpack Compose.

---

## 🛠️ Tecnologías y Librerías

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Asincronía:** [Kotlin Coroutines & Flows](https://kotlinlang.org/docs/coroutines-guide.html)
* **Cámara:** [Jetpack CameraX](https://developer.android.com/training/camerax)
* **Reconocimiento de Texto:** [Google ML Kit Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition)
* **Inyección de Dependencias:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* **Arquitectura:** Componentes de Android Jetpack (ViewModel, StateFlow).
* **Diseño:** [Material 3](https://m3.material.io/)

---

## 🚀 Cómo Empezar

Sigue estos pasos para obtener una copia del proyecto y ejecutarla en tu máquina local.

### Prerrequisitos

* Android Studio Narwhal 3 Feature Drop | 2025.1.3.
* JDK 17.

### Instalación

1.  **Clona el repositorio**
    ```sh
    git clone https://github.com/yemmrojas/launch_camerax.git
    ```
2.  **Abre el proyecto** en Android Studio.
3.  **Sincroniza Gradle** y espera a que se descarguen todas las dependencias.
4.  **Ejecuta la aplicación** en un emulador o en un dispositivo físico.

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

Creado por **Yeimer Rojas** | [linkedin](https://www.linkedin.com/in/yeimerrojas)
