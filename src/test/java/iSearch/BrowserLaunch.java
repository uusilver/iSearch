package iSearch;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JOptionPane;

/**
 * @author Junying Li
 *
 * @Desc: 项目启动后，运行, 查看页面进行检查
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
        openURL(a); //通用标签
        openURL(t); //桃子标签
        openURL(y); //柚子标签
        openURL(j); //酒类标签
        openURL(mobile); //移动查询页面
        openURL(mobile_site); //移动官网
        openURL(mobile_search_ok); //移动搜索页面
    }

    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
        }
    }

    private static void browse(String url) throws Exception {
        //获取操作系统的名字
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) {
            //苹果的打开方式
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        } else if (osName.startsWith("Windows")) {
            //windows的打开方式。
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else {
            // Unix or Linux的打开方式
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
            String browser = null;
            for (int count = 0; count < browsers.length && browser == null; count++)
                //执行代码，在brower有值后跳出，
                //这里是如果进程创建成功了，==0是表示正常结束。
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
                    browser = browsers[count];
            if (browser == null)
                throw new Exception("Could not find web browser");
            else
                //这个值在上面已经成功的得到了一个进程。
                Runtime.getRuntime().exec(new String[] { browser, url });
        }
    }
}
