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
        output.writeInt(0);
        return input.readUTF();
    }
    
    public String getLocation() throws IOException {
        output.writeInt(1);
        return input.readUTF();
    }
    
    public String getActivities() throws IOException {
        output.writeInt(2);
        return input.readUTF();
    }
    
    public String getMinorsNum() throws IOException {
        output.writeInt(3);
        return input.readUTF();
    }
    
    public String getSlideA() throws IOException {
        output.writeInt(4);
        return input.readUTF();
    }
    
    public String getSlideB() throws IOException {
        output.writeInt(5);
        return input.readUTF();
    }
    
    public String getSlideC() throws IOException {
        output.writeInt(6);
        return input.readUTF();
    }
    
    public String getCR() throws IOException {
        output.writeInt(7);
        return input.readUTF();
    }
    
    public String getWP() throws IOException {
        output.writeInt(8);
        return input.readUTF();
    }
    
    public String getCP() throws IOException {
        output.writeInt(9);
        return input.readUTF();
    }
    
    public String getSB() throws IOException {
        output.writeInt(10);
        return input.readUTF();
    }
    
    public String getS() throws IOException {
        output.writeInt(11);
        return input.readUTF();
    }
    
    public String getBP() throws IOException {
        output.writeInt(12);
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
