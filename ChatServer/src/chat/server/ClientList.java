/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.server;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author danny
 */
public class ClientList {
        static ArrayList<ClientHandler> listaUsuarios = new ArrayList<ClientHandler>();
        

    public synchronized static void addUser(ClientHandler userIn){
        listaUsuarios.add(userIn);
    }
    public synchronized static String onlineUsersInfo() throws IOException{
        StringBuilder infoUsers = new StringBuilder();
        for(ClientHandler each : listaUsuarios){
            infoUsers.append("+");
            infoUsers.append(each.getUsername());
            infoUsers.append("\n\n");
        }
        return infoUsers.toString();
    }
    public static boolean existClient(String username){
        boolean ret = false;
        for(ClientHandler client : listaUsuarios){
            if(client.getUsername().equals(username)){
                ret = true;
                break;
            }
        }
        return ret;
    }
    public static ClientHandler searchClient(String username){
        ClientHandler ret = null;
        for(ClientHandler client : listaUsuarios){
            if(client.getUsername().equals(username)){
                ret = client;
                break;
            }
        }
        return ret;
    }
    
}
