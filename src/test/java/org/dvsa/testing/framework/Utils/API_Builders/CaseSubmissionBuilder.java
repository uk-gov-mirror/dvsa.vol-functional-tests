package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "case",
        "submissionType"
})
public class CaseSubmissionBuilder {

    @JsonProperty("case")
    private String _case;
    @JsonProperty("submissionType")
    private String submissionType;

    @JsonProperty("case")
    public String getCase() {
        return _case;
    }

    @JsonProperty("case")
    public void setCase(String _case) {
        this._case = _case;
    }

    public CaseSubmissionBuilder withCase(String _case) {
        this._case = _case;
        return this;
    }

    @JsonProperty("submissionType")
    public String getSubmissionType() {
        return submissionType;
    }

    @JsonProperty("submissionType")
    public void setSubmissionType(String submissionType) {
        this.submissionType = submissionType;
    }

    public CaseSubmissionBuilder withSubmissionType(String submissionType) {
        this.submissionType = submissionType;
        return this;
    }

    @Override
    public String toString() {
        return "_case:" + getCase() + ",submissionType:" + getSubmissionType();
    }
}