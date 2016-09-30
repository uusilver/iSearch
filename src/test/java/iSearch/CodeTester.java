package iSearch;

/**
 * Created by lijunying on 16/7/10.
 */
public class CodeTester {

    public static void main(String args[]){
        String url = "http://weixin.qq.com/r/Vj-i_pDE0M2vrdSZ92pE/99";
        String getPassCode = url.split("\\/")[5];
        System.out.println(getPassCode);
        String s = "9MPjsK7yon";
        System.out.println(s.length());
        String ss = "http://a.315kc.com/m/r/hl-hj/i.htm?9MPjsK7yon";
        String uniqueCode = ss.split("\\?")[1];
        System.out.println(uniqueCode.length());
    }
}
