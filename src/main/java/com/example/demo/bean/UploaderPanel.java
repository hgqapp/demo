package com.example.demo.bean;

import java.util.List;

/**
 * @author houguangqiang
 * @date 2018-01-10
 * @since 1.0
 */
public class UploaderPanel {

    private Long chunkSize;
    private List<UploaderPendingFile> pendingFiles;

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public List<UploaderPendingFile> getPendingFiles() {
        return pendingFiles;
    }

    public void setPendingFiles(List<UploaderPendingFile> pendingFiles) {
        this.pendingFiles = pendingFiles;
    }
}
