(defproject lein-aws-api-gateway "1.0.3-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen false
  :dependencies [[com.amazonaws/aws-apigateway-importer "1.0.3-SNAPSHOT" :exclusions [joda-time com.amazonaws/aws-java-sdk-core]]
                 [com.amazonaws/aws-java-sdk-core "1.9.40" :exclusions [joda-time]]])
