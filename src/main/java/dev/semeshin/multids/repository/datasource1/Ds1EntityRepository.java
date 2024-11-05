package dev.semeshin.multids.repository.datasource1;

import java.math.BigDecimal;

import dev.semeshin.multids.entity.datasource1.Ds1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ds1EntityRepository extends JpaRepository<Ds1Entity, BigDecimal> {
}