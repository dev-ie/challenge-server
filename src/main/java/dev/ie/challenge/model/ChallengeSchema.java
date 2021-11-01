package dev.ie.challenge.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(
    includeClasses = {
        Developer.class,
        Challenge.class
    })
public interface ChallengeSchema extends GeneratedSchema{
    
}
