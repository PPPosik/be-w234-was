package webserver.service;

import exception.http.RequestParsingException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileService {
    private final String RESOURCE_DIR = "webapp";

    public byte[] serviceStaticFile(File file) throws RequestParsingException {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RequestParsingException("파일 읽기에 실패했습니다.");
        }
    }

    public String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }
}
