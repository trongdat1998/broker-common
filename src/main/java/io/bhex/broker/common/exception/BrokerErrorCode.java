/*
 ************************************
 * @项目名称: broker
 * @文件名称: BrokerErrorCode
 * @Date 2018/05/22
 * @Author will.zhao@bhex.io
 * @Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 **************************************
 */
package io.bhex.broker.common.exception;

public enum BrokerErrorCode {

    /* system error code: 30*/
    SUCCESS(200, "Success"),

    /*
     * start cone error code
     */
    /**
     * 实际协议大小和设计不匹配
     */
    INVALID_PROTOSIZE(-1, "invalid_protosize"),

    /**
     * 本地Frag不正确
     */
    INCORRECT_LOCAL_FRAGID(-2, "incorrect_local_fragid"),

    /**
     * 本地Frag不存在
     */
    LOCAL_FRAGID_NOT_EXIST(-3, "local_fragid_not_exist"),

    /**
     * 本地Frag已存在
     */
    LOCAL_FRAGID_ALREADY_EXIST(-4, "local_fragid_already_exist"),

    /**
     * 远程Frag不存在
     */
    INCORRECT_REMOTE_FRAGID(-5, "incorrect_remote_fragid"),

    /**
     * Account本地不存在
     */
    INCORRECT_ACCOUNTID(-6, "incorrect_accountid"),

    /**
     * 本地symbol不存在
     */
    INCORRECT_LOCAL_SYMBOLINDEX(-8, "incorrect_local_symbolindex"),

    /**
     * 远程symbol不存在
     */
    INCORRECT_REMOTE_SYMBOLINDEX(-9, "incorrect_remote_symbolindex"),

    /**
     * mainCmd不存在相应的处理函数
     */
    UNSUPPORT_MAINCMD(-10, "unsupport_maincmd"),

    /**
     * subCmd不存在相应的处理函数
     */
    UNSUPPORT_SUBCMD(-11, "unsupport_subcmd"),

    /**
     * 密码不正确
     */
    WRONG_PASSWORD(-12, "wrong_password"),

    /**
     * 服务ID不正确
     */
    INCORRECT_SERVICEID(-13, "incorrect_serviceid"),

    /**
     * 不是masteractive
     */
    IS_NOT_MASTERACTIVE(-14, "is_not_masteractive"),

    /**
     * 内部问题无法处理
     */
    UNABLE_PROCESS(-15, "unable_process"),

    /**
     * ERROR_CODE_INVALID_ARGUMENT
     */
    INVALID_ARGUMENT(-16, "invalid_argument"),

    /**
     * 试图处理，但处理失败
     */
    PROCESS_FAILED(-17, "process_failed"),

    /**
     * shard指令处理中特殊错误号，不要重复
     */
    SHARD_CMD_PENGDING(-10000000, "shard_cmd_pengding"),

    /**
     * 币对不存在
     */
    MATCH_NOT_IN_ENGINE(2000, "match_not_in_engine"),

    /**
     * 订单过期
     */
    MATCH_EXPIRED(2001, "match_expired"),

    /**
     * 订单拒绝
     */
    MATCH_REJECTED(2002, "match_rejected"),

    /**
     * 参数无效
     */
    MATCH_INVALID_PARAMS(2003, "match_invalid_params"),

    /**
     * 无匹配 撮合失败
     */
    MATCH_NOT_MATCH(2004, "match_not_match"),

    /**
     * 订单不存在
     */
    MATCH_NOT_EXIST(2005, "match_not_exist"),

    /**
     * 超过开仓杠杆限制
     */
    MATCH_REJECTED_OPEN_MARGIN_RATE(2006, "match_rejected_open_margin_rate"),

    /**
     * 全仓模式下可用保证金不足
     */
    MATCH_REJECTED_INSUFFICIENT_AVAILABLE_MARGIN(2007, "match_rejected_insufficient_available_margin"),

    /**
     * 仓位不存在
     */
    MATCH_POSITION_NOT_EXIST(2008, "match_position_not_exist"),

    /**
     * 保证金更新失败
     */
    MATCH_MARGIN_UPDATE_FAILED(2009, "match_margin_update_failed"),

    /**
     * 导入K线失败
     */
    MATCH_IMPORT_KLINE_FAILED(2100, "match_import_kline_failed"),

    /**
     * 保证金类型更新失败
     */
    MATCH_MARGIN_TYPE_UPDATE_FAILED(2011, "match_margin_type_update_failed"),

    /**
     * client order id 已存在
     */
    CONE_CLIENT_ORDER_ID_EXIST(3000, "cone_client_order_id_exist"),

    /**
     * order 不存在
     */
    CONE_ORDER_NOT_FOUND(3001, "cone_order_not_found"),

    /**
     * balance 不存在
     */
    CONE_BALANCE_NOT_FOUND(3002, "cone_balance_not_found"),

    /**
     * balance 不足
     */
    CONE_BALANCE_INSUFFICIENT(3003, "cone_balance_insufficient"),

    /**
     * option position 不足
     */
    CONE_OPTION_POSITION_NOT_FOUND(3004, "cone_option_position_not_found"),

    /**
     * futures position 不足
     */
    CONE_FUTURES_POSITION_NOT_FOUND(3005, "cone_futures_position_not_found"),

    /**
     * 更新充值状态失败
     */
    CONE_UPDATE_DEPOSIT_STATUS_ERROR(3006, "cone_update_deposit_status_error"),

    /**
     * 关闭充值订单失败
     */
    CONE_CLOSE_DEPOSIT_STATUS_ERROR(3007, "cone_close_deposit_status_error"),

    /**
     * 充值订单不存在
     */
    CONE_DEPOSIT_ORDER_NOT_FOUND(3008, "cone_deposit_order_not_found"),

    /**
     * 更新提现订单状态失败
     */
    CONE_UPDATE_WITHDRAW_STATUS_ERROR(3009, "cone_update_withdraw_status_error"),

    /**
     * 关闭提现订单失败
     */
    CONE_CLOSE_WITHDRAW_STATUS_ERROR(3010, "cone_close_withdraw_status_error"),

    /**
     * 提现订单不存在
     */
    CONE_WITHDRAW_ORDER_NOT_FOUND(3011, "cone_withdraw_order_not_found"),

    /**
     * 转出失败
     */
    CONE_TRANSFER_OUT_ERROR(3012, "cone_transfer_out_error"),

    /**
     * 转入失败
     */
    CONE_TRANSFER_IN_ERROR(3013, "cone_transfer_in_error"),

    /**
     * token 不存在
     */
    CONE_TOKEN_NOT_FOUND(3014, "cone_token_not_found"),

    /**
     * 无效参数
     */
    CONE_PARAMETER_INVALID(3015, "cone_parameter_invalid"),

    /**
     * taker 兑换失败（闪兑）
     */
    CONE_TAKER_CONVERT_ERROR(3016, "cone_taker_convert_error"),

    /**
     * maker 兑换失败 （闪兑）
     */
    CONE_MAKER_CONVERT_ERROR(3017, "cone_maker_convert_error"),

    /**
     * 不接受买单
     */
    CONE_BUY_ORDER_NOT_ALLOWED(3018, "cone_buy_order_not_allowed"),

    /**
     * 不接受卖单
     */
    CONE_SELL_ORDER_NOT_ALLOWED(3019, "cone_sell_order_not_allowed"),

    /**
     * 交易已关闭
     */
    CONE_MARKET_CLOSED(3020, "cone_market_closed"),

    /**
     * order 内存不足
     */
    CONE_ORDER_OUT_OF_MEMORY(3021, "cone_order_out_of_memory"),

    /**
     * history order 内存不足
     */
    CONE_HISTORY_ORDER_OUT_OF_MEMORY(3022, "cone_history_order_out_of_memory"),

    /**
     * trade detail 内存不足
     */
    CONE_TRADE_DETAIL_OUT_OF_MEMORY(3023, "cone_trade_detail_out_of_memory"),

    /**
     * balance 内存不足
     */
    CONE_BALANCE_OUT_OF_MEMORY(3024, "cone_balance_out_of_memory"),

    /**
     * balance flow 内存不足
     */
    CONE_BALANCE_FLOW_OUT_OF_MEMORY(3025, "cone_balance_flow_out_of_memory"),

    /**
     * 批量单数量不合法
     */
    CONE_BATCH_ORDER_NUM_INVALID(3026, "cone_batch_order_num_invalid"),

    /**
     * 已达到最大 open orders 数量
     */
    CONE_MAX_OPEN_ORDER_EXCEEDED(3027, "cone_max_open_order_exceeded"),

    /**
     * 交易手续费无效
     */
    CONE_TRADE_FEE_RATE_INVALID(3028, "cone_trade_fee_rate_invalid"),

    /**
     * 精度无效
     */
    CONE_PRECISION_INVALID(3029, "cone_precision_invalid"),

    /**
     * 更新合约保证金失败
     */
    CONE_FUTURES_UPDATE_MARGIN_ERROR(3030, "cone_futures_update_margin_error"),

    /**
     * 更新合约保证金率失败
     */
    CONE_FUTURES_UPDATE_MARGIN_RATE_ERROR(3031, "cone_futures_update_margin_rate_error"),

    /**
     * 更改合约仓位类型失败
     */
    CONE_FUTURES_CHANGE_POSITION_TYPE_ERROR(3032, "cone_futures_change_position_type_error"),

    /**
     * 无效的价格，可能价格超过限制
     */
    CONE_INVALID_PRICE_TOO_HIGH(3033, "cone_invalid_price_too_high"),

    /**
     * 无效的价格，可能价格超过限制
     */
    CONE_INVALID_PRICE_TOO_LOW(3034, "cone_invalid_price_too_low"),

