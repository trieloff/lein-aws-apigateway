(ns leiningen.aws-api-gateway
  "Deploy swagger.json to API Gateway"
  (import com.amazonaws.services.apigateway.AmazonApiGatewayClient
          com.amazonaws.services.apigateway.model.ImportRestApiRequest
          com.amazonaws.services.apigateway.model.PutRestApiRequest
          com.amazonaws.services.apigateway.model.DeleteRestApiRequest)
  (require [clojure.pprint :refer [pprint]]
           [byte-streams :as byte-streams]
           [leiningen.core.project :refer [merge-profiles]]
           [clojure.reflect :refer [reflect]]
           [leiningen.core.eval :refer [eval-in-project]]))

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

(defn delete-rest-api [id]
  (let [request (.withRestApiId (DeleteRestApiRequest.) id)]
    (.deleteRestApi (AmazonApiGatewayClient.) request)))

(defn create-api
  "Create a new API"
  [project args]
  (if-not (-> project :api-gateway :swagger)
    (leiningen.core.main/warn "Please add :api-gateway :swagger to your profile")
    (println "Created API with ID:" (import-rest-api (clojure.java.io/file (-> project :api-gateway :swagger))))))

(defn update-api
  "Update an existing API"
  [project args]
  (if-not (-> project :api-gateway :api-id)
    (leiningen.core.main/warn "Please add :api-gateway :api-id to your profile")
    (if-not (-> project :api-gateway :swagger)
      (leiningen.core.main/warn "Please add :api-gateway :swagger to your profile")
      (println "Updated API with ID:" (update-rest-api
                                         (clojure.java.io/file (-> project :api-gateway :swagger))
                                         (-> project :api-gateway :api-id))))))

(defn delete-api
  "Delete an existing API"
  [project args]
  (if (nil? args)
    (leiningen.core.main/warn "Please specify the API ID, e.g. 'lein aws-api-gateway delete-api 123xyz98ba'")
      (do
        (delete-rest-api (str args))
        (println "Deleted API with ID:" args)
        (identity args))))


(defn aws-api-gateway
  "Deploy swagger.json to AWS API Gateway"
  {:subtasks [#'create-api #'update-api]}
  [project & [task args]]
  (case task
    "create-api" (create-api project args)
    "update-api" (update-api project args)
    "delete-api" (delete-api project args)
    :nil     :not-implemented-yet
    (leiningen.core.main/warn "Use 'create-api', 'delete-api' or 'update-api' as subtasks")))


;(def myfile (clojure.java.io/file "/Users/trieloff/Documents/excelsior/resources/swagger-example.json"))

;(import-rest-api myfile)

;(aws/get-rest-api :rest-api-id "04511k2k4e")

;(update-rest-api myfile "04511k2k4e")
