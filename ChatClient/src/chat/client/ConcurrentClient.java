

package chat.client;

import chat.Message;
import chat.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saul
 */
public class ConcurrentClient extends Thread{
    private final Protocol protocol;
    char type;
    String username;

    public ConcurrentClient(Protocol protocol, char type, String username) throws IOException {
        super();
        this.protocol = protocol;
        this.type = type;
        this.username = username;
    }
     @Override
    public void run(){
        Message sms = new Message();
        BufferedReader entrada = new BufferedReader(new InputStreamReader (System.in));;
        if(type == 'w'){
            while(true){
                try {
                    Message nuevo = new Message();
                    nuevo.setCode(Protocol.DATA_MESSAGE);
                    nuevo.setUsername(username);
                    nuevo.setData(entrada.readLine());
                    if (nuevo.getData().equalsIgnoreCase("exit"))
                        break;
                    protocol.writeMessage(nuevo);
                } catch (IOException ex) {
                    
                }
            }  
        }else if(type == 'r'){
            while(true){
                try {
                    sms = protocol.readMessage();
                    System.out.println( "\n" + sms.getData());
                } catch (IOException ex) {
                    
                } catch (ClassNotFoundException ex) {
                    
                }
            }  
        }
        try {
            entrada.close();
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(ConcurrentClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
