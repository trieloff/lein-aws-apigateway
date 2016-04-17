(defproject lein-aws-api-gateway "1.10.68-1"
  :description "A leiningen plugin that helps with AWS API Gateway"
  :url "https://github.com/trieloff/lein-aws-apigateway"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen false
  :dependencies [[com.amazonaws/aws-java-sdk-api-gateway "1.10.68"]
                 [com.amazonaws/aws-java-sdk-core "1.10.68"]
                 [com.amazonaws/aws-java-sdk-s3 "1.10.68"]
                 [byte-streams "0.2.2"]
                 [leiningen-core "2.6.1"]
                 [org.clojure/clojure "1.8.0"]
                 [amazonica "0.3.53" :exclusions [com.amazonaws/aws-java-sdk]]])
