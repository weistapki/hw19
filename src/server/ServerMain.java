package server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081)){
            System.out.println("Server is listening on port 8081");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
            printWriter.println("Hello!");
            String message;
            while ((message = bufferedReader.readLine()) !=null){
                if (containsRussianLetters(message)){
                    printWriter.println("Що таке паляниця?");
                    message = bufferedReader.readLine();
                    if (message.equalsIgnoreCase("хлеб")) {
                        // Отправка текущей даты и времени
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = now.format(formatter);
                        printWriter.println("Поточна дата і час: " + formattedDateTime);
                    } else {
                        System.out.println("Неправильна відповідь. Відключаю клієнта.");
                    }
                    break;
                }
                System.out.println(message);
            }
            System.out.println("Client disconnected, server will stop now.");

        }catch (IOException e){
            System.out.println("Error when trying to listen/accept connections or read input");
            e.printStackTrace();
        }

    }
    private static boolean containsRussianLetters(String text) {
        return text.matches(".*[а-яА-Я].*");
    }
}
