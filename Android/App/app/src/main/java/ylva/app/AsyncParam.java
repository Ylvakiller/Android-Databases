package ylva.app;

import java.net.URL;

/**
 * Created by Ylva on 18-8-2016.
 */
public class AsyncParam{
    URL url;
    String message;
    String username;
    String password;

    AsyncParam(URL url, String message,String username,String password){
        this.url = url;
        this.message = message;
        this.username = username;
        this.password = password;
    }

}
