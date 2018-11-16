/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.util.Arrays;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
/**
 *
 * @author Peterzxcvbnm
 * @since 2018-10-24
 * @version 1.0
 */
public class ClientConnection {
    public final static int PORT = 2345;
    public final static String algorithm = "SSL";
    private ServerSocket server;
    LinkFacade link;
    //private MessageParser messageParser = new MessageParser;
    //private Encrypt encrypt = new Encrypt;

    public ClientConnection(String ipAddress, LinkFacade link) throws Exception {
        //Create a socket with the passed ip address
        try {
            //Vi vælger en kontekst? Google mere om det. Konteksten fortæller hvordan det sættes op?
            SSLContext context = SSLContext.getInstance(algorithm);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            KeyStore ks = KeyStore.getInstance("JKS");
            char[] password = System.console().readPassword();
            //Den nedenunder skal laves. Det er den ikke endnu. Vi skal finde en der ikke kræver certificate.
            ks.load(new FileInputStream("jnp4e.keys"), password);
            kmf.init(ks, password);
            context.init(kmf.getKeyManagers(), null, null);
            Arrays.fill(password, '0');
            SSLServerSocketFactory factory = context.getServerSocketFactory();
            SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(PORT);
            //Anon non authed cipher
            String[] supported = server.getSupportedCipherSuites();
            String[] anonCipherSuitesSupported = new String[supported.length];
            int numAnonCipherSuitesSupported = 0;
            for (int i = 0; i < supported.length; i++) {
                if (supported[i].indexOf("_anon_") > 0) {
                    anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
                    }
                }
            //Unødvendigt? 
            String[] oldEnabled = server.getEnabledCipherSuites();
            String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuitesSupported];
            System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
            System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuitesSupported);
            server.setEnabledCipherSuites(newEnabled);
        } catch (Exception e) {
        }
        if (ipAddress != null && !ipAddress.isEmpty()) {
            this.server = new ServerSocket(2345, 1, InetAddress.getByName(ipAddress));
        } //If the method is not passed an ip address assign one automatically
        else {
            this.server = new ServerSocket(2345, 1, InetAddress.getLocalHost());
        }
        this.link = link;
    }

    /**
     * Method to create a connection to the client and make a thread for the
     * client to use
     *
     * @throws Exception
     */
    public void establishCommunication() throws Exception {
        Socket client = this.server.accept(); //Accept a client
        Runnable thread = new HandleConnection(client, link); //Create a thread to service the client
        new Thread(thread).start(); //Start the thread
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
    private LinkFacade link;

    public HandleConnection(Socket socket, LinkFacade link) {
        this.socket = socket;
        this.link = link;
    }

    @Override
    public void run() {
        //Show the no. of users connected
        System.out.println("\r\nCurrent users: " + (java.lang.Thread.activeCount() - 1));
        String data = null;
        //Get the ip of the client
        String clientAddress = socket.getInetAddress().getHostAddress();
        //Print the ip of the client
        System.out.println("New connection from " + clientAddress);

        //Communicate with the client
        try {
            //PrintWriter to create a response to the client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //Get the message from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((data = in.readLine()) != null) {
                //Print the message from the client
                System.out.println("Client says: " + data);
                //Send the response to the client
                out.println(link.messageParser(data));
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //When the client disconnects print the decremented no. of users
        System.out.println("Current users: " + (java.lang.Thread.activeCount() - 2));
    }
}
