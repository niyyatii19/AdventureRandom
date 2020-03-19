package com.company;

public class IndexPositions {
    private int startByte;
    private int length;

    public IndexPositions(int startByte, int length) {
        this.startByte = startByte;
        this.length = length;
    }

    public void setStartByte(int startByte) {
        this.startByte = startByte;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getLength() {
        return length;
    }
}
