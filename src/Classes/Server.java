/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author andro
 */
public class Server extends Thread {
    
    WaterPark park;
    
    public Server(WaterPark park) {
        this.park = park;
    }
    
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(25565);
            Socket connection;
            
            while (true) {
                connection = server.accept();
                DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                DataInputStream input = new DataInputStream(connection.getInputStream());

                String response = "";
                String request = input.readUTF();
                switch (request.charAt(0)) {
                    case '0':
                        response = park.getUserInfo(request.substring(2));
                        break;
                    default:
                        break;
                }

                output.writeUTF(response);
                connection.close();
            }
        } catch (IOException e) {}
    }
}
