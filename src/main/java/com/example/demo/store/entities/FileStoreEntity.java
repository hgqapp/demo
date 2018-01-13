package com.example.demo.store.entities;

import java.io.Serializable;

/**
 * @author houguangqiang
 * @date 2018-01-10
 * @since 1.0
 */
public class FileStoreEntity implements Serializable {

    private static final long serialVersionUID = -7279979208932373704L;

    private String absolutePath;
    private FileStateEntity fileState;

    public FileStoreEntity() {
    }

    public FileStoreEntity(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public FileStoreEntity(String absolutePath, FileStateEntity fileState) {
        this.absolutePath = absolutePath;
        this.fileState = fileState;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public FileStateEntity getFileState() {
        return fileState;
    }

    public void setFileState(FileStateEntity fileState) {
        this.fileState = fileState;
    }

    public FileStateEntity newFileState(){
        fileState = new FileStateEntity();
        return fileState;
    }
}
