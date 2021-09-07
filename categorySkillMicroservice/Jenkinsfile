pipeline {
    agent any

    environment {
        JENKINS_NODE_COOKIE = 'dontKillMe'
    }
    
    stages {
        stage('clean') {
            steps {
                sh 'sudo sh ./mvnw clean'
            }
        }
        stage('package') {
            steps {
                sh 'sudo sh ./mvnw package'
            }
        }
        stage('deploy') {
            steps {
                sh 'sudo ps aux | grep -i \'CVT\' | grep -v grep | awk \'{print $2}\' | xargs kill -9 2> /dev/null || true'
                sh 'sudo nohup java -jar ./target/CVT.jar &'
            }
        }
    }
}
