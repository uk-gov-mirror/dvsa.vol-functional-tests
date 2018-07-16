package org.dvsa.testing.framework.Utils.API_Builders;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contactDetails",
        "userType",
        "loginId",
        "team",
        "roles"
})
public class CreateInternalAdminUser {

    @JsonProperty("contactDetails")
    private ContactDetailsBuilder contactDetails;
    @JsonProperty("userType")
    private String userType;
    @JsonProperty("loginId")
    private String loginId;
    @JsonProperty("team")
    private String team;
    @JsonProperty("roles")
    private List<String> roles = null;

    @JsonProperty("contactDetails")
    public ContactDetailsBuilder getContactDetails() {
        return contactDetails;
    }

    @JsonProperty("contactDetails")
    public void setContactDetails(ContactDetailsBuilder contactDetails) {
        this.contactDetails = contactDetails;
    }

    public CreateInternalAdminUser withContactDetails(ContactDetailsBuilder contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    @JsonProperty("userType")
    public String getUserType() {
        return userType;
    }

    @JsonProperty("userType")
    public void setUserType(String userType) {
        this.userType = userType;
    }

    public CreateInternalAdminUser withUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @JsonProperty("loginId")
    public String getLoginId() {
        return loginId;
    }

    @JsonProperty("loginId")
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public CreateInternalAdminUser withLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    @JsonProperty("team")
    public String getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    public CreateInternalAdminUser withTeam(String team) {
        this.team = team;
        return this;
    }

    @JsonProperty("roles")
    public List<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public CreateInternalAdminUser withRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return "contactDetails:" + getContactDetails() + "userType:" + getUserType() + "loginId:" + getLoginId() + "team:" + getTeam()
        + "roles:" + getRoles();
    }
}