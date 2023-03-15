import java.io.IOException;

public class Ville {
    public static void main(String[] args) throws IOException {
        String pythonScriptPath="../recherche ville.py";
        String[] cmd=new String[3];
        cmd[0]="python3";
        cmd[1]=pythonScriptPath;
        cmd[2]="Toulouse";
        System.out.println(cmd[0]);
        Runtime rt=Runtime.getRuntime();
        Process  pr=rt.exec(cmd);
        System.out.println(pr);
    }
}
