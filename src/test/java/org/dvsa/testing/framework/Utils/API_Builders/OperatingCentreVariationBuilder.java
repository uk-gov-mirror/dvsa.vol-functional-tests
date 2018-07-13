package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "noOfVehiclesRequired",
        "application",
        "version"
})
public class OperatingCentreVariationBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("noOfVehiclesRequired")
    private String noOfVehiclesRequired;
    @JsonProperty("application")
    private String application;
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

    public OperatingCentreVariationBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("noOfVehiclesRequired")
    public String getNoOfVehiclesRequired() {
        return noOfVehiclesRequired;
    }

    @JsonProperty("noOfVehiclesRequired")
    public void setNoOfVehiclesRequired(String noOfVehiclesRequired) {
        this.noOfVehiclesRequired = noOfVehiclesRequired;
    }

    public OperatingCentreVariationBuilder withNoOfVehiclesRequired(String noOfVehiclesRequired) {
        this.noOfVehiclesRequired = noOfVehiclesRequired;
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

    public OperatingCentreVariationBuilder withApplication(String application) {
        this.application = application;
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

    public OperatingCentreVariationBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + ",noOfVehiclesRequired:" + getNoOfVehiclesRequired() + ",application:" + getApplication() + ",version:" + getVersion();
    }
}