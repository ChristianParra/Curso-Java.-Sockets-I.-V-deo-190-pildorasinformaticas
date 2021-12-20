
package ec.edu.monster.vista;

import ec.edu.monster.controlador.Controlador;
import ec.edu.monster.modelo.PaqueteEnvio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class LaminaMarcoCliente extends JPanel{
    private JTextField campo1;
    private JLabel nick;
    public JComboBox ip;
    public JTextArea campochat;
    private JButton miboton;
    public LaminaMarcoCliente(){
        String nick_usuario = JOptionPane.showInputDialog("Nick: ");
        JLabel n_nick = new JLabel("Nick: ");
        add(n_nick);
            
        nick = new JLabel();
        nick.setText(nick_usuario);
        add(nick);
        
        JLabel texto = new JLabel(" - Online: ");
        add(texto);
        
        ip = new JComboBox();
        add(ip);
        
        campochat = new JTextArea(12,20);
        add(campochat);
        
        campo1 = new JTextField(20); 
        add(campo1);
        
        miboton = new JButton("Enviar");
        EnviaTexto mievento = new EnviaTexto();
        miboton.addActionListener(mievento); 
        add(miboton);
        
        Controlador x = new Controlador(this);
        //Thread mihilo = new Thread();
        //mihilo.start();
    }
    
    private class EnviaTexto implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            campochat.append("\n " + campo1.getText());
            
            try {
                Socket misocket = new Socket("192.168.1.11", 9999);//La IP de mi equipo Local
                
                PaqueteEnvio datos = new PaqueteEnvio();
                datos.setNick(nick.getText());
                datos.setIp(ip.getSelectedItem().toString());
                datos.setMensaje(campo1.getText());
                
                ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream());
                paquete_datos.writeObject(datos);
                misocket.close();
                
                //DataOutputStream flujo_salida = new DataOutputStream(misocket.getOutputStream());
                //flujo_salida.writeUTF(campo1.getText());
                //flujo_salida.close(); 
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }   
        } 
    }
}

