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
    
    public String getIdUser() throws IOException {
        createConection();
        output.writeInt(0);
        return input.readUTF();
    }
    
    public String getLocation() throws IOException {
        createConection();
        output.writeInt(1);
        return input.readUTF();
    }
    
    public String getActivities() throws IOException {
        createConection();
        output.writeInt(2);
        return input.readUTF();
    }
    
    public String getMinorsNum() throws IOException {
        createConection();
        output.writeInt(3);
        return input.readUTF();
    }
    
    public void createConection() throws IOException {
        client = new Socket(InetAddress.getLocalHost(), 25565);
        output = new DataOutputStream(client.getOutputStream());
        input = new DataInputStream(client.getInputStream());
    }
    
    public void closeConnection() throws IOException {
        client.close();
    }
}
