package util;

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
    private final InputStream in;
    private Request request;

    public RequestParser(InputStream inputStream) {
        this.in = inputStream;
    }

    public Request parse() throws Exception {
        if (request == null) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));

            String requestLine = br.readLine();
            parseRequestLine(requestLine);
            parseHeaders(br);
        }

        return request;
    }

    private void parseRequestLine(String str) {
        String[] splits = str.split(" ");

        if (splits.length != 3) {
            throw new RequestParsingException("잘못된 요청입니다.");
        }

        String[] pathWithParam = splits[1].split("\\?", 2);
        this.request = new Request(splits[0], pathWithParam[0], splits[2]);

        if (pathWithParam.length > 1) {
            parseParams(pathWithParam[1]);
        }
    }

    private void parseParams(String params) {
        for (String q : params.split("&")) {
            String[] p = q.split("=");
            if (p.length == 2) {
                request.addParam(URLDecoder.decode(p[0], Charsets.UTF_8), URLDecoder.decode(p[1], Charsets.UTF_8));
            }
        }
    }

    private void parseHeaders(BufferedReader br) throws IOException {
        String line;

        while (StringUtils.isNotBlank(line = br.readLine())) {
            String[] splits = line.split(":", 2);
            if (splits.length == 2) {
                request.addHeader(splits[0].trim().toLowerCase(), splits[1].trim().toLowerCase());
            }
        }
    }
}
