package com.myorg;

import java.util.Arrays;
import java.util.HashMap;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.services.apigateway.AuthorizationType;
import software.amazon.awscdk.services.apigateway.EndpointConfiguration;
import software.amazon.awscdk.services.apigateway.EndpointType;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.MethodOptions;
import software.amazon.awscdk.services.apigateway.Resource;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.apigateway.RestApiProps;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.s3.Bucket;

public class MicronautService extends Construct {

    @SuppressWarnings("serial")
    public MicronautService(Construct scope, String id) {
        super(scope, id);

        Bucket bucket = new Bucket(this, "WidgetStore");

        Function handler = Function.Builder.create(this, "books")
                .runtime(Runtime.PROVIDED)
                .code(Code.fromAsset("resources/build/libs/demo-0.1-lambda.zip"))
                .handler("io.micronaut.function.aws.proxy.MicronautLambdaHandler")
                .environment(new HashMap<String, String>() {{
                    put("BUCKET", bucket.getBucketName());
                }}).build();

        bucket.grantReadWrite(handler);

        RestApiProps apiGatewayProps = RestApiProps.builder()
                .restApiName("Widget Service").description("This service services widgets.")
                .endpointConfiguration(EndpointConfiguration.builder().types(Arrays.asList(EndpointType.REGIONAL)).build())
                .defaultMethodOptions(MethodOptions.builder()
                        .authorizationType(AuthorizationType.NONE)
                        .build())
                .build();

        RestApi api = new RestApi(this, "Widgets-API", apiGatewayProps);

        LambdaIntegration getWidgetsIntegration = LambdaIntegration.Builder.create(handler)
                .requestTemplates(new HashMap<String, String>() {{
                    put("application/json", "{ \"statusCode\": \"200\" }");
                }}).build();

        api.getRoot().addMethod("ANY", getWidgetsIntegration);
    }
}