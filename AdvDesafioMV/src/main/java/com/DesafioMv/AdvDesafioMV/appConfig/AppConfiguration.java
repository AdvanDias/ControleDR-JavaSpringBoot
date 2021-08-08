package com.DesafioMv.AdvDesafioMV.appConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@ConfigurationProperties("spring.datasource")
@Data
public class AppConfiguration {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devAppConfiguration(){
        System.out.println("DB connection for DEV - Oracle");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection to Oracle";
    }

    @Profile("teste")
    @Bean
    public String testeAppConfiguration(){
        System.out.println("DB connection for Teste - H2");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection to H2";
    }

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }
}
