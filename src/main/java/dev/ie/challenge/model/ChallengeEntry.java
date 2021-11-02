package dev.ie.challenge.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;

import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoEnumValue;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Schema(description = "A developer's entry into a particular challenge")
@ProtoDoc("@Indexed")
public class ChallengeEntry {

    private final String id;
    private final String developerId;
    private final String challengeId;
    private CompletionStatus completionStatus;

    @ProtoFactory
    public ChallengeEntry(String id, String developerId, String challengeId, CompletionStatus completionStatus){
        this.id = Objects.requireNonNull(id);
        this.developerId = Objects.requireNonNull(developerId);
        this.challengeId = Objects.requireNonNull(challengeId);

        this.completionStatus = completionStatus;
    }

    @JsonCreator
    public ChallengeEntry(String id, String developerId, String challengeId){
        this.id = Objects.requireNonNull(id);
        this.developerId = Objects.requireNonNull(developerId);
        this.challengeId = Objects.requireNonNull(challengeId);

        this.completionStatus = CompletionStatus.INCOMPLETE;
    }

    @ProtoField(number = 1)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.NO)")
    public String getId() {
        return id;
    }

    @ProtoField(number = 2)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.NO)")
    public String getDeveloperId() {
        return developerId;
    }

    @ProtoField(number = 3)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.NO)")
    public String getChallengeId() {
        return challengeId;
    }

    @ProtoField(number = 4)
    @ProtoDoc("@Field(index=Index.YES, analyze = Analyze.NO, store = Store.NO)")
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
