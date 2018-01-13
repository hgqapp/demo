package com.example.demo;

import com.example.demo.bean.UploaderCreatedFile;
import com.example.demo.bean.UploaderPanel;
import com.example.demo.bean.UploaderPendingFile;
import com.example.demo.bean.UploaderPreCreateFile;
import com.example.demo.config.UploaderConfiguration;
import com.example.demo.store.entities.ClientEntity;
import com.example.demo.store.entities.FileStateEntity;
import com.example.demo.store.entities.FileStoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author houguangqiang
 * @date 2018-01-10
 * @since 1.0
 */
@Service
public class UploaderService {

    @Autowired
    private ClientManager clientManager;
    @Autowired
    private UploaderConfiguration configuration;

    /**
     * 上传面板，
     * @param clientId
     */
    public UploaderPanel getPanel(UUID clientId){
        Objects.requireNonNull(clientId);
        UploaderPanel panel = new UploaderPanel();
        panel.setChunkSize(configuration.getChunkSize());

        ClientEntity clientEntity = clientManager.get(clientId);
        if (clientEntity != null) {
            List<UploaderPendingFile> pendingFiles = clientEntity.getPendingFiles().stream().map(this::transferPendingFiles).collect(Collectors.toList());
            panel.setPendingFiles(pendingFiles);
        }

        return panel;
    }

    private UploaderPendingFile transferPendingFiles(FileStoreEntity fileStoreEntity) {
        File file = new File(fileStoreEntity.getAbsolutePath());
        FileStateEntity fileState = fileStoreEntity.getFileState();
        UploaderPendingFile pendingFile = new UploaderPendingFile();
        pendingFile.setFileId(fileState.getFileId());
        pendingFile.setFileName(fileState.getFileName());
        pendingFile.setFileSize(fileState.getFileSize());
        pendingFile.setCompletedSize(file.length());
        pendingFile.setCreateTime(fileState.getCreateTime());
        return pendingFile;
    }

    /**
     *  上传准备
     * @param clientId clienId
     * @param files 文件上传列表
     * @return
     */
    public List<UploaderCreatedFile> preCreate(UUID clientId, List<UploaderPreCreateFile> files) {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(files);
        List<UploaderCreatedFile> createdFiles= new ArrayList<>(files.size());
        for (UploaderPreCreateFile file : files) {
            UploaderCreatedFile createdFile = new UploaderCreatedFile();
            createdFile.setIndex(file.getIndex());
            try {
                UUID fileId = clientManager.createNewFile(clientId, file.getFileName(), file.getFileSize(), file.getDigest());
                createdFile.setFileId(fileId);
            } catch (Exception e) {
                createdFile.setError(e.getMessage());
            }
            createdFiles.add(createdFile);
        }
        return createdFiles ;
    }
}
