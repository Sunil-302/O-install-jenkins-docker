# Use the official Playwright image as the base
FROM mcr.microsoft.com/playwright:v1.45.1-jammy

# Install any additional dependencies
USER root
RUN apt-get update && apt-get install -y sudo

# Create jenkins user and group
RUN groupadd -g 1000 jenkins && \
    useradd -u 1000 -g jenkins -m -s /bin/bash jenkins && \
    echo "jenkins ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Switch to the jenkins user
USER jenkins

# Install Playwright browsers
RUN npx playwright install --with-deps
