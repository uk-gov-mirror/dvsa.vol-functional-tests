package org.dvsa.testing.framework.Utils.API_Builders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.junit.platform.commons.util.ToStringBuilder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "niFlag",
        "licenceType",
        "startNumber",
        "endNumber",
        "discSequence",
        "isSuccessfull",
        "queueId"
})

public class ConfirmPrintBuilder {

    @JsonProperty("niFlag")
    private String niFlag;
    @JsonProperty("licenceType")
    private String licenceType;
    @JsonProperty("startNumber")
    private String startNumber;
    @JsonProperty("discSequence")
    private String discSequence;
    @JsonProperty("endNumber")
    private String endNumber;
    @JsonProperty("isSuccessfull")
    private boolean isSuccessfull;
    @JsonProperty("queueId")
    private String queueId;

    @JsonProperty("niFlag")
    public String getNiFlag() {
        return niFlag;
    }
    @JsonProperty("niFlag")
    public void setNiFlag(String niFlag) {
        this.niFlag = niFlag;
    }
    public ConfirmPrintBuilder withNiFlag(String niFlag) {
        this.niFlag = niFlag;
        return this;
    }
    @JsonProperty("endNumber")
    public String getEndNumber() {
        return endNumber;
    }

    @JsonProperty("endNumber")
    public void setEndNumber(String endNumber) {
        this.endNumber = endNumber;
    }

    public ConfirmPrintBuilder withEndNumber(String endNumber) {
        this.endNumber = endNumber;
        return this;
    }
    @JsonProperty("isSuccessfull")
    public boolean getIsSuccessfull() {
        return isSuccessfull;
    }

    @JsonProperty("isSuccessfull")
    public void setIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
    }

    public ConfirmPrintBuilder withIsSuccessfull(boolean isSuccessfull) {
        this.isSuccessfull = isSuccessfull;
        return this;
    }
    @JsonProperty("queueId")
    public String getQueueId() {
        return queueId;
    }

    @JsonProperty("queueId")
    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public ConfirmPrintBuilder withQueueId(String queueId) {
        this.queueId = queueId;
        return this;
    }

    @JsonProperty("licenceType")
    public String getLicenceType() {
        return licenceType;
    }
    @JsonProperty("licenceType")
    public void setLicenceType(String licenceType) {
        this.licenceType = licenceType;
    }

    public ConfirmPrintBuilder withLicenceType(String licenceType) {
        this.licenceType = licenceType;
        return this;
    }
    @JsonProperty("startNumber")
    public String getStartNumber() {
        return startNumber;
    }
    @JsonProperty("startNumber")
    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }
    public ConfirmPrintBuilder withStartNumber(String startNumber) {
        this.startNumber = startNumber;
        return this;
    }
    @JsonProperty("discSequence")
    public String getDiscSequence() {
        return discSequence;
    }
    @JsonProperty("discSequence")
    public void setDiscSequence(String discSequence) {
        this.discSequence = discSequence;
    }
    public ConfirmPrintBuilder withDiscSequence(String discSequence) {
        this.discSequence = discSequence;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("niFlag", niFlag)
                .append("licenceType", licenceType)
                .append("startNumber", startNumber)
                .append("discSequence", discSequence)
                .append("endNumber", endNumber)
                .append("isSuccessfull", isSuccessfull)
                .append("queueId", discSequence).toString();
    }
}