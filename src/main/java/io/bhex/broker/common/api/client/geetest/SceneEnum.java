package io.bhex.broker.common.api.client.geetest;

public enum SceneEnum {
    //建议的场景
    REGISTER("sign_up"),
    LOGIN("log_in"),
    CHAT("chat"),
    PAYMENT("payment"),
    BUY("buy"),
    //自定义场景
    ORDER("order"),
    SMS("sms"),
    EMAIL("email"),
    RED_PACKET("red_packet"),
    MODIFY("modify_basic"),
    SET_PASSWORD("set_password"),
    MODIFY_PASSWORD("modify_password"),
    FIND_PASSWORD("find_password"),
    OTHER("other");
    private final String name;

    SceneEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