    /**
     * 超出最大限制（用于批量操作）
     */
    CONE_EXCEED_THE_MAXIMUM_LIMIT(3035, "cone_exceed_the_maximum_limit"),

    /**
     * 无效的数量，可能数量超过限制
     */
    CONE_INVALID_QUANTITY_TOO_HIGH(3036, "cone_invalid_quantity_too_high"),

    /**
     * 无效的数量，可能数量超过限制
     */
    CONE_INVALID_QUANTITY_TOO_LOW(3037, "cone_invalid_quantity_too_low"),

    /**
     * 无效的额度，可能额度超过限制
     */
    CONE_INVALID_AMOUNT_TOO_HIGH(3038, "cone_invalid_amount_too_high"),

    /**
     * 无效的额度，可能额度超过限制
     */
    CONE_INVALID_AMOUNT_TOO_LOW(3039, "cone_invalid_amount_too_low"),

    /**
     * 无效的订单类型
     */
    CONE_INVALID_ORDER_TYPE(3040, "cone_invalid_order_type"),

    /**
     * 当前无法下市价单
     */
    CONE_UNABLE_PUSH_MARKET_ORDER(3041, "cone_unable_push_market_order"),

    /**
     * 时间过早
     */
    CONE_ORDER_TIME_TOO_SLOW(3042, "cone_order_time_too_slow"),

    /**
     * 时间过晚
     */
    CONE_ORDER_TIME_TOO_FAST(3043, "cone_order_time_too_fast"),

    /**
     * 两个价格不符合规则
     */
    CONE_ORDER_COMPLEX_ORDER_INVALID_PRICE(3044, "cone_order_complex_order_invalid_price"),

    /**
     * 已达到最大 特殊 orders 数量
     */
    CONE_MAX_SPECIAL_ORDER_EXCEEDED(3045, "cone_max_special_order_exceeded"),

    /**
     * 不接受开仓
     */
    CONE_FUTURES_OPEN_ORDER_NOT_ALLOWED(3046, "cone_futures_open_order_not_allowed"),

    /**
     * 不接受平仓
     */
    CONE_FUTURES_CLOSE_ORDER_NOT_ALLOWED(3047, "cone_futures_close_order_not_allowed"),

    /**
     * 无效操作类型。合并仓位指定开仓平仓，独立仓位没有指定开仓平仓
     */
    CONE_FUTURES_INVALID_OPERATION_ORDER(3048, "cone_futures_invalid_operation_order"),

    /**
     * 独立仓位的平仓单，数量超过持仓了
     */
    CONE_FUTURES_INSUFFICIENT_HOLD(3049, "cone_futures_insufficient_hold"),

    /**
     * 找不到父ID
     */
    CONE_PARENT_ID_NOT_FOUND(3050, "cone_parent_id_not_found"),

    /**
     * 父订单已经有子订单
     */
    CONE_PARENT_ALREADY_HAVE_CHILD(3051, "cone_parent_already_have_child"),

    /**
     * 当前有 Open orders 存着
     */
    CONE_OPEN_ORDER_EXIST(3052, "cone_open_order_exist"),

    /**
     * 合约仓位正在处理中
     */
    CONE_FUTURES_POSITION_IS_PROCESSING(3053, "cone_futures_position_is_processing"),

    /**
     * 精度无效
     */
    CONE_QUANTITY_PRECISION_INVALID(3054, "cone_quantity_precision_invalid"),

    /**
     * 精度无效
     */
    CONE_PRICE_PRECISION_INVALID(3055, "cone_price_precision_invalid"),

    /**
     * 精度无效
     */
    CONE_AMOUNT_PRECISION_INVALID(3056, "cone_amount_precision_invalid"),

    /**
     * isGlobalAssetLocked 参数错误
     */
    CONE_PARAMETER_GLOBAL_LOCKED_INVALID(3057, "cone_parameter_global_locked_invalid"),

    /**
     * globalAsset shardFrozen 余额不足
     */
    CONE_GLOBAL_ASSET_SHARD_FROZEN_INSUFFICIENT(3058, "cone_global_asset_shard_frozen_insufficient"),

    /**
     * 用户资产Frozen不足
     */
    CONE_BALANCE_FROZEN_INSUFFICIENT(3059, "cone_balance_frozen_insufficient"),

    /**
     * 币对不为空
     */
    CONE_SYMBOL_IS_NOT_EMPTY(3060, "cone_symbol_is_not_empty"),

    /**
     * 余额不为空
     */
    CONE_BALANCE_IS_NOT_EMPTY(3061, "cone_balance_is_not_empty"),

    /**
     * 当前账户禁止交易
     */
    CONE_ACCOUNT_DISABLE_TRADE(3062, "cone_account_disable_trade"),

    /**
     * 可用保证金不足
     */
    CONE_AVAILABLE_MARGIN_INSUFFICIENT(3063, "cone_available_margin_insufficient"),

    /**
     * 超出最大杠杆限制
     */
    CONE_MAX_LEVERAGE_LIMIT_EXCEEDED(3064, "cone_max_leverage_limit_exceeded"),
    /*
     * end cone error code
     * don't insert ErrorCode on `cone error` block
     */

    AUTHORIZE_ERROR(30000, "Authentication failed, login again"),
    // Do not throw this error in the program
    UNCAUGHT_EXCEPTION(30001, "broker server internal error"),
    BAD_REQUEST(30002, "Bad request"),
    PARAM_INVALID(30003, "Request parameter verification error"),
    BROKER_INFO_ERROR(30004, "Illegal request, broker information error"),
    RECAPTCHA_INVALID(30005, "reCaptcha verification failed"),
    GRPC_SERVER_TIMEOUT(30006, "Grpc service invoke timeout"),
    GRPC_SERVER_SYSTEM_ERROR(30007, "Grpc service invoke occur system error"),
    SYSTEM_ERROR(30008, "system error"),
    BROKER_SECURITY_ERROR(30009, "invoke security error"),
    REQUEST_INVALID(30010, "Request invalid"),

    DB_RECORD_NOT_EXISTS(30011, "Record does not exist"),
    DB_RECORD_NOT_UNIQUE(30013, "Record does not unique"),
    DB_RECORD_ERROR(30014, "Bad records"),
    DB_ERROR(30015, "DB error"),
    REQUEST_TOO_FAST(30016, "request too fast"),
    URL_NOT_EXIST(30017, "url not exist"),
    CONTAINS_SENSITIVE_WORDS(30018, "contains sensitive words"),
    PRE_REQUEST_INVALID(30019, "pre-request is invalid, please refresh page"),
    REPEATED_SUBMIT_REQUEST(30020, "submit request repeatedly"),
    DATA_EXCEEDED_LIMIT(30030, "Data exceeds the amount that can be processed"),

    FORBIDDEN_ACTION_FOR_SECURITY(30040, "For safety reasons, this operation has been restricted."),
    FEATURE_NOT_OPEN(30050, "This feature is not yet open"),
    RETURN_403(30060, "give a 403 status"),
    UPDATE_LATEST_VERSION(30061, "please update to latest version"),
    RETURN_404(30062, "Resource not found"),
    LOAD_S3_DATA_ERROR(30063, "load data from s3 error"),

    AES_CIPHER_ERROR(30070, "AES Cipher error"),

    FEATURE_SUSPENDED(30071, "The feature has been suspended"),
    OPERATION_HAS_NO_PERMISSION(30080, "has no permission to operate"),

    SCAN_LOGIN_QRCODE_ERROR(30091, "scan login qrcode error"),
    LOGIN_QRCODE_EXPIRED_OR_NOT_EXIST(30092, "login qrcode has been expired or not exists"),
    SCAN_LOGIN_QRCODE_REPEATED(30093, "scan login qrcode repeated"),
    SCAN_LOGIN_QRCODE_WITH_ANOTHER_USER(30094, "scan login qrcode repeated for another people"),
    AUTHORIZE_QRCODE_LOGIN_ERROR(30095, "qrcode authorize login error"),
    GET_SCAN_LOGIN_QRCODE_RESULT_ERROR(30096, "get scan login qrcode error"),
    SCAN_LOGIN_QRCODE_WITH_APP(30097, "please use app scan login qrcode"),

    MAX_OPEN_ORDER_EXCEEDED(30200, "max open order exceeded"),

    CONE_CHANNEL_ERROR(30300, "cone channel error"),
    CONE_SYSTEM_ERROR(30400, "cone system error"),

