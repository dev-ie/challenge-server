package dev.ie.challenge.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Schema(description = "An entrant into the developer challenge")
public class Developer {

    private final String id;
    private final String displayName;

    @ProtoFactory
    @JsonCreator
    public Developer(String id, String displayName){
        this.id = Objects.requireNonNull(id);
        this.displayName = displayName;
    }

    @ProtoField(number = 1)
    public String getId() {
        return id;
    }

    @ProtoField(number = 2)
    public String getDisplayName() {
        return displayName;
    }
    
}
