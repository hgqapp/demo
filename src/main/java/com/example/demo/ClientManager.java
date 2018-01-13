package com.example.demo;

import com.example.demo.store.DirectoryProvider;
import com.example.demo.store.DiskClientStore;
import com.example.demo.store.entities.ClientEntity;
import com.example.demo.store.entities.FileStoreEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author houguangqiang
 * @date 2018-01-06
 * @since 1.0
 */
@Component
public class ClientManager {

    private static final Logger logger = LoggerFactory.getLogger(ClientManager.class);

    @Autowired
    protected DiskClientStore diskStore;

    @Autowired
    private DirectoryProvider directoryProvider;


    LoadingCache<UUID, ClientEntity> cache = CacheBuilder
            .newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(1, TimeUnit.DAYS)
            .build(new CacheLoader<UUID, ClientEntity>() {
                @Override
                public ClientEntity load(UUID clientId) {
                    return create(clientId);
                }
            });


    protected ClientEntity create(UUID clientId) {
        Objects.requireNonNull(clientId);
        return diskStore.getOrLoad(clientId);
    }

    public ClientEntity get(UUID clientId) {
        Objects.requireNonNull(clientId);
        return cache.getUnchecked(clientId);
    }

    public ClientEntity getIfPresent(UUID clientId){
        Objects.requireNonNull(clientId);
        return cache.getIfPresent(clientId);
    }

    public void put(UUID clientId, ClientEntity client) {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(client);
        if (logger.isDebugEnabled()) {
            logger.debug("更新client，id：{}，value：{}", clientId, client.toString());
        }
        cache.put(clientId, client);
        // TODO 这里可能需要异步更新
        diskStore.update(clientId, client);
    }

    /**
     * 移除客户端
     * @param clientId clientId
     */
    public void clear(UUID clientId) {
        Objects.requireNonNull(clientId);
        // 从内存中移除
        cache.invalidate(clientId);
        // TODO 从磁盘中移除,这里需要异步执行
        diskStore.delete(clientId);
    }

    public void removeFile(UUID clientId, UUID fileId){

    }

    /**
     * clientId是否启用
     * @param clientId clientId
     * @return 启用返回true，否则返回false
     */
    public boolean isEnable(UUID clientId) {
        return true;
    }

    public UUID createNewFile(UUID clientId, String fileName, long size, String digest) throws IOException {
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(digest);
        ClientEntity clientEntity = get(clientId);
        UUID fileId = UUID.randomUUID();
        Path path = Paths.get(directoryProvider.getParent(clientId).getAbsolutePath(), fileId.toString());
        Files.createFile(path);
        FileStoreEntity pendingFile = new FileStoreEntity(path.toAbsolutePath().toString());
        pendingFile.newFileState()
                .fileId(fileId)
                .fileName(fileName)
                .fileSize(size)
                .digest(digest)
                .createTime(System.currentTimeMillis());
        clientEntity.addPendingFile(pendingFile);
        put(fileId, clientEntity);
        return fileId;
    }
}
