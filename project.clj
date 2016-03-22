(defproject lein-aws-api-gateway "1.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen false
  :dependencies [[com.amazonaws/aws-apigateway-importer "1.0.1" :exclusions [joda-time com.amazonaws/aws-java-sdk-core]]
                 [com.amazonaws/aws-java-sdk "1.10.62"]])
