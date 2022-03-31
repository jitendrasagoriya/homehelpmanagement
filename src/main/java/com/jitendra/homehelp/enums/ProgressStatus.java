package com.jitendra.homehelp.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProgressStatus {

    NOTSTARTED(101,"NOT-STARTED"),
    INPROC(102,"INPROC"),
    COMPLETED(103,"COMPLETED"),
    SICK(104,"SICK-LEAVE"),
    PERSONAL(105,"PERSONAL-LEAVE");

    private static final Map<Integer, ProgressStatus> byId = new HashMap<Integer, ProgressStatus>();
    private static final Map<String, ProgressStatus> byValue = new HashMap<String, ProgressStatus>();


    static {
        for (ProgressStatus e : ProgressStatus.values() ) {
            if (byId.put(e.getId(), e) != null) {
                throw new IllegalArgumentException("duplicate id: " + e.getId());
            }

            if (byValue.put(e.getValue(), e) != null) {
                throw new IllegalArgumentException("duplicate value: " + e.getValue());
            }
        }
    }


    public static ProgressStatus getById(int id) {
        return byId.get(id);
    }

    public static ProgressStatus getByValue(String value) {
        return byValue.get(value);
    }


    private int id;

    private String value;

    /**
     * @param id
     * @param value
     */
    private ProgressStatus(int id, String value) {
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
