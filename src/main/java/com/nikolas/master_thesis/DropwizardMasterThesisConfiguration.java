package com.nikolas.master_thesis;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.annotation.JsonProperty;
// import com.nikolas.master_thesis.config.HikariConfiguration;
// import com.nikolas.master_thesis.config.ManagedHikariPoolWrapper;
// import com.zaxxer.hikari.HikariConfig;
import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DropwizardMasterThesisConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @JsonProperty("applicationContextPath")
    public String applicationContextPath;

    @JsonProperty("adminContextPath")
    public String adminContextPath;

/*
    HikariConfiguration hikariConfiguration;

    private ManagedDataSource managedDataSource;

    public long connectionTimeoutMillis = TimeUnit.SECONDS.toMillis(30);
    public long validationTimeoutMillis = TimeUnit.SECONDS.toMillis(5);
    public long idleTimeoutMillis = TimeUnit.MINUTES.toMillis(10);
    public long leakDetectionThresholdMillis = 0;
    public long maxLifetimeMillis = TimeUnit.MINUTES.toMillis(30);

    public int maxPoolSize = 10;
    public int minIdle = -1;

    public String catalog;
    public String connectionInitSql;
    public String connectionTestQuery;
    public String dataSourceClassName;
    public String dataSourceJndiName;
    public String driverClassName;
    public String jdbcUrl;
    public String password;
    public String transactionIsolationName;
    public String username;
    public boolean autoCommit = true;
    public boolean readOnly = false;
    public boolean initializationFailFast = true;
    public boolean isolateInternalQueries = false;
    public boolean registerMbeans = true;
    public boolean allowPoolSuspension = false;

    public long healthCheckConnectionTimeoutMillis = TimeUnit.SECONDS.toMillis(1);


    public HikariConfiguration getHikariConfiguration(){
        return this.hikariConfiguration;
    }

    @Valid
    @NotNull
    @JsonProperty
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();
*/
    @Valid
    @NotNull
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
    /*

    @JsonProperty("httpClient")
    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClient;
    }

    @JsonProperty("httpClient")
    public void setHttpClient(HttpClientConfiguration httpClient) {
        this.httpClient = httpClient;
    }

    public void buildDataSource(MetricRegistry metricRegistry, HealthCheckRegistry healthCheckRegistry, String name) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setConnectionTimeout(connectionTimeoutMillis);
        hikariConfig.setValidationTimeout(validationTimeoutMillis);
        hikariConfig.setIdleTimeout(idleTimeoutMillis);
        hikariConfig.setLeakDetectionThreshold(leakDetectionThresholdMillis);
        hikariConfig.setMaxLifetime(maxLifetimeMillis);
        hikariConfig.setMaximumPoolSize(maxPoolSize);

        if (minIdle > 0) {
            hikariConfig.setMinimumIdle(minIdle);
        }

        hikariConfig.setCatalog(catalog);
        hikariConfig.setConnectionInitSql(connectionInitSql);
        hikariConfig.setConnectionTestQuery(connectionTestQuery);
        hikariConfig.setDataSourceClassName(dataSourceClassName);
        hikariConfig.setDataSourceJNDI(dataSourceJndiName);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setPassword(password);
        hikariConfig.setPoolName(name);
        hikariConfig.setTransactionIsolation(transactionIsolationName);
        hikariConfig.setUsername(username);
        hikariConfig.setAutoCommit(autoCommit);
        hikariConfig.setReadOnly(readOnly);
//        hikariConfig.setInitializationFailFast(initializationFailFast);
        hikariConfig.setIsolateInternalQueries(isolateInternalQueries);
        hikariConfig.setRegisterMbeans(registerMbeans);
        hikariConfig.setAllowPoolSuspension(allowPoolSuspension);

        hikariConfig.setMetricRegistry(metricRegistry);
        hikariConfig.setHealthCheckRegistry(healthCheckRegistry);

        Properties healthCheckProperties = new Properties();
        healthCheckProperties.setProperty("connectivityCheckTimeoutMs", String.valueOf(healthCheckConnectionTimeoutMillis));
        hikariConfig.setHealthCheckProperties(healthCheckProperties);

        this.managedDataSource = new ManagedHikariPoolWrapper(hikariConfig);
    }

    public ManagedDataSource getManagedDataSource() {

        if (managedDataSource == null) {
            throw new IllegalStateException("DataSource has not been built yet - have you added the HikariBundle in your application initialize method?");
        }

        return managedDataSource;
    }
    */
}
