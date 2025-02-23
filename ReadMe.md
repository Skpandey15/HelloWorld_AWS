Step-by-Step Deployment Summary
Create Your Spring Boot Application:

Use Spring Initializr to generate your project.
Include at least the Spring Web dependency.
Write a simple “Hello World” REST controller.
Build the Application:

Run:
sh
Copy
mvn clean package
This generates an executable (fat) JAR file in the target/ folder.
Prepare Your Deployment Files:

Copy the JAR to the Root Directory:
In Windows Command Prompt:
sh
Copy
copy target\HelloWorld_AWS-0.0.1-SNAPSHOT.jar application.jar
Verify with:
sh
Copy
dir application.jar
It should be a large file (around 20MB), not a tiny placeholder.
Create a Procfile:
In your project root, create a file named Procfile (without any extension).
Content:
less
Copy
web: java -jar application.jar --server.port=5000
This file tells Elastic Beanstalk how to start your application.
Set the Server Port:
Open src/main/resources/application.properties and add:
properties
Copy
server.port=5000
This ensures your app listens on the port Elastic Beanstalk expects.
Ensure Files Are Included in Your Deployment Bundle:

Elastic Beanstalk uses Git by default. Check that all necessary files (especially application.jar and Procfile) are tracked:
sh
Copy
git status
If they’re untracked, add and commit them:
sh
Copy
git add Procfile application.jar
git commit -m "Include deployment files for Elastic Beanstalk"
Initialize Elastic Beanstalk (if not already done):

Run:
sh
Copy
eb init
Select your AWS region and choose the Java platform (e.g., Corretto 17 on Amazon Linux 2023).
Create the Environment:

Run:
sh
Copy
eb create springboot-env
This provisions the necessary resources (EC2, load balancer, etc.) and deploys your application.
Deploy Your Application:

For subsequent deployments, run:
sh
Copy
eb deploy
If you run into issues with missing files in the bundle, use:
sh
Copy
eb deploy --staged
to force EB CLI to use the current state of your directory.
Verify Your Deployment:

Check environment status:
sh
Copy
eb status
Open your live app in a browser:
sh
Copy
eb open
Troubleshooting and Logs:

If deployment fails or you encounter runtime errors, fetch logs:
sh
Copy
eb logs
Common issues include:
Missing files in the bundle: Ensure Procfile and application.jar are committed.
Port issues: Make sure server.port=5000 is set.
Java version mismatches: Use the same Java version locally as configured in Elastic Beanstalk (e.g., set <java.version>17</java.version> in pom.xml).
Tips to Avoid Future Issues
Commit Everything:
Always add and commit deployment-critical files (like Procfile and the built JAR) to your Git repository so that EB CLI includes them.

Use a Consistent Port:
Elastic Beanstalk expects your app on port 5000 by default. Keep this consistent in your Procfile and application.properties.

Verify Deployment Bundle Contents:
If you suspect missing files, use the eb deploy --staged option or manually create a ZIP package including all necessary files.

Check Logs Frequently:
Use eb logs to diagnose any issues. The logs (especially /var/log/eb-engine.log) give clues about startup errors or misconfigurations.

Match Java Versions:
Ensure your local build (using Java 17 as specified in your pom.xml) matches the runtime environment in AWS Elastic Beanstalk.