package com.octobyte.rag.mshandledocuments.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

        @Bean
        @ConfigurationProperties("spring.datasource.relationaldatabase")
        public DataSourceProperties relationalDataBaseDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Bean
        public DataSource relationalDataBaseDataSource() {
            return relationalDataBaseDataSourceProperties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean("JdbcTemplateDBRelational")
        public JdbcTemplate todosJdbcTemplate(@Qualifier("relationalDataBaseDataSource") DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

    }
