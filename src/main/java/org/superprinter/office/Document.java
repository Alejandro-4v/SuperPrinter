package org.superprinter.office;

import org.superprinter.utils.DocumentType;

public record Document (
        String title,
        String content,
        DocumentType type,
        String sender
) {}
