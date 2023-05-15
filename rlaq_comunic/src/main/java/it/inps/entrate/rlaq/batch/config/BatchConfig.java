package it.inps.entrate.rlaq.batch.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(basePackages = "it.inps.entrate.rlaq.batch" )
public class BatchConfig {
	
	@Bean
	public DataSource dataSource(@Value("${jdbc.driverClassName}") String driverClassName, @Value("${jdbc.url}") String url, @Value("${jdbc.username}") String username,@Value("${jdbc.password}") String password,@Value("${connectionpool}")int connectionPool) {
		BasicDataSource dataSource = new BasicDataSource();
	        dataSource.setDriverClassName(driverClassName);
	        dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        dataSource.setPoolPreparedStatements(true);
	        dataSource.setMaxIdle(connectionPool);
	        dataSource.setMaxTotal(connectionPool);
	        dataSource.setValidationQuery("SELECT DB_NAME()");
	        
	        
	        return dataSource;
	}
	
	@Bean
	public DataSource inMemoryDataSource(@Value("${jdbc.h2.driverClassName}") String driverClassName, @Value("${jdbc.h2.url}") String url, @Value("${jdbc.h2.username}") String username,@Value("${jdbc.h2.password}") String password,@Value("${connectionpool}")int connectionPool) {
		BasicDataSource dataSource = new BasicDataSource();
	        dataSource.setDriverClassName(driverClassName);
	        dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        dataSource.setPoolPreparedStatements(true);
	        dataSource.setMaxIdle(connectionPool);
	        dataSource.setMaxTotal(connectionPool);
	        
	        return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	@Bean
	public JobRepository jobRepository(@Autowired DataSource inMemoryDataSource) throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(inMemoryDataSource);
		factory.setTransactionManager(new ResourcelessTransactionManager());
		return factory.getObject();
	}
	
	
	@Bean
	public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
		TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}
