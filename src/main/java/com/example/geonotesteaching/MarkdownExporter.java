package com.example.geonotesteaching;

public final class MarkdownExporter extends AbstractExporter implements Exporter {
    private final String payload;

    public MarkdownExporter(String payload) {
        this.payload = payload;
    }

    public String export() {
        return payload;
    }
}