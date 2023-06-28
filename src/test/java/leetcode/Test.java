package leetcode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meteor.jsherp.constant.BusinessConstant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 刘鑫
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
//        String s = "[10][20][55][40][100]";
//        List nums = nums(s);
//        nums.stream().forEach(System.out::println);
//        String s = "{'name':'jack','age':''}";
//        People people = JSONObject.parseObject(s, People.class);
//        System.out.println(people.getAge());
        char c = 10 + 'a';
        System.out.println(Character.toString(c));

    }

    //先遍历物品，再遍历背包
    @org.junit.Test
    public void testCompletePack(){
        int[] weight = {1, 3, 4};
        int[] value = {7, 20, 30};
        int bagWeight = 5;
        int[] dp = new int[bagWeight + 1];
        for (int i = 0; i < weight.length; i++){ // 遍历物品
            for (int j = weight[i]; j <= bagWeight; j++){ // 遍历背包容量
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
            System.out.println(Arrays.toString(dp));
        }
        for (int maxValue : dp){
            System.out.println(maxValue + "   ");
        }
    }

    //先遍历背包，再遍历物品
    @org.junit.Test
    public void testCompletePackAnotherWay(){
        int[] weight = {1, 3, 4};
        int[] value = {7, 20, 30};
        int bagWeight = 5;
        int[] dp = new int[bagWeight + 1];
        for (int i = 1; i <= bagWeight; i++){ // 遍历背包容量
            for (int j = 0; j < weight.length; j++){ // 遍历物品
                if (i - weight[j] >= 0){
                    dp[i] = Math.max(dp[i], dp[i - weight[j]] + value[j]);
                }
            }
            System.out.println(Arrays.toString(dp));
        }
        for (int maxValue : dp){
            System.out.println(maxValue + "   ");
        }
    }
    public static String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        int length = chars.length;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' '){
                length += 2;
            }
        }
        char[] res = new char[length];
        for (int i = length - 1, j = chars.length - 1; i >= 0; i--) {
            if (chars[j] == ' '){
                res[i--] = '0';
                res[i--] = '2';
                res[i] = '%';
                j--;
            }else {
                res[i] = chars[j--];
            }
        }
        s.replaceAll(" ","%20");
        return new String(res);
    }
    public static String reverseWords(String s) {
//        StringBuilder res = new StringBuilder();
//        char[] chars = s.toCharArray();
//        int index = chars.length - 1;
//        int end;
//        for (int i = chars.length - 1; i >= 0 ;) {
//            while (i >= 0 && chars[i] == ' '){
//                i--;
//            }
//            if(i < 0){
//                break;
//            }
//            end = i;
//            while (i >= 0 && chars[i] != ' '){
//                i--;
//            }
//            index = i + 1;
//            if(res.length() > 0){
//                res.append(" ");
//            }
//            res.append(chars, index, end - index + 1);
//        }
//        return res.toString();
        char[] res = new char[s.length()];
        char[] chars = s.toCharArray();
        int right = 0;
        int left = chars.length - 1;
        int index;
        while (left >= 0){
            while (left >= 0 && chars[left] == ' '){
                left--;
            }
            if (left < 0){
                break;
            }
            index = left;
            while (left >= 0 && chars[left] != ' ') {
                left--;
            }
            if(right != 0){
                res[right++] = ' ';
            }
            for (int i = left + 1; i <= index; i++) {
                res[right++] = chars[i];
            }
        }
        return new String(res, 0, right);
    }
    public static String reverseLeftWords(String s, int n) {
//        char[] chars = s.toCharArray();
//        char[] res = new char[chars.length];
//        int index = 0;
//        for (int i = n; i < chars.length; i++) {
//            res[index++] = chars[i];
//        }
//        for (int i = 0; i < n; i++) {
//            res[index++] = chars[i];
//        }
//        return new String(res);
        char[] chars = s.toCharArray();
        int start;
        if(s.length() >= 2 * n){
            start = 0;
            for (int i = n; i < chars.length; i++) {
                char temp = chars[start];
                chars[start] = chars[i];
                chars[i] = temp;
                start++;
            }
        }else {
            start = n;
            for (int i = 0; i < n; i++) {
                char temp = chars[start];
                chars[start] = chars[i];
                chars[i] = temp;
                start = start >= chars.length - 1 ? n : start + 1;
            }
        }
        return new String(chars);
    }
    public static String reverseLeftWords2(String s, int n) {
        char[] chars = s.toCharArray();
        reverse(chars, 0, chars.length - 1);
        reverse(chars, 0, n - 1);
        reverse(chars, n, chars.length - 1);
        return new String(chars);
    }
    public static void reverse(char[] chars, int start, int end){
        int right = end;
        for(int i = start; i <= end; i++){
            char temp =chars[i];
            chars[i] = chars[right];
            chars[right--] = temp;
        }
    }

    public static List nums(String s){
        String regex = "(\\[[^\\]]*\\])";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(s);
        ArrayList<Object> objects = new ArrayList<>();
        while (matcher.find()){
            objects.add(matcher.group().substring(1, matcher.group().length()-1));
        }
        return objects;
    }

    public static int strStr(String haystack, String needle) {
        int[] next = new int[needle.length()];
        getNext(next, needle);
        char[] hChar = haystack.toCharArray();
        char[] nChar = needle.toCharArray();
        int start = 0;
        while(start < hChar.length){
            int nStart = 0;
            while(true){
                if(nStart > 0 && hChar[start] != nChar[nStart]){
                    nStart = next[nStart - 1];
                }else if( hChar[start] == nChar[nStart] ){
                    start++;
                    nStart++;
                    if(nStart >= nChar.length){
                        return start - nStart;
                    }
                    if(start >= hChar.length){
                        break;
                    }
                }else{
                    break;
                }
            }
            start++;
        }
        return -1;
    }
    public static boolean repeatedSubstringPattern(String s) {
        int[] next = new int[s.length()];
        getNext(next, s);
        for(int i = 1; i < s.length(); i++){
            if(next.length % i != 0){
                continue;
            }
            int j = 2 * i - 1;
            if(j >= next.length){
                break;
            }
            for(int count = 1; j < next.length; j = j + i, count++){
                if(next[j] != i * count){
                    break;
                }
            }
            if(j >= next.length){
                return true;
            }
        }
        return false;
    }
    public static void getNext(int[] next, String s){
        Stack stack = new Stack();
        stack.peek();
        char[] c = s.toCharArray();
        int j = 0;
        next[0] = j;
        for(int i = 1; i < c.length; i++){
            while(j > 0 && c[i] != c[j]){
                j = next[j - 1];
            }
            if(c[i] == c[j]){
                j++;
            }
            next[i] = j;
        }
    }
    @org.junit.Test
    public void dateTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BusinessConstant.DEFAULT_DATETIME_FORMAT);
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate firstDayOfYear = now.with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDayOfYear = now.with(TemporalAdjusters.lastDayOfYear());
        String todayBegin = formatter.format(now.atTime(0, 0, 0));
        String todayEnd = formatter.format(now.atTime(23, 59, 59));
        String yesterdayBegin = formatter.format(yesterday.atTime(0, 0, 0));
        String yesterdayEnd = formatter.format(yesterday.atTime(23, 59, 59));
        String monthBegin = formatter.format(firstDayOfMonth.atTime(0, 0, 0));
        String monthEnd = formatter.format(lastDayOfMonth.atTime(23, 59, 59));
        String yearBegin = formatter.format(firstDayOfYear.atTime(0, 0, 0));
        String yearEnd = formatter.format(lastDayOfYear.atTime(23, 59, 59));
        LocalDate of = LocalDate.of(2004, 2, 1);
        LocalDate firstDayOf = of.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOf = of.with(TemporalAdjusters.lastDayOfMonth());
        System.out.printf("今天是%s - %s\n",todayBegin, todayEnd);
        System.out.printf("昨天是%s - %s\n",yesterdayBegin, yesterdayEnd);
        System.out.printf("这个月是%s - %s\n",monthBegin, monthEnd);
        System.out.printf("今年是%s - %s\n",yearBegin, yearEnd);
        System.out.printf("二月是%s - %s\n",firstDayOf, lastDayOf);
        System.out.println(yesterday.range(ChronoField.DAY_OF_MONTH));
    }

    @org.junit.Test
    public void jsonTest(){
        String test = "{'name': ''}";
        JSONObject jsonObject = JSONObject.parseObject(test);
        Object name = jsonObject.get("name");

    }
}