    /* user and account: 31 */
    MOBILE_CANNOT_BE_NULL(31001, "Phone number cannot be empty"),
    MOBILE_ILLEGAL(31002, "Illegal phone number"),
    MOBILE_EXIST(31003, "Phone number already exists"),
    EMAIL_CANNOT_BE_NULL(31004, "Email can not be empty"),
    EMAIL_ILLEGAL(31005, "Illegal email"),
    EMAIL_EXIST(31006, "The email already exists"),
    EMAIL_UNBIND_FIRST(31007, "Email is bound, please untie email first"),
    MOBILE_UNBIND_FIRST(31008, "Mobile is bound, please untie mobile first"),
    USER_NOT_EXIST(31009, "User does not exist"),
    VERIFY_CODE_SEND_FAILED(31010, "verify code failed to send"),
    PASSWORD_CANNOT_BE_NULL(31011, "password can not be empty"),
    PASSWORD_NOT_EQUAL(31012, "Inconsistent entry password"),
    PASSWORD_ILLEGAL(31013, "password illegal"),
    VERIFY_CODE_CANNOT_BE_NULL(31014, "verify code cannot be empty"),
    VERIFY_CODE_ERROR(31015, "mobile verify code error"),
    EMAIL_VERIFY_CODE_ERROR(31016, "email verify code error"),
    SIGN_UP_ERROR(31017, "registration failed"),
    ACCOUNT_NOT_EXIST(31018, "Account does not exist"),
    LOGIN_INPUTS_ERROR(31019, "username or password is wrong"),
    INVITE_CODE_ERROR(31020, "Invitation code error"),
    USER_STATUS_FORBIDDEN(31021, "User status is not available"),
    NATIONAL_CODE_CANNOT_BE_NULL(31022, "Country code cannot be empty"),
    GA_BIND_ERROR(31023, "GA binding failed"),
    GA_VALID_ERROR(31024, "GA verification failed"),
    GA_UNBIND_FIRST(31025, "GA is bound, please untie GA first"),
    CREATE_API_KEY_EXCEED_LIMIT(31026, "Exceeded the creations limit"),
    NATIONAL_CODE_ERROR(31027, "National code error"),
    NATIONALITY_CANNOT_BE_NULL(31028, "Nationality cannot be empty"),
    NATIONALITY_ERROR(31029, "Nationality error"),
    FIRST_NAME_CANNOT_BE_NULL(31030, "Last name cannot be empty"),
    SECOND_NAME_CANNOT_BE_NULL(31031, "First name cannot be empty"),
    GENDER_ERROR(31032, "Gender selection error"),
    ID_CARD_TYPE_ERROR(31033, "ID card type error"),
    ID_CARD_NO_CANNOT_BE_NULL(31034, "ID number cannot be empty"),
    ID_CARD_NO_ERROR(31035, "Wrong ID number"),
    ID_CARD_FRONT_URL_ERROR(31036, "Please re-upload the front photo of your ID"),
    ID_CARD_HAND_URL_ERROR(31037, "Please re-upload your handheld ID photo"),
    OLD_PASSWORD_CANNOT_BE_NULL(31038, "Old password cannot be empty"),
    NEW_PASSWORD_CANNOT_BE_NULL(31039, "New password cannot be empty"),
    OLD_PASSWORD_ERROR(31040, "Old password error"),
    API_KEY_EXIST(31041, "ApiKey for this account already exists"),
    API_KEY_IP_RESTRICT_CANNOT_BE_NULL(31042, "At least one ip should be in the ip whitelist"),
    API_KEY_IP_RESTRICT_LIMIT_EXCEEDED(31043, "Up to five ips in the ip whitelist"),
    API_KEY_TAG_CANNOT_BE_NULL(31044, "Tag cannot be null"),
    API_KEY_NOT_ENABLE(31045, "ApiKey is not enable"),
    IP_NOT_IN_WHITELIST(31046, "Request ip is not in the whitelist"),
    IP_ILLEGAL(31047, "Illegal ip address"),
    LOGIN_INPUT_ERROR_2(31048, "Login input error, and error times equals 2"),
    LOGIN_INPUT_ERROR_3(31049, "Login input error, and error times equals 3"),
    LOGIN_INPUT_ERROR_4(31050, "Login input error, and error times equals 4"),
    LOGIN_INPUT_ERROR_5(31051, "Login input error, and error times equals 5"),
    LOGIN_INPUT_ERROR_6(31052, "Login input error, and error times great than 5"),
    UNSUPPORTED_FILE_TYPE(31053, "Unsupported file type"),
    NOT_IN_WHILE_LIST(31054, "Not in white list"),
    TRADE_PWD_CANNOT_BE_NULL(31055, "Trade password cannot be empty"),
    PWD_CANNOT_EQ_TRADE_PWD(31056, "The login password can't be the same as the trade password"),
    INCONSISTENT_IDENTITY_INFO(31057, "Identity authentication: Inconsistent identity information"),
    IDENTITY_AUTHENTICATION_FAILED(31058, "Identity authentication: Identity information authentication failed"),
    IDENTITY_AUTHENTICATION_TIMEOUT(31059, "Identity authentication: Identity information authentication timeout"),
    INCONSISTENT_BANK_CARD_INFO(31060, "BankCard authentication: Inconsistent bank card information"),
    BANK_CARD_AUTHENTICATION_FAILED(31061, "BankCard authentication: Bank card information authentication failed"),
    BANK_CARD_AUTHENTICATION_TIMEOUT(31062, "BankCard authentication: Bank card information authentication timeout"),
    CARD_TYPE_NOT_MATCH_WITH_NATIONALITY(31063, "Identity authentication: ID type and nationality do not match"),
    IDENTITY_AUTHENTICATION_NAME_INVALID(31064, "Identity authentication: identity name invalid"),
    IDENTITY_AUTHENTICATION_CARD_NO_INVALID(31065, "Identity authentication: identity card No. invalid"),
    IDENTITY_CARD_NO_BEING_USED(31066, "identity card no is used for other people"),
    IDENTITY_SUBMIT_REPEATED(31067, "repeated submit identity request"),
    TRADE_PWD_ERROR_1(31068, "trade pwd error, and error code equals 1"),
    TRADE_PWD_ERROR_2(31069, "trade pwd error, and error code equals 2"),
    TRADE_PWD_ERROR_3(31070, "trade pwd error, and error code equals 3"),
    TRADE_PWD_ERROR_4(31071, "trade pwd error, and error code equals 4"),
    TRADE_PWD_ERROR_5(31072, "trade pwd error, and error code equals 5"),
    TRADE_PWD_ERROR_6(31073, "trade pwd error, and error code equals 6"),
    REPEAT_SET_TRADE_PWD(31074, "repeat set trade pwd"),
    TRADE_PWD_TOO_SIMPLE(31075, "trade pwd too simple"),
    NEED_KYC(31076, "need kyc"),
    OPERATION_NOT_SUPPORT_IN_VERSION(31077, "operation not support in this version"),
    WITHDRAW_NOT_SUPPORT_IN_VERSION(31078, "withdraw not support in this version"),
    FROZEN_LOGIN_BY_SYSTEM(31079, "Can't log in because the account is frozen by the system"),
    FROZEN_LOGIN_BY_ILLEGAL_OPERATION(31080, "Cannot log in because the user has illegal operations"),
    FROZEN_WITHDRAW_BY_SYSTEM(31081, "Can't withdraw because the account is frozen by the system"),
    FROZEN_WITHDRAW_BY_RISK_CONTROL(31082, "Can't withdraw because the account is frozen by the risk control"),
    FROZEN_WITHDRAW_BY_ILLEGAL_OPERATION(31083, "Cannot withdraw because the user has illegal operations"),
    CANCEL_ORDER_CANCELLED(31084, "cancel order: order has been cancelled"),
    CANCEL_ORDER_FINISHED(31085, "cancel order: order has been finished"),
    CANCEL_ORDER_REJECTED(31086, "cancel order: order has been rejected"),
    CANCEL_ORDER_LOCKED(31087, "cancel order: order is locked"),
    CANCEL_ORDER_UNSUPPORTED_ORDER_TYPE(31088, "cancel order: Order type does not support cancel"),
    CANCEL_ORDER_ARCHIVED(31089, "cancel order: order is archived"),
    CREATE_ORDER_TIMEOUT(31090, "create order timeout"),
    CANCEL_ORDER_TIMEOUT(31091, "cancel order timeout"),

    UNBIND_MOBILE_FAILED(31092, "unbind mobile failed"),
    UNBIND_EMAIL_FAILED(31093, "unbind email failed "),
    EMAIL_NOT_BIND(31094, "email not bind."),
    MOBILE_NOT_BIND(31095, "mobile not bind."),
    ACCOUNT_NOT_IN_SAME_SHARD(31096, "account id not in the same shard"),
    USER_NOT_ACTIVATED_FEATURE(31097, "User has not activated this feature"),
    USER_NOT_ON_OPTION_WHITELIST(31098, "User is not on the option whitelist"),
    SYMBOL_OPENAPI_TRADE_FORBIDDEN(31099, "symbol does not allowed openapi trade"),
    NEED_BIND_EMAIL(31100, "Need bind email first"),
    NEED_BIND_MOBILE(31101, "Need bind mobile first"),
    NEED_BIND_PASSWORD(31102, "Need bind password first"),
    NEED_BIND_GA(31103, "Need bind GA first"),
    REBIND_GA_VALID_ERROR(31104, "rebind GA verification failed"),
    CANNOT_CREATE_API_WITH_INVALID_ACCOUNT_TYPE(31105, "cannot create apikey with this account_type"),
    NEED_BIND_MOBILE_OR_GA(31106, "Need bind mobile or ga first"),
    NEED_BIND_EMAIL_OR_GA(31107, "Need bind email or ga first"),
    AGE_LESS_18_ERROR(31108, "age less 18"),
    BIND_IP_WHITE_LIST_FIRST(31109, "set ip white_list before use"),

    FIND_PWD_VERIFY_CODE_ERROR(31201, "check find pwd verify code error"),
    FIND_PWD_VERIFY_CODE_ERROR2(31202, "check find pwd verify code error 2 times"),
    FIND_PWD_VERIFY_CODE_ERROR3(31203, "check find pwd verify code error 3 times"),
    FIND_PWD_VERIFY_CODE_ERROR4(31204, "check find pwd verify code error 4 times"),
    FIND_PWD_VERIFY_CODE_ERROR5(31205, "check find pwd verify code error 5 times"),
    FIND_PWD_VERIFY_CODE_ERROR6(31206, "check find pwd verify code error more than 5 times"),
    FIND_PWD_VERIFY_CODE_ERROR10(31207, "check find pwd verify code error 10 times"),
    FIND_PWD_VERIFY_CODE_ERROR11(31208, "check find pwd verify code error more than 10 times"),
    FIND_PWD_BY_EMAIL(31209, "Please operate 'Find Password' By email"),
    FIND_PWD_BY_MOBILE(31210, "Please operate 'Find Password' By mobile"),
    FIND_PWD_NOT_SUPPORT(31211, "Cannot support 'Find Password' with old-version interface"),


