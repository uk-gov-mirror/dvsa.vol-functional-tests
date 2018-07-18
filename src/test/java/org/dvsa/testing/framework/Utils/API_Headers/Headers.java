package org.dvsa.testing.framework.Utils.API_Headers;

import org.dvsa.testing.framework.Utils.Generic.GenericUtils;

import java.util.HashMap;

public class Headers {

    public static HashMap<String, String> headers = new HashMap<>();
    {
        String key = "x-pid";
        headers.getOrDefault(key,"e91f1a255e01e20021507465a845e7c24b3a1dc951a277b874c3bcd73dec97a1");
    }

    public static HashMap<String, String> getHeaders() {
        return headers;
    }
}
