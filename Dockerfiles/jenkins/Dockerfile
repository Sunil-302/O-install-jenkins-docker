FROM jenkins/jenkins:lts-jdk17

RUN apt-get update && apt-get install -y --no-install-recommends \
    curl openssl gnupg ca-certificates lsb-release

RUN curl -sL https://deb.nodesource.com/setup_18.x | bash -
RUN apt-get update && apt-get install -y --no-install-recommends nodejs

RUN npm install -g playwright

USER jenkins
