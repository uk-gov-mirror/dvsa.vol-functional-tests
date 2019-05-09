package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.platform.commons.util.ToStringBuilder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "friendlyName",
        "configName",
        "status",
        "back_surrender"
})

public class FeatureToggleBuilder {
    @JsonProperty("id")
    private String id;
    @JsonProperty("friendlyName")
    private String friendlyName;
    @JsonProperty("configName")
    private String configName;
    @JsonProperty("status")
    private String status;


    public FeatureToggleBuilder() {
    }


    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("friendlyName")
    public String getFriendlyName() {
        return friendlyName;
    }

    @JsonProperty("friendlyName")
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    @JsonProperty("configName")
    public String getConfigName() {
        return configName;
    }

    @JsonProperty("configName")
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public FeatureToggleBuilder withId(String id){
        this.id = id;
        return this;
    }

    public FeatureToggleBuilder withFriendlyName(String friendlyName){
        this.friendlyName = friendlyName;
        return this;
    }

    public FeatureToggleBuilder withConfigName(String configName){
        this.configName = configName;
        return this;
    }

    public FeatureToggleBuilder withStatus(String status){
        this.status = status;
        return this;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(ToStringStyle.JSON_STYLE)
                .append("id", getId())
                .append("friendlyName", getFriendlyName())
                .append("configName", getConfigName())
                .append("status", getStatus())
                .toString();
    }
}




