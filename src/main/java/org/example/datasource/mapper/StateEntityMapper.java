package org.example.datasource.mapper;

import org.example.datasource.model.StateEntity;
import org.example.domain.model.State;

public class StateEntityMapper {
    public State toDomain(StateEntity stateEntity){
        return State.valueOf(stateEntity.name());
    }

    public StateEntity toEntity(State state){
        return StateEntity.valueOf(state.name());
    }
}
