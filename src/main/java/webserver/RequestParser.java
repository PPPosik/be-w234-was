package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import com.google.common.base.Charsets;
import exception.RequestParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public String method;
    public String path;
    public String version;
    public Map<String, String> headers = new HashMap<>();
    public Map<String, String> params = new HashMap<>();

    public RequestParser(InputStream in) throws Exception {
        init(in);
    }

    private void init(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));

        String requestLine = br.readLine();
        parseRequestLine(requestLine);
        parseHeaders(br);
    }

    private void parseRequestLine(String str) {
        String[] splits = str.split(" ");

        if (splits.length != 3) {
            throw new RequestParsingException("잘못된 요청입니다.");
        }

        this.method = splits[0];
        parseParams(splits[1]);
        this.version = splits[2];
    }

    private void parseParams(String str) {
        String[] splits = str.split("\\?", 2);

        this.path = splits[0];
        if (splits.length > 1) {
            for (String q : splits[1].split("&")) {
                String[] p = q.split("=");
                if (p.length == 2) {
                    params.put(URLDecoder.decode(p[0], Charsets.UTF_8), URLDecoder.decode(p[1], Charsets.UTF_8));
                }
            }
        }
    }

    private void parseHeaders(BufferedReader br) throws IOException {
        String line;

        while (StringUtils.isNotBlank(line = br.readLine())) {
            String[] splits = line.split(":", 2);
            if (splits.length == 2) {
                this.headers.put(splits[0].trim().toLowerCase(), splits[1].trim().toLowerCase());
            }
        }
    }
}
