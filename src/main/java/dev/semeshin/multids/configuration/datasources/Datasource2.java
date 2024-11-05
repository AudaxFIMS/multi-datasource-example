package dev.semeshin.multids.configuration.datasources;

import javax.sql.DataSource;

import java.util.Objects;

import dev.semeshin.multids.entity.datasource2.Ds2Entity;
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
		entityManagerFactoryRef = "datasource2EntityManagerFactory",
		transactionManagerRef = "datasource2TransactionManager",
		basePackages = "dev.semeshin.multids.repository.datasource2"
)
public class Datasource2 {
	@Primary
	@Bean("datasource2Datasource")
	@ConfigurationProperties("spring.datasource2.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean("datasource2JpaProperties")
	@ConfigurationProperties("spring.datasource2.jpa")
	public JpaProperties datasource2JpaProperties() {
		return new JpaProperties();
	}
	
	@Primary
	@Bean("datasource2EntityManagerFactoryBuilder")
	EntityManagerFactoryBuilder datasource2EntityManagerFactoryBuilder(
			ObjectProvider<PersistenceUnitManager> persistenceUnitManager,
			@Qualifier("datasource2JpaProperties") JpaProperties jpaProperties
	) {
		return new EntityManagerFactoryBuilder(
				new HibernateJpaVendorAdapter(),
				jpaProperties.getProperties(),
				persistenceUnitManager.getIfAvailable()
		);
	}
	
	@Primary
	@Bean("datasource2EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean datasource2EntityManagerFactory(
			@Qualifier("datasource2EntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
			@Qualifier("datasource2Datasource") DataSource dataSource,
			@Qualifier("datasource2PersistenceManagedTypes") PersistenceManagedTypes persistenceManagedTypes
	) {
		return builder.dataSource(dataSource).packages(Ds2Entity.class).managedTypes(persistenceManagedTypes).build();
	}
	
	@Primary
	@Bean("datasource2PersistenceManagedTypes")
	public PersistenceManagedTypes datasource2PersistenceManagedTypes(ResourceLoader resourceLoader) {
		return new PersistenceManagedTypesScanner(resourceLoader)
				.scan("dev.semeshin.multids.entity.datasource2");
	}
	
	
	@Primary
	@Bean("datasource2TransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("datasource2EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
	) {
		return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
	}
}
