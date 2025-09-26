package com.example.geonotesteaching;

public abstract sealed class AbstractExporter implements Exporter permits JsonExporter, MarkdownExporter, Timeline.Render, Timeline.RenderMarkdown {
    public abstract String export();
}