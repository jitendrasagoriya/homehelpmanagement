package com.jitendra.homehelp.enums;

import java.util.HashMap;
import java.util.Map;

public enum BatchEvent {
    HOMEHELPATTENDANCE(1,"HOMEHELPATTENDANCE");

    private static final Map<Integer, BatchEvent> byId = new HashMap<Integer, BatchEvent>();
    private static final Map<String, BatchEvent> byValue = new HashMap<String, BatchEvent>();


    static {
        for (BatchEvent e : BatchEvent.values() ) {
            if (byId.put(e.getId(), e) != null) {
                throw new IllegalArgumentException("duplicate id: " + e.getId());
            }

            if (byValue.put(e.getValue(), e) != null) {
                throw new IllegalArgumentException("duplicate value: " + e.getValue());
            }
        }
    }


    public static BatchEvent getById(int id) {
        return byId.get(id);
    }

    public static BatchEvent getByValue(String value) {
        return byValue.get(value);
    }


    private int id;

    private String value;

    /**
     * @param id
     * @param value
     */
    private BatchEvent(int id, String value) {
        this.id = id;
        this.value = value;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}
