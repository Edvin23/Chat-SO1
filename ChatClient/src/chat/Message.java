

package chat;

import java.io.Serializable;

/**
 *
 * @author saul
 */
public class Message implements Serializable{
       private short code;
       private String username;
       private String data;
    public Message(){
        super();
    }
    public Message(String username) {
        this.username = username;
    }

    public Message(short code, String username) {
        this.code = code;
        this.username = username;
    }

    public Message(short code, String username, String data) {
        this.code = code;
        this.username = username;
        this.data = data;
    }
    
       
    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
       
    
}
