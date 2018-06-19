package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "case",
        "defendantType",
        "personFirstname",
        "personLastname",
        "birthDate",
        "convictionCategory",
        "categoryText",
        "offenceDate",
        "convictionDate",
        "msi",
        "court",
        "penalty",
        "costs",
        "notes",
        "takenIntoConsideration",
        "isDeclared",
        "isDealtWith",
        "version"
})
public class CaseConvictionBuilder {

    @JsonProperty("case")
    private int _case;
    @JsonProperty("defendantType")
    private String defendantType;
    @JsonProperty("personFirstname")
    private String personFirstname;
    @JsonProperty("personLastname")
    private String personLastname;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("convictionCategory")
    private String convictionCategory;
    @JsonProperty("categoryText")
    private String categoryText;
    @JsonProperty("offenceDate")
    private String offenceDate;
    @JsonProperty("convictionDate")
    private String convictionDate;
    @JsonProperty("msi")
    private String msi;
    @JsonProperty("court")
    private String court;
    @JsonProperty("penalty")
    private String penalty;
    @JsonProperty("costs")
    private String costs;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("takenIntoConsideration")
    private String takenIntoConsideration;
    @JsonProperty("isDeclared")
    private String isDeclared;
    @JsonProperty("isDealtWith")
    private String isDealtWith;
    @JsonProperty("version")
    private Integer version;

    @JsonProperty("case")
    public int getCase() {
        return _case;
    }

    @JsonProperty("case")
    public void setCase(int _case) {
        this._case = _case;
    }

    public CaseConvictionBuilder withCase(int _case) {
        this._case = _case;
        return this;
    }

    @JsonProperty("defendantType")
    public String getDefendantType() {
        return defendantType;
    }

    @JsonProperty("defendantType")
    public void setDefendantType(String defendantType) {
        this.defendantType = defendantType;
    }

    public CaseConvictionBuilder withDefendantType(String defendantType) {
        this.defendantType = defendantType;
        return this;
    }

    @JsonProperty("personFirstname")
    public String getPersonFirstname() {
        return personFirstname;
    }

    @JsonProperty("personFirstname")
    public void setPersonFirstname(String personFirstname) {
        this.personFirstname = personFirstname;
    }

    public CaseConvictionBuilder withPersonFirstname(String personFirstname) {
        this.personFirstname = personFirstname;
        return this;
    }

    @JsonProperty("personLastname")
    public String getPersonLastname() {
        return personLastname;
    }

    @JsonProperty("personLastname")
    public void setPersonLastname(String personLastname) {
        this.personLastname = personLastname;
    }

    public CaseConvictionBuilder withPersonLastname(String personLastname) {
        this.personLastname = personLastname;
        return this;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public CaseConvictionBuilder withBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @JsonProperty("convictionCategory")
    public String getConvictionCategory() {
        return convictionCategory;
    }

    @JsonProperty("convictionCategory")
    public void setConvictionCategory(String convictionCategory) {
        this.convictionCategory = convictionCategory;
    }

    public CaseConvictionBuilder withConvictionCategory(String convictionCategory) {
        this.convictionCategory = convictionCategory;
        return this;
    }

    @JsonProperty("categoryText")
    public String getCategoryText() {
        return categoryText;
    }

    @JsonProperty("categoryText")
    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public CaseConvictionBuilder withCategoryText(String categoryText) {
        this.categoryText = categoryText;
        return this;
    }

    @JsonProperty("offenceDate")
    public String getOffenceDate() {
        return offenceDate;
    }

    @JsonProperty("offenceDate")
    public void setOffenceDate(String offenceDate) {
        this.offenceDate = offenceDate;
    }

    public CaseConvictionBuilder withOffenceDate(String offenceDate) {
        this.offenceDate = offenceDate;
        return this;
    }

    @JsonProperty("convictionDate")
    public String getConvictionDate() {
        return convictionDate;
    }

    @JsonProperty("convictionDate")
    public void setConvictionDate(String convictionDate) {
        this.convictionDate = convictionDate;
    }

    public CaseConvictionBuilder withConvictionDate(String convictionDate) {
        this.convictionDate = convictionDate;
        return this;
    }

    @JsonProperty("msi")
    public String getMsi() {
        return msi;
    }

    @JsonProperty("msi")
    public void setMsi(String msi) {
        this.msi = msi;
    }

    public CaseConvictionBuilder withMsi(String msi) {
        this.msi = msi;
        return this;
    }

    @JsonProperty("court")
    public String getCourt() {
        return court;
    }

    @JsonProperty("court")
    public void setCourt(String court) {
        this.court = court;
    }

    public CaseConvictionBuilder withCourt(String court) {
        this.court = court;
        return this;
    }

    @JsonProperty("penalty")
    public String getPenalty() {
        return penalty;
    }

    @JsonProperty("penalty")
    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public CaseConvictionBuilder withPenalty(String penalty) {
        this.penalty = penalty;
        return this;
    }

    @JsonProperty("costs")
    public String getCosts() {
        return costs;
    }

    @JsonProperty("costs")
    public void setCosts(String costs) {
        this.costs = costs;
    }

    public CaseConvictionBuilder withCosts(String costs) {
        this.costs = costs;
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

    public CaseConvictionBuilder withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @JsonProperty("takenIntoConsideration")
    public String getTakenIntoConsideration() {
        return takenIntoConsideration;
    }

    @JsonProperty("takenIntoConsideration")
    public void setTakenIntoConsideration(String takenIntoConsideration) {
        this.takenIntoConsideration = takenIntoConsideration;
    }

    public CaseConvictionBuilder withTakenIntoConsideration(String takenIntoConsideration) {
        this.takenIntoConsideration = takenIntoConsideration;
        return this;
    }

    @JsonProperty("isDeclared")
    public String getIsDeclared() {
        return isDeclared;
    }

    @JsonProperty("isDeclared")
    public void setIsDeclared(String isDeclared) {
        this.isDeclared = isDeclared;
    }

    public CaseConvictionBuilder withIsDeclared(String isDeclared) {
        this.isDeclared = isDeclared;
        return this;
    }

    @JsonProperty("isDealtWith")
    public String getIsDealtWith() {
        return isDealtWith;
    }

    @JsonProperty("isDealtWith")
    public void setIsDealtWith(String isDealtWith) {
        this.isDealtWith = isDealtWith;
    }

    public CaseConvictionBuilder withIsDealtWith(String isDealtWith) {
        this.isDealtWith = isDealtWith;
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

    public CaseConvictionBuilder withVersion(Integer version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "_case:" +  getCase()
        + ", defendantType" + getDefendantType() + ",personFirstname:" + getPersonFirstname() + ",personLastname:" + getPersonLastname()
        + ",birthDate:" + getBirthDate() + ",convictionCategory:" + getConvictionCategory() + ",categoryText:" + getCategoryText()
        + ",offenceDate:" + getOffenceDate() + ",convictionDate:" + getConvictionDate() + ",msi:" + getMsi() + ",court:" + getCourt()
        + ",penalty:" + getPenalty() + ",costs:" + getCosts() + ",notes:" + getNotes() + ",takenIntoConsideration:" + getTakenIntoConsideration()
        + ",isDeclared:" + getIsDeclared() + ",isDealtWith:" + getIsDealtWith() + ",version:" + getVersion();
    }
}