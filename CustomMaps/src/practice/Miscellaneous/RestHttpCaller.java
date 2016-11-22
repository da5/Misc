package practice.Miscellaneous;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;

/**
 * Created by dasarindam on 10/26/2016.
 */
public class RestHttpCaller {

    private String url;

    public RestHttpCaller(String url){
        this.url = url;
    }
    public static void main(String[] args) throws Exception{
        RestHttpCaller restHttpCaller = new RestHttpCaller("https://10.152.40.1/api/versions");
        restHttpCaller.execute();
    }

    private HttpsURLConnection getHttpsConnection() throws Exception{
        URL url = new URL(this.url);
        return (HttpsURLConnection) url.openConnection();
    }

    private void printCertificates(HttpsURLConnection httpsURLConnection){
        if(httpsURLConnection!=null){

            try {

                System.out.println("Response Code : " + httpsURLConnection.getResponseCode());
                System.out.println("Cipher Suite : " + httpsURLConnection.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = httpsURLConnection.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void execute()throws Exception{
        HttpsURLConnection httpsURLConnection = getHttpsConnection();
        printCertificates(httpsURLConnection);
        if(httpsURLConnection != null){
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    httpsURLConnection.getInputStream()
                            )
                    );

            String input;
             while((input = bufferedReader.readLine())!=null){
                 System.out.println(input);
             }
             bufferedReader.close();
        }
    }
}
