package dev.ie.challenge.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.infinispan.protostream.annotations.ProtoEnumValue;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Schema(description = "A developer's entry into a particular challenge")
public class ChallengeEntry {

    private final String developerId;
    private final String challengeId;
    private CompletionStatus completionStatus;

    @ProtoFactory
    @JsonCreator
    public ChallengeEntry(String developerId, String chellengeId){
        this.developerId = Objects.requireNonNull(developerId);
        this.challengeId = Objects.requireNonNull(chellengeId);

        this.completionStatus = CompletionStatus.INCOMPLETE;
    }

    @ProtoField(number = 1)
    public String getDeveloperId() {
        return developerId;
    }

    @ProtoField(number = 2)
    public String getChallengeId() {
        return challengeId;
    }

    @ProtoField(number = 3)
    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    


    enum CompletionStatus{
        @ProtoEnumValue(number = 0, name = "i")
        INCOMPLETE,
        @ProtoEnumValue(number = 1, name = "c")
        COMPLETE
    }



    
}