    INVITE_USER_NOT_EXIST(31300, "Invite user not exist"),
    INVITE_RELATION_HAS_BEEN_ESTABLISHED(31301, "user invitation relation has been established"),
    WRONG_INVITE_RELATION(31302, "wrong invitation relation"),
    INVITE_USER_ID_ERROR(31303, "cannot find invite user_id"),
    SUB_ACCOUNT_NUMBER_EXCEED_UPPER_LIMIT(31304, "The number of sub accounts exceeds the upper limit"),
    ACCOUNT_NAME_TO_LONG(31305, "account_name to long"),
    AGENT_LEVEL_MAX_LIMIT(31306, "Brokers support up to 10 levels"),
    CANNOT_EXCEED_MAXIMUM_LIMIT(31307, "Cannot exceed the maximum limit"),
    AGENT_CONTACT_EXCHANGE_CUSTOMER(31308, "Agent contact exchange customer"),
    SUB_USER_WITHDRAW_LIMIT(31309, "Sub user cant withdraw"),
    SUB_USER_CREATE_LIMIT(31310, "Maximum limit of sub-users"),
    SUB_USER_NOT_EXISTS(31311, "cannot find user with given subUserId"),

    FINANCE_ACCOUNT_EXIST(31401, "finance account already exist."),
    TENCENT_KYC_BUSINESS_ERROR(31501, "tencent kyc error"),
    TENCENT_KYC_REQUEST_ERROR(31502, "request tencent server error"),
    TENCENT_KYC_UNKNOWN_APP_TYPE(31503, "unknown app type"),
    KYC_INVALID_COUNTRY_CODE(31504, "invalid country code"),
    KYC_INVALID_NAME(31505, "invalid name"),
    KYC_BASIC_VERIFY_UNDONE(31506, "can not complete basic verify"),
    KYC_SENIOR_VERIFY_UNDONE(31507, "can not complete senior verify"),
    KYC_PHOTO_PARAM_INVALID(31508, "plsease upload your correct photo"),
    KYC_VIDEO_VERIFY_ERROR(31509, "video verify apply error"),
    KYC_BASIC_VERIFY_NONEED(31510, "do not need basic verify"),
    KYC_SENIOR_VERIFY_NONEED(31511, "do not need senior verify"),
    KYC_VIP_VERIFY_NONEED(31512, "do not need vip verify"),
    KYC_BROKER_CONFIG_NOT_FOUND(31513, "broker kyc config not found"),
    KYC_MOBILE_EMAIL_REQUIRED(31514, "please complete your mobile and email"),
    KYC_IDENTITY_PHOTO_NOT_MATCH(31515, "identity info and photo not match"),
    KYC_BASICI_VERIFY_NEED(31516, "need basic verify"),
    KYC_SENIOR_VERIFY_NEED(31517, "need senior verify"),
    KYC_VIP_VERIFY_NEED(31518, "need vip verify"),
    KYC_EXIST(31519, "user kyc info exist"),
    KYC_USER_ORG_NOT_MATCH(31520, "user id and org id not match"),
    NOT_SUPPORT_THIS_AREA(31521, "Not support this Area"),
    KYC_VIDEO_VERIFY_NEED_MOBILE(31522, "Please bind your mobile number before submit KYC 3 Authentication"),
    SUB_USER_FROZEN(31523, "The sub-user is frozen"),
    SUB_USER_NAME_EMPTY(31524, "The sub-user username cannot be empty"),
    SUB_USER_USERNAME_RULE_LIMIT(31525, "The sub-user username does not meet the rules"),
    SUB_USER_REMARK_LENGTH_LIMIT(31526, "The sub-user remark length exceeds the limit"),
    SUB_USER_STATUS_MODIFY_LIMIT(31527, "The sub-user user status cannot be modified"),
    SUB_USER_REMARK_EMPTY(31528, "The sub-user remark cannot be empty"),
    SUB_USER_REMARK_RULES_limit(31529, "The sub-user remark content does not meet the rules"),
    SUB_USER_TOTAL_ASSETS_EXCEEDING(31530, "The sub-user total assets exceeding 0.00001BTC are not allowed to be deleted"),
    SUB_USER_NO_OPERATION_AUTHORITY(31531, "No operation authority"),

    CREATE_FAVORITE_FAILED(31700, "create favorite symbol failed"),
    CANCEL_FAVORITE_FAILED(31701, "cancel favorite symbol failed"),
    SORT_FAVORITE_FAILED(31702, "sort favorite symbol failed"),
    SYNC_DATA_FROM_SERVER_FIRST(31703, "Please sync data from the server"),

    /* order: 32 */
    ORDER_HANDLE_FAILED(32000, "Order handle error"),
    ORDER_SIDE_ILLEGAL(32001, "Wrong order side"),
    ORDER_TYPE_ILLEGAL(32002, "Wrong order type"),
    DUPLICATED_ORDER(32003, "Duplicate order request"),
    ORDER_NOT_FOUND(32004, "Order does not exist"),
    ORDER_FAILED(32005, "Create order failed"),
    CANCEL_ORDER_FAILED(32006, "Cancel order failed"),
    ORDER_PRICE_CANNOT_BE_NULL(32007, "Order price cannot be null"),
    ORDER_PRICE_ILLEGAL(32008, "Order price is illegal"),
    ORDER_AMOUNT_CANNOT_BE_NULL(32009, "Order amount cannot be null"),
    ORDER_AMOUNT_ILLEGAL(32010, "Order amount is illegal"),
    ORDER_HAS_BEEN_FILLED(32011, "Order has been filled"),
    ORDER_AMOUNT_TOO_BIG(32012, "Order amount is too big"),
    ORDER_LIMIT_MAKER_FAILED(32013, "Create limit maker order failed"),
    ORDER_SIGN_ERROR(32100, "Order sign is error"),
    ORDER_REQUEST_SYMBOL_INVALID(32101, " Order symbol is invalid."),
    ORDER_PRICE_TOO_HIGH(32102, "Order price is too high"),
    ORDER_PRICE_TOO_SMALL(32103, "Order price small than the min precision"),
    ORDER_PRICE_PRECISION_TOO_LONG(32104, "Order price's Precision is too long"),
    ORDER_QUANTITY_TOO_BIG(32105, "Order quantity is too big."),
    ORDER_QUANTITY_TOO_SMALL(32106, "Order quantity small than the min precision"),
    ORDER_QUANTITY_PRECISION_TOO_LONG(32107, "Order quantity's Precision is too long"),
    ORDER_PRICE_WAVE_EXCEED(32108, "Order price out of wave limit."),
    ORDER_AMOUNT_TOO_SMALL(32109, "Order amount is too small"),
    ORDER_AMOUNT_PRECISION_TOO_LONG(32110, "Order amount's Precision is too long"),
    ERR_CANCEL_ORDER_POSITION_LIMIT(32111, "You have passed the trade limit, please pay attention to the risks."),
    OPTION_ORDER_AMOUNT_TOO_SMALL(32112, "Order amount is too small"),
    OPTION_ORDER_QUANTITY_TOO_SMALL(32113, "Order quantity small than the min precision"),
    SYMBOL_PROHIBIT_ORDER(32114, "Symbol prohibit order operation"),
    ORDER_OVER_USER_LIMIT(32115, "Purchase over user limit"),
    ORDER_OVER_PLATFORM_LIMIT(32116, "Purchase over platform limit"),
    ORDER_ACTIVITY_NOT_START(32117, "Activity has not started"),
    ORDER_ACTIVITY_ALREADY_END(32118, "Activity has ended"),
    ORDER_ACTIVITY_SYSTEM_BUSY(32119, "The system is busy, please try again"),
    ORDER_ACTIVITY_NEED_BALANCE_LIMIT(32120, "Insufficient assets"),
    ORDER_ACTIVITY_NEED_KYC(32121, "Need to pass KYC certification"),
    ORDER_ACTIVITY_NEED_BIND_PHONE(32122, "Please bind your phone number"),
    ORDER_PRICE_OUT_OF_LIQ(32123, "Order price is out of liquidation price"),
    ORDER_PRICE_UNDER_LIQ(32124, "Order price is under liquidation price"),
    ORDER_MAYBE_TRIGGER_LIQ(32125, "Order price is unreasonable to exceed (or be lower than) the liquidation price"),
    ORDER_FUTURES_QUANTITY_INVALID(32126, "Order quantity invalid."),
    ORDER_FUTURES_TRADE_CLOSED(32127, "Order trade closed."),
    ORDER_FROZEN_BY_ADMIN(32128, "Order trade frozened by admin."),
    SET_RISKLIMIT_MAYBE_TRIGGER_LIQUI(32130, "set risk limit maybe trigger liquidation"),
    SET_RISKLIMIT_POS_NOT_ENOUGH(32131, "please adjust risk limit after you decrease your position size"),
    ORDER_FUTURES_LEVERAGE_INVALID(32132, "create order failed for invalid leverage."),
    SYMBOL_PLATFORM_PROHIBIT_ORDER(32133, "Symbol prohibit order operation"),
    SYMBOL_EXCHANGEP_PROHIBIT_ORDER(32134, "Symbol prohibit order operation"),
    REDUCE_MARGIN_INVALID(32135, "Reduce margin invalid"),
    INVALID_SYMBOL(32136, "Invalid symbol"),
    CHANGE_POSITION_TYPE_FAILED(32137, "change position type failed"),
    CHANGE_MARGIN_TYPE_FAILED(32138, "change margin type failed"),
    CREATE_ORDER_REJECTED(32139, "create order rejected"),
    FUTURES_POSITION_IS_PROCESSING(32140, "futures position is processing"),
    OPEN_ORDER_EXISTS(32141, "open order exists"),
    OPEN_ORDER_NOT_ALLOWED(32142, "open order not allow"),
    CLOSE_ORDER_NOT_ALLOWED(32143, "close order not allow"),

