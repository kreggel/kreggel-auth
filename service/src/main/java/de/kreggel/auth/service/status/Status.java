package de.kreggel.auth.service.status;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonTypeName(value = "status")
public class Status {
    public enum State {
        OK,
        RECOVERING,
        MAINTENANCE,
        OVERLOAD,
        DOWN
    }

    public State state = State.OK;

    public Status() {
    }
}