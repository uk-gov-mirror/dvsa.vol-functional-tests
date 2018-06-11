package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "filename",
        "content",
        "irfoOrganisation",
        "submission",
        "trafficArea",
        "operatingCentre",
        "opposition",
        "continuationDetail",
        "category",
        "subCategory",
        "description",
        "isExternal",
        "isScan",
        "isEbsrPack",
        "issuedDate",
        "user",
        "shouldUploadOnly",
        "additionalCopy",
        "application",
        "busReg",
        "case",
        "transportManager",
        "licence"
})
public class UploadDocumentBuilder {

    @JsonProperty("filename")
    private String filename;
    @JsonProperty("content")
    private String content;
    @JsonProperty("irfoOrganisation")
    private String irfoOrganisation;
    @JsonProperty("submission")
    private String submission;
    @JsonProperty("trafficArea")
    private String trafficArea;
    @JsonProperty("operatingCentre")
    private String operatingCentre;
    @JsonProperty("opposition")
    private String opposition;
    @JsonProperty("continuationDetail")
    private String continuationDetail;
    @JsonProperty("category")
    private String category;
    @JsonProperty("subCategory")
    private String subCategory;
    @JsonProperty("description")
    private String description;
    @JsonProperty("isExternal")
    private String isExternal;
    @JsonProperty("isScan")
    private String isScan;
    @JsonProperty("isEbsrPack")
    private String isEbsrPack;
    @JsonProperty("issuedDate")
    private String issuedDate;
    @JsonProperty("user")
    private String user;
    @JsonProperty("shouldUploadOnly")
    private String shouldUploadOnly;
    @JsonProperty("additionalCopy")
    private String additionalCopy;
    @JsonProperty("application")
    private String application;
    @JsonProperty("busReg")
    private String busReg;
    @JsonProperty("case")
    private String _case;
    @JsonProperty("transportManager")
    private String transportManager;
    @JsonProperty("licence")
    private String licence;

    @JsonProperty("filename")
    public String getFilename() {
        return filename;
    }

    @JsonProperty("filename")
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public UploadDocumentBuilder withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    public UploadDocumentBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    @JsonProperty("irfoOrganisation")
    public String getIrfoOrganisation() {
        return irfoOrganisation;
    }

    @JsonProperty("irfoOrganisation")
    public void setIrfoOrganisation(String irfoOrganisation) {
        this.irfoOrganisation = irfoOrganisation;
    }

    public UploadDocumentBuilder withIrfoOrganisation(String irfoOrganisation) {
        this.irfoOrganisation = irfoOrganisation;
        return this;
    }

    @JsonProperty("submission")
    public String getSubmission() {
        return submission;
    }

    @JsonProperty("submission")
    public void setSubmission(String submission) {
        this.submission = submission;
    }

    public UploadDocumentBuilder withSubmission(String submission) {
        this.submission = submission;
        return this;
    }

    @JsonProperty("trafficArea")
    public String getTrafficArea() {
        return trafficArea;
    }

    @JsonProperty("trafficArea")
    public void setTrafficArea(String trafficArea) {
        this.trafficArea = trafficArea;
    }

    public UploadDocumentBuilder withTrafficArea(String trafficArea) {
        this.trafficArea = trafficArea;
        return this;
    }

    @JsonProperty("operatingCentre")
    public String getOperatingCentre() {
        return operatingCentre;
    }

    @JsonProperty("operatingCentre")
    public void setOperatingCentre(String operatingCentre) {
        this.operatingCentre = operatingCentre;
    }

    public UploadDocumentBuilder withOperatingCentre(String operatingCentre) {
        this.operatingCentre = operatingCentre;
        return this;
    }

    @JsonProperty("opposition")
    public String getOpposition() {
        return opposition;
    }

    @JsonProperty("opposition")
    public void setOpposition(String opposition) {
        this.opposition = opposition;
    }

    public UploadDocumentBuilder withOpposition(String opposition) {
        this.opposition = opposition;
        return this;
    }

    @JsonProperty("continuationDetail")
    public String getContinuationDetail() {
        return continuationDetail;
    }

    @JsonProperty("continuationDetail")
    public void setContinuationDetail(String continuationDetail) {
        this.continuationDetail = continuationDetail;
    }