    ORDER_REJECT_FUTURES_ORDER_PRICE_LESS_THAN_MIN_ASK_PRICE(32170, "order_price_less_than_min_ask_price"), // 合约卖单价格小于最小卖单价
    ORDER_REJECT_FUTURES_ORDER_PRICE_GREATER_THAN_MAX_BID_PRICE(32171, "order_price_greater_than_max_bid_price"), // 合约买单价格大于最大买单价
    ORDER_REJECT_FUTURES_ORDER_MARGIN_RATE_LESS_THAN_MIN_MARGIN_RATE(32172, "order_margin_rate_less_than_min_margin_rate"), // 委托保证金率小于最低要求
    ORDER_REJECT_FUTURES_ORDER_REDUCE_POSITION_BUT_OPEN(32173, "order_reduce_position_but_open"), // 减仓单却导致可能开仓被拒
    ORDER_REJECT_FUTURES_ORDER_SYSTEM_BALANCE_ERROR(32174, "order_system_balance_error"), // 系统账平异常
    ORDER_REJECT_FUTURES_ORDER_MARGIN_INSUFFICIENT(32175, "order_margin_insufficient"), // 保证金不足
    ORDER_REJECT_FUTURES_ORDER_OPEN_VALUE_INSUFFICIENT(32176, "order_open_value_insufficient"), // 开仓价值不足
    ORDER_REJECT_FUTURES_ORDER_REJECTED_OPEN_MARGIN_RATE(32177, "order_rejected_open_margin_rate"), // 超过开仓杠杆限制
    ORDER_REJECT_FUTURES_ORDER_MATCH_REJECTED(32178, "order_match_rejected"), // Match 系统原因拒绝
    ORDER_REJECT_FUTURES_ORDER_TIME_EXPIRED(32179, "order_time_expired"), // 订单时间过期

    ERR_REDUCE_MARGIN_FORBIDDEN(32201, "reduce margin is forbidden"),

    THIRD_USER_EXIST(32202, "Third user exist"),
    THIRD_TOKEN_EXIST(32203, "Third token exist"),
    BIND_THIRD_USER_ERROR(32204, "Bind third user error"),
    GET_THIRD_TOKEN_ERROR(32205, "GET third token error"),
    NO_PERMISSION_TO_CREATE_VIRTUAL_ACCOUNT(32206, "No permission to create a virtual account"),
    THIRD_USER_NOT_EXIST(32207, "Third user not exist"),

    ORDER_INVALID_PLAN_ORDER_TYPE(32301, "Invalid plan order type"),
    ORDER_SPL_REJECT(32302, "Create stop profit-loss plan order reject"),
    ORDER_SPL_NO_POSITION(32303, "No position"),
    ORDER_SPL_POSITION_NOT_ENOUGH(32304, "Position not enough"),
    ORDER_SPL_ERR_LONG_STOP_PROFIT_PRICE(32305, "Invalid long stop profit price"),
    ORDER_SPL_ERR_LONG_STOP_LOSS_PRICE(32306, "Invalid long stop loss price"),
    ORDER_SPL_ERR_SHORT_STOP_PROFIT_PRICE(32307, "Invalid short stop profit price"),
    ORDER_SPL_ERR_SHORT_STOP_LOSS_PRICE(32308, "Invalid short stop loss price"),
    CREATE_ORDER_ERROR_REACH_HOLD_LIMIT(32309, "token balance great/equals than order quantity"),

    ORDER_PLAN_NEW_LIMIT(32401, "Fail to create order: reach open trigger order limit of the currency pair."),
    ORDER_PLAN_TOTAL_BALANCE_LIMIT(32402, "Fail to create order: exceed the total balance of the coin."),
    ORDER_PLAN_ALLOW_LIMIT(32403, "the currency pair is not allowed to create trigger orders."),

    POSITION_NOT_EXISTS(32601, "position not exists"),
    TRANSFER_MARGIN_FAILED(32602, "transfer margin error"),
    POSITION_MARGIN_INSUFFICIENT(32603, "position margin insufficient"),

    /* balance and asset: 33 */
    INSUFFICIENT_BALANCE(33001, "Insufficient account balance"),
    USER_ACCOUNT_TRANSFER_FILLED(33002, "User asset internal transfer failed"),
    USER_ACCOUNT_LOCK_FILLED(33003, "User lock failed"),
    USER_ACCOUNT_UNLOCK_FILLED(33004, "User unlock failed"),
    USER_ACCOUNT_UNLOCK_AMOUNT_OVERSTEP_LIMIT(33005, "User unlock amount overstep limit"),
    USER_ACCOUNT_LOCK_TASK_STATUS_ILLEGAL(33006, "user account lock task status illegal"),
    ERROR_AMOUNT(33007, "invalid amount"),
    TRANSFER_INSUFFICIENT_BALANCE(33010, "Transfer Insufficient account balance"),
    MAPPING_SOURCE_INSUFFICIENT_BALANCE(33011, "Mapping source Insufficient account balance"),
    MAPPING_TARGET_INSUFFICIENT_BALANCE(33012, "Mapping target Insufficient account balance"),
    MAPPING_TRANSFER_FAILED(33013, "Mapping transfer failed"),
    BALANCE_TRANSFER_FAILED(33014, "Balance transfer failed"),
    BALANCE_BATCH_TRANSFER_FAILED(33015, "Balance Batch transfer failed"),
    TRANSFER_LIMIT_FAILED(33016, "Trigger transfer limit failed"),
    BALANCE_BATCH_LOCK_POSITION_FAILED(33020, "Balance batch Lock failed"),
    BALANCE_BATCH_UNLOCK_POSITION_FAILED(33021, "Balance batch unlock failed"),
    ERROR_SUB_BUSINESS_SUBJECT(33022, "Cannot find Sub BusinessSubject"),
    SUB_ACCOUNT_TRANSFER_FAILED(33023, "Sub accounts are not allowed to transfer in and out"),
    END_TIME_MUST_GREATER_CONFIG_TIME(33024, "End time must be greater than the configured time\n"),
    TRANSFER_SYMBOL_NOT_ALLOWED(33025, "transfer token not allow(or not in white list)"),
    ACCOUNT_TRANSFER_MUST_BE_MAIN_ACCOUNT(33026, "one of the accounts must be main account"),
    NO_SUB_ACCOUNT_AUTHORIZED_ORG_CONFIG(33027, "no account authorized org config"),
    NO_CENTRAL_COUNTER_PARTY_CONFIG(33028, "no Central Counter Party config"),
    NOT_AUTHORIZED_ACCOUNT(33029, "account not authorized"),
    HOBBIT_ACTIVITY_ACCOUNT_TRANSFERIN_FAILED(33030, "not allowed to transfer in"),
    HOBBIT_ACTIVITY_ACCOUNT_TRANSFEROUT_FAILED(33031, "not allowed to transfer out except USDT"),
    TRANSFER_AMOUNT_TOO_SMALL(33032, "amount is too small"),
    /* RedPacket: 34 **/
    RED_PACKET_INVALID_AMOUNT(34001, "invalid red packet amount"),
    RED_PACKET_AMOUNT_LESS_THAN_MIN(34002, "red packet amount less than min amount"),
    RED_PACKET_AMOUNT_GREAT_THAN_MAX(34003, "red packet amount great than max amount"),
    RED_PACKET_INVALID_TOTAL_COUNT(34004, "invalid red packet total count"),
    RED_PACKET_TOTAL_COUNT_LESS_THAN_MIN(34005, "red packet count less than 1"),
    RED_PACKET_TOTAL_COUNT_GREAT_THAN_MIN(34006, "red packet count great than max count"),
    RED_PACKET_INVALID_TOTAL_AMOUNT(34007, "invalid red packet total amount"),
    RED_PACKET_TOTAL_AMOUNT_LESS_THAN_MIN(34008, "red packet total amount less than min amount * total count"),
    RED_PACKET_TOTAL_AMOUNT_GREAT_THAN_MAX(34009, "red packet total amount great than max amount"),
    RED_PACKET_SEND_FAILED(34010, "send red packet failed"),
    RED_PACKET_RECEIVER_FAILED(34011, "receive red packet failed"),
    RED_PACKET_HAS_BEEN_OVER(34012, "red packet has been over"),
    RED_PACKET_HAS_BEEN_EXPIRED(34013, "red packet has been expired"),
    RED_PACKET_RECEIVE_CONDITION_NOT_MATCH(34014, "cannot match red packet condition"),
    RED_PACKET_INVALID_THEME(34015, "invalid red packet theme"),
    RED_PACKET_INVALID_TOKEN(34016, "invalid red packet token"),
    RED_PACKET_NOT_FOUND(34017, "cannot find red packet"),
    RED_PACKET_EMPTY_PASSWORD(34020, "please input red packet password"),
    RED_PACKET_ERROR_PASSWORD1(34021, "error red packet password 1 time"),
    RED_PACKET_ERROR_PASSWORD2(34022, "error red packet password 2 times"),
    RED_PACKET_ERROR_PASSWORD3(34023, "error red packet password 3 times"),
    RED_PACKET_ERROR_PASSWORD4(34024, "error red packet password 4 times"),
    RED_PACKET_ERROR_PASSWORD5(34025, "error red packet password 5 times"),
    RED_PACKET_ERROR_PASSWORD6(34026, "error red packet password 6 times"),

