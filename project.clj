(defproject lein-aws-api-gateway "1.10.68"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen false
  :dependencies [[com.amazonaws/aws-java-sdk-api-gateway "1.10.68"]
                 [byte-streams "0.2.2"]
                 [leiningen-core "2.6.1"]
                 [org.clojure/clojure "1.8.0"]
                 [amazonica "0.3.53" :exclusions
                  [com.amazonaws/aws-java-sdk-datapipeline
                   com.amazonaws/aws-java-sdk-devicefarm
                   com.amazonaws/aws-java-sdk-directconnect
                   com.amazonaws/aws-java-sdk-directory
                   com.amazonaws/aws-java-sdk-dynamodb
                   com.amazonaws/aws-java-sdk-ec2
                   com.amazonaws/aws-java-sdk-ecr
                   com.amazonaws/aws-java-sdk-ecs
                   com.amazonaws/aws-java-sdk-efs
                   com.amazonaws/aws-java-sdk-elasticache
                   com.amazonaws/aws-java-sdk-elasticbeanstalk
                   com.amazonaws/aws-java-sdk-elasticloadbalancing
                   com.amazonaws/aws-java-sdk-elasticsearch
                   com.amazonaws/aws-java-sdk-elastictranscoder
                   com.amazonaws/aws-java-sdk-emr
                   com.amazonaws/aws-java-sdk-events
                   com.amazonaws/aws-java-sdk-glacier
                   com.amazonaws/aws-java-sdk-iam
                   com.amazonaws/aws-java-sdk-importexport
                   com.amazonaws/aws-java-sdk-inspector
                   com.amazonaws/aws-java-sdk-iot
                   com.amazonaws/aws-java-sdk-kinesis
                   com.amazonaws/aws-java-sdk-kms
                   com.amazonaws/aws-java-sdk-lambda
                   com.amazonaws/aws-java-sdk-logs
                   com.amazonaws/aws-java-sdk-machinelearning
                   com.amazonaws/aws-java-sdk-marketplacecommerceanalytics
                   com.amazonaws/aws-java-sdk-opsworks
                   com.amazonaws/aws-java-sdk-rds
                   com.amazonaws/aws-java-sdk-redshift
                   com.amazonaws/aws-java-sdk-route53
                   com.amazonaws/aws-java-sdk-s3
                   com.amazonaws/aws-java-sdk-ses
                   com.amazonaws/aws-java-sdk-simpledb
                   com.amazonaws/aws-java-sdk-simpleworkflow
                   com.amazonaws/aws-java-sdk-sns
                   com.amazonaws/aws-java-sdk-sqs
                   com.amazonaws/aws-java-sdk-ssm
                   com.amazonaws/aws-java-sdk-storagegateway
                   com.amazonaws/aws-java-sdk-sts
                   com.amazonaws/aws-java-sdk-support
                   com.amazonaws/aws-java-sdk-swf-libraries
                   com.amazonaws/aws-java-sdk-waf
                   com.amazonaws/aws-java-sdk-workspaces
                   com.amazonaws/aws-java-sdk-acm
                   com.amazonaws/aws-java-sdk-api-gateway
                   com.amazonaws/aws-java-sdk-autoscaling
                   com.amazonaws/aws-java-sdk-cloudformation
                   com.amazonaws/aws-java-sdk-cloudfront
                   com.amazonaws/aws-java-sdk-cloudhsm
                   com.amazonaws/aws-java-sdk-cloudsearch
                   com.amazonaws/aws-java-sdk-cloudtrail
                   com.amazonaws/aws-java-sdk-cloudwatch
                   com.amazonaws/aws-java-sdk-cloudwatchmetrics
                   com.amazonaws/aws-java-sdk-codecommit
                   com.amazonaws/aws-java-sdk-codedeploy
                   com.amazonaws/aws-java-sdk-codepipeline
                   com.amazonaws/aws-java-sdk-cognitoidentity
                   com.amazonaws/aws-java-sdk-cognitosync]]])
