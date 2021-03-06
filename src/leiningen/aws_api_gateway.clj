(ns leiningen.aws-api-gateway
  "Deploy swagger.json to API Gateway"
  (:refer-clojure :exclude [update])
  (import com.amazonaws.services.apigateway.AmazonApiGatewayClient
          com.amazonaws.services.apigateway.model.ImportRestApiRequest
          com.amazonaws.services.apigateway.model.PutRestApiRequest
          com.amazonaws.services.apigateway.model.CreateDeploymentRequest
          com.amazonaws.services.apigateway.model.DeleteRestApiRequest)
  (require [byte-streams :as byte-streams]
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

(defn deploy-rest-api [id stage]
  (let [request (.withStageName
                  (.withRestApiId
                    (CreateDeploymentRequest.)
                    id)
                  stage)]
    (.getId (.createDeployment (AmazonApiGatewayClient.) request))))

(defn create
  "Create a new API"
  [project args]
  (if-not (-> project :api-gateway :swagger)
    (leiningen.core.main/abort "Please add :api-gateway :swagger to your profile")
    (println "Created API with ID:" (import-rest-api (clojure.java.io/file (-> project :api-gateway :swagger))))))

(defn update
  "Update an existing API"
  [project args]
  (if-not (-> project :api-gateway :api-id)
    (leiningen.core.main/abort "Please add :api-gateway :api-id to your profile")
    (if-not (-> project :api-gateway :swagger)
      (leiningen.core.main/abort "Please add :api-gateway :swagger to your profile")
      (println "Updated API with Deployment ID:" (update-rest-api
                                         (clojure.java.io/file (-> project :api-gateway :swagger))
                                         (-> project :api-gateway :api-id))))))

(defn delete
  "Delete an existing API"
  [project args]
  (if (nil? args)
    (leiningen.core.main/abort "Please specify the API ID, e.g. 'lein aws-api-gateway delete 123xyz98ba'")
      (do
        (delete-rest-api (str args))
        (println "Deleted API with ID:" args)
        (identity args))))

(defn deploy
  "Deploy an existing API"
  [project args]
  (if-not (-> project :api-gateway :api-id)
    (leiningen.core.main/abort "Please add :api-gateway :api-id to your profile")
    (if (nil? args)
      (leiningen.core.main/abort "Please specify the deploy stage, e.g. 'lein aws-api-gateway deploy dev'")
      (println "Deployed API with ID:" (deploy-rest-api
                                         (-> project :api-gateway :api-id)
                                         (str args))))))

(defn aws-api-gateway
  "Deploy swagger.json to AWS API Gateway"
  {:subtasks [#'create #'update #'delete #'deploy]}
  [project & [task args]]
  (case task
    "create" (create project args)
    "update" (update project args)
    "delete" (delete project args)
    "deploy" (deploy project args)
    :nil     :not-implemented-yet
    (leiningen.core.main/warn "Use 'create', 'delete', 'deploy' or 'update' as subtasks")))
