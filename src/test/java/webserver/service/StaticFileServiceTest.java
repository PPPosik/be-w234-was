package webserver.service;

import exception.http.RequestParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StaticFileServiceTest {
    private final StaticFileService service = new StaticFileService();

    @Test
    void serviceStaticFileTest() {
        final String RESOURCE_PATH = "webapp/index.html";

        try {
            byte[] indexFileBody = Files.readAllBytes(new File(RESOURCE_PATH).toPath());
            byte[] body = service.serviceStaticFile(new File(RESOURCE_PATH));

            assertThat(indexFileBody).isEqualTo(body);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void fileReadFailTest() {
        Assertions.assertThrows(RequestParsingException.class, () -> service.serviceStaticFile(new File("not.found")));
    }
}