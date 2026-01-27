package org.superprinter;

public record Document (
        String title,
        String document,
        DocumentType type,
        String sender
) {}