    public UploadDocumentBuilder withContinuationDetail(String continuationDetail) {
        this.continuationDetail = continuationDetail;
        return this;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public UploadDocumentBuilder withCategory(String category) {
        this.category = category;
        return this;
    }

    @JsonProperty("subCategory")
    public String getSubCategory() {
        return subCategory;
    }

    @JsonProperty("subCategory")
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public UploadDocumentBuilder withSubCategory(String subCategory) {
        this.subCategory = subCategory;
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

    public UploadDocumentBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("isExternal")
    public String getIsExternal() {
        return isExternal;
    }

    @JsonProperty("isExternal")
    public void setIsExternal(String isExternal) {
        this.isExternal = isExternal;
    }

    public UploadDocumentBuilder withIsExternal(String isExternal) {
        this.isExternal = isExternal;
        return this;
    }

    @JsonProperty("isScan")
    public String getIsScan() {
        return isScan;
    }

    @JsonProperty("isScan")
    public void setIsScan(String isScan) {
        this.isScan = isScan;
    }

    public UploadDocumentBuilder withIsScan(String isScan) {
        this.isScan = isScan;
        return this;
    }

    @JsonProperty("isEbsrPack")
    public String getIsEbsrPack() {
        return isEbsrPack;
    }

    @JsonProperty("isEbsrPack")
    public void setIsEbsrPack(String isEbsrPack) {
        this.isEbsrPack = isEbsrPack;
    }

    public UploadDocumentBuilder withIsEbsrPack(String isEbsrPack) {
        this.isEbsrPack = isEbsrPack;
        return this;
    }

    @JsonProperty("issuedDate")
    public String getIssuedDate() {
        return issuedDate;
    }

    @JsonProperty("issuedDate")
    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    public UploadDocumentBuilder withIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
        return this;
    }

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    public UploadDocumentBuilder withUser(String user) {
        this.user = user;
        return this;
    }

    @JsonProperty("shouldUploadOnly")
    public String getShouldUploadOnly() {
        return shouldUploadOnly;
    }

    @JsonProperty("shouldUploadOnly")
    public void setShouldUploadOnly(String shouldUploadOnly) {
        this.shouldUploadOnly = shouldUploadOnly;
    }

    public UploadDocumentBuilder withShouldUploadOnly(String shouldUploadOnly) {
        this.shouldUploadOnly = shouldUploadOnly;
        return this;
    }

    @JsonProperty("additionalCopy")
    public String getAdditionalCopy() {
        return additionalCopy;
    }

    @JsonProperty("additionalCopy")
    public void setAdditionalCopy(String additionalCopy) {
        this.additionalCopy = additionalCopy;
    }

    public UploadDocumentBuilder withAdditionalCopy(String additionalCopy) {
        this.additionalCopy = additionalCopy;
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

    public UploadDocumentBuilder withApplication(String application) {
        this.application = application;
        return this;
    }

    @JsonProperty("busReg")
    public String getBusReg() {
        return busReg;
    }

    @JsonProperty("busReg")
    public void setBusReg(String busReg) {
        this.busReg = busReg;
    }

    public UploadDocumentBuilder withBusReg(String busReg) {
        this.busReg = busReg;
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

    public UploadDocumentBuilder withCase(String _case) {
        this._case = _case;
        return this;
    }

    @JsonProperty("transportManager")
    public String getTransportManager() {
        return transportManager;
    }

    @JsonProperty("transportManager")
    public void setTransportManager(String transportManager) {
        this.transportManager = transportManager;
    }

    public UploadDocumentBuilder withTransportManager(String transportManager) {
        this.transportManager = transportManager;
        return this;
    }

    @JsonProperty("licence")
    public String getLicence() {
        return licence;
    }

    @JsonProperty("licence")
    public void setLicence(String licence) {
        this.licence = licence;
    }

    public UploadDocumentBuilder withLicence(String licence) {
        this.licence = licence;
        return this;
    }

    @Override
    public String toString() {
        return "filename:" + getFilename() + ",content:" + getContent() + ",irfoOrganisation:" + getIrfoOrganisation()
        + ",submission:" + getSubmission() + ",trafficArea:" + getTrafficArea() + ",operatingCentre:" + getOperatingCentre()
        + ",opposition:" + getOpposition() + ",continuationDetail:" + getContinuationDetail() + ",category:" + getCategory()
        + ",subCategory:" + getSubCategory() + ",description:" + getDescription() + ",isExternal:" + getIsExternal() + ",isScan:" + getIsScan()
        + ",isEbsrPack:" + getIsEbsrPack() + ", issuedDate:" + getIssuedDate() + ",user:" + getUser() + ",shouldUploadOnly:" + getShouldUploadOnly()
        + ",additionalCopy:" + getAdditionalCopy() + ",application:" + getApplication() + ",busReg:" + getBusReg() + "_case:" + getCase()
        + ",transportManager:" + getTransportManager() + ",licence:" + getLicence();
    }
}