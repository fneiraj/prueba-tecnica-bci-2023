<a href="#"><img src=".github/bci.png"/></a>

# Desafio Java BCI 2023

Desafio técnico para evaluar el conocimiento de los aspirantes a unirse al equipo de **Backend**.

---
# Solución

### Desarrollado con
    - Java 17
    - Springboot
    - Lombok
    - Maven
    - Junit
    - Swagger

### Consideraciones
- Se utilizo Docker para la creación de contenedores y despliegue del servicio. (puede implementarse kubernetes para una escalabilidad horizontal)
- Se creo un api rest con mocks para consumir los datos de las ofertas y demas.
    
- Endpoint para tomar las ofertas tiene una logica que lo hace fallar de vez en cuando para probar el retry del servicio. 
    Cuando el api de tomar ofertas falla se hace un retry del servicio 3 veces con un delay de 1 segundo entre cada intento.
    Ademas se guarda el error en una base de datos para hacer un analisis posterior. (se puede consultar en http://localhost:8080/reportes/solicitudes-fallidas)

### Servicio desplegado on-premise
Tambien dejare desplegado unos dias el servicio en mi servidor onpremise: [https://bci.fneira.dev](https://bci.fneira.dev/swagger-ui/index.html)
    

### Levantar servicio
#### Levantar servicio via docker-compose:
    Debes abrir una terminal/cmd en el root del repositorio y ejecutar el siguiente comando:
        
        Para compilar el proyecto:
        > docker compose up --build -d
        

 Se desplegara el servicio spring escuchando en el puerto 8080 y el api-mocks en el puerto 3000, se expondran los siguientes endpoints:

    Method: GET  |  Descripción: Iniciar sesión
        > curl -X 'POST' 'http://localhost:8080/auth/iniciar-sesion' -H 'accept: */*' -H 'Content-Type: application/json' -d '{ "email": "string", "password": "string"}'

    Usuarios de prueba:
        - fe.neiraj@gmail.com:12345
        - ariel.orta@bci.cl:12345
    
    Method: GET  |  Descripción: Obtener las ofertas segun importancia y urgencia
        > curl -X 'GET' 'http://localhost:8080/ofertas?importancia=MUY_BAJA&urgencia=BAJA' -H "accept: application/json"
            
    Method: GET  |  Descripción: Obtener las ofertas segun categoria, importancia y urgencia
        > curl -X 'GET' 'http://localhost:8080/ofertas/insurance?urgencia=MEDIA'
        
    Method: POST  |  Descripción: Tomar una oferta
        > curl -X 'POST' 'http://localhost:8080/ofertas' -H 'accept: application/json' -H 'Content-Type: application/json' -d '{ "idOferta": 0, "idMedioPago": 0 }'

    Method: GET  |  Descripción: Visualizar errores en la toma de ofertas
        > curl -X 'GET' 'http://localhost:8080/reportes/solicitudes-fallidas' -H 'accept: application/json'

Para temas de documentación del servicio mediante swagger se disponibiliza los siguientes endpoints:
    
    Descripción: Ver documentación de API mediante Swagger UI
            http://localhost:8080/swagger-ui/index.html
            
    Descripción: Obtener endpoints codificados en JSON para importar a herramientas como postman
           http://localhost:8080/v3/api-docs
    
    Descripción: Collecion Postman para ejecutar APIS:
        https://api.postman.com/collections/11940123-f57f636e-6213-442a-9ecb-bd70e9e733a3?access_key=PMAT-01HHR7NWWKB636H5TJN33NZ8J8