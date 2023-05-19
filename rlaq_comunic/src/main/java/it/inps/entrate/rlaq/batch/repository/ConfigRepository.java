package it.inps.entrate.rlaq.batch.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.inps.entrate.rlaq.batch.entity.Config;

@Repository
public interface ConfigRepository extends CrudRepository<Config, Long>{
	
	
	@Query("SELECT valore FROM RLAQ_CONFIG WHERE chiave = :chiave AND CURRENT_TIMESTAMP BETWEEN data_inizio_validita AND data_fine_validita")
	public String findValoreByChiave(@Param("chiave") String chiave);

}
