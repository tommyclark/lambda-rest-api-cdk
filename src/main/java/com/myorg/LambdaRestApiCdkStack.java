package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

public class LambdaRestApiCdkStack extends Stack {
    public LambdaRestApiCdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public LambdaRestApiCdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        new MicronautService(this, "micronaut");

    }
}
