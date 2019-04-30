/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat.client;

import chat.Message;
import chat.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author danny
 */
public class ChatClient {
    Protocol protocol;
    public ChatClient(Socket socket) throws IOException{
        protocol = new Protocol(socket);
    }

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException{
        if (args.length != 2){
            System.err.println("Número de parámetros incorrecto. Use java CipherClient <IP> <Puerto>");
            System.exit(-1);
        }
        Message sms;
        BufferedReader inUsuario;
        String ip = args[0];
        ChatClient cli;
        int puerto = Integer.parseInt(args[1]);
        try{
            Socket cliente = new Socket(ip, puerto);
            String username;
            cli = new ChatClient(cliente);//protocol = new Protocol(cliente);
            inUsuario = new BufferedReader(new InputStreamReader (System.in));
            //Registro inicial del cliente de chat
            do{
                System.out.print(">> Ingrese un nombre de usuario: ");
                username = inUsuario.readLine();
                sms = new Message(Protocol.REGISTER_MESSAGE, username ,"Hola, deseo conectarme!");
                cli.protocol.writeMessage(sms);
                //Información de usuarios conectados
                System.out.println("_____________________\n\n"
                        + "Usuarios conectados\n---------------------\n");
                sms = cli.protocol.readMessage();
                System.out.print( sms.getData());
            }while(sms.getCode() == Protocol.ERROR_MESSAGE);
            //Seleccion del usuario con el que se desea chatear
            do{
                System.out.print(">> ¿Con qué usuario desea chatear? ");
                sms.setData(inUsuario.readLine());
                cli.protocol.writeMessage(sms);
                 sms = cli.protocol.readMessage();
                System.out.print( sms.getData());
            }while(sms.getCode() == Protocol.ERROR_MESSAGE);
            ConcurrentClient hiloLectura = new ConcurrentClient(cli.protocol, 'r', username);
            ConcurrentClient hiloEscritura = new ConcurrentClient(cli.protocol, 'w', username);
            hiloLectura.start();
            hiloEscritura.start();
        }
        catch (UnknownHostException e){
            System.err.println("No se puede resolver el host: " + ip);
            //e.printStackTrace();
        }
        catch(IOException e){
            System.err.println("Error al conectar a: " + ip );
            //e.printStackTrace();
        }
    }
}
