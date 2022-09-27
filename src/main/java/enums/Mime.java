package enums;

import exception.NotAcceptableException;

public enum Mime {
    HTML("html", "text/html"),
    CSS("css", "text/css"),
    JAVASCRIPT("js", "text/javascript"),
    ICO("ico", "image/x-icon"),
    TTF("ttf", "font/ttf"),
    WOFF("woff", "font/woff"),
    ALL("*", "*/*"),
    NONE("", "text/plain");


    private final String type;
    private final String mime;

    Mime(String type, String mime) {
        this.type = type;
        this.mime = mime;
    }

    public String getType() {
        return type;
    }

    public String getMime() {
        return mime;
    }

    public static Mime getByType(String type) {
        for (Mime m : values()) {
            if (m.getType().equalsIgnoreCase(type)) {
                return m;
            }
        }

        return NONE;
    }

    public static String getContentType(String path, String accept) {
        String[] pathSplits = path.split("\\.");
        String[] acceptSplits = accept.split(",");

        Mime mime = getByType(pathSplits[pathSplits.length - 1]);
        for (String s : acceptSplits) {
            if (s.equalsIgnoreCase(mime.getMime()) || s.equalsIgnoreCase(ALL.getMime())) {
                return mime.getMime();
            }
        }

        return NONE.getMime();
    }

    public static Mime canAcceptHtml(String accept) throws NotAcceptableException {
        if (accept.contains(Mime.HTML.getMime())) {
            return Mime.HTML;
        } else {
            throw new NotAcceptableException("HTML을 지원하지 않는 형식입니다.");
        }
    }
}
