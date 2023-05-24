package it.inps.entrate.rlaq.batch.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import it.inps.entrate.rlaq.batch.stats.StatisticheFactory;

@Configuration
public class BatchConfig {

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "job-repo.datasource")
	@BatchDataSource
	public DataSource jobRepoDataSource() {
		return DataSourceBuilder.create().type(BasicDataSource.class).build();
	}

	@Bean
	public StatisticheFactory statisticheFactory() {
		return new StatisticheFactory();
	}

}
