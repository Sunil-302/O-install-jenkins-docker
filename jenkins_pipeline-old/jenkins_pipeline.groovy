pipeline {
    agent {
        docker {
            image 'mcr.microsoft.com/playwright:v1.45.1-jammy'
        }
    }

    environment {
        GIT_CREDENTIALS_ID = 'Git_creds' // Replace with your Jenkins credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git credentialsId: env.GIT_CREDENTIALS_ID, url: 'https://github.com/Sunil-302/Wj5_publications.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                // Install Node.js and Playwright dependencies
                sh 'npm ci'
                sh 'npx playwright install --with-deps'
            }
        }

        stage('Run Tests') {
            steps {
                // Run the Playwright tests
                sh 'npx cross-env test="qa" npx playwright test src/tests/test_dataExport/OVSYN_Pub.spec.ts --project=chrome'
            }
        }

        stage('Generate Report') {
            steps {
                // Generate the HTML report
                sh 'npx playwright show-report'
            }
        }

        stage('Publish Blue Ocean Report') {
            steps {
                // Publish the Blue Ocean report
                publishHTML(target: [
                    reportDir: 'playwright-report',
                    reportFiles: 'index.html',
                    reportName: 'Playwright HTML Report'
                ])
            }
        }
    }

    post {
        always {
            // Archive the test results
            archiveArtifacts artifacts: 'playwright-report/**', allowEmptyArchive: true
            junit 'playwright-report/*.xml'
        }
    }
}
