package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "case",
        "complainantForename",
        "complainantFamilyName",
        "complaintType",
        "status",
        "isCompliance",
        "complaintDate",
        "infringementDate",
        "description",
        "vrm",
        "driverForename",
        "driverFamilyName"
})
public class CaseComplaintBuilder {

    @JsonProperty("case")
    private int _case;
    @JsonProperty("complainantForename")
    private String complainantForename;
    @JsonProperty("complainantFamilyName")
    private String complainantFamilyName;
    @JsonProperty("complaintType")
    private String complaintType;
    @JsonProperty("status")
    private String status;
    @JsonProperty("isCompliance")
    private String isCompliance;
    @JsonProperty("complaintDate")
    private String complaintDate;
    @JsonProperty("infringementDate")
    private String infringementDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("vrm")
    private Object vrm;
    @JsonProperty("driverForename")
    private String driverForename;
    @JsonProperty("driverFamilyName")
    private String driverFamilyName;

    @JsonProperty("case")
    public int getCase() {
        return _case;
    }

    @JsonProperty("case")
    public void setCase(int _case) {
        this._case = _case;
    }

    public CaseComplaintBuilder withCase(int _case) {
        this._case = _case;
        return this;
    }

    @JsonProperty("complainantForename")
    public String getComplainantForename() {
        return complainantForename;
    }

    @JsonProperty("complainantForename")
    public void setComplainantForename(String complainantForename) {
        this.complainantForename = complainantForename;
    }

    public CaseComplaintBuilder withComplainantForename(String complainantForename) {
        this.complainantForename = complainantForename;
        return this;
    }

    @JsonProperty("complainantFamilyName")
    public String getComplainantFamilyName() {
        return complainantFamilyName;
    }

    @JsonProperty("complainantFamilyName")
    public void setComplainantFamilyName(String complainantFamilyName) {
        this.complainantFamilyName = complainantFamilyName;
    }

    public CaseComplaintBuilder withComplainantFamilyName(String complainantFamilyName) {
        this.complainantFamilyName = complainantFamilyName;
        return this;
    }

    @JsonProperty("complaintType")
    public String getComplaintType() {
        return complaintType;
    }

    @JsonProperty("complaintType")
    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public CaseComplaintBuilder withComplaintType(String complaintType) {
        this.complaintType = complaintType;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public CaseComplaintBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("isCompliance")
    public String getIsCompliance() {
        return isCompliance;
    }

    @JsonProperty("isCompliance")
    public void setIsCompliance(String isCompliance) {
        this.isCompliance = isCompliance;
    }

    public CaseComplaintBuilder withIsCompliance(String isCompliance) {
        this.isCompliance = isCompliance;
        return this;
    }

    @JsonProperty("complaintDate")
    public String getComplaintDate() {
        return complaintDate;
    }

    @JsonProperty("complaintDate")
    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public CaseComplaintBuilder withComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
        return this;
    }

    @JsonProperty("infringementDate")
    public String getInfringementDate() {
        return infringementDate;
    }

    @JsonProperty("infringementDate")
    public void setInfringementDate(String infringementDate) {
        this.infringementDate = infringementDate;
    }

    public CaseComplaintBuilder withInfringementDate(String infringementDate) {
        this.infringementDate = infringementDate;
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

    public CaseComplaintBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("vrm")
    public Object getVrm() {
        return vrm;
    }

    @JsonProperty("vrm")
    public void setVrm(Object vrm) {
        this.vrm = vrm;
    }

    public CaseComplaintBuilder withVrm(Object vrm) {
        this.vrm = vrm;
        return this;
    }

    @JsonProperty("driverForename")
    public String getDriverForename() {
        return driverForename;
    }

    @JsonProperty("driverForename")
    public void setDriverForename(String driverForename) {
        this.driverForename = driverForename;
    }

    public CaseComplaintBuilder withDriverForename(String driverForename) {
        this.driverForename = driverForename;
        return this;
    }

    @JsonProperty("driverFamilyName")
    public String getDriverFamilyName() {
        return driverFamilyName;
    }

    @JsonProperty("driverFamilyName")
    public void setDriverFamilyName(String driverFamilyName) {
        this.driverFamilyName = driverFamilyName;
    }

    public CaseComplaintBuilder withDriverFamilyName(String driverFamilyName) {
        this.driverFamilyName = driverFamilyName;
        return this;
    }

    @Override
    public String toString() {
        return  "_case:" + getCase() + ",complainantForename:" +  getComplainantForename() + ",complainantFamilyName:" + getComplainantFamilyName() + ",complaintType:" + getComplaintType() + ",status:" +  getStatus() + ",isCompliance:" + getIsCompliance() + ",complaintDate:" + getComplaintDate() + ",infringementDate:" + getInfringementDate() + ",description:" +  getDescription()
        + ",vrm:" +  getVrm() + ",driverForename:" +  getDriverForename() + ",driverFamilyName:" +  getDriverFamilyName();
    }

}