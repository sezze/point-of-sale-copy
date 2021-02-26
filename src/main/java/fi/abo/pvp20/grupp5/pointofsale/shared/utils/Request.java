package fi.abo.pvp20.grupp5.pointofsale.shared.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Request {
    final HttpHeaders headers;
    final MultiValueMap<String, String> map;

    public Request() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        map = new LinkedMultiValueMap<>();
    }

    public Request(String key, String value) {
        this();
        add(key, value);
    }

    public Request(String key, double value) {
        this();
        add(key, value);
    }

    public Request(String key, int value) {
        this();
        add(key, value);
    }

    public void add(String key, String value) {
        map.add(key, value);
    }

    public void add(String key, double value) {
        map.add(key, Double.toString(value));
    }

    public void add(String key, int value) {
        map.add(key, Integer.toString(value));
    }

    public HttpEntity<MultiValueMap<String, String>> httpEntity() {
        return new HttpEntity<>(map, headers);
    }
}
