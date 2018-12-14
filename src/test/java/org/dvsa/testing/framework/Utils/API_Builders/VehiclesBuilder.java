package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.platform.commons.util.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "application",
        "hasEnteredReg",
        "vrm",
        "platedWeight",
        "version"
})
public class VehiclesBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("application")
    private String application;
    @JsonProperty("hasEnteredReg")
    private String hasEnteredReg;
    @JsonProperty("vrm")
    private String vrm;
    @JsonProperty("platedWeight")
    private String platedWeight;
    @JsonProperty("version")
    private Integer version;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public VehiclesBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("application")
    public void setApplication(String application) {
        this.application = application;
    }

    public VehiclesBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @JsonProperty("hasEnteredReg")
    public String getHasEnteredReg() {
        return hasEnteredReg;
    }

    @JsonProperty("hasEnteredReg")
    public void setHasEnteredReg(String hasEnteredReg) {
        this.hasEnteredReg = hasEnteredReg;
    }

    public VehiclesBuilder withHasEnteredReg(String hasEnteredReg) {
        this.hasEnteredReg = hasEnteredReg;
        return this;
    }

    @JsonProperty("vrm")
    public String getVrm() {
        return vrm;
    }

    @JsonProperty("vrm")
    public void setVrm(String vrm) {
        this.vrm = vrm;
    }

    public VehiclesBuilder withVrm(String vrm) {
        this.vrm = vrm;
        return this;
    }

    @JsonProperty("platedWeight")
    public String getPlatedWeight() {
        return platedWeight;
    }

    @JsonProperty("platedWeight")
    public void setPlatedWeight(String platedWeight) {
        this.platedWeight = platedWeight;
    }

    public VehiclesBuilder withPlatedWeight(String platedWeight) {
        this.platedWeight = platedWeight;
        return this;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    public VehiclesBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(ToStringStyle.JSON_STYLE)
                .append("hasEnteredReg", getHasEnteredReg())
                .append("platedWeight", getPlatedWeight())
                .append("vrm", getVrm())
                .append("version", getVersion())
                .append("application:", getApplication())
                .append("id:", getId())
                .toString();
    }
}