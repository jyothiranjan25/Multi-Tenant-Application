# Step 0: Use Node.js to build the project
FROM node:20 AS node

# Set the working directory in the container
WORKDIR /react-app

# Copy the package.json and the source code into the container
COPY src/main/webapp/myApp ./

# Install the dependencies
RUN npm install
RUN npm run build

# Step 1: Use Maven to build the project with OpenJDK 21
FROM maven:3.9-amazoncorretto-21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code into the container
COPY docker/pom.xml ./
COPY src ./src

# Copy the sample database configuration file
COPY src/main/resources/DatabaseContextFile-sample.xml src/main/resources/DatabaseContextFile.xml

# Update the parameters inside the DatabaseContextFile.xml using sed with proper delimiters
RUN sed -i 's|<property name="jdbcUrl" value="jdbc:postgresql://localhost:5432/test"/>|<property name="jdbcUrl" value="jdbc:postgresql://postgres:5432/springApp"/>|g' src/main/resources/DatabaseContextFile.xml
RUN sed -i 's|<property name="username" value="postgres"/>|<property name="username" value="postgres"/>|g' src/main/resources/DatabaseContextFile.xml
RUN sed -i 's|<property name="password" value="postgres"/>|<property name="password" value="postgres"/>|g' src/main/resources/DatabaseContextFile.xml
#RUN sed -i 's|<prop key="initialize.initialData.enabled">false</prop>|<prop key="initialize.initialData.enabled">true</prop>|g' src/main/resources/applicationContext.xml

# Copy the compiled files from the Node.js build stage
COPY --from=node /react-app src/main/webapp/myApp

# Build the project using Maven inside the container
RUN mvn clean package

# Step 2: Use the Tomcat image
FROM tomcat:10

# Copy the WAR file from the build stage to the Tomcat webapps directory
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose the port for Tomcat
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]