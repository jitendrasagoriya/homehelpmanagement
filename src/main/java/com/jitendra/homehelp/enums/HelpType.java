package com.jitendra.homehelp.enums;

import java.util.HashMap;
import java.util.Map;

public enum HelpType {

    NA(0,"NA"),
    CLOTHS(1,"CLOTHS"),
    BARTAN(2,"BARTAN"),
    SAFAI(3,"SAFAI"),
    MALIS(4,"MALIS"),
    DRIVER(5,"DRIVER"),
    FOOD(6,"FOOD");



    private static final Map<Integer, HelpType> byId = new HashMap<Integer, HelpType>();
    private static final Map<String, HelpType> byValue = new HashMap<String, HelpType>();


    static {
        for (HelpType e : HelpType.values() ) {
            if (byId.put(e.getId(), e) != null) {
                throw new IllegalArgumentException("duplicate id: " + e.getId());
            }

            if (byValue.put(e.getValue(), e) != null) {
                throw new IllegalArgumentException("duplicate value: " + e.getValue());
            }
        }
    }


    public static HelpType getById(int id) {
        return byId.get(id);
    }

    public static HelpType getByValue(String value) {
        return byValue.get(value);
    }


    private int id;

    private String value;

    /**
     * @param id
     * @param value
     */
    private HelpType(int id, String value) {
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
