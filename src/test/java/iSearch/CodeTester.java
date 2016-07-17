package iSearch;

/**
 * Created by lijunying on 16/7/10.
 */
public class CodeTester {

    public static void main(String args[]){
        String url = "http://weixin.qq.com/r/Vj-i_pDE0M2vrdSZ92pE/99";
        String getPassCode = url.split("\\/")[5];
        System.out.println(getPassCode);
    }
}
