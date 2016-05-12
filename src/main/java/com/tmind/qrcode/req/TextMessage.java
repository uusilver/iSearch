package com.tmind.qrcode.req;

/**
 * 文本消息
 *
 * @author liufeng
 * @date 2013-05-19
 */
/**
 * @author Vani Li
 */
public class TextMessage extends BaseMessage{
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
