package webserver.service;

import util.HttpStatusCode;
import util.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileService {
    public ResponseEntity service(File file) throws IOException {
        ResponseEntity response = new ResponseEntity();

        if (file.exists()) {
            response.setBody(serviceStaticFile(file));
            response.setHttpStatusCode(HttpStatusCode.OK);
        } else {
            response.setBody(serviceDefault());
            response.setHttpStatusCode(HttpStatusCode.NOT_FOUND);
        }

        return response;
    }

    private String serviceDefault() {
        return "Hello World";
    }

    private byte[] serviceStaticFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
}
