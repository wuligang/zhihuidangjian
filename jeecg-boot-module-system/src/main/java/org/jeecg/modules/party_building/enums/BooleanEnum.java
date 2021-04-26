package org.jeecg.modules.party_building.enums;

/**
 * The enum Boolean enum.
 *
 * @author wangxy
 * @date 2020-7-2 11:19
 * @Description: 布尔值枚举类型
 */
public enum BooleanEnum {
    /**
     * 是
     */
    TRUE("1"),
    /**
     * 否
     */
    FALSE("0");

    /**
     * The Value.
     */
    private String value;

    /**
     * Instantiates a new Boolean enum.
     *
     * @param value the value
     * @date
     */
    BooleanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BooleanEnum getBooleanEnum(String booleanStr) {
        for (BooleanEnum booleanEnum : BooleanEnum.values()) {
            if (booleanEnum.getValue().equals(booleanStr)) {
                return booleanEnum;
            }
        }
        return null;
    }
}