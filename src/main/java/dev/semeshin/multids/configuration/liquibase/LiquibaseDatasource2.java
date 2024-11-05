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
public class LiquibaseDatasource2 {
	@Value("${spring.datasource2.liquibase.change-log}")
	private String changeLogFile;
	@Value("${spring.datasource2.liquibase.enabled}")
	private boolean isEnabled;
	
	@Bean("datasource2LiquibaseDataSource")
	@ConfigurationProperties("spring.datasource2.liquibase")
	DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean("datasource2Liquibase")
	public SpringLiquibase springLiquibase(
			@Qualifier("datasource2LiquibaseDataSource") DataSource dataSource
	) {
		SpringLiquibase liquibase = new SpringLiquibase();
		
		liquibase.setChangeLog(changeLogFile);
		liquibase.setShouldRun(isEnabled);
		liquibase.setDataSource(dataSource);
		
		return liquibase;
	}
}
