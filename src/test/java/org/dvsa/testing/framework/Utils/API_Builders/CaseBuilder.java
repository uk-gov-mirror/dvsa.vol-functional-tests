package org.dvsa.testing.framework.Utils.API_Builders;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "caseType",
        "categorys",
        "description",
        "ecmsNo",
        "outcomes",
        "id",
        "application",
        "version"
})
public class CaseBuilder {

    @JsonProperty("caseType")
    private String caseType;
    @JsonProperty("categorys")
    private List<String> categorys = null;
    @JsonProperty("description")
    private String description;
    @JsonProperty("ecmsNo")
    private Object ecmsNo;
    @JsonProperty("outcomes")
    private List<String> outcomes = null;
    @JsonProperty("id")
    private String id;
    @JsonProperty("application")
    private String application;
    @JsonProperty("version")
    private Integer version;

    @JsonProperty("caseType")
    public String getCaseType() {
        return caseType;
    }

    @JsonProperty("caseType")
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public CaseBuilder withCaseType(String caseType) {
        this.caseType = caseType;
        return this;
    }

    @JsonProperty("categorys")
    public List<String> getCategorys() {
        return categorys;
    }

    @JsonProperty("categorys")
    public void setCategorys(List<String> categorys) {
        this.categorys = categorys;
    }

    public CaseBuilder withCategorys(List<String> categorys) {
        this.categorys = categorys;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public CaseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("ecmsNo")
    public Object getEcmsNo() {
        return ecmsNo;
    }

    @JsonProperty("ecmsNo")
    public void setEcmsNo(Object ecmsNo) {
        this.ecmsNo = ecmsNo;
    }

    public CaseBuilder withEcmsNo(Object ecmsNo) {
        this.ecmsNo = ecmsNo;
        return this;
    }

    @JsonProperty("outcomes")
    public List<String> getOutcomes() {
        return outcomes;
    }

    @JsonProperty("outcomes")
    public void setOutcomes(List<String> outcomes) {
        this.outcomes = outcomes;
    }

    public CaseBuilder withOutcomes(List<String> outcomes) {
        this.outcomes = outcomes;
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

    public CaseBuilder withId(String id) {
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

    public CaseBuilder withApplication(String application) {
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

    public CaseBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "caseType:" + getCaseType() + ",categorys:" + getCategorys() + ",description:" + getDescription()
        + ",ecmsNo:" + getEcmsNo() + ",outcomes:" + getOutcomes() + ",id:" +  getId() + ",application:" +getApplication() + ",version:" + getVersion();
    }
}