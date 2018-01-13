package com.example.demo.store;

import com.example.demo.config.UploaderConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @author houguangqiang
 * @date 2018-01-08
 * @since 1.0
 */
@Component
public class DirectoryProvider implements ServletContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryProvider.class);

    @Autowired
    private UploaderConfiguration configuration;

    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public File getParent(UUID clientId){
        Objects.requireNonNull(clientId);
        File parent = new File(getRoot(), clientId.toString());
        if (!parent.exists() && !parent.mkdirs()) {
            logger.error("无法创建目录：{}", parent.getAbsoluteFile());
        }
        return parent;
    }

    private File getRoot(){
        String realPath = configuration.getDir();
        if (configuration.isDirRelative()) {
            realPath = servletContext.getRealPath(realPath);
        }
        File root = new File(realPath);

        if (!root.exists() && !root.mkdirs()) {
            logger.error("无法创建目录：{}", root.getAbsoluteFile());
        }
        if (root.isFile()) {
            logger.error("{} 是一个文件，你应该指定一个目录用于存放上传的临时文件。", root.getAbsolutePath());
        }
        return root;
    }

}
