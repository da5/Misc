package practice.Miscellaneous;

/**
 * Created by dasarindam on 11/11/2016.
 */
public class Misc {

    public static void main(String args[]){
        String version = ".5.2";
        String firstComponent = version.split("\\.")[0];
        System.out.println(Integer.parseInt(firstComponent) >= 6);
    }
}
