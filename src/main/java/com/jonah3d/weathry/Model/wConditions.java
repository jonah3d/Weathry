package com.jonah3d.weathry.Model;

public enum wConditions {
    THUNDERSTORM("Thunderstorm"),
    DRIZZLE("Drizzle"),
    RAIN("Rain"),
    SNOW("Snow"),
    CLEAR("Clear"),
    CLOUDS("Clouds");

    private final String condition;

    wConditions(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return condition;
    }
}
