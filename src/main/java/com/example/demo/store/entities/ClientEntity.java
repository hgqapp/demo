package com.example.demo.store.entities;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author houguangqiang
 * @date 2018-01-06
 * @since 1.0
 */
public class ClientEntity implements Serializable {

    private static final long serialVersionUID = 1663593985818148547L;

    private LinkedHashMap<UUID, FileStoreEntity> pendingFiles = new LinkedHashMap<>();

    public List<FileStoreEntity> getPendingFiles() {
        return pendingFiles.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public FileStoreEntity removePendingFile(UUID fileId){
        return pendingFiles.remove(fileId);
    }

    public void addPendingFile(FileStoreEntity pendingFile){
        UUID fileId = pendingFile.getFileState().getFileId();
        pendingFiles.put(fileId, pendingFile);
    }

    public void addPendingFiles(List<FileStoreEntity> pendingFiles){
        pendingFiles.forEach(this::addPendingFile);
    }

    public void clearPendingFiles(){
        pendingFiles.clear();
    }

}
