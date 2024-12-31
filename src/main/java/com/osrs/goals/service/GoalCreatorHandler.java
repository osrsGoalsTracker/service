package com.osrs.goals.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dsql.DsqlUtilities;

/**
 * Lambda handler for creating new goals.
 */
@Log4j2
public class GoalCreatorHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final int HTTP_OK = 200;

    /**
     * Default constructor that uses Guice for dependency injection.
     */
    public GoalCreatorHandler() {
    }

    /**
     * Get a connection to Aurora DSQL.
     * 
     * @param clusterEndpoint a
     * @param region          a
     * @return a
     * @throws SQLException a
     */
    public static Connection getConnection(String clusterEndpoint, String region) throws SQLException {
        Properties props = new Properties();

        // Use the DefaultJavaSSLFactory so that Java's default trust store can be used
        // to verify the server's root cert.
        String url = "jdbc:postgresql://" + clusterEndpoint
                + ":5432/postgres?sslmode=verify-full&sslfactory=org.postgresql.ssl.DefaultJavaSSLFactory";

        DsqlUtilities utilities = DsqlUtilities.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        AwsCredentialsProviderChain.builder()
                                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
                                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
                                .addCredentialsProvider(DefaultCredentialsProvider.create())
                                .build())
                .build();

        String password = utilities.generateDbConnectAdminAuthToken(builder -> builder.hostname(clusterEndpoint)
                .region(Region.of(region)));

        props.setProperty("user", "admin");
        props.setProperty("password", password);
        return DriverManager.getConnection(url, props);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        log.info("Received request to create goal");
        String clusterEndpoint = "aeabtvep72mpv3z3ce3vlgx4r4.dsql.us-east-1.on.aws";
        String region = "us-east-1";
        try (Connection conn = getConnection(clusterEndpoint, region)) {
            String selectSQL = "SELECT * FROM public.goal";
            Statement read = conn.createStatement();
            ResultSet rs = read.executeQuery(selectSQL);
            while (rs.next()) {
                log.info(rs.getString("userid"));
                log.info(rs.getString("id"));
            }
        } catch (SQLException e) {
            log.error("Error getting connection", e);
        }

        APIGatewayProxyResponseEvent apiResponse = new APIGatewayProxyResponseEvent();
        try {
            apiResponse.setBody(OBJECT_MAPPER.writeValueAsString("test"));
            apiResponse.setStatusCode(HTTP_OK);
        } catch (Exception e) {
            log.error("Error creating goal", e);
            throw new RuntimeException(e);
        }

        return apiResponse;
    }
}
