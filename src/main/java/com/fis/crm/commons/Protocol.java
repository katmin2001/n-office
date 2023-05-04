package com.fis.crm.commons;

public class Protocol {

    public Protocol(String url) {
        this.url = url;
    }

    public String getProtocol() {
        if (url == null || url.length() == 0)
            return null;
        int i = url.indexOf("://");
        if (i != -1 && i < url.length())
            return url.substring(0, i);
        else
            return null;
    }

    public String getIp() {
        if (url == null || url.length() == 0)
            return null;
        int s = url.indexOf("://");
        if (s != -1 && s < url.length()) {
            int e = url.indexOf(":", s + 1);
            if (e == -1)
                e = url.indexOf("/", s + 3);
            if (e != -1 && e < url.length())
                return url.substring(s + 3, e);
        }
        return null;
    }

    public int getPort() {
        if (url == null || url.length() == 0)
            return getDefaultPort();
        int s = url.indexOf("://");
        if (s != -1 && s < url.length()) {
            s = url.indexOf(":", s + 1);
            if (s != -1 && s < url.length()) {
                int e = url.indexOf("/", s + 1);
                String parten;
                if (e == -1)
                    parten = url.substring(s + 1);
                else
                    parten = url.substring(s + 1, e);
                try {
                    return Integer.parseInt(parten);
                } catch (NumberFormatException ex) {
                    return getDefaultPort();
                }
            }
        }
        return getDefaultPort();
    }

    public String getServer() {
        if (url == null || url.length() == 0)
            return null;
        int s = url.indexOf("://");
        if (s != -1 && s < url.length()) {
            s = url.indexOf("/", s + 3);
            if (s != -1 && s < url.length())
                return url.substring(s);
        }
        return null;
    }

    public int getDefaultPort() {
        String protocol = getProtocol();
        if ("rmi".equals(protocol))
            return 1099;
        return !"eic".equals(protocol) ? 80 : 8001;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    protected String url;
}

