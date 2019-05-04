package tools;

import com.alibaba.fastjson.JSON;
import dataHandler.Crawler;
import tools.data.StackList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @projectName: HFUTOntology
 * @packageName: tools
 * @Author: hanqing zhu
 * @Date: 22:14 2019/4/27
 * @Description: 字符串解析工具
 */
public class StringParser {

    /**
     * @Author: hanqing zhu
     * @Date: 17:33 2019/4/27
     * @Return:
     *
     * @Description: 字符串括号匹配算法
     */
    public static Map<String, List<String>> parseMSData(String text){

        /**
         * 存储教师分类及其对应集合
         */
        Map<String,List<String>> teacherCollection=new HashMap<String, List<String>>();
        List<String> teacherNames=null;
        /**
         * “[”入栈，碰到“]”出栈，
         * “"”入栈，碰到“"”出栈，
         * 遇到“,”跳过
         * 如果栈顶元素“[”是位于栈底向上两层，则后面元素是键标签；
         * 如果栈顶元素“[”是位于栈底向上四层，则后面元素是值标签。
         */
        StackList<Character> stackList=new StackList<Character>();

        List<Character> key=new ArrayList<Character>();
        List<Character> value=new ArrayList<Character>();
        int valueCount=0;

        //标志是键标签还是值标签
        boolean isBehindKey=false;

        for (int i=0;i<text.length();i++){
            char e=text.charAt(i);
            //如果是键标签，并且栈顶元素是“"”
            if (stackList.getTotalEle()>0&&e!='"') {
                if (isBehindKey) {
                    if (stackList.peek() == '"') {
                        key.add(e);
                        continue;
                    }
                }
                //如果是值标签
                else {
                    if (stackList.peek() == '"') {
                        if (valueCount == 2) {
                            value.add(e);
                        }
                        continue;
                    }
                }
            }

            if (e=='"'){
                //碰到“"”,且当前栈顶元素不是“"”，入栈
                if (stackList.peek()!='"'){
                    if (!isBehindKey){
                        valueCount++;
                    }
                    stackList.push(e);
                    continue;
                }
                ////碰到“"”,且当前栈顶元素是“"”，出栈
                else{
                    stackList.pop();
                    if (!isBehindKey){
                        if (valueCount==2) {
                            teacherNames.add(charListToString(value));
                            value.clear();
                            valueCount=0;
                        }
                    }

                }
            }

            //碰到“[”入栈
            if (e=='['){
                stackList.push(e);
                //如果栈顶元素“[”是位于栈底向上两层，则后面元素是键标签；
                if (stackList.getTotalEle()==2){
                    isBehindKey=true;
                    teacherNames=new ArrayList<String>();
                }else{
                    isBehindKey=false;
                }
            }

            //碰到“]”出栈
            if (e==']'){
                if (stackList.getTotalEle()==2){
                    teacherCollection.put(charListToString(key),teacherNames);
                    key.clear();
                    teacherNames=null;
                }
                stackList.pop();
                continue;
            }

            //碰到“,”直接略过
            if (e==','){
                continue;
            }

            //其它元素直接略过，主要是指数字元素
        }

        return teacherCollection;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 22:12 2019/4/27
     * @Return:
     *
     * @Description: 将Character类型的list转换成String字符串
     */
    public static String charListToString(List<Character> chars){
        String result=null;
        char [] c=new char[chars.size()];
        for (int i=0;i<chars.size();i++){
            c[i]=chars.get(i);
        }
        result=String.copyValueOf(c);
        return result;
    }

    /*public static void main(String[] args) {
        String test= Crawler.getJson("http://som.hfut.edu.cn/wgp/generalForms/getJsmlDataFormsData.do?ids=1001");
        System.out.println(JSON.parse(test));

        Map<String,List<String>> teacherCollection=parseMSData(test);
        for (Map.Entry entry:teacherCollection.entrySet()){
            System.out.println(removeBrackets((String) entry.getKey())+":");
            System.out.print("      ");
            for (String teacher:(List<String>)entry.getValue()){
                System.out.print(teacher+";");
            }
            System.out.println();
        }

    }*/

    /**
     * @Author: hanqing zhu
     * @Date: 22:37 2019/4/27
     * @Return:
     *
     * @Description: 去括号
     */
    public static String removeBrackets(String text){
        String result=null;
        List<Character> rc=new ArrayList<Character>();
        StackList<Character> chars=new StackList<Character>();
        for (int i=0;i<text.length();i++){
            char c=text.charAt(i);

            if (chars.getTotalEle()==0&&!(c=='('||c=='（')){
                rc.add(c);
                continue;
            }
            if (c=='('||c=='（'){
                chars.push(c);
                continue;
            }
            if (chars.peek()=='('||chars.peek()=='（'){
                continue;
            }
            if (c=='）'||c==')'){
                chars.pop();
            }
        }
        result=charListToString(rc);
        return result;
    }

    /**
     * @Author: hanqing zhu
     * @Date: 17:36 2019/5/3
     * @Return:
     *
     * @Description: list输出字符串
     */
    public static void printListToString(List<String> list){
        for (String term:list){
            System.out.println(term);
        }
    }

    /**
     * @Author: hanqing zhu
     * @Date: 15:37 2019/5/4
     * @Return:
     *
     * @Description: 去除字符串中的空格（停用词处理）
     */
    public static String removeBlank(String  content){
        String result="";
        for (char c:content.toCharArray()){
            if (c!=' '){
                result+=String.valueOf(c);
            }
        }
        return result;
    }

    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p;
        p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }

}
