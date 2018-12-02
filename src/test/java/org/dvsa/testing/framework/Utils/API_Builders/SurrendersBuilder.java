package org.dvsa.testing.framework.Utils.API_Builders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.platform.commons.util.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "paramValue",
        "licence",
        "discStolen",
        "version"
})

public class SurrendersBuilder {
    @JsonProperty("id")
    private String id;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("discStolen")
    private String discStolen;
    @JsonProperty("paramValue")
    private String paramValue;
    @JsonProperty("version")
    private int version;

    public SurrendersBuilder() {

    }

    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }

    @JsonProperty("licence")
    public void setLicence(String licence) {
        this.licence = licence;
    }

    @JsonProperty("discStolen")
    public String getDiscStolen() {
        return discStolen;
    }

    @JsonProperty("discStolen")
    public void setDiscStolen(String discStolen) {
        this.discStolen = discStolen;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public SurrendersBuilder withLicence(String licence) {
        this.licence = licence;
        return this;
    }

    public SurrendersBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("paramValue")
    public String getParamValue() {
        return paramValue;
    }

    @JsonProperty("paramValue")
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public SurrendersBuilder withParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }

    @JsonProperty("version")
    public int getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(int version) {
        this.version = version;
    }

    public SurrendersBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(ToStringStyle.JSON_STYLE)
                .append("id",getId())
                .append("paramValue",getParamValue())
                .append("discStolen",getDiscStolen())
                .append("licence",getLicence())
                .append("version",getVersion())
                .toString();
    }
}