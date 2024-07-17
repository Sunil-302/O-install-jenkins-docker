# Use the official Playwright image as the base
FROM mcr.microsoft.com/playwright:v1.45.1-jammy

# Install any additional dependencies
USER root
RUN apt-get update && apt-get install -y sudo

# Allow the jenkins user to use sudo without password
RUN echo "jenkins ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Switch back to the jenkins user
USER jenkins

# Install Playwright browsers
RUN npx playwright install --with-deps
