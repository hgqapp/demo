package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @author houguangqiang
 * @date 2018-01-11
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploaderServiceTest {

    @Autowired
    UploaderService uploaderService;

    @Test
    public void panel() {
        UUID clinetId = UUID.fromString("8b8c04ae-7d50-4eca-abd2-2613a4f4d23c");
        uploaderService.getPanel(clinetId);
    }

    @Test
    public void prepare() {
    }
}