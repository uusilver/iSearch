package iSearch;

/**
 * Created by lijunying on 16/9/4.
 */
public class HongbaoTester {

    public static void main(String args[]){
        System.out.println(sendRedPackage("1", "123"));
        System.out.println(sendRedPackage("2", "123"));
        System.out.println(sendRedPackage("3", "123"));
        System.out.println(sendRedPackage("5", "123"));
        System.out.println(sendRedPackage("10", "123"));
        System.out.println(sendRedPackage("50", "123"));
        System.out.println(sendRedPackage("90", "123"));
        System.out.println(sendRedPackage("91", "123"));
        System.out.println(sendRedPackage("99", "123"));
        System.out.println(sendRedPackage("100", "123"));
        System.out.println("--------------------------");
        System.out.println(test("1"));
        System.out.println(test("2"));
        System.out.println(test("3"));
        System.out.println(test("5"));
        System.out.println(test("10"));
        System.out.println(test("50"));
        System.out.println(test("90"));
        System.out.println(test("91"));
        System.out.println(test("99"));



    }

    private static String sendRedPackage(String lotteryDesc, String uniqueCode){
        String result = null;
        if("1".equals(lotteryDesc)){
            //发送一元红包
            result = "恭喜您获得了一元现金红包奖励";
        }else if("2".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了两元现金红包奖励";
        }else if("3".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了三元现金红包奖励";
        }else if("5".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了五元现金红包奖励";
        }else if("10".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了十元现金红包奖励";
        }else if("50".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了五十元现金红包奖励";
        }else if("90".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了山地自行车一辆，识别码:\"+uniqueCode+\"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系官方对付，联系电话:025-XXXXXXX";
        }else if("91".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了手机一台，识别码:\"+uniqueCode+\"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系官方对付，联系电话:025-XXXXXXX";
        }else if("99".equals(lotteryDesc)){
            //发送两元红包
            result = "恭喜您获得了泰国双飞六日游，识别码:"+uniqueCode+"，请保留好此条消息，凭完好的酒瓶和中奖二维码标签实物联系官方对付，联系电话:025-XXXXXXX";
        }

        return  result;
    }

    private static String  test(String code){
        String respContent = null;
        if("1".equals(code)){
            respContent = code;
        }else if("2".equals(code)){
            respContent = code;
        }else if("3".equals(code)){
            respContent = code;
        }else if("5".equals(code)){
            respContent = code;
        }else if("10".equals(code)){
            respContent = code;
        }else if("50".equals(code)){
            respContent = code;
        }else if("90".equals(code)){
            respContent = code;
        }else if("91".equals(code)){
            respContent = code;
        }else if("99".equals(code)){
            respContent = code;
        }
        return  respContent;
    }
}
