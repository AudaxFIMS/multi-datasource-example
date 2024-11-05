package dev.semeshin.multids.repository.datasource2;

import dev.semeshin.multids.entity.datasource2.Ds2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ds2EntityRepository extends JpaRepository<Ds2Entity, Integer> {
}