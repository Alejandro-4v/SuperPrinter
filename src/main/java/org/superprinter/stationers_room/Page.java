package org.superprinter.stationers_room;

import java.util.Objects;

public record Page (
    String title,
    String content
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(title, page.title) && Objects.equals(content, page.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content);
    }
}