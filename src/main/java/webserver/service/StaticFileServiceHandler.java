package webserver.service;

import util.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileServiceHandler implements ServiceHandler {
    private final String RESOURCE_DIR = "webapp";

    @Override
    public String handle(Request request) throws IOException {
        File file = new File(getResourcePath(request.getPath()));

        if (file.exists()) {
            return Files.readString(file.toPath());
        } else {
            return defaultHandle();
        }
    }

    private String getResourcePath(String path) {
        return RESOURCE_DIR + path;
    }

    private String defaultHandle() {
        return "Hello World";
    }
}
