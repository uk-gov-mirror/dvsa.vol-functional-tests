package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "caseType",
        "categorys][]",
        "description",
        "ecmsNo",
        "outcome",
        "id",
        "application",
        "version"
})
public class CaseBuilder {

    @JsonProperty("caseType")
    private String caseType;
    @JsonProperty("categorys][]")
    private String categorys;
    @JsonProperty("description")
    private String description;
    @JsonProperty("ecmsNo")
    private String ecmsNo;
    @JsonProperty("outcome")
    private String outcome;
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

    @JsonProperty("categorys][]")
    public String getCategorys() {
        return categorys;
    }

    @JsonProperty("categorys][]")
    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public CaseBuilder withCategorys(String categorys) {
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
    public String getEcmsNo() {
        return ecmsNo;
    }

    @JsonProperty("ecmsNo")
    public void setEcmsNo(String ecmsNo) {
        this.ecmsNo = ecmsNo;
    }

    public CaseBuilder withEcmsNo(String ecmsNo) {
        this.ecmsNo = ecmsNo;
        return this;
    }

    @JsonProperty("outcome")
    public String getOutcome() {
        return outcome;
    }

    @JsonProperty("outcome")
    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public CaseBuilder withOutcome(String outcome) {
        this.outcome = outcome;
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
        + ",ecmsNo:" + getEcmsNo() + ",outcome:" + getOutcome() + ",id:" +  getId() + ",application:" +getApplication() + ",version:" + getVersion();
    }
}