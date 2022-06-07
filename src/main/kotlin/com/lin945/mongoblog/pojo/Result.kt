package com.lin945.mongoblog.pojo

/**
 * 统一异常处理类
 */
data class Result<T>(
    var data: T?,
    var message: String = "ok",
    var code: Int = 200
) {
    companion object {
        public fun <T> ok(): Result<T> {
            return Result<T>(null)
        }
        public fun <T> ok(data:T): Result<T> {
            return Result<T>(data)
        }

        public fun fail(message: String): Result<Unit> {
            return Result<Unit>(null)
        }

        public fun fail(enum: CodeConfig): Result<Unit> {
            return Result<Unit>(data = null, message = enum.message, code = enum.code)
        }
    }
}

enum class CodeConfig(val code: Int, val message: String) {
    ERROR(100, "失败"),
    PARAM_ERROR(101, "参数错误"),
    TOKEN_NOTNULL(102, "身份信息不能为空"),
    SFM_JYM_ERROR(
        103,
        "身份信息有误"
    ),
    PERMISSION_DENIED(105, "权限不足"),
    SUCCESS(200, "成功"),
    FUNCTION_NOT_OPEN(400, "功能未开放"),
    ACCESS_LIMIT(
        401,
        "请求次数频繁"
    ),
    ERROR_REQUEST_TYPE(405, "请求方法错了"),
    SERVER_BUSY(500, "服务器繁忙"),
    ERROR_LOGIN(10003, "无此登陆方式"),
    CACHE_ERROR(
        10004,
        "缓存过期请重新发送验证码"
    ),
    CODE_ERROR(10005, "验证码错误！"),
    ERROR_LENGTH(10009, "账户位数错误"),
    ERROR_PASSWORD(10010, "密码错误"),
    ERROR_NAME(
        10011,
        "暂无此账号信息"
    ),
    ERROR_SERIALIZE(10012, "序列化失败"),
    ERROR_DESERIALIZE(10013, "反序列化失败"),
    ERROR_USER_EXISTS(
        10014,
        "已存在账号信息"
    ),
    ERROR_IMG_ERROR(10015, "图片违规"),
    ERROR_REPORT(10016, "你已经举报过了"),  /*缓存服务器异常*/
    REDIS_MISS(20000, "缓存服务器异常"),
    ERROR_OPENID(20004, "获取用户openid失败"),
    ERROR_LOST_UPDATE(
        30000,
        "修改信息的不存在或已经找到了"
    ),
    ERROR_MAX_FileUpload(50000, "图片过大大小超过1M"),
    ERROR_REFUSE_FileUpload(50001, "文件类型不符合"),  /*微信解码异常*/
    ERROR_NOT_INFO(60000, "错误，没有解码数据"),
    ERROR_DECODE(60001, "解码失败: 参数已失效!");
}