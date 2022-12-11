package org.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Homework 1 (from 03/11/2022)
 *
 * Реализовать логику простейшего сервера на Java.
 *
 * Предложенные варианты запросов:
 *
 * http://localhost -> index.html
 * http://localhost/entities/name -> имя
 * http://localhost/entities/age -> возраст
 */

public class Main {
    private static ArrayList<HandleRequest> requests = new ArrayList<>();
    public static void main(String[] args) {
        /**
         * A server socket waits for requests to come in over the network.
         * It performs some operation based on that request,
         *  and then possibly returns a result to the requester.
         */

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started");

            while (true) {
                // метод accept() ожидает соединение
                Socket socket = serverSocket.accept();
                System.out.println("New client connected.");
                // Через канал (ввода/вывода) класса Socket происходит обмен сообщения клиента с сервером

                // Создаём в отдельном потоке Request
                new Thread(() -> makeRequestHandling(socket)).start();
            } // end while(true)

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } // end void main()

    private static void makeRequestHandling(Socket socket) {
        // Создаём новый Request и помещаем ссылку на создающий объект в динамический массив requests
        HandleRequest handleRequest = new HandleRequest(socket);
        requests.add(handleRequest);
        requests.get(requests.size() - 1).handleRequest();
    }
}