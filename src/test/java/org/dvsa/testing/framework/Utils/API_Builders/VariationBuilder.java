package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "receivedDate",
        "feeRequired",
        "licenceType",
        "appliedVia",
        "variationType",
        "id"
})
public class VariationBuilder {

    @JsonProperty("receivedDate")
    private Object receivedDate;
    @JsonProperty("feeRequired")
    private Object feeRequired;
    @JsonProperty("licenceType")
    private Object licenceType;
    @JsonProperty("appliedVia")
    private Object appliedVia;
    @JsonProperty("variationType")
    private Object variationType;
    @JsonProperty("id")
    private String id;

    @JsonProperty("receivedDate")
    public Object getReceivedDate() {
        return receivedDate;
    }

    @JsonProperty("receivedDate")
    public void setReceivedDate(Object receivedDate) {
        this.receivedDate = receivedDate;
    }

    public VariationBuilder withReceivedDate(Object receivedDate) {
        this.receivedDate = receivedDate;
        return this;
    }

    @JsonProperty("feeRequired")
    public Object getFeeRequired() {
        return feeRequired;
    }

    @JsonProperty("feeRequired")
    public void setFeeRequired(Object feeRequired) {
        this.feeRequired = feeRequired;
    }

    public VariationBuilder withFeeRequired(Object feeRequired) {
        this.feeRequired = feeRequired;
        return this;
    }

    @JsonProperty("licenceType")
    public Object getLicenceType() {
        return licenceType;
    }

    @JsonProperty("licenceType")
    public void setLicenceType(Object licenceType) {
        this.licenceType = licenceType;
    }

    public VariationBuilder withLicenceType(Object licenceType) {
        this.licenceType = licenceType;
        return this;
    }

    @JsonProperty("appliedVia")
    public Object getAppliedVia() {
        return appliedVia;
    }

    @JsonProperty("appliedVia")
    public void setAppliedVia(Object appliedVia) {
        this.appliedVia = appliedVia;
    }

    public VariationBuilder withAppliedVia(Object appliedVia) {
        this.appliedVia = appliedVia;
        return this;
    }

    @JsonProperty("variationType")
    public Object getVariationType() {
        return variationType;
    }

    @JsonProperty("variationType")
    public void setVariationType(Object variationType) {
        this.variationType = variationType;
    }

    public VariationBuilder withVariationType(Object variationType) {
        this.variationType = variationType;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public VariationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "receivedDate:" +  getReceivedDate() + "feeRequired:" + getFeeRequired()
        + "licenceType:" + getLicenceType() + "appliedVia:" + getAppliedVia() + "variationType:" + getVariationType()
        + "id:" + getId();
    }
}