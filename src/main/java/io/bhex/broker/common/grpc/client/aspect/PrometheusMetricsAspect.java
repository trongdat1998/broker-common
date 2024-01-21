/**********************************
 *@项目名称: broker-parent
 *@文件名称: io.bhex.broker.grpc.aspect
 *@Date 2018/7/15
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.common.grpc.client.aspect;

import io.bhex.broker.common.exception.BrokerException;
import io.prometheus.client.Histogram;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class PrometheusMetricsAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final double[] SERVICE_TIME_BUCKETS = new double[]{
            5.0, 10.0, 25.0, 50, 100, 200, 500, 1000, 2000, 8000
    };

//    private final Counter REQUEST_COUNTER = Counter.build()
//            .namespace("broker")
//            .subsystem("grpc_client")
//            .name("request_total")
//            .labelNames("server_name", "method_name")
//            .help("Total number of grpc_client request")
//            .register();
//    private final Summary SUMMARY_LATENCY_MILLISECONDS = Summary.build()
//            .namespace("broker")
//            .subsystem("grpc_client")
//            .name("summary_latency_milliseconds")
//            .labelNames("server_name", "method_name", "result")
//            .help("Summary of grpc_client response latency (in milliseconds) for completed invoke.")
//            .register();
    private final Histogram HISTOGRAM_LATENCY_MILLISECONDS = Histogram.build()
            .namespace("broker")
            .subsystem("grpc_client")
            .name("histogram_latency_milliseconds")
            .labelNames("server_name", "method_name")
            .help("Histogram of grpc_client response latency (in milliseconds) for completed invoke.")
            .buckets(SERVICE_TIME_BUCKETS)
            .register();

    public PrometheusMetricsAspect() {
    }

    @Pointcut("@within(io.bhex.broker.common.grpc.client.annotation.PrometheusMetrics) || @annotation(io.bhex.broker.common.grpc.client.annotation.PrometheusMetrics)")
    public void grpcInvoke() {
    }

    @Before("grpcInvoke()")
    public void doBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classShortName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        startTime.set(System.currentTimeMillis());

//        this.REQUEST_COUNTER.labels(className, classShortName + "." + methodName).inc();
    }

    @AfterReturning(pointcut = "grpcInvoke()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classShortName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        double latency = (System.currentTimeMillis() - startTime.get());

//        this.SUMMARY_LATENCY_MILLISECONDS.labels(className, classShortName + "." + methodName, "Success:0").observe(latency);
        this.HISTOGRAM_LATENCY_MILLISECONDS.labels(className, classShortName + "." + methodName).observe(latency);
    }

    @AfterThrowing(pointcut = "grpcInvoke()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String classShortName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        double latency = (System.currentTimeMillis() - startTime.get());
        String[] labelValues;
        if (throwable instanceof BrokerException) {
            labelValues = new String[]{classShortName, classShortName + "." + methodName, "BrokerException:" + ((BrokerException) throwable).getCode()};
        } else if (throwable.getCause() != null && throwable.getCause() instanceof BrokerException) {
            labelValues = new String[]{classShortName, classShortName + "." + methodName, "BrokerException:" + ((BrokerException) throwable.getCause()).getCode()};
        } else {
            labelValues = new String[]{classShortName, classShortName + "." + methodName, "Exception:" + throwable.getClass().getName()
                    + (throwable.getCause() == null ? "" : ", \nCause:" + throwable.getCause().getClass().getName())};
        }
//        this.SUMMARY_LATENCY_MILLISECONDS.labels(labelValues).observe(latency);
        this.HISTOGRAM_LATENCY_MILLISECONDS.labels(classShortName, classShortName + "." + methodName).observe(latency);
    }

}
