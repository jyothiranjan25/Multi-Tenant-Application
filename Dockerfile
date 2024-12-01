# Step 1: Use Maven to build the project with OpenJDK 21
FROM maven:3.9-amazoncorretto-21 AS build

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the pom.xml and the source code into the container
COPY pom.xml ./
COPY src ./src

# Copy the sample database configuration file
COPY src/main/resources/DatabaseContextFile-sample.xml src/main/resources/DatabaseContextFile.xml

# Update the parameters inside the DatabaseContextFile.xml using sed with proper delimiters
RUN sed -i 's|<property name="jdbcUrl" value="jdbc:postgresql://localhost:5432/test"/>|<property name="jdbcUrl" value="jdbc:postgresql://postgres:5432/springApp"/>|g' src/main/resources/DatabaseContextFile.xml
RUN sed -i 's|<property name="username" value="postgres"/>|<property name="username" value="postgres"/>|g' src/main/resources/DatabaseContextFile.xml
RUN sed -i 's|<property name="password" value="postgres"/>|<property name="password" value="postgres"/>|g' src/main/resources/DatabaseContextFile.xml
#RUN sed -i 's|<prop key="initialize.initialData.enabled">false</prop>|<prop key="initialize.initialData.enabled">true</prop>|g' src/main/resources/applicationContext.xml

# Step 4: Build the project using Maven inside the container
RUN mvn clean package

# Step 5: Use the Tomcat image
FROM tomcat:10

# Step 6: Copy the WAR file from the build stage to the Tomcat webapps directory
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Step 7: Expose the port for Tomcat
EXPOSE 8080

# Step 8: Start Tomcat
CMD ["catalina.sh", "run"]