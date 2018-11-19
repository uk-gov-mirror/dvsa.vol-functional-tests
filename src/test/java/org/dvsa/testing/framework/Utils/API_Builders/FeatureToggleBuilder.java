package org.dvsa.testing.framework.Utils.API_Builders;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "id",
            "friendlyName",
            "configName",
            "status"


    })

    public class FeatureToggleBuilder {
        @JsonProperty("id")
        private String id;
        @JsonProperty("friendlyName")
        private String friendlyName;
        @JsonProperty("configName")
        private String configName;
        @JsonProperty("status")
        private String status;
        static public String BACKEND_SURRENDER = "15";

        public FeatureToggleBuilder (){

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFriendlyName() {
            return friendlyName;
        }

        public void setFriendlyName(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }



    }





