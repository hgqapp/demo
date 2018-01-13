package com.example.demo.bean;

import java.util.UUID;

/**
 * @author houguangqiang
 * @date 2018-01-11
 * @since 1.0
 */
public class UploaderCreatedFile {

    private int index;
    private UUID fileId;
    private String error;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