    /* deposit: 35 */
    DEPOSIT_NOT_ALLOW(35001, "Currently not allowed to charge"),

    /* withdraw: 36 */
    WITHDRAW_VERIFY_CODE_EXPIRED(36001, "The withdrawal request verification code is invalid"),
    WITHDRAW_VERIFY_CODE_ERROR(36002, "The withdrawal request verification code error"),
    WITHDRAW_NOT_ALLOW(36003, "Currently not allowed to withdraw"),
    WITHDRAW_ADDRESS_ILLEGAL(36004, "Wrong withdrawal address"),
    WITHDRAW_ADDRESS_CANNOT_BE_NULL(36005, "Withdrawal address cannot be null"),
    WITHDRAW_AMOUNT_ILLEGAL(36006, "Withdrawal amount illegal"),
    WITHDRAW_AMOUNT_CANNOT_BE_NULL(36007, "Withdrawal amount cannot be null"),
    WITHDRAW_AMOUNT_MAX_LIMIT(36008, "Withdraw amount exceeds the daily limit"),
    WITHDRAW_AMOUNT_MIN_LIMIT(36009, "Withdraw amount less than the min withdraw amount limit"),
    WITHDRAW_FAILED(36010, "Withdraw failed, maybe occurred a error"),
    UPDATE_TRADE_PWD_WITHDRAW_FROZEN(36011, "update trade pwd, withdraw frozen"),
    UNSUPPORTED_CONTRACT_ADDRESS(36012, "not support contract address"),
    WITHDRAW_NEED_SENIOR_VERIFY(36013, "withdraw need senior verify"),
    WITHDRAW_ORDER_CANNOT_BE_CANCELLED(36014, "withdraw order cannot be cancelled"),
    WITHDRAW_ADDRESS_NOT_IN_WHITE_LIST(36015, "withdraw address not in whitelist"),

    /* point-card 38 */
    // 非法的请求
    POINT_CARD_ILLEGAL_REQUEST(38001, "Illegal request"),
    // 点卡套餐已售罄
    POINT_CARD_SOLD_OUT(38002, "Point card package is sold out"),
    // 用户购买额度不足
    POINT_CARD_USER_LIMIT_INSUFFICIENT(38003, "Insufficient user purchase limit"),
    // 购买失败
    POINT_CARD_PURCHASE_FAIL(38005, "Failed purchase"),
    // F码不存在
    POINT_CARD_FCODE_NOT_FOUND(38006, "`F code` does not exist"),
    // F码已使用
    POINT_CARD_FCODE_HAS_USED(38007, "`F code has been used"),
    // F码已使用
    POINT_CARD_FCODE_HAS_USED_BY_SELF(38011, "`F code has been used by you"),
    // 点卡价格获取失败
    POINT_CARD_PRICE_UNKNOWN(38008, "Point card price acquisition failed"),
    // 点卡套餐不存在
    POINT_CARD_PACK_NOT_FOUND(38009, "Point card package does not exist"),
    // 点卡套餐已下架
    POINT_CARD_PACK_NOT_IN_SHELF(38010, "Point card package has been removed"),
    // 点卡套餐还未开售
    POINT_CARD_PACK_NOT_START(38011, "Point card package has not start."),

    POINT_CARD_PACK_OUT_LIMIT(38012, "Exceeded Purchase Limit"),
    // 点卡后台，非法请求
    POINT_CARD_ADMIN_ILLEGAL_REQUEST(38501, "Illegal request"),

    /* other: 39 */
    TOKEN_NOT_FOUND(39001, "Token does not exist"),

    /* otc 4xxxx */
    PARAM_ERROR(40001, "Request parameter verification error"),
    /**
     * PERMISSION_DENIED = 40002; //无操作权限 SYS_ERROR = 40003; //无操作权限 TOKEN_ERROR = 40004;
     * //数字货币币种错误，不支持该币种 CURRENCY_ERROR = 40005; //法币币种错误，不支持该币种 SYMBOL_ERROR=40006;//币对错误，不支持该币对
     */
    OTC_PERMISSION_DENIED(40002, "Permission denied"),
    OTC_SYS_ERROR(40003, "System error"),
    OTC_TOKEN_ERROR(40004, "Invalid token"),
    OTC_CURRENCY_ERROR(40005, "Invalid currency"),
    OTC_SYMBOL_ERROR(40006, "Invalid symbol"),

    /*  账户  */
    OTC_ACCOUNT_NOT_EXIST(41000, "Account does not exist"), //账户不存在
    OTC_NICK_NAME_NOT_SET(41001, "Please set your personal information before proceeding OTC"), //昵称未设置，未在OTC开户
    OTC_BALANCE_NOT_ENOUGH(41002, "Insufficient Balance"),
    OTC_NICK_NAME_EXIST(41003, "Nick name already exists"), //昵称已存在

    /*  广告  */
    OTC_ITEM_NOT_EXIST(41100, "The AD has been removed. Please select another AD"), //广告不存在
    OTC_LAST_QUANTITY_NOT_ENOUGH(41101, "The remaining amount of the ad is insufficient, please re-select"), //广告剩余单量不足
    OTC_ITEM_CANNOT_TRADE(41102, "The AD has been removed. Please select another AD"), //广告不在可交易状态（广告已下架）
    OTC_UN_FINISHED_ITEM(41103, "The same type of ad order has not finished, the new ad cann't be post yet."), //有未完结广告单

    /*  订单  */
    OTC_ORDER_NOT_EXIST(41200, ""), //订单不存在
    OTC_PAYMENT_NOT_SUPPORT(41201, ""), //不支持该付款方式
    CAN_NOT_EXCHANGE_SELF(41202, "Can't trade with your own ads"),

    /*  付款方式  */
    OTC_PAYMENT_NOT_EXIST(41300, "Be sure to use your real-name account and activate at least one payment method."), //付款方式不存在
    OTC_PAYMENT_DOES_NOT_MATCH(41302, "User placed an order, but payment type does not match OTC merchant payment type"),//付款方式不匹配
    /* 其他认证 */
    OTC_KYC_VERIFY(42000, "For your account security, please carry out legal currency operation after KYC"),

    OTC_ORDER_FAILED(42001, "Order Failed"),
    OTC_PAY_FAILED(42002, "Payment Fail"),
    OTC_APPEAL_FAILED(42003, "Complaint Failed"),
    SET_TRADE_PWD_AND_NICKNAME(42004, "HI, you need to set a nickname and a trade password before trading. "),
    TRADE_PASSWORD_VERIFY_FAILED(42005, "Trade password error"),
    OTC_CANCEL_ORDER_MAX_TIMES(42006, "otc cancel order max times"),
    BIND_CARD_ERROR(42007, "bind card failed"),
    OTC_ERROR_CODE3(42008, ""),
    OTC_ERROR_CODE4(42009, ""),
    OTC_ERROR_CODE5(42010, ""),
    OTC_ERROR_CODE6(42011, "Illegal Message Type"),
    OTC_ERROR_CODE7(42012, ""),
    OTC_ERROR_CODE8(42013, ""),
    OTC_ERROR_CODE9(42014, ""),
    OTC_ERROR_CODE10(42015, ""),
    OTC_ERROR_CODE11(42016, ""),
    OTC_ERROR_CODE12(42017, ""),
    OTC_CROSS_EXCHANG_FORBIDDEN(42018, "Could not cross exchange"),
    IN_WITHDRAW_BLACK_LIST(42019, "In withdraw black list"),
    MAX_ORDER_LIMIT(42020, "Max order limit"),
    SELL_HIGHER_THAN_INDEX(42021, "Sell higher than index"),
    BUY_LOWER_THAN_INDEX(42022, "Buy lower than index"),
    MERCHANT_ORDER_LIMIT(42023, "Merchant order limit"),//不同券商之间商家不允许交易
    USER_BUY_ORDER_USDT_DAY_LIMIT(42024, "User buy order usdt day limit"),//普通用户买币单日USDT最大限制
    NON_PERSON_PAYMENT_LIMIT(42025, "Non-person payment method"),//非本人支付方式限制
    NOT_SUPPORT_WECHAT_PAYMENT_LIMIT(42026, "Not support wechat payment limit"),//不支持微信支付
    MUST_HAVE_REAL_NAME_PAYMENT_LIMIT(42027, "Must have a real-name payment method"),//必须有本人实名的支付方式
    RISK_CONTROL_INTERCEPTION_LIMIT(42028, "Risk control interception limit"),//风控禁止出金
    BROKER_RISK_CONTROL_INTERCEPTION_LIMIT(42029, "Broker risk control interception limit"),//风控禁止出金
    OTC_INSTITUTIONAL_USER_LIMIT(42030, "OTC Service not available for institutional investors"),

    /**
     * payment
     */
    PAYMENT_PAY_FAILED(43001, "order pay failed, please check"),
    CLIENT_ORDER_ID_EXCEED_LENGTH_LIMIT(43002, "client order id exceed length limit"),
    ORDER_DESC_EXCEED_LENGTH_LIMIT(43003, "order desc exceed length limit"),
    EXTEND_INFO_EXCEED_LENGTH_LIMIT(43004, "order extend info exceed length limit"),
    PAYMENT_CANNOT_SKIP_2FA(43005, "pay request cannot skip 2fa"),

