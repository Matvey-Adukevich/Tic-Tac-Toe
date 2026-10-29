package org.example.web.mapper;

import org.example.domain.model.State;
import org.example.web.model.StateDTO;

public class StateWebMapper {
    public State toDomain(StateDTO stateDTO){
        return State.valueOf(stateDTO.name());
    }

    public StateDTO toWeb(State state){
        return StateDTO.valueOf(state.name());
    }
}
