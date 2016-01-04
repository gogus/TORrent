/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.communication.server;

import mikolaj.torrent.actions.Parser;
import mikolaj.torrent.actions.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap {
    private int port;
    private String shareDirectory;

    public Bootstrap(int port, String shareDirectory) {
        this.port = port;
        this.shareDirectory = shareDirectory;
        Service.getInstance().setServer(this);
        this.start();
    }

    public void start() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("Waiting for clients to connect...");
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientProcessingPool.submit(new ClientTask(clientSocket));
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private class ClientTask implements Runnable {
        private final Socket clientSocket;

        private ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String command = reader.readLine();

                Result result = new Parser(command, Service.getInstance(), Parser.PARAMETER_PARSER_TRADITIONAL).perform();

                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                outToClient.print(result.getData());
                outToClient.flush();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getShareDirectory() {
        return shareDirectory;
    }

    public int getPort() {
        return port;
    }
}