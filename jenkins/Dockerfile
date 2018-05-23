
FROM jenkins/jenkins:lts

#just info about who created this
MAINTAINER Amit Kumar (toamitkumar@gmail.com)

# Add binaries for Maven to be setup
# USER root
# RUN apt-get update && apt-get install -y maven

USER jenkins

#get rid of admin password setup
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Adding default Jenkins Seed Job
COPY jobs/job-dsl-seed-job.xml /usr/share/jenkins/ref/jobs/job-dsl-seed-job/config.xml

# installing all plugins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt