

package chat.server;

import chat.Message;
import chat.Protocol;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author saul
 */
public class ClientHandler implements Runnable {
    private String username;
    private Socket socket;
    private ClientHandler remoteClient;
    private Protocol protocol;

    public ClientHandler(String username) {
        this.username = username;
    }
    public ClientHandler(String username, Socket socket) throws IOException {
        this.username = username;
        this.socket = socket;
        protocol = new Protocol(socket);
    }
   
    public ClientHandler(Socket socket) throws IOException {
        this.socket= socket;
        protocol = new Protocol(socket);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientHandler getRemoteClient() {
        return remoteClient;
    }

    public void setRemoteClient(ClientHandler remoteClient) {
        this.remoteClient = remoteClient;
    }

    public Protocol getProtocol() {
        return protocol;
    }
    
    public String toString(){
        return username;
    }

    @Override
    public void run() {
        try {
            //protocol = new Protocol(socket);
            Message sms;
            //while(true){
                sms = getProtocol().readMessage();
                switch(sms.getCode()){
                    case Protocol.REGISTER_MESSAGE:

                        do{
                            System.out.println(">> " + sms.getUsername() + ": " + sms.getData());
                            if(ClientList.existClient(sms.getUsername())){
                                sms.setCode(Protocol.ERROR_MESSAGE);
                                sms.setData("El nombre de usuario indicado ya existe! Intente con otro nombre!\n");
                                protocol.writeMessage(sms);
                                sms = protocol.readMessage();
                            }
                        }while(ClientList.existClient(sms.getUsername()));
                        this.setUsername(sms.getUsername());
                        //Se envía la información de usuarios conectados
                        sms.setCode(Protocol.REGISTER_MESSAGE);
                        sms.setData(ClientList.onlineUsersInfo());
                        ClientList.addUser(this);
                        protocol.writeMessage(sms);
                        do{
                            sms = protocol.readMessage();
                            System.out.println(">> " +sms.getUsername() + ": Iniciar chat con <<" + sms.getData() +">>");
                            if(!ClientList.existClient(sms.getData())){
                                sms.setCode(Protocol.ERROR_MESSAGE);
                                sms.setData("El nombre de usuario indicado no existe! Intente con otro nombre!\n");
                                protocol.writeMessage(sms);
                            }
                        }while(!ClientList.existClient(sms.getData()));
                        this.remoteClient = ClientList.searchClient(sms.getData());
                        do{
                            Thread.sleep(3000);
                            System.out.println("Esperando por " +  this.getRemoteClient().username);
                        }while(this.remoteClient.getRemoteClient() != this);
                        sms.setCode(Protocol.REGISTER_MESSAGE);
                        sms.setData("Conexión establecida entre " + this + " y " + this.remoteClient + ". Digite exit para salir.\n");
                        protocol.writeMessage(sms);
                        System.out.println(sms.getData());
                        while(true){
                            sms = protocol.readMessage();
                            System.out.println(">> " + sms.getData());
                            sms.setData(this.username + ":" + sms.getData());
                            this.remoteClient.protocol.writeMessage(sms);                            
                            
                        }
                    default:
                        System.err.println("Mensaje desconocido!");
                }
        } catch (IOException ex) {
            //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
           // Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }   
}
