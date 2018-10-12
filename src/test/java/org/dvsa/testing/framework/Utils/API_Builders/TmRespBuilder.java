package org.dvsa.testing.framework.Utils.API_Builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "placeOfBirth",
        "homeAddress",
        "workAddress",
        "tmType",
        "isOwner",
        "hoursMon",
        "hoursTue",
        "hoursWed",
        "hoursThu",
        "hoursFri",
        "hoursSat",
        "hoursSun",
        "additionalInfo",
        "dob",
        "submit",
        "id",
        "version"
})

public class TmRespBuilder {
    @JsonProperty("email")
    private String email;
    @JsonProperty("placeOfBirth")
    private String placeOfBirth;
    @JsonProperty("homeAddress")
    private AddressBuilder homeAddress;
    @JsonProperty("workAddress")
    private AddressBuilder workAddress;
    @JsonProperty("tmType")
    private String tmType;
    @JsonProperty("isOwner")
    private String isOwner;
    @JsonProperty("hoursMon")
    private String hoursMon;
    @JsonProperty("hoursTue")
    private String hoursTue;
    @JsonProperty("hoursWed")
    private String hoursWed;
    @JsonProperty("hoursThu")
    private String hoursThu;
    @JsonProperty("hoursFri")
    private String hoursFri;
    @JsonProperty("hoursSat")
    private String hoursSat;
    @JsonProperty("hoursSun")
    private String hoursSun;
    @JsonProperty("additionalInfo")
    private String additionalInfo;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("submit")
    private String submit;
    @JsonProperty("id")
    private String id;
    @JsonProperty("version")
    private int version;


    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("placeOfBirth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @JsonProperty("placeOfBirth")
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }
    @JsonProperty("homeAddress")
    public AddressBuilder getHomeAddress() {
        return homeAddress;
    }

    @JsonProperty("homeAddress")
    public void setHomeAddress(AddressBuilder homeAddress) {
        this.homeAddress = homeAddress;
    }

    @JsonProperty("workAddress")
    public AddressBuilder getWorkAddress() {
        return workAddress;
    }

    @JsonProperty("workAddress")
    public void setWorkAddress(AddressBuilder homeAddress) {
        this.workAddress = workAddress;
    }

    @JsonProperty("tmType")
    public String getTmType() {
        return tmType;
    }

    @JsonProperty("tmType")
    public void setTmType(String tmType) {
        this.tmType = tmType;
    }

    @JsonProperty("isOwner")
    public String getIsOwner() {
        return isOwner;
    }

    @JsonProperty("isOwner")
    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    @JsonProperty("hoursMon")
    public String getHoursMon() {
        return hoursMon;
    }

    @JsonProperty("hoursMon")
    public void setHoursMon(String hoursMon) {
        this.hoursMon = hoursMon;
    }

    @JsonProperty("hoursTue")
    public String getHoursTue() {
        return hoursTue;
    }

    @JsonProperty("hoursTue")
    public void setHoursTue(String hoursTue) {
        this.hoursTue = hoursTue;
    }

    @JsonProperty("hoursWed")
    public String getHoursWed() {
        return hoursWed;
    }

    @JsonProperty("hoursWed")
    public void sethoursWed(String hoursWed) {
        this.hoursWed = hoursWed;
    }

    @JsonProperty("hoursThu")
    public String getHoursThu() {
        return hoursThu;
    }

    @JsonProperty("hoursThu")
    public void setHoursThu(String hoursThu) {
        this.hoursThu = hoursThu;
    }

    @JsonProperty("hoursFri")
    public String getHoursFri() {
        return hoursFri;
    }

    @JsonProperty("hoursFri")
    public void setHoursFri(String hoursFri) {
        this.hoursFri = hoursFri;
    }

    @JsonProperty("hoursSat")
    public String getHoursSat() {
        return hoursSat;
    }

    @JsonProperty("hoursSat")
    public void setHoursSat(String hoursSat) {
        this.hoursSat = hoursSat;
    }

    @JsonProperty("hoursSun")
    public String getHoursSun() {
        return hoursSun;
    }

    @JsonProperty("hoursSun")
    public void setHoursSun(String hoursSun) {
        this.hoursSun = hoursSun;
    }

    @JsonProperty("additionalInfo")
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @JsonProperty("additionalInfo")
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    @JsonProperty("submit")
    public String getSubmit() {
        return submit;
    }

    @JsonProperty("submit")
    public void setSubmit(String submit) {
        this.submit = submit;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("version")
    public int getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(int version) {
        this.version = version;
    }

    public TmRespBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    public TmRespBuilder withPlaceOfBirth (String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    } public TmRespBuilder withHomeAddress(AddressBuilder homeAddress) {
        this.homeAddress = homeAddress;
        return this;
    }

    public TmRespBuilder withTmType(String tmType) {
        this.tmType = tmType;
        return this;
    }
    public TmRespBuilder withWorkAddress(AddressBuilder workAddress) {
        this.workAddress = workAddress;
        return this;
    }
    public TmRespBuilder withIsOwner(String isOwner) {
        this.isOwner = isOwner;
        return this;
    } public TmRespBuilder withHoursMon(String hoursMon) {
        this.hoursMon = hoursMon;
        return this;
    } public TmRespBuilder withHoursTue(String hoursTue) {
        this.hoursTue = hoursTue;
        return this;
    } public TmRespBuilder withHoursWed(String hoursWed) {
        this.hoursWed = hoursWed;
        return this;

    } public TmRespBuilder withHoursThu(String hoursThu) {
        this.hoursThu = hoursThu;
        return this;
    } public TmRespBuilder withHoursFri(String hoursFri) {
        this.hoursFri = hoursFri;
        return this;
    } public TmRespBuilder withHoursSat(String hoursSat) {
        this.hoursSat = hoursSat;
        return this;
    } public TmRespBuilder withHoursSun(String hoursSun) {
        this.hoursSun = hoursSun;
        return this;
    }
    public TmRespBuilder withAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    } public TmRespBuilder withDob(String dob) {
        this.dob = dob;
        return this;
    }
    public TmRespBuilder withSubmit(String submit) {
        this.submit = submit;
        return this;
    }

    public TmRespBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public TmRespBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "email:" + getEmail() + "placeOfBirth:" + getPlaceOfBirth() + "homeAddress:" + getHomeAddress() + "workAddress:" + getWorkAddress() + "tmType:" + getTmType() + "isOwner:" + getIsOwner()
                + "hoursMon:" + getHoursMon() + "hoursTue" + getHoursTue() + "hoursWed:" + getHoursWed() + "hoursThu:" + getHoursThu() + "hoursFri:" + getHoursFri() + "hoursSat:" + getHoursSat() + "hoursSun:"
                + getHoursSun() + "additionalInfo:" + getAdditionalInfo() + "dob:" + getDob() + "submit:" + getSubmit() + "id:" + getId() + "version:" + getVersion();
    }

}

