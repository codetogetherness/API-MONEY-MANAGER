package com.backend_api.money_manager.helper;


import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class StorageUtil {

    public String generateFileName() {
        return (new Date().getTime() + "_" + UUID.randomUUID().toString());
    }

    public File convertBase64ToFile(String base64Content, String fileName) {
        if (!fileName.contains(".")) {
            fileName += UUID.randomUUID().toString() + ".png";
        }

        byte[] decodedContent = Base64.getDecoder().decode(base64Content.getBytes(StandardCharsets.UTF_8));
        return bytesToFile(decodedContent, fileName);
    }


    public File bytesToFile(byte[] content, String fileName) {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        } catch (IOException e) {
            return null;
        }

        return file;
    }

}
