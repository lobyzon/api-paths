# Proyect Challenge Java api-paths
# Instrucciones para el despliegue de la app
# 1 - Download del codigo
# Descargar o clonar el código del siguiente repositorio
# https://github.com/lobyzon/api-paths.git
#
# 2 - En este paso se va a ejecutar el siguiente comando para levantar en docker una base de datos Postgres
# Sobre la raiz del proyecto api_paths> en una terminal, se ejecuta lo siguiente:
# docker-compose up -d
#
# 3 - Build de la imagen docker del proyecto
# Sobre la raiz del proyecto api_paths> en una terminal, se ejecuta lo siguiente:
# docker build -t api-paths . -f .\Dockerfile
#
# 4 - Levantar el contenedor con la imagen generada en el paso anterior
# Sobre la raiz del proyecto api_paths> en una terminal, ejecutar el siguiente comando para levantar el container:
# docker run -p 8080:8080 --name api-paths-container api-paths
# Si el puerto 8080 está no disponible, o se prefiere otro puerto, cambiar a gusto
#
# Ejecutar los servicios rest de la api desde el cliente que se desee, por ej Postman
#
# PUT /stations/$station_id
# Ejemplo
# http://localhost:8080/stations/13
# En la sección Body, como JSON, completar con lo siguiente, escribiendo el name que se desee para cada caso
# {
#    "name" : "Retiro"
# }
# Si la station no existe en la base de datos crea una nueva, si no da error
#
# PUT /paths/$path_id
# Ejemplo
# http://localhost:8080/paths/1
# En la sección body, como JSON, completar los campos correspondientes. Tener en cuenta que los nros de station source_id y   destination_id, deben existir, sino obtendremos un error.
# {
#    "cost" : 50,
#    "source_id": 10,
#    "destination_id": 11
# }
#
# GET /paths/$source_id/$destination_id
# Ejemplo
# http://localhost:8080/paths/10/13
# El primer argumento es la station source y el segundo la station destination. Se retornará un JSON con el costo de transporte # más bajo para llegar desde el source al destination