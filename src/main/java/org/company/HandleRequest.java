package org.company;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

class HandleRequest {
    private Socket socket = null;
    private Path currentPath = null;

    public HandleRequest(Socket socket) {
        this.socket = socket;
    } // end constructor

    public void handleRequest() {
        try (BufferedReader reader = new BufferedReader(                     // создаём reader для текущего socket~а
                new InputStreamReader(
                        socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(socket.getOutputStream()); // создаём writer для текущего socket~а
        ) {
            while (!reader.ready());

            String firstLine = reader.readLine();
            String[] partsFirstLine = firstLine.split(" ");

            if (partsFirstLine[1].equals("/") || partsFirstLine[1].equals("/index.html") ) // case : "http://localhost -> index.html"
            {
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/html; charset=utf-8");
                writer.println(); // обязательная пустая строка в ответе

                byte[] data;
                try (InputStream in = getClass().getResourceAsStream("/www/index.html")) {
                    data = in.readAllBytes();
                    for (byte bt : data) writer.print((char) bt);
                }

                System.out.println("Client disconnected");
                return;
            }

            else if (partsFirstLine[1].equals("/entities/name"))  // case : "http://localhost/entities/name -> имя"
            {
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/html; charset=utf-8");
                writer.println(); // обязательная пустая строка в ответе

                byte[] data;
                try (InputStream in = getClass().getResourceAsStream("/www/entities/name.html")) {
                    data = in.readAllBytes();
                    for (byte bt : data) writer.print((char) bt);
                }

                System.out.println("Client disconnected");
                return;
            }

            else if (partsFirstLine[1].equals("/entities/age"))  // case : "http://localhost/entities/age -> возраст"
            {
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/html; charset=utf-8");
                writer.println(); // обязательная пустая строка в ответе

                byte[] data;
                try (InputStream in = getClass().getResourceAsStream("/www/entities/age.html")) {
                    data = in.readAllBytes();
                    for (byte bt : data) writer.print((char) bt);
                }

                System.out.println("Client disconnected");
                return;
            }

            else // Other cases
            {
                writer.println("HTTP/1.1 404 NOT_FOUND");
                writer.println("Content-Type: text/html; charset=utf-8");
                writer.println(); // обязательная пустая строка в ответе

                writer.println("<h5>WARNING ! File not found</h5>");
                writer.flush();

                System.out.println("Client disconnected");
                return;
            }
        } // end TRY bufferedReader block

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
} // end class