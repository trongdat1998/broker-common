/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.grpc.log
 *@Date 2018/7/13
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.grpc.client.aspect;

import com.google.common.base.Strings;
import com.google.gson.*;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.TextFormat;
import io.bhex.broker.common.exception.BrokerException;
import io.bhex.broker.common.grpc.client.annotation.GrpcLog;
import io.bhex.broker.common.grpc.client.annotation.NoGrpcLog;
import io.bhex.broker.common.util.JsonUtil;
import io.bhex.broker.core.exception.V3BrokerException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Slf4j
public class GrpcLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //    @Pointcut("(@within(io.bhex.broker.common.grpc.client.annotation.GrpcLog) || @annotation(io.bhex.broker.common.grpc.client.annotation.GrpcLog))" +
//            "&& !@annotation(io.bhex.broker.common.grpc.client.annotation.NoGrpcLog)")
    @Pointcut("@within(io.bhex.broker.common.grpc.client.annotation.GrpcLog) || @annotation(io.bhex.broker.common.grpc.client.annotation.GrpcLog)")
    public void grpcInvoke() {
    }

    @Before("grpcInvoke()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "grpcInvoke()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            JsonObject logJson = getCommonData(joinPoint);
            if (method.getAnnotation(NoGrpcLog.class) != null) {
                return;
            } else {
                GrpcLog grpcLog = method.getAnnotation(GrpcLog.class);
                if (grpcLog != null && grpcLog.printNoResponse()) {
                    logJson.addProperty("success", "response data need not print");
                } else {
                    if (result instanceof MessageOrBuilder) {
                        try {
                            logJson.addProperty("success", JsonUtil.defaultProtobufJsonPrinter().print((MessageOrBuilder) result));
                        } catch (Exception e) {
                            logJson.addProperty("success", "invalid proto message");
                        }
                    } else {
                        logJson.addProperty("success", JsonUtil.defaultGson().toJson(result));
                    }
                }
            }
            logJson.addProperty("duration", System.currentTimeMillis() - startTime.get());
            log.info(JsonUtil.defaultGson().toJson(logJson));
        } catch (Exception e) {
            // ignore
        }
    }

    @AfterThrowing(pointcut = "grpcInvoke()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            JsonObject logJson = getCommonData(joinPoint);
            if (method.getAnnotation(NoGrpcLog.class) != null) {
                return;
            } else {
                String errorMessage = throwable.getMessage();
                if (throwable instanceof BrokerException) {
                    // handle BrokerException
                    logJson.addProperty("code", ((BrokerException) throwable).code());
                } else if (throwable.getCause() != null && throwable.getCause() instanceof BrokerException) {
                    // handle cause is BrokerException
                    logJson.addProperty("code", ((BrokerException) throwable.getCause()).code());
                } else if (throwable instanceof V3BrokerException) {
                    // handle V3BrokerException
                    logJson.addProperty("code", ((V3BrokerException) throwable).getCode());
                } else if (throwable.getCause() != null && throwable.getCause() instanceof V3BrokerException) {
                    // handle cause is V3BrokerException
                    logJson.addProperty("code", ((V3BrokerException) throwable.getCause()).getCode());
                }
                if (throwable.getCause() != null) {
                    errorMessage = "[message:" + errorMessage + (", Cause:"
                            + Strings.nullToEmpty(throwable.getCause().getMessage())) + "]";
                }
                logJson.addProperty("error", errorMessage);
            }
            logJson.addProperty("duration", System.currentTimeMillis() - startTime.get());
            if (throwable instanceof BrokerException || throwable instanceof V3BrokerException) {
                log.warn(JsonUtil.defaultGson().toJson(logJson));
            } else {
                log.error(JsonUtil.defaultGson().toJson(logJson));
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private JsonObject getCommonData(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        JsonObject logJson = new JsonObject();
        logJson.addProperty("methodName", signature.getDeclaringTypeName() + "#" + signature.getName());
        logJson.add("arguments", addMethodArgsToJsonLog(joinPoint.getArgs()));
        return logJson;
    }

    private JsonElement addMethodArgsToJsonLog(Object[] args) {
        if (args == null || args.length == 0) {
            return JsonNull.INSTANCE;
        } else if (args.length == 1) {
            String message = "";
            if (args[0] instanceof MessageOrBuilder) {
                message = TextFormat.shortDebugString((MessageOrBuilder) args[0]);
            } else {
                message = Strings.nullToEmpty(args[0].toString());
            }
            return new JsonPrimitive(message);
        } else {
            JsonArray jsonArray = new JsonArray();
            for (Object arg : args) {
                if (arg == null) {
                    continue;
                }
                if (arg instanceof MessageOrBuilder) {
                    try {
                        jsonArray.add(TextFormat.shortDebugString((MessageOrBuilder) arg));
                    } catch (Exception e) {
                        // ignore
                    }
                } else {
                    jsonArray.add(Strings.nullToEmpty(arg.toString()));
                }
            }
            return jsonArray;
        }
    }

}
