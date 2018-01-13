package com.example.demo.store.entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author houguangqiang
 * @date 2018-01-06
 * @since 1.0
 */
public class FileStateEntity implements Serializable {

    private static final long serialVersionUID = -2118452944439641316L;


    private UUID fileId;
    private String fileName;
    private long fileSize;
    private long createTime;
    private String digest;

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public FileStateEntity fileId(UUID fileId){
        this.fileId = fileId;
        return this;
    }

    public FileStateEntity fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public FileStateEntity fileSize(long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public FileStateEntity createTime(long createTime) {
        this.createTime = createTime;
        return this;
    }

    public FileStateEntity digest(String digest) {
        this.digest = digest;
        return this;
    }

}
