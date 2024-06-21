package ru.netology.graphics;

import ru.netology.graphics.image.Converter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        Converter converter = new Converter();

        GServer server = new GServer(converter);
        server.start();
    }
}