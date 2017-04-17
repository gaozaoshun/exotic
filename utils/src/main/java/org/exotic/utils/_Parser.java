package org.exotic.utils;

/**
 * @author 高灶顺
 * @date 2016-12-7
 */
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
public class _Parser extends ParserCallback {    //继承ParserCallback，解析结果驱动这些回调方法
    protected String base;
    protected boolean isImg = false;
    protected boolean isParagraph = false;
    protected static Vector<String> element = new Vector<String>();
    protected static String paragraphText = new String();
    public _Parser() {
    }
    public static String getParagraphText() {
        return paragraphText;
    }
    public void handleComment(char[] data, int pos) {
    }
    public void handleEndTag(HTML.Tag t, int pos) {
        if (t == HTML.Tag.P) {
            if (isParagraph) {
                isParagraph = false;
            }
        } else if (t == HTML.Tag.IMG) {
            if (isImg) {
                isImg = false;
            }
        }
    }
    public void handleError(String errorMsg, int pos) {
    }
    public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        handleStartTag(t, a, pos);
    }
    public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        if (t == HTML.Tag.P) {
            isParagraph = true;
        } else if ((t == HTML.Tag.IMG)) {
            String src = (String) a.getAttribute(HTML.Attribute.SRC);
            if (src != null) {
                element.addElement(src);
                isImg = true;
            }
        }
    }
    public void handleText(char[] data, int pos) {
        if (isParagraph) {
            String tempParagraphText = new String(data);
            if (paragraphText != null) {
                element.addElement(tempParagraphText);
            }
        }
    }

    public static String startParse(String sHtml,int len) {
        try {
            ParserDelegator ps = new ParserDelegator();//负责每次在调用其 parse 方法时启动一个新的 DocumentParser
            ParserCallback parser = new _Parser();//解析结果驱动这些回调方法。
            ps.parse(new StringReader(sHtml), parser, true);//解析给定的流并通过解析的结果驱动给定的回调。
            Vector link = element;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < link.size(); i++) {
                sb.append(link.get(i));
            }
            String str = sb.toString();
            return str.substring(0,str.length()>len?len:str.length());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String args[]) {
        try {
            String sHtml = new String("<p>请输入文章内容。。。<p><h class='555'>sdsd</h>");
            System.out.println( startParse(sHtml,200));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
