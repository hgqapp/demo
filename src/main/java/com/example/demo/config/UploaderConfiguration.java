package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author houguangqiang
 * @date 2018-01-06
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "uploader")
public class UploaderConfiguration {

    private static final String DEFAULT_DIR = "/temp/uploader";

    /** 分片大小，默认 1024*1024*4 */
    private long chunkSize = 1024 * 1024 * 4L;
    /** 临时文件存储目录 */
    private String dir = DEFAULT_DIR;
    /** 是否是相对目录，默认true */
    private boolean dirRelative = true;
    /** 每个客户端的速率控制，单位秒/字节，默认 1024 * 1024 * 10 */
    private long limitRate = 1024 * 1024 * 10L;
    /** 速率控制张总线，单位秒/字节，默认 1024 * 1024 * 20 */
    private long limitTrafficRate = 1024 * 1024 * 20L;

    public long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public boolean isDirRelative() {
        return dirRelative;
    }

    public void setDirRelative(boolean dirRelative) {
        this.dirRelative = dirRelative;
    }

    public long getLimitRate() {
        return limitRate;
    }

    public void setLimitRate(long limitRate) {
        this.limitRate = limitRate;
    }

    public long getLimitTrafficRate() {
        return limitTrafficRate;
    }

    public void setLimitTrafficRate(long limitTrafficRate) {
        this.limitTrafficRate = limitTrafficRate;
    }
}
