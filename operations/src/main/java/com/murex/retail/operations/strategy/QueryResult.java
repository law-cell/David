package com.murex.retail.operations.strategy;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DoubleResult.class, name = "double"),
        @JsonSubTypes.Type(value = ComputerComponentResult.class, name = "component"),
        @JsonSubTypes.Type(value = MapResult.class, name = "map"),
        @JsonSubTypes.Type(value = StringResult.class, name = "string"),
})
public interface QueryResult {
    Object getResult();
}
