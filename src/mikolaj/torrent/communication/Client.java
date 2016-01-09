package mikolaj.torrent.communication;

import mikolaj.torrent.communication.server.Service;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static final String SERVER_PULL_STRING = "pullString";
    public static final String SERVER_PULL_BYTE = "pullByte";

    private String host;
    private int port;
    private String pullType;

    public Client(String host, int port, String pullType) {
        this.host = host;
        this.port = port;
        this.pullType = pullType;
    }

    public String sendMessage(String message) {
        return this.start(message);
    }

    public String start(String message) {
//        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        try {
            Socket clientSocket = new Socket(host, port);
            return new ClientTask(clientSocket, message, pullType).run();
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
        private final String pullType;

        private ClientTask(Socket clientSocket, String message, String pullType) {
            this.clientSocket = clientSocket;
            this.message = message;
            this.pullType = pullType;
        }

        public String run() {
            String output = null;

            try {
                switch (pullType) {
                    case Client.SERVER_PULL_BYTE:
                        try {
                            byte[] aByte = new byte[1];
                            int bytesRead;

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();

                            FileOutputStream fos;
                            BufferedOutputStream bos;

                            fos = new FileOutputStream(Service.getInstance().getServer().getShareDirectory() + "/test.txt");
                            bos = new BufferedOutputStream(fos);

                            bytesRead = clientSocket.getInputStream().read(aByte, 0, aByte.length);

                            do {
                                baos.write(aByte);
                                bytesRead = clientSocket.getInputStream().read(aByte);
                            } while (bytesRead != -1);

                            bos.write(baos.toByteArray());
                            bos.flush();
                            bos.close();
                            clientSocket.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case Client.SERVER_PULL_STRING:
                        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        outToServer.writeBytes(message + '\n');

                        output = inFromServer.readLine();
                        break;
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output;
        }
    }
}
