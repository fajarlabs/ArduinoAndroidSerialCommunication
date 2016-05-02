package us.fajar.app.socket.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by masfajar on 4/30/2016.
 */
public class CommandSenderUtil {
    String ip;
    String port;
    SocketClientUtil socket;

    public CommandSenderUtil(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public Boolean connect() {
        // Create instance socket connection
        socket = new SocketClientUtil(this.ip,this.port);
        return socket.connect();
    }

    public String sendData(String switchName, String commandValue, String options) {
        // Create new JSONObject
        JSONObject obj = new JSONObject();
        // Check error parse to json
        if(options.equals("arduino")) {
            try {
                obj.put("switchName", switchName);
                obj.put("commandValue", commandValue);
                obj.put("synchronizer","N");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(options.equals("synchronize")) {
            try {
                obj.put("switchName", switchName);
                obj.put("commandValue", commandValue);
                obj.put("synchronizer", "Y");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Send Data
        socket.sendRequest(obj.toString());

        String receive = socket.receiveData();
        // Disconnect
        socket.disconnect();

        return receive;
    }
}
