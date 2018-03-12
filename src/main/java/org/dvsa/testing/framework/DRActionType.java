package org.dvsa.testing.framework;

public enum DRActionType {
    REVIEW("Review"),
    AUTOMATE("Automate");

    private String type;

    DRActionType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static DRActionType getEnum(String name) {
        DRActionType actionType;

        switch (name.toLowerCase()) {
            case "review":
                actionType = REVIEW;
                break;
            case "automate":
                actionType = AUTOMATE;
                break;
            default:
                throw new IllegalArgumentException();
        }

        return actionType;
    }
}
