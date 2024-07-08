package org.catrip.payments.repository;

import org.catrip.payments.entity.PagoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends CrudRepository<PagoEntity, Long> {

}
