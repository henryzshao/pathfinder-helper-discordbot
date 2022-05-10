package me.henry.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BotDatabaseMemoQueries {
    public static String NewMemo(String nameMemo, String[] memoArray){
        String query;
        System.out.println("Attempting to create a new memo...");
        //check if memoName already exists
        int memoIDKey = FindMemoIDKey(nameMemo);
        if(memoIDKey!=-1){
            System.out.println("The memo already exists.");
            return "The memo already exists.";
        }
        //entry does not already exist
        query = "INSERT INTO memoNameTable (memoName) " +
                "VALUES ('" + nameMemo + "');";
        BotDatabase.DatabaseInsert(query);
        memoIDKey = FindMemoIDKey(nameMemo);

        System.out.println("New memo created. Inserting content into the memo...");
        for (String s : memoArray) {
            query = String.format("INSERT INTO memoContentTable (memoID, memoContent) " +
                    "VALUES (%d, '%s');", memoIDKey, s);
            BotDatabase.DatabaseInsert(query);
        }

        StringBuilder createdMemo = new StringBuilder();
        for (String s : memoArray) {
            createdMemo.append(s).append(", ");
        }
        String returnedMemo = createdMemo.toString();
        returnedMemo = returnedMemo.replaceAll(", $", "");
        return returnedMemo;

    }

    public static String DeleteMemo(String memoName){
        String query;
        System.out.println("Attempting to locate the memo...");
        //check if memoName already exists
        int memoIDKey = FindMemoIDKey(memoName);
        if(memoIDKey==-1){
            String returnedMemo = "The memo does not exist.";
            System.out.println(returnedMemo);
            return returnedMemo;
        }
        query = "DELETE FROM memoNameTable WHERE memoID = ?;";
        try {
            Connection conn = BotDatabase.openDatabase();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,memoIDKey);
            ps.executeUpdate();
            conn.close();
            ps.close();
        } catch(Exception e){
            System.out.println("DeleteMemo Error");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return "The memo "+memoName+" has been deleted.";
    }

    public static String AddMemo(String memoName, String[] memoArray){
        String query;
        System.out.println("Attempting to locate the memo...");
        int memoIDKey = FindMemoIDKey(memoName);
        if(memoIDKey==-1){
            String returnedMemo = "The memo does not exist.";
            System.out.println(returnedMemo);
            return returnedMemo;
        }
        query = "INSERT INTO memoContentTable (memoID, memoContent) VALUES (?,?);";
        try {
            Connection conn = BotDatabase.openDatabase();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, memoIDKey);
            for(String s : memoArray) {
                ps.setString(2, s);
                ps.executeUpdate();
            }
            conn.close();
            ps.close();
        } catch(Exception e){
            System.out.println("AddMemo Error");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return "The memo "+memoName+" has been updated.";
    }

    public static String SubMemo(String memoName, String[] memoArray){
        String query;
        System.out.println("Attempting to locate the memo...");
        int memoIDKey = FindMemoIDKey(memoName);
        if(memoIDKey==-1){
            String returnedMemo = "The memo does not exist.";
            System.out.println(returnedMemo);
            return returnedMemo;
        }
        query = "DELETE FROM memoContentTable WHERE memoContent LIKE ?;";
        try {
            Connection conn = BotDatabase.openDatabase();
            PreparedStatement ps = conn.prepareStatement(query);
            for(String s : memoArray) {
                ps.setString(1, s);
                ps.executeUpdate();
            }
            conn.close();
            ps.close();
        } catch(Exception e){
            System.out.println("SubMemo Error");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return "The memo "+memoName+" has been updated.";
    }

    public static String DisplayMemo(String memoName){
        String query;

        //check if memo doesn't exist. If it doesn't, return so.
        int memoIDKey = FindMemoIDKey(memoName);
        if(memoIDKey==-1){
            return "The memo you requested does not yet exist.";
        }

        //memo does exist. return all values
        query = "SELECT memoContent " +
                "FROM memoContentTable " +
                "WHERE memoID = " + memoIDKey + ";";
        ResultSet result = BotDatabase.DatabaseSelect(query);
        ArrayList<String> resultList = new ArrayList<>();
        try{
            while(result.next()){
                resultList.add(result.getString("memoContent"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        String[] queryResults = resultList.toArray(new String[0]);
        StringBuilder response = new StringBuilder();
        for (String s : queryResults) {
            response.append(s).append(", ");
        }
        String returnedMemo = response.toString();
        if(returnedMemo.isEmpty()){
            returnedMemo = "Your memo is empty.";
        }
        else{
            returnedMemo = memoName + ": " + returnedMemo;
        }
        returnedMemo = returnedMemo.replaceAll(", $", "");
        return returnedMemo;
    }

    public static int FindMemoIDKey(String nameMemo){
        String query;

        int memoKey = -1;
        query = "SELECT memoID " +
                "FROM memoNameTable " +
                "WHERE memoName LIKE '" + nameMemo + "';";
        ResultSet result = BotDatabase.DatabaseSelect(query);
        try{
            if(result.next()){
                //System.out.println("Got Result Set");
                memoKey = result.getInt("memoID");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        return memoKey;
    }
}
