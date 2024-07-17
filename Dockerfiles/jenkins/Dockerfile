# Use the base Playwright image
FROM mcr.microsoft.com/playwright:v1.45.1-jammy

# Install sudo and configure Jenkins user with sudo access
RUN apt-get update \
    && apt-get install -y sudo \
    && echo "jenkins ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Switch to the jenkins user (assuming your Jenkins user is named 'jenkins')
USER jenkins

# Set the working directory
WORKDIR /home/jenkins

# Install Playwright with dependencies as the Jenkins user
RUN npx playwright install --with-deps
