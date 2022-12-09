#!/usr/bin/groovy
pipeline {

    agent { node { label 'murex-build-mpl' } }

    stages {
        stage('compile') {
            steps {
                sh 'mvn clean install -DuseDevMavenRepo -DskipTests=true'
            }
        }

        stage('test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}