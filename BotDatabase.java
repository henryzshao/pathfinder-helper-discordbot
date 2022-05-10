package me.henry.bot;

import java.sql.*;

public class BotDatabase {

    public static Connection openDatabase() {
        Connection databaseConnection = null;
        try {
            String dbURL = "jdbc:postgresql://localhost:5432/botDB";
            String user = "postgres";
            String pass = "990528";
            databaseConnection = DriverManager.getConnection(dbURL, user, pass);
            //System.out.println("Opened database successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return databaseConnection;
    }

    public static void DatabaseInsert(String query){
        try {
            Connection conn = BotDatabase.openDatabase();
            //System.out.println("Create connection");
            Statement stmt = conn.createStatement();
            System.out.println("Create statement: " + query);
            stmt.executeUpdate(query);
            System.out.println("Execute query");
        }
        catch(Exception e){
            System.out.println("Caught Exception");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public static ResultSet DatabaseSelect(String query){
        ResultSet result = null;
        try {
            Connection conn = BotDatabase.openDatabase();
            //System.out.println("Create connection");
            Statement stmt = conn.createStatement();
            System.out.println("Create statement: " + query);
            result = stmt.executeQuery(query);
            System.out.println("Execute query");
        }
        catch(Exception e){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return result;
    }



    public static void CreateTable(){
        try {
            BotDatabaseTables.CharacterTable();
            BotDatabaseTables.ClassTable();
            BotDatabaseTables.EquipmentTable();
            BotDatabaseTables.ConditionTable();
            BotDatabaseTables.BonusTable();
            BotDatabaseTables.MemoNameTable();
            BotDatabaseTables.MemoContentTable();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }


}
