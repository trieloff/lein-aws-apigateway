(ns leiningen.aws-api-gateway
  "Deploy swagger.json to API Gateway"
  (import com.amazonaws.services.apigateway.AmazonApiGatewayClient
          com.amazonaws.services.apigateway.model.ImportRestApiRequest
          com.amazonaws.services.apigateway.model.PutRestApiRequest)
  (require [clojure.pprint :refer [pprint]]
           [byte-streams :as byte-streams]
           [amazonica.aws.apigateway :as aws]
           [leiningen.core.project :refer [merge-profiles]]
           [clojure.reflect :refer [reflect]]
           [leiningen.core.eval :refer [eval-in-project]]))


(defn build-args
  [{api-gateway :api-gateway} task]
  (let [{:keys [swagger deploy api-id profile raml-config]} api-gateway
        basearg (case task
                  :update (vector "--update" api-id)
                  :create (vector "--create"))
        stagearg (if (nil? deploy) '() (vector "--deploy" deploy))
        profilearg (if (nil? profile) '() (vector "--profile" profile))
        raml-arg (if (nil? raml-config) '() (vector "--raml-config" raml-config))]
    (concat basearg stagearg profilearg raml-arg (vector swagger))))

(defn update-api
  "Update an existing API"
  [project args]
  (pprint (build-args project :update))
  (println "ApiImporterMain/main" (into-array String (build-args project :update))))


(defn create-api
  "Create a new API"
  [project args]
  (pprint (build-args project :create))
  (println "ApiImporterMain/main" (into-array String (build-args project :create))))

(defn aws-api-gateway
  "Deploy swagger.json to AWS API Gateway"
  {:subtasks [#'create-api #'update-api]}
  [project & [task args]]
  (case task
    "create-api" (create-api project args)
    "update-api" (update-api project args)
    :nil     :not-implemented-yet
    (leiningen.core.main/warn "Use 'create-api' or 'update-api' as subtasks")))

(defn import-rest-api [swagger]
  (let [swaggerbb (byte-streams/convert swagger java.nio.ByteBuffer)
        request (.withBody
                   (ImportRestApiRequest.)
                   swaggerbb)]
    (do
      (.setParameters request {})
      (.getId (.importRestApi (AmazonApiGatewayClient.) request)))))

(defn update-rest-api [swagger id]
  (let [swaggerbb (byte-streams/convert swagger java.nio.ByteBuffer)
        request (.withMode
                  (.withRestApiId
                    (.withBody
                      (PutRestApiRequest.)
                      swaggerbb)
                    id)
                  "overwrite")]
    (do
      (.setParameters request {})
      (.getId (.putRestApi (AmazonApiGatewayClient.) request)))))

(def myfile (clojure.java.io/file "/Users/trieloff/Documents/excelsior/resources/swagger-example.json"))

;(import-rest-api myfile)

(aws/get-rest-api :rest-api-id "04511k2k4e")

(update-rest-api myfile "04511k2k4e")
