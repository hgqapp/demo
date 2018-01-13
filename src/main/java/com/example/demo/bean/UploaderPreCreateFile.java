package com.example.demo.bean;

/**
 * @author houguangqiang
 * @date 2018-01-11
 * @since 1.0
 */
public class UploaderPrepare {

    private String fileName;
    private long fileSize;
    private String digest;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
