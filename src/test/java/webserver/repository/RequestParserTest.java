package webserver.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.Request;
import util.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParserTest {

    @Test
    void testRequestParsing() throws Exception {
        InputStream in1 = new ByteArrayInputStream((
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").getBytes());
        InputStream in2 = new ByteArrayInputStream((
                "A A\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Accept: */*").getBytes());

        // index.html 호출
        Request request1 = new RequestParser(in1).parse();

        assertThat(request1.getMethod()).isEqualTo("GET");
        assertThat(request1.getPath()).isEqualTo("/index.html");
        assertThat(request1.getVersion()).isEqualTo("HTTP/1.1");

        assertThat(request1.getHeaders().size()).isEqualTo(3);
        assertThat(request1.getHeaders().get("host")).isEqualTo("localhost:8080");
        assertThat(request1.getHeaders().get("connection")).isEqualTo("keep-alive");
        assertThat(request1.getHeaders().get("accept")).isEqualTo("*/*");

        Assertions.assertThrows(Exception.class, () -> {
            // 유효하지 않은 호출
            Request request2 = new RequestParser(in2).parse();
        });
    }
}