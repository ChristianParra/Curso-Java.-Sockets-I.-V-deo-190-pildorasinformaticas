
package ec.edu.monster.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class PaqueteEnvio implements Serializable{
    private String nick, ip, mensaje;
    private ArrayList<String> Ips;
    
    public PaqueteEnvio() {
    }

    public PaqueteEnvio(String nick, String ip, String mensaje, ArrayList<String> Ips) {
        this.nick = nick;
        this.ip = ip;
        this.mensaje = mensaje;
        this.Ips = Ips;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public ArrayList<String> getIps() {
        return Ips;
    }

    public void setIps(ArrayList<String> Ips) {
        this.Ips = Ips;
    }
}
