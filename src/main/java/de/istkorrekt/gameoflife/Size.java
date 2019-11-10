package de.istkorrekt.gameoflife;

import java.util.Objects;

public class Size {

    private final int width;
    private final int height;

    private Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return width == size.width &&
                height == size.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return width + " x " + height;
    }

    public static Size of(int size) {
        return of(size, size);
    }

    public static Size of(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size dimensions must not be negative");
        }
        return new Size(width, height);
    }
}
