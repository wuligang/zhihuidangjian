package org.jeecg.modules.party_building.enums;

/**
 * The enum Boolean enum.
 *
 * @author wangxy
 * @date 2020-7-2 11:19
 * @Description: 积分规则枚举类型
 */
public enum IntegralRuleEnum {

    R001("R001", "观看微课堂视频"),
    R002("R002", "观看微课堂文本"),
    R003("R003", "稿件审核通过"),
    R004("R004", "访问思政网"),
    R998("R998", "%s商品兑换"),
    R999("R999", "手动修改");

    private String value;

    private String desc;

    IntegralRuleEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static IntegralRuleEnum getIntegralRuleEnum(String integralRuleNo) {
        for (IntegralRuleEnum integralRuleEnum : IntegralRuleEnum.values()) {
            if (integralRuleEnum.getValue().equals(integralRuleNo)) {
                return integralRuleEnum;
            }
        }
        return null;
    }
}