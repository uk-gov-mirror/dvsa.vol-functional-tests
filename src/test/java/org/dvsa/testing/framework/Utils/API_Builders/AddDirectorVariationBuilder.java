package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.platform.commons.util.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "receivedDate",
        "feeRequired",
        "licenceType",
        "appliedVia",
        "variationType",
        "id"
})
public class AddDirectorVariationBuilder {

    @JsonProperty("receivedDate")
    private Object receivedDate;
    @JsonProperty("feeRequired")
    private Object feeRequired;
    @JsonProperty("licenceType")
    private Object licenceType;
    @JsonProperty("appliedVia")
    private String appliedVia;
    @JsonProperty("variationType")
    private String variationType;
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

    public AddDirectorVariationBuilder withReceivedDate(Object receivedDate) {
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

    public AddDirectorVariationBuilder withFeeRequired(Object feeRequired) {
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

    public AddDirectorVariationBuilder withLicenceType(Object licenceType) {
        this.licenceType = licenceType;
        return this;
    }

    @JsonProperty("appliedVia")
    public String getAppliedVia() {
        return appliedVia;
    }

    @JsonProperty("appliedVia")
    public void setAppliedVia(String appliedVia) {
        this.appliedVia = appliedVia;
    }

    public AddDirectorVariationBuilder withAppliedVia(String appliedVia) {
        this.appliedVia = appliedVia;
        return this;
    }

    @JsonProperty("variationType")
    public String getVariationType() {
        return variationType;
    }

    @JsonProperty("variationType")
    public void setVariationType(String variationType) {
        this.variationType = variationType;
    }

    public AddDirectorVariationBuilder withVariationType(String variationType) {
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

    public AddDirectorVariationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(ToStringStyle.JSON_STYLE)
                .append("receivedDate", getReceivedDate())
                .append("feeRequired", getFeeRequired())
                .append("licenceType", getLicenceType())
                .append("appliedVia", getAppliedVia())
                .append("variationType", getVariationType())
                .append("id:", getId())
                .toString();
    }
}