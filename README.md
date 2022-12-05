Pilotes order service API
=
This is pilotes order service API using Spring boot.

Download the source code. You can either download it as a zip file 
and extract it or simply execute the following command in the terminal.

```git clone https://github.com/messor2000/order_service.git```

Change your directory into the project folder. And run the following command.

```mvn install```

Next run the command,

```mvn spring-boot:run```

and the server will start automatically. 
Try to put this  ```http://localhost:8080/v2/api-docs``` link to the poster to see the api documentation.

>NOTE
> 
>This project used in-memory H2 database. 
> 
>So the database will be overridden each time when you rerun your project 

If you want to run the ```jar``` version of the spring app, 
locate to the folder ```/target``` which will be in your directory when you run the mvn 
install command, and in there you will see you ```.jar``` file.

To run the war file, use the below code.

```java -jar target/PilotesOrderServiceAPI-0.0.1-SNAPSHOT.jar```

## Run project in the docker

If you wish to use the docker file for the building, please follow the following steps.

1. Make sure you have already installed Docker in your machine. If it is not, please refere this [link](https://docs.docker.com/engine/install/)
2. Use a terminal inside the project where the Dockerfile is located. Execute the following command to build the image. Replace ```<your tag>``` by the tag of your own. ```docker build -t <your tag>```
3. Run the image as a container. ```docker run -d -p 8080:8080 <yout tag>```
4. Past this ```http://localhost:8080/<endpoint from controller>``` into the Postman and use API

Please submit any issue that you faced when developing your spring-boot application.