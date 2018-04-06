package org.dvsa.testing.framework.stepdefs.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "version",
        "requested",
        "reason",
        "startDate",
        "endDate",
        "authVehicles",
        "authTrailers",
        "status",
        "action"
})
public class AdminInterimBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private int version;
    @JsonProperty("requested")
    private String requested;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("authVehicles")
    private int authVehicles;
    @JsonProperty("authTrailers")
    private int authTrailers;
    @JsonProperty("status")
    private String status;
    @JsonProperty("action")
    private String action;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public AdminInterimBuilder withId(String id) {
        this.id = id;
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

    public AdminInterimBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    @JsonProperty("requested")
    public String getRequested() {
        return requested;
    }

    @JsonProperty("requested")
    public void setRequested(String requested) {
        this.requested = requested;
    }

    public AdminInterimBuilder withRequested(String requested) {
        this.requested = requested;
        return this;
    }

    @JsonProperty("reason")
    public String getReason() {
        return reason;
    }

    @JsonProperty("reason")
    public void setReason(String reason) {
        this.reason = reason;
    }

    public AdminInterimBuilder withReason(String reason) {
        this.reason = reason;
        return this;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public AdminInterimBuilder withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public AdminInterimBuilder withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    @JsonProperty("authVehicles")
    public int getAuthVehicles() {
        return authVehicles;
    }

    @JsonProperty("authVehicles")
    public void setAuthVehicles(int authVehicles) {
        this.authVehicles = authVehicles;
    }

    public AdminInterimBuilder withAuthVehicles(int authVehicles) {
        this.authVehicles = authVehicles;
        return this;
    }

    @JsonProperty("authTrailers")
    public int getAuthTrailers() {
        return authTrailers;
    }

    @JsonProperty("authTrailers")
    public void setAuthTrailers(int authTrailers) {
        this.authTrailers = authTrailers;
    }

    public AdminInterimBuilder withAuthTrailers(int authTrailers) {
        this.authTrailers = authTrailers;
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

    public AdminInterimBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    public AdminInterimBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public String toString() {
        return "id:" + getId() + "version:" + getVersion() + "requested:" + getRequested() + "reason:" + getReason()
        + "startDate:" + getStartDate() + "endDate:" + getEndDate() + "authVehicles:" + getAuthVehicles()
        + "authTrailers:" + getAuthTrailers() + "status:" + getStatus() + "action:" + getAction();
    }
}