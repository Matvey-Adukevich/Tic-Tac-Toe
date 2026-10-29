package org.example.datasource.repository;

import org.example.datasource.model.GameEntity;
import org.example.datasource.model.StateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface JpaGameRepository extends CrudRepository<GameEntity, UUID>{
    List<GameEntity> findByStateEntity(StateEntity stateEntity);
}
