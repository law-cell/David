package com.murex.retail.operations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NestedMapOutput.class, name = "nested"),
        @JsonSubTypes.Type(value = ComponentMapOutput.class, name = "component"),
        @JsonSubTypes.Type(value = LongMapOutput.class, name = "long"),
})
public interface MapOutput {
    Map getResult();
}
