## Native Micronaut app deployed to AWS lambda with AWS CDK

A micronaut app built as a native Graal app, deployed (via GitHub Actions) to AWS with an API Gateway endpoint, using a CDK.

### More info on CDK

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a Maven based project (with a Gradle built Micronaut app).

### Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!
