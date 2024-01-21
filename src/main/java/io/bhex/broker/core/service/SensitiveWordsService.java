package io.bhex.broker.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.bhex.base.grpc.client.channel.IGrpcClientPool;
import io.bhex.broker.core.domain.BrokerCoreConstants;
import io.bhex.broker.grpc.basic.BasicServiceGrpc;
import io.bhex.broker.grpc.basic.GetAllSensitiveWordsRequest;
import io.bhex.broker.grpc.basic.GetAllSensitiveWordsResponse;
import io.bhex.broker.grpc.basic.SensitiveWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author lizhen
 * @date 2018-12-04
 */
public class SensitiveWordsService {

    @Autowired
    private IGrpcClientPool pool;

    private Map<Integer, Set<String>> sensitiveWordsConfig = Maps.newHashMap();

    public enum SensitiveType {
        COMMON(0, "全局敏感词"),
        NICKNAME(1, "昵称类敏感词"),
        POST(2, "帖子评论类敏感词"),
        REMARK(3, "签名备注类敏感词");

        private int type;
        private String desc;

        SensitiveType(int type, String desc) {
            this.type = type;
            this.desc = desc;
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void refreshConfig() {
        BasicServiceGrpc.BasicServiceBlockingStub stub = BasicServiceGrpc.newBlockingStub(
            pool.borrowChannel(BrokerCoreConstants.BROKER_SERVER_CHANNEL_NAME));
        GetAllSensitiveWordsResponse response = stub.getAllSensitiveWords(GetAllSensitiveWordsRequest.newBuilder()
            .build());
        List<SensitiveWords> sensitiveWordsList = response.getSensitiveWordList();
        if (CollectionUtils.isEmpty(sensitiveWordsList)) {
            sensitiveWordsConfig = Maps.newHashMap();
            return;
        }
        Map<Integer, Set<String>> configMap = Maps.newHashMap();
        for (SensitiveWords sensitiveWords : sensitiveWordsList) {
            Set<String> swSet = configMap.get(sensitiveWords.getType());
            if (swSet == null) {
                swSet = Sets.newHashSet();
                configMap.put(sensitiveWords.getType(), swSet);
            }
            swSet.add(sensitiveWords.getWords().toLowerCase());
        }
        sensitiveWordsConfig = configMap;
    }

    /**
     * 是否命中敏感词
     *
     * @param sensitiveType 敏感词类别
     * @param content       待过滤内容
     * @return
     */
    public boolean hitSensitiveWords(SensitiveType sensitiveType, String content) {
        content = content.toLowerCase();
        if (sensitiveType == null) {
            sensitiveType = SensitiveType.COMMON;
        }
        if (sensitiveType != SensitiveType.COMMON) {
            Set<String> commonSet = sensitiveWordsConfig.get(SensitiveType.COMMON.type);
            if (!CollectionUtils.isEmpty(commonSet)) {
                for (String s : commonSet) {
                    if (content.contains(s)) {
                        return true;
                    }
                }
            }
        }
        Set<String> swSet = sensitiveWordsConfig.get(sensitiveType.type);
        if (!CollectionUtils.isEmpty(swSet)) {
            for (String s : swSet) {
                if (content.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换敏感词
     *
     * @param sensitiveType 敏感词类别
     * @param content       待过滤内容
     * @return
     */
    public String replaceSensitiveWords(SensitiveType sensitiveType, String content) {
        String contentLower = content.toLowerCase();
        if (sensitiveType == null) {
            sensitiveType = SensitiveType.COMMON;
        }
        if (sensitiveType != SensitiveType.COMMON) {
            Set<String> commonSet = sensitiveWordsConfig.get(SensitiveType.COMMON.type);
            if (!CollectionUtils.isEmpty(commonSet)) {
                for (String s : commonSet) {
                    content = content.replaceAll("(?i)" + s, "*");
                }
            }
        }
        Set<String> swSet = sensitiveWordsConfig.get(sensitiveType.type);
        if (!CollectionUtils.isEmpty(swSet)) {
            for (String s : swSet) {
                content = content.replaceAll("(?i)" + s, "*");
            }
        }
        return content;
    }
}