pipeline {
    environment {
        GIT_CREDENTIALS_ID = 'Git_creds' // Replace with your Jenkins credentials ID
        PLAYWRIGHT_IMAGE = 'custom-playwright:v1.45.1-jammy'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git credentialsId: env.GIT_CREDENTIALS_ID, url: 'https://github.com/Sunil-302/Wj5_publications.git'
            }
        }

        stage('Run Playwright Container') {
            steps {
                // Run Playwright container and execute tests
                script {
                    docker.image(env.PLAYWRIGHT_IMAGE).inside {
                        sh 'npm ci'
                        sh 'npx playwright install --with-deps'
                        sh 'npx cross-env test="qa" npx playwright test src/tests/test_dataExport/OVSYN_Pub.spec.ts --project=chrome'
                        sh 'npx playwright show-report'
                    }
                }
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
