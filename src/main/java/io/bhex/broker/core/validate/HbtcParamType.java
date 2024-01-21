package io.bhex.broker.core.validate;

import com.google.common.base.Strings;
import io.bhex.broker.grpc.order.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public enum HbtcParamType implements HbtcParamValidator {

    /**
     * HBTC transfer amount validator:
     * 1、Not Support Scientific notation
     * 2、
     */
    HBTC_TRANSFER_AMOUNT {
        public final BigDecimal MIN_TRANSFER_AMOUNT = new BigDecimal("0.00000001");

        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            String amount = value.toString();
            if (amount.toUpperCase().contains("E")) {
                return false;
            }
            try {
                if (new BigDecimal(amount).compareTo(MIN_TRANSFER_AMOUNT) < 0) {
                    return false;
                }
            } catch (Exception e) {
                log.warn("HbtcParamValidator is not decimal: {}", value);
                return false;
            }
            return true;
        }
    },

    /**
     * OrderType Enum
     */
    ORDER_TYPE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (OrderType enumValue : OrderType.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },
    /**
     * OrderSide Enum
     */
    ORDER_SIDE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (OrderSide enumValue : OrderSide.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },
    /**
     * OrderTimeInForce Enum
     */
    ORDER_TIME_IN_FORCE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (OrderTimeInForceEnum enumValue : OrderTimeInForceEnum.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },
    /**
     * FuturesOrderSide Enum
     */
    FUTURES_ORDER_SIDE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (FuturesOrderSide enumValue : FuturesOrderSide.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },

    /**
     * PlanOrder.FuturesOrderType Enum
     */
    FUTURES_ORDER_TYPE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (PlanOrder.FuturesOrderType enumValue : PlanOrder.FuturesOrderType.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },

    /**
     * FuturesPriceType Enum
     */
    FUTURES_PRICE_TYPE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (FuturesPriceType enumValue : FuturesPriceType.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },

    /**
     * PlanOrder.PlanOrderTypeEnum
     */
    PLAN_ORDER_TYPE {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            for (PlanOrder.PlanOrderTypeEnum enumValue : PlanOrder.PlanOrderTypeEnum.values()) {
                if (value.toString().equalsIgnoreCase(enumValue.name())) {
                    return true;
                }
            }
            return false;
        }
    },

    /**
     * Order Quantity can't be empty string.
     */
    ORDER_QUANTITY {
        @Override
        public boolean validate(Object value) {
            if (value == null || Strings.isNullOrEmpty(value.toString())) {
                return false;
            }
            String qty = value.toString();
            if (qty.toUpperCase().contains("E")) {
                log.warn("validate ORDER_QUANTITY:{} .", qty);
//                return false;
            }
            try {
                if (new BigDecimal(qty).stripTrailingZeros().scale() > 18) {
                    log.warn("validate ORDER_QUANTITY:{} .", qty);
//                    return false;
                }
                if (new BigDecimal(qty).compareTo(BigDecimal.ZERO) < 0) {
                    log.warn("validate ORDER_QUANTITY:{} .", qty);
                    return false;
                }
            } catch (Exception e) {
                log.warn("HbtcParamValidator is not decimal: {}", value);
                return false;
            }
            return true;
        }
    },

    /**
     * Order price valid.
     */
    ORDER_PRICE {
        @Override
        public boolean validate(Object value) {
            if (value != null && !Strings.isNullOrEmpty(value.toString())) {
                String price = value.toString();
                if (price.toUpperCase().contains("E")) {
                    log.warn("validate ORDER_PRICE:{} .", price);
//                    return false;
                }
                try {
                    if (new BigDecimal(price).stripTrailingZeros().scale() > 18) {
                        log.warn("validate ORDER_PRICE:{} .", price);
//                        return false;
                    }
                    if (new BigDecimal(price).compareTo(BigDecimal.ZERO) < 0) {
                        log.warn("validate ORDER_PRICE:{} .", price);
                        return false;
                    }
                } catch (Exception e) {
                    log.warn("HbtcParamValidator price is not decimal: {}", value);
                    return false;
                }
            }
            return true;
        }
    },

    /**
     * Order amount valid.
     */
    ORDER_AMOUNT {
        @Override
        public boolean validate(Object value) {
            if (value != null && !Strings.isNullOrEmpty(value.toString())) {
                String amount = value.toString();
                if (amount.toUpperCase().contains("E")) {
                    log.warn("validate ORDER_AMOUNT:{} .", amount);
//                    return false;
                }
                try {
                    if (new BigDecimal(amount).stripTrailingZeros().scale() > 18) {
                        log.warn("validate ORDER_AMOUNT:{} .", amount);
//                        return false;
                    }
                    if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) < 0) {
                        log.warn("validate ORDER_AMOUNT:{} .", amount);
                        return false;
                    }
                } catch (Exception e) {
                    log.warn("HbtcParamValidator amount is not decimal: {}", value);
                    return false;
                }
            }
            return true;
        }
    },

    /**
     * CLIENT_ORDER_ID
     */
    CLIENT_ORDER_ID {
        @Override
        public boolean validate(Object value) {
            if (value != null && !Strings.isNullOrEmpty(value.toString())) {
                if (value.toString().length() > 255) {
                    return false;
                }
            }
            return true;
        }
    };

}
