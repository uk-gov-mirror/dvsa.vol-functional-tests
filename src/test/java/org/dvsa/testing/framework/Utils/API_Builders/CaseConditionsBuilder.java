package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "licence",
        "application",
        "case",
        "type",
        "conditionCategory",
        "fulfilled",
        "attachedTo",
        "notes"
})
public class CaseConditionsBuilder {

    @JsonProperty("licence")
    private String licence;
    @JsonProperty("application")
    private String application;
    @JsonProperty("case")
    private String _case;
    @JsonProperty("type")
    private String type;
    @JsonProperty("conditionCategory")
    private String conditionCategory;
    @JsonProperty("fulfilled")
    private String fulfilled;
    @JsonProperty("attachedTo")
    private String attachedTo;
    @JsonProperty("notes")
    private String notes;

    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }

    @JsonProperty("licence")
    public void setLicence(String licence) {
        this.licence = licence;
    }

    public CaseConditionsBuilder withLicence(String licence) {
        this.licence = licence;
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

    public CaseConditionsBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @JsonProperty("case")
    public String getCase() {
        return _case;
    }

    @JsonProperty("case")
    public void setCase(String _case) {
        this._case = _case;
    }

    public CaseConditionsBuilder withCase(String _case) {
        this._case = _case;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public CaseConditionsBuilder withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("conditionCategory")
    public String getConditionCategory() {
        return conditionCategory;
    }

    @JsonProperty("conditionCategory")
    public void setConditionCategory(String conditionCategory) {
        this.conditionCategory = conditionCategory;
    }

    public CaseConditionsBuilder withConditionCategory(String conditionCategory) {
        this.conditionCategory = conditionCategory;
        return this;
    }

    @JsonProperty("fulfilled")
    public String getFulfilled() {
        return fulfilled;
    }

    @JsonProperty("fulfilled")
    public void setFulfilled(String fulfilled) {
        this.fulfilled = fulfilled;
    }

    public CaseConditionsBuilder withFulfilled(String fulfilled) {
        this.fulfilled = fulfilled;
        return this;
    }

    @JsonProperty("attachedTo")
    public String getAttachedTo() {
        return attachedTo;
    }

    @JsonProperty("attachedTo")
    public void setAttachedTo(String attachedTo) {
        this.attachedTo = attachedTo;
    }

    public CaseConditionsBuilder withAttachedTo(String attachedTo) {
        this.attachedTo = attachedTo;
        return this;
    }

    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CaseConditionsBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public String toString() {
        return  "_licence:" + getLicence() + ",application:" + getApplication() + "_case:" +  getCase() + ",type:" + getType()
                + ",conditionCategory:" +  getConditionCategory() + "fulfilled:" + getFulfilled() + ",attachedTo" + getAttachedTo()
                + ",notes" +  getNotes();
    }

}