package dev.semeshin.multids.configuration.datasources;

import javax.sql.DataSource;

import java.util.Objects;

import dev.semeshin.multids.entity.datasource1.Ds1Entity;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypesScanner;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		entityManagerFactoryRef = "datasource1EntityManagerFactory",
		transactionManagerRef = "datasource1TransactionManager",
		basePackages = "dev.semeshin.multids.repository.datasource1"
)
public class Datasource1 {
	@Primary
	@Bean("datasource1Datasource")
	@ConfigurationProperties("spring.datasource1.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean("datasource1JpaProperties")
	@ConfigurationProperties("spring.datasource1.jpa")
	public JpaProperties datasource1JpaProperties() {
		return new JpaProperties();
	}

	@Primary
	@Bean("datasource1EntityManagerFactoryBuilder")
	EntityManagerFactoryBuilder datasource1EntityManagerFactoryBuilder(
			ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
			@Qualifier("datasource1JpaProperties") JpaProperties jpaProperties
	) {
		return new EntityManagerFactoryBuilder(
				new HibernateJpaVendorAdapter(),
				jpaProperties.getProperties(),
				persistenceUnitManager.getIfAvailable()
		);
	}
	
	@Primary
	@Bean("datasource1EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean datasource1EntityManagerFactory(
			@Qualifier("datasource1EntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
			@Qualifier("datasource1Datasource") DataSource dataSource,
			@Qualifier("datasource1PersistenceManagedTypes") PersistenceManagedTypes persistenceManagedTypes
	) {
		return builder.dataSource(dataSource).packages(Ds1Entity.class).managedTypes(persistenceManagedTypes).build();
	}
	
	@Primary
	@Bean("datasource1PersistenceManagedTypes")
	public PersistenceManagedTypes datasource1PersistenceManagedTypes(ResourceLoader resourceLoader) {
		return new PersistenceManagedTypesScanner(resourceLoader)
				.scan("dev.semeshin.multids.entity.datasource1");
	}
	
	
	@Primary
	@Bean("datasource1TransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("datasource1EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
	) {
		return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
	}
}
