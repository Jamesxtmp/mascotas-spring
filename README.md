# API REST CRUD - Sistema de Mascotas

## Endpoints Disponibles

### Cliente - `/cliente`
```
GET    /cliente              Obtener todos los clientes
GET    /cliente/{id}         Obtener cliente por ID
POST   /cliente              Crear nuevo cliente
PUT    /cliente/{id}         Actualizar cliente
DELETE /cliente/{id}         Eliminar cliente
```

### Mascota - `/mascotas`
```
GET    /mascotas             Obtener todas las mascotas
GET    /mascotas/{id}        Obtener mascota por ID
POST   /mascotas             Crear nueva mascota
PUT    /mascotas/{id}        Actualizar mascota
DELETE /mascotas/{id}        Eliminar mascota
```

### Servicio - `/servicio`
```
GET    /servicio             Obtener todos los servicios
GET    /servicio/{id}        Obtener servicio por ID
POST   /servicio             Crear nuevo servicio
PUT    /servicio/{id}        Actualizar servicio
DELETE /servicio/{id}        Eliminar servicio
```

### Dirección - `/direccion`
```
GET    /direccion            Obtener todas las direcciones
GET    /direccion/{id}       Obtener dirección por ID
POST   /direccion            Crear nueva dirección
PUT    /direccion/{id}       Actualizar dirección
DELETE /direccion/{id}       Eliminar dirección
```

### Mascota-Servicio - `/mascota-servicio`
```
GET    /mascota-servicio     Obtener todos los registros
GET    /mascota-servicio/{id} Obtener registro por ID
POST   /mascota-servicio     Crear nuevo registro
PUT    /mascota-servicio/{id} Actualizar registro
DELETE /mascota-servicio/{id} Eliminar registro
```

---

## Ejemplos CURL

### Crear Cliente
```bash
curl -X POST http://localhost:8080/cliente \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apePaterno": "García",
    "apeMaterno": "López",
    "email": "juan@mail.com"
  }'
```

### Obtener todos los clientes
```bash
curl http://localhost:8080/cliente
```

### Obtener cliente por ID
```bash
curl http://localhost:8080/cliente/1
```

### Actualizar cliente
```bash
curl -X PUT http://localhost:8080/cliente/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Carlos",
    "apePaterno": "García",
    "apeMaterno": "López",
    "email": "juanc@mail.com"
  }'
```

### Eliminar cliente
```bash
curl -X DELETE http://localhost:8080/cliente/1
```

### Crear mascota (requiere cliente existente)
```bash
curl -X POST http://localhost:8080/mascotas \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Pelusa",
    "sexo": "H",
    "tipo": "Gato",
    "edad": 3,
    "enPeligro": false,
    "cliente": {"idCliente": 1}
  }'
```

### Crear servicio
```bash
curl -X POST http://localhost:8080/servicio \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Vacunación anual",
    "precio": 50.00
  }'
```

### Crear dirección
```bash
curl -X POST http://localhost:8080/direccion \
  -H "Content-Type: application/json" \
  -d '{
    "calle": "Calle Principal",
    "numero": "123",
    "cliente": {"idCliente": 1}
  }'
```

### Asociar servicio a mascota
```bash
curl -X POST http://localhost:8080/mascota-servicio \
  -H "Content-Type: application/json" \
  -d '{
    "fecha": "2026-03-07T10:30:00",
    "nota": "Primera vacunación",
    "mascota": {"idMascota": 1},
    "servicio": {"idServicio": 1}
  }'
```

---

## Códigos HTTP

- **200** OK - Operación exitosa (GET, PUT)
- **201** Created - Recurso creado (POST)
- **204** No Content - Eliminado correctamente (DELETE)
- **404** Not Found - Recurso no existe
- **422** Unprocessable Entity - Datos inválidos

---

## Compilar y Ejecutar

```bash
# Compilar
mvnw clean compile

# Ejecutar
mvnw spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

