package org.superprinter.office;

import org.superprinter.utils.DocumentType;

public record Document (
        String title,
        String document,
        DocumentType type,
        String sender
) {}
