/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author danny
 */
public class Protocol {
    public static final short REGISTER_MESSAGE = 0;
    public static final short DATA_MESSAGE = 1;
    public static final short ERROR_MESSAGE = -1;
    
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    
    public Protocol(Socket socket) throws IOException{
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        
    }
   
    public Message readMessage() throws IOException, ClassNotFoundException{
        Message sms;
        sms = (Message) in.readObject();
        return sms;
    }
    
    public  void writeMessage(Message sms) throws IOException{
        out.writeObject(sms);
        out.flush();
    }
}

