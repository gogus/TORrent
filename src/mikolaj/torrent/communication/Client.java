package mikolaj.torrent.communication;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String sendMessage(String message) {
        return this.start(message);
    }

    public String start(String message) {
//        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        try {
            Socket clientSocket = new Socket(host, port);
            return new ClientTask(clientSocket, message).run();
        } catch (IOException e) {
            System.err.println("Unable to process client request");
            e.printStackTrace();
        }

        return null;

//        Runnable serverTask = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Socket clientSocket = new Socket(host, port);
//                    clientProcessingPool.submit(new ClientTask(clientSocket, message));
//                } catch (IOException e) {
//                    System.err.println("Unable to process client request");
//                    e.printStackTrace();
//                }
//            }
//        };
//        Thread serverThread = new Thread(serverTask);
//        serverThread.start();
    }

    private class ClientTask {
        private final Socket clientSocket;
        private final String message;

        private ClientTask(Socket clientSocket, String message) {
            this.clientSocket = clientSocket;
            this.message = message;
        }

        public String run() {
            String output = null;

            try {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                outToServer.writeBytes(message + '\n');

                output = inFromServer.readLine();

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }
    }
}
