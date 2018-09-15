import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.*;
import java.math.*;
import java.util.*;

public class P1 {
    public static void main(String... ignored) throws IOException {
        BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
        while((bf.readLine())!=null) {
            int n = Integer.parseInt(bf.readLine());
            if(n==0)
                break;
            int total=0,min=25;
            for(int i=0;i<n;i++) {
                String s=bf.readLine();
                int cnt=0;
                for(int j=1;j<24;j++){
                    if(s.charAt(j)!='X')
                        cnt++;
                }
                total+=cnt;
                if(cnt<min)
                    min=cnt;
            }
            System.out.println(total-min*n);
        }
    }
}