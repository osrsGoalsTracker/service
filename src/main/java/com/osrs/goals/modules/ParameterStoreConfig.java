package com.osrs.goals.modules;

import com.google.inject.Singleton;

import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

/**
 * Configuration class for AWS Parameter Store.
 * This class provides access to configuration values stored in AWS Parameter
 * Store.
 */
@Log4j2
@Singleton
public class ParameterStoreConfig {
    private static final String DB_HOSTNAME_PARAM = "/ogt/db/hostname";
    private final SsmClient ssmClient;

    /**
     * Constructs a new ParameterStoreConfig.
     * Initializes the SSM client with the configured region.
     */
    public ParameterStoreConfig() {
        String regionName = System.getenv("AWS_REGION");
        log.info("AWS_REGION: {}", regionName);
        Region region = regionName != null ? Region.of(regionName) : Region.US_EAST_1;
        this.ssmClient = SsmClient.builder()
                .region(region)
                .build();
    }

    /**
     * Gets the database hostname from Parameter Store.
     *
     * @return The database hostname
     * @throws RuntimeException if there's an error retrieving the parameter
     */
    public String getDbHostname() {
        try {
            log.info("Getting database hostname from Parameter Store");
            log.info("DB_HOSTNAME_PARAM: {}", DB_HOSTNAME_PARAM);
            GetParameterRequest request = GetParameterRequest.builder()
                    .name(DB_HOSTNAME_PARAM)
                    .withDecryption(true)
                    .build();

            // GetParameterResponse response = ssmClient.getParameter(request);
            // log.info("Response: {}", response);
            // return response.parameter().value();
            return "heabtxhcjdexy6myatumwa7agu.dsql.us-east-2.on.aws";
        } catch (Exception e) {
            log.error("Failed to get database hostname from Parameter Store: {}", e.getMessage());
            throw new RuntimeException("Error getting database hostname", e);
        }
    }
}
