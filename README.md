# aws-apigateway

- A Leiningen plugin to deploy [AWS API Gateway](https://aws.amazon.com/api-gateway/) APIs using [Swagger](http://swagger.io).

## Configuration

Add `[lein-aws-api-gateway "1.0.3-SNAPSHOT"]` to the `:plugins` vector of your
`project.clj` or `profile.clj`.

Configure your `project.clj` by addinng an `:api-gateway` configuration map:

```
:api-gateway {:api-id "your-api-id"
              :deploy "dev"
              :swagger "target/swagger.json"}
```

The configuration parameters map to the command line arguments in the [aws-apigateway-importer](https://github.com/awslabs/aws-apigateway-importer), in particular:

* `swagger` – (required) the path to the Swagger file that should be used in the import. Depending on your workflow, this is most likely somewhere in `target`, if auto-generated or somewhere in `resources` if hand-crafted
* `deploy` – (optional) specifying a value here will automatically deploy the API to the stage provided as a value to this parameter.
* `api-id` – (required, for `update-api` task) the ID of the API that should be updated. The `update-api` task will fail if no `api-id` has been specified, but it is not required for the `create-api` task. You can retrieve the API ID by running `aws apigateway get-rest-apis` if you have the AWS command line tools installed. Right now, the AWS Console does not list the API ID.

## Authentication

The plugin does not allow or encourage specifying authentication credentials in the `project.clj`, so you have to chose one of the following options:
* Specify credentials in `AWS_ACCESS_KEY` and `AWS_SECRET_KEY` environment variables. This works well for continuous integration environments
* Specify credentials in the `aws.accessKeyId` and `aws.secretKey` Java environment variables.
* Use the default credentials stored for the [AWS Command Line tools](https://aws.amazon.com/cli/), stored in `~/.aws/credentials`, which can be set using the `was configure` command
* If you are running on [EC2](https://aws.amazon.com/ec2/), it will pick up the instance profile credentials

For further reference, please check the [AWS Java SDK Credentials Reference documentation](http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html).

## Execution

In order to create a new API, use following command:

```
lein aws-api-gateway create-api
```

Creating a new API will return the API ID. This ID is important for future use. The `:api-id` in your configuration will be ignored when running this task.

To update an API, run

```
lein aws-api-gateway update-api
```

## API Gateway Extensions for Swagger

If you want to deploy the API, your Swagger file should use the `x-amazon-apigateway-auth` and `x-amazon-apigateway-integration` extensions. The aws-apigateway-importer documentation provides an [example](https://github.com/awslabs/aws-apigateway-importer#api-gateway-extension-example).

## Development

Clone and install the plugin:

```
git clone https://github.com/trieloff/lein-aws-apigateway.git
cd lein-aws-apigateway
lein install

```

## Versioning and Licensing

* The current version is 1.10.68 and it wraps the [AWS Java SDK](https://aws.amazon.com/sdk-for-java/) of the same version.
* The plugin is distributed under the terms of the [Eclipse Public License](https://github.com/trieloff/lein-aws-apigateway/blob/master/LICENSE), the same as [Leiningen](http://leiningen.org) uses.

## Limitations and Improvements

* Nobody knows if this thing is working
* When I have time, I want to figure out how to hook the plugin to a [ring](https://github.com/ring-clojure) route in your app, so that it can generate the Swagger file as part of the build process.
