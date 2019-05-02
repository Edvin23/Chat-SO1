


package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author saul
 */
public class ChatServer  {
    
    public static void main(String args[]) throws ClassNotFoundException{
        if(args.length != 1){
            System.out.println("Use: java CipherServer <numero_puerto>");
            System.exit(1);
        }
        int numeroPuerto = Integer.parseInt(args[0]);
        try{
            ServerSocket serverSocket = new ServerSocket(numeroPuerto);
            System.out.println("Escuchando...Listo");
            while(true){
                Socket cliente = serverSocket.accept();
                System.out.println("Conexión aceptada: " + cliente.getRemoteSocketAddress());
                ClientHandler clientHandler = new ClientHandler(cliente);
                Thread th = new Thread (clientHandler);
                th.start();                
            }
            
        }catch(IOException e){
            System.out.println("Error al escuchar por el puerto:" + numeroPuerto);
            e.printStackTrace();
        }
    }
}
