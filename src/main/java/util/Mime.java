package util;

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
            if (m.getType().equals(type)) {
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
            if (s.equals(mime.getMime()) || s.equals(ALL.getMime())) {
                return mime.getMime();
            }
        }

        return NONE.getMime();
    }
}
