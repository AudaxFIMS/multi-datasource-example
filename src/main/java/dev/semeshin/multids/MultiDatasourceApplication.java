package dev.semeshin.multids;

import dev.semeshin.multids.repository.datasource1.Ds1EntityRepository;
import dev.semeshin.multids.repository.datasource2.Ds2EntityRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiDatasourceApplication {
	private final Ds1EntityRepository ds1EntityRepository;
	private final Ds2EntityRepository ds2EntityRepository;
	
	@Autowired
	public MultiDatasourceApplication(Ds1EntityRepository ds1EntityRepository, Ds2EntityRepository ds2EntityRepository) {
		this.ds1EntityRepository = ds1EntityRepository;
		this.ds2EntityRepository = ds2EntityRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MultiDatasourceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println("==================== Datasource 1 data ============================");
		
		ds1EntityRepository.findAll().stream().forEach(System.out::println);
		
		System.out.println("==================== Datasource 2 data ============================");
		
		ds2EntityRepository.findAll().stream().forEach(System.out::println);
		
		System.out.println("===================================================================");
	}
}
