package leetcode;

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
        String s = "ababba";
//        System.out.println( repeatedSubstringPattern(s));

        System.out.println(s.substring(1, s.length() - 1));
        Stack stack = new Stack();
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
}
