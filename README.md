#Wallester Task

Test task - simple CRUD web application.

Modules:
 - frontend - React.js app
 - backend - Java 13 app

##Backend

Backend API represents CRUD operations of Customer entity.

Base API url: 
    
    host:port/api

Swagger API doc: 

    host:port/api/swagger-ui.html

###Docker

Repo has Dockerfile and .env file with needed variables.

For push an image after build, use maven command:

    mvn clean install -Ddockerfile.useMavenSettingsForAuth=true

And configuration below for settings.xml:

```xml
<servers>
    <server>
		<id>docker.io</id>
		<username>*USERNAME*</username>
		<password>*PASSWORD*</password>
		<configuration>
			<email>*EMAIL*</email>
		</configuration>
	</server>
</servers>
```