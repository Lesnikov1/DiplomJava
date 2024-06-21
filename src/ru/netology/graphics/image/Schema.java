package ru.netology.graphics.image;

public class Schema implements TextColorSchema {
    @Override
    public char convert(int color) {
        char[] changeSymbol = {'#', '$', '@', '%', '*', '+', '-', '\'', '\''};
        int i = color * (changeSymbol.length - 1) / 255;
        return changeSymbol[i];
    }
}
