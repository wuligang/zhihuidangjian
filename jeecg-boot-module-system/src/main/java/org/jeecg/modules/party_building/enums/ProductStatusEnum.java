package org.jeecg.modules.party_building.enums;

/**
 * @author wangxy
 * @date 2020-7-4 15:00:00
 * @Description: 商品状态枚举类型
 */
public enum ProductStatusEnum {

    /**
     * 新增
     */
    NEW("0"),
    /**
     * 上架
     */
    ON_SALE("1"),
    /**
     * 下架
     */
    OFF_SALE("2");

    private String value;


    ProductStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    public static ProductStatusEnum getProductStatusEnum(String status) {
        for (ProductStatusEnum productStatusEnum : ProductStatusEnum.values()) {
            if (productStatusEnum.getValue().equals(status)) {
                return productStatusEnum;
            }
        }
        return null;
    }
}