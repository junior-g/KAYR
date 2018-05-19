package com.example.abis.kayr.rushi;
import java.io.IOException;
import java.util.*;
public class searchTrie extends trie{
    public static void fun(List<String> list) throws IOException {
        // TODO code application logic here
        /*String rushikesh="rush";
        rushikesh=rushikesh.substring(4,4);
        System.out.println(rushikesh);*/
        int n,i,q,test,test1;
        root = new TrieNode();
        n=list.size();
        String[] st=new String[n+1];
        for(i=0;i<n;i++){
                insert(list.get(i));
        }


    }
    public static String queary(String s)
    {
        Vector vec,secondIteration=new Vector();
        String input=s;
        vec=getAllString(input);
        vec.add(input);
            for(int i=0;i<vec.size();i++){
                //System.out.println("now is "+vec.get(i));
                secondIteration=getAllString(vec.get(i).toString());
                secondIteration.add(vec.get(i));
                for(int j=0;j<secondIteration.size();j++){
                    String check=secondIteration.get(j).toString();
                    if(search(check)){
                        return check;

                    }
                }
            }
            return "Not Found";
                    

    }

    public static Vector getAllString(String input){
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        Vector vec=new Vector();
        for(int i=0;i<=input.length();i++){
            for(int j=0;j<26;j++){
                if(i+1<=input.length())
                    vec.add(input.substring(0, i)+alphabet[j]+input.substring(Math.min(i+1,input.length()),input.length()));

                vec.add(input.substring(0, i)+alphabet[j]+input.substring(i,input.length()));
            }
            if(i+1<input.length()){
                char y=input.charAt(i),x=input.charAt(i+1);
                vec.add(input.substring(0,i)+x+y+input.substring(i+2,input.length()));
            }
            if(i<input.length()){
                vec.add(input.substring(0,i)+input.substring(i+1,input.length()));
            }
        }
        return vec;
    }
}
