package ec.edu.monster.controlador;



import ec.edu.monster.modelo.PaqueteEnvio;
import ec.edu.monster.vista.LaminaMarcoCliente;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Controlador implements Runnable{
    
    
    
    private LaminaMarcoCliente cli;

    public Controlador(LaminaMarcoCliente cli) {
        this.cli=cli;
    }
    

    @Override
    public void run() {
        try{
            ServerSocket servidor_cliente = new ServerSocket(9090);
            Socket cliente;
            PaqueteEnvio paquete_recibido;
            
            while(true){
                cliente = servidor_cliente.accept();
                ObjectInputStream flujo_entrada = new ObjectInputStream(cliente.getInputStream());
                paquete_recibido = (PaqueteEnvio) flujo_entrada.readObject();

                if(!paquete_recibido.getMensaje().equals(" online")){
                    cli.campochat.append("\n " + paquete_recibido.getNick() + ": " + paquete_recibido.getMensaje());   
                }else{
                    ArrayList <String> IpsMenu = new ArrayList<String>();
                    IpsMenu = paquete_recibido.getIps();
                    cli.ip.removeAllItems();
                    for(String z:IpsMenu){
                        cli.ip.addItem(z);
                    }
                    cli.campochat.append("\n" + paquete_recibido.getIps());
                }
            }  
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
