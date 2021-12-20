
package ec.edu.monster.vista;

import ec.edu.monster.modelo.PaqueteEnvio;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.*;

public class MarcoCliente extends JFrame{
    
    public static void main(String[] args) {
        MarcoCliente mimarco = new MarcoCliente();
        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public MarcoCliente(){
        setBounds(600, 300, 280, 350);
        LaminaMarcoCliente milamina = new LaminaMarcoCliente();
        add(milamina);
        setVisible(true);
        addWindowListener(new EnvioOnline());
    }
}

class EnvioOnline extends WindowAdapter{
    public void windowOpened(WindowEvent e){
            try{
                Socket misocket = new Socket("192.168.1.11",9999); //IP servidor
                PaqueteEnvio datos = new PaqueteEnvio();
                datos.setMensaje(" online");
                ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
                paquete_datos.writeObject(datos);
                misocket.close();
            }catch (Exception e2){
                System.out.println(e2.getMessage());
            }
        }
}