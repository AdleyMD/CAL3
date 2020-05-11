package Classes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    
    private static Socket client;
    private DataOutputStream output;
    private DataInputStream input;
    
    public Client() {
    }
    
    public String getUserInfo(String id) throws IOException {
        createConnection();
        output.writeUTF("0 " + id);
        String response = input.readUTF();
        closeConnection();
        return response;
    }
    
    public String getMinorsNum() throws IOException {
        output.writeInt(3);
        return input.readUTF();
    }
    
    public void createConnection() throws IOException {
        client = new Socket(InetAddress.getLocalHost(), 25565);
        output = new DataOutputStream(client.getOutputStream());
        input = new DataInputStream(client.getInputStream());
    }
    
    public void closeConnection() throws IOException {
        client.close();
    }
}
