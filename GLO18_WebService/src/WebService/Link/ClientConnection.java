/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Peterzxcvbnm
 * @since 2018-10-24
 * @version 1.0
 */
public class ClientConnection {

    private ServerSocket server;
    //private MessageParser messageParser = new MessageParser;
    //private Encrypt encrypt = new Encrypt;

    public ClientConnection(String ipAddress) throws Exception {
        if (ipAddress != null && !ipAddress.isEmpty()) {
            this.server = new ServerSocket(2345, 1, InetAddress.getByName(ipAddress));
        } else {
            this.server = new ServerSocket(2345, 1, InetAddress.getLocalHost());
        }
    }

    public void establishCommunication() throws Exception {
        Socket client = this.server.accept(); //Accept a client
        Runnable thread = new HandleConnection(client);
        new Thread(thread).start();
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }
}

class HandleConnection implements Runnable {

    private Socket socket; //Socket for connection
    private LinkFacade link = new LinkFacade();

    public HandleConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\r\nCurrent users: " + (java.lang.Thread.activeCount() - 1));
        String data = null;
        String clientAddress = socket.getInetAddress().getHostAddress();
        System.out.println("New connection from " + clientAddress);

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((data = in.readLine()) != null) {
                System.out.println("Client says: " + data);
                System.out.println(link.messageParser(data));
                out.println(link.messageParser(data));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("Current users: " + (java.lang.Thread.activeCount() - 2));
    }
}
