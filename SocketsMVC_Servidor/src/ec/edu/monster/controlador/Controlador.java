
package ec.edu.monster.controlador;

import ec.edu.monster.modelo.PaqueteEnvio;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class Controlador implements Runnable{
    private JTextArea areatexto;
    public Controlador(JTextArea areatexto){
        this.areatexto=areatexto;
        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9999);
            String nick, ip, mensaje;
            ArrayList <String> listaIp = new ArrayList<String>();
            PaqueteEnvio paquete_recibido = new PaqueteEnvio();
            
            while(true){
                Socket misocket = servidor.accept();
                ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream()); 
                paquete_recibido = (PaqueteEnvio) paquete_datos.readObject();
                nick = paquete_recibido.getNick();
                ip = paquete_recibido.getIp();
                mensaje =  paquete_recibido.getMensaje();
                
                if(!mensaje.equals(" online")){
                    areatexto.append("\n " + nick + ": " + mensaje + " - Para: " + ip);
                
                    Socket enviar_destinatario = new Socket(ip,9090);
                    ObjectOutputStream paquete_reenvio= new ObjectOutputStream(enviar_destinatario.getOutputStream());
                    paquete_reenvio.writeObject(paquete_recibido);

                    paquete_reenvio.close();
                    enviar_destinatario.close(); 
                    misocket.close();
                }else{
                    //=========================  Detecta Online ==================================
                    InetAddress localizacion = misocket.getInetAddress();
                    String IpRemota = localizacion.getHostAddress();
                    System.out.println("Online " + IpRemota);
                    listaIp.add(IpRemota);
                    paquete_recibido.setIps(listaIp);
                    
                    for(String z:listaIp){
                        System.out.println("Array: " + z); //Imprime la lista de las IP Cliente conectadas a las servidor
                        Socket enviar_destinatario = new Socket(z,9090);
                        ObjectOutputStream paquete_reenvio= new ObjectOutputStream(enviar_destinatario.getOutputStream());
                        paquete_reenvio.writeObject(paquete_recibido);

                        paquete_reenvio.close();
                        enviar_destinatario.close(); 
                        misocket.close();
                    }
                    //============================================================================
                }
                
            }      
        } catch (IOException e1) {
            System.out.println(e1.getMessage());
        }catch (ClassNotFoundException e2) {
             System.out.println(e2.getMessage());
        }
    }
    
}
