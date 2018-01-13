package com.example.demo.store;

import com.example.demo.store.entities.ClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;

/**
 * @author houguangqiang
 * @date 2018-01-06
 * @since 1.0
 */
@Component
public class DiskClientStore {

    private static final Logger logger = LoggerFactory.getLogger(DiskClientStore.class);

    private final static String CLIENT_NAME = "client";

    @Autowired
    private DirectoryProvider directoryProvider;

    public ClientEntity getOrLoad(UUID clientId) {
        // TODO 检查parent是否在删除队列中，这里可能需要重新分配一个新的clientId，不过应该不在这里生成，可以提供一个检查方法，检查通过后再调用本方法
        // 获取存储路径
        File clientFile = withTargetFile(directoryProvider.getParent(clientId), CLIENT_NAME);

        ClientEntity client;
        if (clientFile.exists()) {
            logger.debug("磁盘中已存在clientId：{}，尝试直接读取缓存...", clientId);
            client = read(clientFile);
        } else {
            logger.debug("创建一个新的clientId：{}，路径：", clientId, clientFile.getAbsoluteFile());
            client = new ClientEntity();
            write(client, clientFile);
        }
        if (client != null) {
            logger.debug("初始化client完成，clientId：{}", clientId);
        }
        return client;
    }

    protected File withTargetFile(File parent, String clientName) {
        return new File(parent, clientName);
    }

    protected boolean write(ClientEntity client, File target) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(target))) {
            out.writeObject(client);
        } catch (IOException e) {
            logger.error("无法在磁盘中创建缓存文件："+ e.getMessage(), e);
            return false;
        }
        return true;
    }

    protected ClientEntity read(File clientFile) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(clientFile))) {
            return (ClientEntity) input.readObject();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("无法读取磁盘缓存文件："+ e.getMessage(), e);
        }
        return null;
    }

    public void update(UUID clientId, ClientEntity client) {
        File clientFile = withTargetFile(directoryProvider.getParent(clientId), CLIENT_NAME);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(clientFile))) {
            out.writeObject(client);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("无法保存磁盘缓存文件："+ e.getMessage(), e);
        }
    }

    public void delete(UUID clientId) {
        File clientFile = withTargetFile(directoryProvider.getParent(clientId), CLIENT_NAME);

        try {
            Files.walkFileTree(clientFile.toPath(),new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    logger.debug("删除缓存文件：{}", file.toFile().getAbsoluteFile());
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    logger.debug("删除client：{}", dir.toFile().getAbsoluteFile());
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("移除磁盘缓存失败：" + e.getMessage(), e);
        }
    }
}
