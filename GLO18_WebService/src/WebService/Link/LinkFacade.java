/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import java.net.DatagramSocket;
import java.net.InetAddress;
import WebService.Acquaintance.ILink;
import WebService.Acquaintance.ILogic;

/**
 *
 * @author Jeppe Enevold
 */
public class LinkFacade implements ILink {

    private static ILogic logic;

    @Override
    public void injectLogic(ILogic LogicLayer) {
        logic = LogicLayer;
    }

    private ClientConnection connection;

    /**
     * Method to initiate a connection so the web service can accept clients
     */
    @Override
    public void startConnection() {
        String ip = "";
        //Get the ip
        try {
            final DatagramSocket socketTest = new DatagramSocket();
            socketTest.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socketTest.getLocalAddress().getHostAddress();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //Create a connection with the given ip
        try {
            connection = new ClientConnection(ip, logic);
            System.out.println("\r\nRunning Server: "
                    + "Host=" + connection.getSocketAddress().getHostAddress()
                    + " Port=" + connection.getPort());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //Continously establish communication with clients
        try {
            while (true) {
                connection.establishCommunication();

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
