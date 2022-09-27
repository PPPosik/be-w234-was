package webserver.service;

import exception.HttpException;
import exception.PageNotFoundException;
import exception.RequestParsingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileService {
    private final String RESOURCE_DIR = "webapp";

    public byte[] serviceDefault() {
        return "Hello World".getBytes();
    }

    public byte[] serviceStaticFile(String path) throws HttpException {
        File file = new File(getResourcePath(path));

        if (file.exists()) {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RequestParsingException("파일 읽기에 실패했습니다.");
            }
        } else {
            throw new PageNotFoundException("파일을 찾을 수 없습니다.");
        }
    }

    private String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }
}
