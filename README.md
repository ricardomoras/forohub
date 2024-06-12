# ForoHub

ForoHub es una aplicación de foro desarrollada con Spring Boot y JPA, que permite a los usuarios crear, leer, actualizar y eliminar tópicos y respuestas.

## Características

- **Autenticación JWT**: Autenticación y autorización segura utilizando JSON Web Tokens.
- **CRUD de Tópicos y Respuestas**: Funcionalidades completas de creación, lectura, actualización y eliminación para tópicos y respuestas.
- **Gestión de Usuarios y Perfiles**: Gestión de usuarios con diferentes roles y perfiles.
- **Gestión de Cursos**: Gestión de cursos asociados a los tópicos.

## API Endpoints

### Tópicos

- **GET** `/api/topicos` - Obtener todos los tópicos
- **GET** `/api/topicos/{id}` - Obtener un tópico por ID
- **POST** `/api/topicos` - Crear un nuevo tópico
- **PUT** `/api/topicos/{id}` - Actualizar un tópico existente

### Respuestas

- **GET** `/api/respuestas` - Obtener todas las respuestas
- **GET** `/api/respuestas/{id}` - Obtener una respuesta por ID
- **POST** `/api/respuestas` - Crear una nueva respuesta
- **PUT** `/api/respuestas/{id}` - Actualizar una respuesta existente

### Usuarios

- **GET** `/api/usuarios` - Obtener todos los usuarios
- **GET** `/api/usuarios/{id}` - Obtener un usuario por ID
- **POST** `/api/usuarios` - Crear un nuevo usuario
- **PUT** `/api/usuarios/{id}` - Actualizar un usuario existente

## Autorización

ForoHub utiliza JWT para la autenticación y autorización. Para acceder a los endpoints protegidos, es necesario incluir un token JWT en el encabezado de la solicitud.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/ricardomoras/forohub.git
