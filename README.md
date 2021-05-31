![GitHub repo size](https://img.shields.io/github/repo-size/AlianPro/AmeDigital-Challenge--API)
![GitHub language count](https://img.shields.io/github/languages/count/AlianPro/AmeDigital-Challenge--API)

<details id="pt">
  <summary>PT/BR:brazil:</summary>
  
  # AmeDigital-Challenge--API

## :page_with_curl:Sobre
- Nossos associados são aficionados por Star Wars e com isso, queremos criar um jogo com algumas informações da franquia. Para possibilitar a equipe de front criar essa aplicação, queremos desenvolver uma API que contenha os dados dos planetas, que podem ser obtidas pela API pública do Star Wars (https://swapi.dev/).
```console
https://github.com/AmeDigital/challenge-back-end
```

## :bookmark_tabs:Dependências

Este projeto é construido usando:

- Java 11
- Spring Boot 2.4.5
- Spring Data
- Lombok
- MapStruct
- Swagger
- Junit 5, AssertJ, Mockito - H2 para o repositório de teste
- MySql DataBase
- Docker - docker compose


## :earth_americas:LINUX
## :whale:Run
```console
docker-compose up
```
## :hammer:Build
```console
./mvnw clean install
./mvnw spring-boot:run
```
---  
    
## :earth_americas:WINDOWS
## :whale:Run
```console
docker-compose up
```
## :hammer:Build
```console
mvnw.cmd clean install 
mvnw.cmd spring-boot:run
```

## :mag_right:Endpoints

|Método | 	Url		| 	Descrição |
|-------| ------- | ----------- |
|POST|/ame| 	Criar um planeta|
|GET| /v2/api-docs| 	Swagger json|
|GET|/swagger-ui.html| 	Swagger html|
|GET|/ame/list/planets| 	Listar todos os planetas|
|GET|/ame/list/planets/{id}| 	Escolher um planeta específico|
|GET|/ame/list/planets/swapi| 	Listar todos os planetas do Swapi|
|GET|/ame/list/planets/search?name| 	Listar os planetas e filtre-os por nome|
|DELETE|/ame/remove/{id}| 	Deletar um planeta|

![Screenshot_2021-05-19 Swagger UI](https://user-images.githubusercontent.com/13512651/118895482-a249e600-b8dc-11eb-9f02-6bc11d0469a7.png)
>Swagger

## :unlock:Licença 

Este software foi criado apenas para fins de estudo. Sinta-se à vontade para experimentar. 

</details>

---
  
# AmeDigital-Challenge--API

## :page_with_curl:About
- Our associates are passionate about Star Wars and with that, we want to create a game with some information from the franchise. To enable the front team to create this application, we want to develop an API that contains the data from the planets, which can be obtained through the public Star Wars API (https://swapi.dev/).
```console
https://github.com/AmeDigital/challenge-back-end
```

## :bookmark_tabs:Dependencies

This project is built using:

- Java 11
- Spring Boot 2.4.5
- Spring Data
- Lombok
- MapStruct
- Swagger
- Junit 5, AssertJ, Mockito - H2 to repository tests
- MySql DataBase
- Docker - docker compose


## :earth_americas:LINUX
## :whale:Run
```console
docker-compose up
```
## :hammer:Build
```console
./mvnw clean install
./mvnw spring-boot:run
```
---

## :earth_americas:WINDOWS
## :whale:Run
```console
docker-compose up
```
## :hammer:Build
```console
mvnw.cmd clean install 
mvnw.cmd spring-boot:run
```

## :mag_right:Endpoints

|Method | 	Url		| 	Description |
|-------| ------- | ----------- |
|POST|/ame| 	Create a planet|
|GET| /v2/api-docs| 	Swagger json|
|GET|/swagger-ui.html| 	Swagger html|
|GET|/ame/list/planets| 	List all planets|
|GET|/ame/list/planets/{id}| 	Get a specific planet|
|GET|/ame/list/planets/swapi| 	List all planets Swapi|
|GET|/ame/list/planets/search?name| 	List planets and filter them by name|
|DELETE|/ame/remove/{id}| 	Delete a planet|


![Screenshot_2021-05-19 Swagger UI](https://user-images.githubusercontent.com/13512651/118895482-a249e600-b8dc-11eb-9f02-6bc11d0469a7.png)
>Swagger


## :unlock:License 

This software was created for study purposes only. Feel free to try it out.
