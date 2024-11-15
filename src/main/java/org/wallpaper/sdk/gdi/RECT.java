package org.wallpaper.sdk.gdi;

public record RECT(int left, int top, int right, int bottom) {
    public boolean isEmpty() {
        return left >= right || top >= bottom;
    }
}
