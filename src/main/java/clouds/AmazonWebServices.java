package clouds;

import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.domain.InstanceType;

public class AmazonWebServices extends CloudProvider {

    public AmazonWebServices() {
        super("awsUser", "awsPassword", "aws-ec2");
    }

    @Override
    protected Template getTemplate() {
        Template template = connection.templateBuilder()
                .hardwareId(InstanceType.T2_MICRO)
                .imageNameMatches("14.04")
                .options(connection.templateOptions()
                        .overrideLoginPrivateKey("") // RSA key as String
                        .as(AWSEC2TemplateOptions.class).keyPair("id_rsa") // Name of key pair
                )
                .build();
        return template;
    }

}
