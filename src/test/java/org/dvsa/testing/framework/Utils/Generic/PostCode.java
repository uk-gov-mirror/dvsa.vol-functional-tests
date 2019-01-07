package org.dvsa.testing.framework.Utils.Generic;

import enums.TrafficArea;
import org.jetbrains.annotations.NotNull;

public class PostCode {

    private String area;

    public String getArea() {
        return area;
    }


    public static String getPostCode(@NotNull TrafficArea trafficArea) {
        String postCode;
        switch (trafficArea) {
            case B:
                postCode = "BD162UA";
                break;
            case C:
                postCode = "M446TL";
                break;
            case D:
                postCode = "B440TA";
                break;
            case F:
                postCode = "IP138ES";
                break;
            case G:
                postCode = "CF116EE";
                break;
            case H:
                postCode = "OX11BY";
                break;
            case K:
                postCode = "E72EW";
                break;
            case M:
                postCode = "EH139DY";
                break;
            case N:
                postCode = "BT28HQ";
                break;
            default:
                postCode = "NG23HX";
        }
        return postCode;
    }
}