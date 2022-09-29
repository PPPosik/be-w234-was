package webserver.service;

import java.io.File;
import java.nio.file.Files;

public class StaticFileService {
    private final String RESOURCE_DIR = "webapp";

    public byte[] serviceDefault() {
        return "Hello World".getBytes();
    }

    public byte[] serviceStaticFile(String path) throws Exception {
        File file = new File(getResourcePath(path));

        if (file.exists()) {
            return Files.readAllBytes(file.toPath());
        } else {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }
    }

    private String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }
}
