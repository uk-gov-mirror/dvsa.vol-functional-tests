package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "privateHireLicenceNo",
        "councilName",
        "licence",
        "address"
})
public class PhvTaxiBuilder {

    @JsonProperty("id")
    private String id;
    @JsonProperty("privateHireLicenceNo")
    private String privateHireLicenceNo;
    @JsonProperty("councilName")
    private String councilName;
    @JsonProperty("licence")
    private String licence;
    @JsonProperty("address")
    private AddressBuilder address;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public PhvTaxiBuilder withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("privateHireLicenceNo")
    public String getPrivateHireLicenceNo() {
        return privateHireLicenceNo;
    }

    @JsonProperty("privateHireLicenceNo")
    public void setPrivateHireLicenceNo(String privateHireLicenceNo) {
        this.privateHireLicenceNo = privateHireLicenceNo;
    }

    public PhvTaxiBuilder withPrivateHireLicenceNo(String privateHireLicenceNo) {
        this.privateHireLicenceNo = privateHireLicenceNo;
        return this;
    }

    @JsonProperty("councilName")
    public String getCouncilName() {
        return councilName;
    }

    @JsonProperty("councilName")
    public void setCouncilName(String councilName) {
        this.councilName = councilName;
    }

    public PhvTaxiBuilder withCouncilName(String councilName) {
        this.councilName = councilName;
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

    public PhvTaxiBuilder withLicence(String licence) {
        this.licence = licence;
        return this;
    }

    @JsonProperty("address")
    public AddressBuilder getAddress() {
        return address;
    }

    @JsonProperty("Address")
    public void setAddress(AddressBuilder address) {
        this.address = address;
    }

    public PhvTaxiBuilder withAddress(AddressBuilder address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "id" + getId() + ",licence:" + getLicence() + ",privateHireLicenceNo:" + getPrivateHireLicenceNo() +
                ",councilName:" + getCouncilName() + ",registeredAddress:" + getAddress();
    }
}