    /**
     * guild
     */
    INVALID_GUILD_STATUS(51000, "Guild status invalid"),
    GUILD_NAME_EXIST(51001, "Guild name already exists"),
    GUILD_FUNDER_NICKNAME_EXIST(51002, "Funder nickname already exists"),
    INVALID_MEMBER_STATUS(51010, "Member status invalid"),
    MEMBER_NICKNAME_EXIST(51011, "Nickname already exists"),
    MEMBER_NOT_EXIST(51012, "Member does not exist"),
    INVALID_MEMBER(51013, "Guild hasn't this member"),
    INVALID_NICKNAME(51014, "Incorrect nickname"),
    INVALID_GUILD_NAME(51015, "Incorrect guild name"),
    INVALID_NICKNAME_LENGTH(51016, "invalid nickname length"),
    INVALID_GUILD_NAME_LENGTH(51017, "invalid guild name length"),
    DUPLICATED_REGISTER_REQUEST(51020, "Duplicate register request"),
    REGISTER_RELATION_EXIST(51021, "Register relation already exists"),
    REGISTER_RELATION_FAILED(51022, "Register relation fail"),
    REGISTER_FAILED(51023, "Register fail"),
    CURRENT_BLOCK_NOT_EXIST(51030, "Current Block or latest block does not exist"),
    NEW_BLOCK_INVALID(51031, "New block is invalid"),
    COMPLETE_NEW_BLOCK_FAILED(51032, "Update new block failed"),
    COMMENT_TEXT_EMPTY(51033, "Content cannot be empty"),
    COMMENT_TEXT_EXCEED_LENGTH(51034, "Content is too long"),
    FEED_TEXT_EMPTY(51035, "Content cannot be empty"),
    FEED_TEXT_EXCEED_LENGTH(51036, "Content is too long"),
    CONTENT_NOT_EXIST(51037, "Content does not exist"),
    INCORRECT_PARAMETER(51038, "The parameter is incorrect"),
    ILLEGAL_REQUEST(51039, "Illegal request"),
    FORBIDDEN_TO_POST_CARD(51040, "User is forbidden to post"),
    FORBIDDEN_TO_POST_COMMENT(51041, "User is forbidden to comment"),
    UPLOAD_IMAGE_EXCEED_MAX_SIZE(51042, "Upload image exceed max size"),
    UPLOAD_IMAGE_EXCEED_MAX_COUNT(51043, "Upload image exceed max count"),
    EXCHANGE_RATE_NOT_EXIST(51044, "Exchange rate does not exist"),
    NAME_TOO_SHORT(51045, "name too short"),
    POST_FREQUENTLY(51046, "post frequently"),
    POST_TOO_MUCH(51047, "too much post"),


    /**
     * activity  start -->520
     */

    ACTIVITY_DRAW_CHANCE_EMPTY(52001, "Activity draw has no chance."),
    ACTIVITY_DRAW_ITEM_NOT_EXIST(52002, "Activity item is null."),
    ACTIVITY_DRAW_ITEM_OUT_LIMIT(52002, "Activity item done count out limit count."),
    ACTIVITY_DRAW_SHARE_TICKET_INVALID(52003, "Activity share ticket is invalid."),
    ACTIVITY_INVITE_ACTIVITY_NOT_EXIST(52004, "Invite activity not exsit"),
    ACTIVITY_INVITE_LEVEL_NOT_EXIST(52005, "Invite level not exsit"),
    ACTIVITY_FAIL_IN_USER_LEVEL_REQUIREMENT(52006, "Fail in user Level requirement"),
    ACTIVITY_FAIL_IN_PHONE_BINDING_REQUIREMENT(52007, "Fail in phone-binding requirement"),
    ACTIVITY_FAIL_IN_KYC_LEVEL_REQUIREMENT(52008, "Fail in KYC level requirement"),
    ACTIVITY_FAIL_IN_TOKEN_HOLDING_REQUIREMENT(52009, "Fail in token-holding requirement"),
    ACTIVITY_FAIL_IN_AVERAGE_TOKEN_HOLDING_REQUIREMENT(52010, "Fail in average token-holding requirement"),
    CANNOT_REPURCHASE(52011, "Cannot repurchase"),
    EXCEED_MAXIMUM_REPURCHASE_QUANTITY(52012, "Exceed the maximum repurchase quantity"),
    REPURCHASE_COMPLETED(52013, "Repurchase completed"),
    REPURCHASE_END(52014, "Repurchase end"),

    /*
     * Finance
     */
    FINANCE_PRODUCT_NOT_EXIST(53001, "Finance product not exist."),
    FINANCE_USER_LIMIT_OUT(53002, "Out of finance user limit."),
    FINANCE_DAILY_LIMIT_OUT(53003, "Out of finance daily limit."),
    FINANCE_TOTAL_LIMIT_OUT(53004, "Out of finance total limit."),
    FINANCE_PURCHASE_RECORD_NOT_EXIST(53005, "Finance purchase record not exist"),
    FINANCE_PURCHASE_RECORD_STATUS_ERROR(53006, "Finance purchase record status error"),
    FINANCE_INSUFFICIENT_BALANCE(53007, "Finance balance not enough"),
    FINANCE_REDEEM_RECORD_NOT_EXIST(53008, "Finance redeem record not exist"),
    FINANCE_REDEEM_RECORD_STATUS_ERROR(53009, "Finance redeem record status error"),
    FINANCE_TRADE_AMOUNT_SO_SMALL(53010, "Finance trade amount less than min amount."),
    FINANCE_NOT_OPEN(53011, "Finance trade not open."),
    FINANCE_BALANCE_FLOW_NOT_FOUND(53012, "balance record not found"),
    FINANCE_GET_WALLET_ASSET_ERROR(53013, "get finance wallet asset error"),
    FINANCE_GET_BALANCE_FLOW_ERROR(53014, "get finance balance flow error"),
    FINANCE_GET_PURCHASE_RECORD_ERROR(53015, "get purchase record error"),
    FINANCE_GET_REDEEM_RECORD_ERROR(53016, "get redeem record error"),
    FINANCE_PURCHASE_ERROR(53017, "finance purchase error"),
    FINANCE_REDEEM_ERROR(53018, "finance redeem error"),
    FINANCE_ILLEGAL_PURCHASE_AMOUNT(53019, "finance purchase amount is illegal"),
    FINANCE_ILLEGAL_REDEEM_AMOUNT(53020, "finance redeem amount is illegal"),
    FINANCE_PRODUCT_STOP_PURCHASE(53021, "stop purchase product"),
    FINANCE_PRODUCT_STOP_REDEEM(53022, "stop redeem product"),
    FINANCE_PURCHASE_INSUFFICIENT_BALANCE(53023, "purchase amount great than account available asset"),
    FINANCE_REDEEM_INSUFFICIENT_BALANCE(53024, "redeem amount great than finance available asset"),
    FINANCE_PURCHASE_AMOUNT_TOO_SMALL(53025, "purchase amount too small"),
    FINANCE_REDEEM_AMOUNT_TOO_SMALL(53026, "redeem amount too small"),

    /**
     * staking start
     */
    STAKING_PROCESSING(53800, "In the processing"),
    STAKING_SUBSCRIBE_ACCOUNT_CONFLICT(53801, "Configuration Conflict between subscription account and Bitmore account"),
    STAKING_TRANSFER_ID_ILLEGAL(53802, "Invalid transferId"),
    STAKING_MQ_SEND_MESSAGE_FAILED(53803, "Subscription message failed, please try again later"),
    STAKING_REDEEM_TRANSFER_WAIT(53805, "Redeeming application submitted, your assets will be transferred into your wallet account within 24 hours, please check then."),


    /**
     * option  start -->530
     */

    OPTION_NOT_EXIST(53001, "Option not exsit."),
    OPTION_HAS_EXPIRED(53002, "The option has expired."),

    /**
     * oauth
     */
    OAUTH_INVOKE_ERROR(60000, "oauth request occurred a error"),
    OAUTH_PARAM_MISSING(60001, "param is missing"),
    OAUTH_PARAM_INVALID(60002, "param value is illegal"),
    OAUTH_APP_NOT_FOUNT(60003, "app not found"),
    OAUTH_APP_OFFLINE(60004, "app is offline"),
    OAUTH_CODE_ILLEGAL(60005, "code is illegal"),
    OAUTH_CODE_EXPIRED(60006, "code is expired"),
    OAUTH_GET_CODE_ERROR(60007, "get code error"),
    OAUTH_CODE_REUSE(60008, "code repeated request"),
    OAUTH_ACCESS_TOKEN_ILLEGAL(60009, "access_token is illegal"),
    OAUTH_ACCESS_TOKEN_EXPIRED(60010, "access_token is expired"),
    OAUTH_ACCESS_TOKEN_DISABLED_BY_USER(60011, "access_token disabled by user"),
    OAUTH_ACCESS_TOKEN_DISABLED_BY_CHANGE_PWD(60012, "access_token disabled by user change pwd"),
    OAUTH_ACCESS_TOKEN_DISABLED_BY_USER_STATUS_CHANGE(60013, "access_token disabled by user status change"),
    OAUTH_ACCESS_TOKEN_DISABLED_BY_APP_STATUS_CHANGE(60014, "access_token disabled by app status is disable"),
    OAUTH_GET_ACCESS_TOKEN_ERROR(60015, "get access_token error"),
    OAUTH_REQUEST_FORBIDDEN(60016, "user is not authorized to use"),
    OAUTH_REDIRECT_URL_ILLEGAL(60017, "redirect url is illegal"),
    OAUTH_GET_APP_OPEN_FUNCTION_ERROR(60018, "get app open functions failed"),
    OAUTH_REQUEST_HAS_NO_PERMISSION(60019, "User did not authorize the request"),
    OAUTH_CHECK_OAUTH_REQUEST_ERROR(60020, "check oauth request error"),
    OAUTH_RESPONSE_TYPE_ILLEGAL(60030, "response type is null or illegal"),
    OAUTH_GRANT_TYPE_ILLEGAL(60031, "grant type is null or illegal"),
    OAUTH_CLIENT_ID_IS_NULL(60032, "client_id is null"),
    OAUTH_CLIENT_SECRET_IS_NULL(60033, "client_secret is null"),
    OAUTH_AUTHORIZATION_CODE_IS_NULL(60034, "authorization code is null"),

