/**
 * TORrent
 *
 * @copyright Copyright (c) 2016 Mikołaj Goguła
 * @license   Proprietary License
 */
package mikolaj.torrent.communication.server;

import mikolaj.torrent.actions.Parser;
import mikolaj.torrent.actions.Result;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap {
    public static final String SERVER_RETURN_STRING = "returnString";
    public static final String SERVER_RETURN_BYTE = "returnByte";

    private int port = 10101;
    private String shareDirectory;

    public Bootstrap(String shareDirectory) {
        this.shareDirectory = shareDirectory;
        Service.getInstance().setServer(this);
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

                    switch (result.getServerReturnType()) {
                        case Bootstrap.SERVER_RETURN_STRING:
                            stringSend(result);
                            break;
                        case Bootstrap.SERVER_RETURN_BYTE:
                            byteSend(result);
                            break;
                    }


                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void stringSend(Result result) {
            try {
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                outToClient.print(result.getData());
                outToClient.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void byteSend(Result result) {
            try {
                BufferedOutputStream outToClient = new BufferedOutputStream(clientSocket.getOutputStream());

                File file = new File(Service.getInstance().getServer().getShareDirectory() + "/" + result.getData().toString().replace("__", " "));
                byte[] byteArray = new byte[(int) file.length()];

                FileInputStream fileInputStream;

                try {
                    fileInputStream = new FileInputStream(file);

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    bufferedInputStream.read(byteArray, 0, byteArray.length);
                    outToClient.write(byteArray, 0, byteArray.length);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                outToClient.flush();
                outToClient.close();

                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getShareDirectory() {
        return shareDirectory;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}