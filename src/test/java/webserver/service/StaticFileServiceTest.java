package webserver.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StaticFileServiceTest {
    private final StaticFileService service = new StaticFileService();
    private final String DEFAULT = "Hello World";

    @Test
    void serviceDefaultTest() {
        assertThat(service.serviceDefault()).isEqualTo(DEFAULT.getBytes());
    }

    @Test
    void serviceStaticFileTest() {
        final String FILE_PATH = "/index.html";
        final String RESOURCE_PATH = "webapp" + FILE_PATH;

        try {
            byte[] indexFileBody = Files.readAllBytes(new File(RESOURCE_PATH).toPath());
            byte[] body = service.serviceStaticFile(FILE_PATH);

            assertThat(indexFileBody).isEqualTo(body);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void serviceStaticFileNotFoundTest() {
        assertThrows(IllegalArgumentException.class, () -> service.serviceStaticFile("wrong path"));
    }
}