    /**
     * hobbit
     */
    HOBBIT_IS_VOTED(70001, "Voted."),
    HOBBIT_NON_VOTING(70002, "Non-voting"),
    END_VOTING(70003, "End of voting"),
    WRONG_PURCHASE_AMOUNT(70004, "Wrong purchase amount"),//申购金额错误
    REDUCTION_SHARES_PENDING_CALCULATION(70005, "Reduction of shares pending calculation"),//减持待计算
    END_REDUCTION(70006, "End of reduction"),//减持结束
    REPEAT_REDUCTION_OPERATION(70007, "Repeat the reduction operation"),//重复减持操作
    REDUCTION_LIMIT(70008, "Reduction limit"),//超出减持总量限制
    NOT_SOLD_OUT_LIMIT(70009, "Not sold out"),


    /**
     * margin
     */
    MARGIN_TOKEN_NOT_BORROW(80001, "token can not borrow"),
    MARGIN_LOAN_AMOUNT_TOO_BIG_OR_SMALL(80002, "loan amount is too big or small"),
    MARGIN_LOAN_AMOUNT_PRECISION_TOO_LONG(80004, "loan amount precision is too long"),
    MARGIN_LOAN_FAILED(80005, "loan failed"),
    MARGIN_LOAN_REPAY_AMOUNT_IS_SMALL(80006, "loan repay amount is small"),
    MARGIN_REPAY_FAILED(80007, "repay failed"),
    MARGIN_WITHDRAW_FAILED(80008, "margin withdraw failed"),
    MARGIN_GET_WITHDRAW_FAILED(80009, "margin get withdraw failed"),
    MARGIN_AVAIL_WITHDRAW_NOT_ENOUGH_FAILED(80010, "margin avail withdraw not enough failed"),
    MARGIN_GET_ALL_POSITION_FAILED(80011, "margin get all position failed"),
    MARGIN_SYMBOL_NOT_FOUND(80012, "margin symbol not found"),
    MARGIN_ACCOUNT_IS_FORCE_CLOSE(80013, "margin account is force close"),
    MARGIN_SYMBOL_NOT_TRADE(80014, "margin symbol not trade"),
    MARGIN_LOAN_ORDER_NOT_EXIST(80015, "loan order not exist"),
    MARGIN_DEFUALT_INTEREST_NOT_EXIST(80016, "margin default interest not exist"),
    MARGIN_CREATE_ACCOUNT_FAILED(80017, "create margin create account failed"),
    MARGIN_ORDER_PRICE_GREATER_LIMIT_FAILED(80018, "price greater than risk max limit price"),
    MARGIN_ORDER_PRICE_LESS_LIMIT_FAILED(80019, "price less than risk min limit price"),
    MARGIN_RISK_CONFIG_NOT_EXIT(80020, "risk config is not exit"),
    MARGIN_INDICES_NOT_EXIT(80021, "indices is not exit"),
    MARGIN_LATEST_PRICE_NOT_EXIT(80022, "latest price is not exit"),
    CHANGE_MARGIN_POSITION_STATUS_ERROR(80023, "change margin position status error"),
    IN_MARGIN_RISK_BLACK(80024, "In margin risk black list"),
    MARGIN_CONDITION_ORDER_EXECUTE_PRICE_RISKING(80025, "margin condition order execute price risking"),
    MARGIN_OPEN_ACTIVITY_RESUMBIT_ERROR(80026, "Please do not submit repeatedly"),
    MARGIN_RECALCULATION_PROFIT_LIMIT_ERROR(80027, "recalculation profit exceeds the limit of times"),
    NEED_TO_LOAN_ERROR(80028, " need to loan first"),
    MARGIN_SUBMIT_TIMES_LIMIT_ERROR(80029, "Submission times limit exceeded"),
    /**
     * convert 闪电兑付
     */
    CONVERT_SYMBOL_NOT_FOUND(90001, "convert symbol not found"),
    CONVERT_SYMBOL_PRICE_IS_NULL(90002, "convert symbol price is null"),
    CONVERT_SYMBOL_STATUS_FORBIDDEN(90003, "convert symbol status is forbidden"),
    CONVERT_PURCHASE_INSUFFICIENT_BALANCE(90004, "purchase amount great than user account available asset"),
    CONVERT_OFFERINGS_INSUFFICIENT_BALANCE(90005, "offerings amount great than broker account available asset"),
    CONVERT_ORDER_AMOUNT_OVER_ACCOUNT_DAILY_LIMIT(90006, "order amount great than account daily limit"),
    CONVERT_ORDER_AMOUNT_OVER_ACCOUNT_TOTAL_LIMIT(90007, "order amount great than account total limit"),
    CONVERT_ORDER_AMOUNT_OVER_SYMBOL_DAILY_LIMIT(90008, "order amount great than symbol daily limit"),
    CONVERT_OFFERINGS_TOKEN_NOT_FOUND(90009, "convert offerings token not found"),
    CONVERT_PURCHASE_TOKEN_NOT_FOUND(90010, "convert purchase token not found"),
    CONVERT_SYMBOL_IS_EXIST(90011, "convert symbol is exist"),

    /**
     * otcThirdParty otc第三方
     */
    OTC_THIRD_PARTY_NOT_FOUND(100001, "third party not found"),
    OTC_THIRD_PARTY_SYMBOL_NOT_FOUND(100002, "symbol not found"),
    OTC_THIRD_PARTY_WALLET_ADDRESS_NOT_FOUND(100003, "wallet address not found"),
    OTC_THIRD_PARTY_INSUFFICIENT_BALANCE(100004, "insufficient balance"),
    OTC_THIRD_PARTY_REQUEST_FAIL(100005, "third party request fail"),
    OTC_THIRD_PARTY_UNKNOWN_CONTROL_TYPE(100006, "unknown control type"),
    OTC_THIRD_PARTY_ORDER_NOT_FOUND(100007, "order not found"),
    OTC_THIRD_PARTY_WALLET_ADDRESS_INVALID(100008, "transfer address invalid"),
    OTC_THIRD_PARTY_TOKEN_AMOUNT_INVALID(100009, "transfer token amount invalid"),


    /**
     * hbc链投票
     */
    USER_ALREADY_HAVE_NODE(110001, "The user already has a node"),
    VOTE_NODE_NOT_FOUND(110002, "vote node not found"),
    VOTE_NODE_CAN_NOT_UNLOCK(110003, "vote node status can not unlock"),
    VOTE_NODE_NOT_HAVE_LOCK_AMOUNT(110004, "vote node does not have lock amount"),
    USER_VOTE_NOT_FOUND(110005, "user vote not found"),
    USER_VOTE_CAN_NOT_UNFROZEN(110006, "user vote can not unfrozen"),
    USER_VOTE_IS_EXIST(110007, "user vote is already exist"),
    LOCK_BALANCE_FAILED(110008, "lock balance failed"),
    UNLOCK_BALANCE_FAILED(110009, "unlock balance failed"),
    VOTE_NODE_NOT_BIND_USER(110010, "vote node not bind user"),
    ADMIN_NODE_CAN_NOT_UNLOCK(110011, "admin node can not unlock"),
    VOTE_NODE_LOCK_AMOUNT_TOO_SMALL(110012, "node lock amount too small"),
    VOTE_NODE_TYPE_ERROR(110013, "node type error"),

    /**
     * kyc合规报错信息
     */
    USER_COMPLIANCE_RECORD_NOT_EXIST(120001, "compliance record is not exist"),
    COMPLIANCE_STEP_CAN_NOT_SUBMIT(120002, "compliance step can not submit"),
    COMPLIANCE_CHECK_OCR_FAILED(120003, "compliance check ocr failed"),
    COMPLIANCE_CHECK_FACE_COMPARE_FAILED(120004, "compliance check face compare failed"),
    UNKNOWN_COMPLIANCE_TYPE(120005, "unknown compliance type"),
    COMPLIANCE_IS_NOT_OPEN(120006, "compliance is not open"),
    GET_ARES_ACCESS_TOKEN_FAILED(120007, "get ares access token failed"),
    SHAREHOLDER_IS_NOT_EXIST(120008, "shareholder is not exist"),
    AGE_YOUNGER_THAN_18(120009, "user younger than 18 years old"),
    AGE_BE_OVER_75(120010, "user be over 75 years old"),
    AMERICAN_CITIZEN(120011, "The user cannot be a U.S. citizen"),
    LIVE_IN_AMERICAN(120012, "The user cannot live in the U.S."),
    ONLY_CARD_ID_KYC(120013, "Singapore citizens can only use ID card for KYC"),
    ONLY_PASSPORT_KYC(120014, "Non-Singapore citizens can only use passports for KYC"),
    ;

    private final int code;

    private final String msg;

    BrokerErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static BrokerErrorCode fromCode(int errorCode) {
        for (BrokerErrorCode brokerError : BrokerErrorCode.values()) {
            if (brokerError.code == errorCode) {
                return brokerError;
            }
        }
        return null;
    }

    public int code() {
        return code;
    }

    public String sCode() {
        return String.valueOf(code);
    }

    public String msg() {
        return msg;
    }

    @Override
    public String toString() {
        return "{"
                + "code:" + this.code
                + ", message:" + this.msg
                + "}";
    }
}


