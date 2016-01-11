package mikolaj.torrent.communication;

import mikolaj.torrent.actions.Result;
import mikolaj.torrent.communication.server.Service;

import java.io.*;
import java.net.Socket;

public class Client {
    public static final String SERVER_PULL_STRING = "pullString";
    public static final String SERVER_PULL_BYTE = "pullByte";

    private String host;
    private int port;
    private String pullType;
    private Result result = null;

    public Client(String host, int port, String pullType) {
        this.host = host;
        this.port = port;
        this.pullType = pullType;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String sendMessage(String message) {
        return this.start(message);
    }

    public String start(String message) {
        try {
            Socket clientSocket = new Socket(host, port);
            return new ClientTask(clientSocket, message, pullType, result).run();
        } catch (IOException e) {
            System.err.println("Unable to process client request");
            e.printStackTrace();
        }

        return null;
    }

    private class ClientTask {
        private final Socket clientSocket;
        private final String message;
        private final String pullType;
        private final Result result;

        private ClientTask(Socket clientSocket, String message, String pullType, Result result) {
            this.clientSocket = clientSocket;
            this.message = message;
            this.pullType = pullType;
            this.result = result;
        }

        public String run() {
            String output = null;

            try {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                outToServer.writeBytes(message + '\n');

                switch (pullType) {
                    case Client.SERVER_PULL_BYTE:
                        byte[] contents = new byte[10000];
                        int bytesRead;

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                        String dir = Service.getInstance().getServer().getShareDirectory() + "/_" + result.getData();

                        FileOutputStream fileOutputStream = new FileOutputStream(dir);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

                        bytesRead = clientSocket.getInputStream().read(contents, 0, contents.length);

                        do {
                            byteArrayOutputStream.write(contents);
                            bytesRead = clientSocket.getInputStream().read(contents);
                        } while (bytesRead != -1);

                        bufferedOutputStream.write(byteArrayOutputStream.toByteArray());
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();

                        clientSocket.close();

                        System.out.println(String.format("File saved successfully to %s!", Service.getInstance().getServer().getShareDirectory() + "/_" + result.getData()));
                        break;
                    case Client.SERVER_PULL_STRING:
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        output = inFromServer.readLine();
                        break;
                }

                clientSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output;
        }
    }
}
