package dev.semeshin.multids.configuration.liquibase;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseDatasource1 {
	@Value("${spring.datasource1.liquibase.change-log}")
	private String changeLogFile;
	@Value("${spring.datasource1.liquibase.enabled}")
	private boolean isEnabled;
	
	@Bean("datasource1LiquibaseDataSource")
	@ConfigurationProperties("spring.datasource1.liquibase")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean("datasource1Liquibase")
	public SpringLiquibase springLiquibase(
			@Qualifier("datasource1LiquibaseDataSource") DataSource dataSource
	) {
		SpringLiquibase liquibase = new SpringLiquibase();
		
		liquibase.setChangeLog(changeLogFile);
		liquibase.setShouldRun(isEnabled);
		liquibase.setDataSource(dataSource);
		
		return liquibase;
	}
}
