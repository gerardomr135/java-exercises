package com.fireal99.roomwebapp.models;

public enum Position {
    HOUSEKEEPING,
    FRONT_DESK,
    SECURITY,
    CONCIERGE;

    @Override
    public String toString() {
        return switch (this) {
            case HOUSEKEEPING -> "HOUSEKEEPING";
            case FRONT_DESK -> "FRONT_DESK";
            case SECURITY -> "SECURITY";
            case CONCIERGE -> "CONCIERGE";
            default -> "";
        };
    }
}
