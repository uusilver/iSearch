package iSearch;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;

/**
 * @author Junying Li
 *
 * @Desc: ��Ŀ����������, �鿴ҳ����м��
 */
public class BrowserLaunch {

    private final static String a = "http://localhost:2015/m/r/a/i.htm?1458404292390OF0wd3qQ";
    private final static String t = "http://localhost:2015/m/r/t/i.htm?1458404292390OF0wd3qQ";
    private final static String y = "http://localhost:2015/m/r/y/i.htm?1458404292390OF0wd3qQ";
    private final static String j = "http://localhost:2015/m/r/j/i.htm?1458404292390OF0wd3qQ";
    private final static String mobile = "http://localhost:2015/mobile/index.html";
    private final static String mobile_site = "http://localhost:2015/mobile/index1.html";
    private final static String mobile_search_ok = "http://localhost:2015/mobile/ok.html?1458404292390OF0wd3qQ";

    public static void main(String args[]){
        openURL(a); //ͨ�ñ�ǩ
        openURL(t); //���ӱ�ǩ
        openURL(y); //���ӱ�ǩ
        openURL(j); //�����ǩ
        openURL(mobile); //�ƶ���ѯҳ��
        openURL(mobile_site); //�ƶ�����
        openURL(mobile_search_ok); //�ƶ�����ҳ��
    }

    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
        }
    }

    private static void browse(String url) throws Exception {
        //��ȡ����ϵͳ������
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) {
            //ƻ���Ĵ򿪷�ʽ
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        } else if (osName.startsWith("Windows")) {
            //windows�Ĵ򿪷�ʽ��
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else {
            // Unix or Linux�Ĵ򿪷�ʽ
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
            String browser = null;
            for (int count = 0; count < browsers.length && browser == null; count++)
                //ִ�д��룬��brower��ֵ��������
                //������������̴����ɹ��ˣ�==0�Ǳ�ʾ����������
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
                    browser = browsers[count];
            if (browser == null)
                throw new Exception("Could not find web browser");
            else
                //���ֵ�������Ѿ��ɹ��ĵõ���һ�����̡�
                Runtime.getRuntime().exec(new String[] { browser, url });
        }
    }
}
