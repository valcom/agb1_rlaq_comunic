package it.inps.entrate.rlaq.batch.log;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariDataSource;

public class LogsStoreConnectionFactory {
	private static HikariDataSource dataSource;

	private LogsStoreConnectionFactory() {
	}

	public static Connection getConnection() throws SQLException, IOException {
		if (dataSource == null) {
			Properties prop = new Properties();
			prop.load(LogsStoreConnectionFactory.class.getClassLoader().getResourceAsStream("config.properties"));

			dataSource = new HikariDataSource();

			String url = prop.getProperty("spring.datasource.jdbcUrl");
			String username = prop.getProperty("spring.datasource.username");
			String password = prop.getProperty("spring.datasource.password");
			String driverClassName = prop.getProperty("spring.datasource.driverClassName");
			String maxPoolSize = prop.getProperty("spring.datasource.maximumPoolSize");

			dataSource.setJdbcUrl(url);
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
		}
		return dataSource.getConnection();
	}
}
