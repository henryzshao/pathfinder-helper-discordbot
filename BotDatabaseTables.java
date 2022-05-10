package me.henry.bot;

import java.sql.Connection;
import java.sql.Statement;

public class BotDatabaseTables {
    public static void CharacterTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS characterTable( " +
                    "characterID    SERIAL  NOT NULL," +
                    "characterName  TEXT    NOT NULL," +
                    "characterHP    INTEGER NOT NULL," +
                    "characterBAB   INTEGER NOT NULL," +
                    "characterSTR   INTEGER NOT NULL," +
                    "characterCON   INTEGER NOT NULL," +
                    "characterDEX   INTEGER NOT NULL," +
                    "characterWIS   INTEGER NOT NULL," +
                    "characterINT   INTEGER NOT NULL," +
                    "characterCHA   INTEGER NOT NULL," +
                    "PRIMARY KEY (characterID));";
            stmt.executeUpdate(sql);
            //System.out.println("Character Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void ClassTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS classTable( " +
                    "characterID    INTEGER NOT NULL," +
                    "classID        INTEGER NOT NULL," +
                    "className      TEXT    NOT NULL," +
                    "classLevel     INTEGER NOT NULL," +
                    "FOREIGN KEY (characterID) REFERENCES characterTable(characterID));";
            stmt.executeUpdate(sql);
            //System.out.println("Class Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void EquipmentTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS equipmentTable( " +
                    "characterID    INTEGER NOT NULL," +
                    "equipmentID    SERIAL  NOT NULL," +
                    "equipmentName  TEXT    NOT NULL," +
                    "equipmentSlot  INTEGER NOT NULL," +
                    "FOREIGN KEY (characterID) REFERENCES characterTable(characterID));";
            stmt.executeUpdate(sql);
            //System.out.println("Equipment Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void ConditionTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS conditionTable( " +
                    "characterID    INTEGER NOT NULL," +
                    "conditionID    SERIAL  NOT NULL," +
                    "conditionName  TEXT    NOT NULL," +
                    "duration       INTEGER," +
                    "initiative     INTEGER," +
                    "start          BOOLEAN," +
                    "PRIMARY KEY (characterID, conditionID));";
            stmt.executeUpdate(sql);
            //System.out.println("Condition Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void BonusTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS bonusTable( " +
                    "characterID    INTEGER NOT NULL," +
                    "conditionID    INTEGER NOT NULL," +
                    "bonusName      TEXT    NOT NULL," +
                    "bonusSource    INTEGER," +
                    "bonusValue     INTEGER," +
                    "permanent      BOOLEAN," +
                    "FOREIGN KEY (characterID, conditionID) REFERENCES conditionTable(characterID, conditionID)," +
                    "PRIMARY KEY (characterID, conditionID));";
            stmt.executeUpdate(sql);
            //System.out.println("Bonus Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void MemoNameTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS memoNameTable( " +
                    "memoID     SERIAL  NOT NULL," +
                    "memoName   TEXT    NOT NULL," +
                    "PRIMARY KEY (memoID));";
            stmt.executeUpdate(sql);
            //System.out.println("MemoName Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

    public static void MemoContentTable(){
        try (Connection conn = BotDatabase.openDatabase(); Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS memoContentTable( " +
                    "memoID         INTEGER NOT NULL," +
                    "memoContent    TEXT    NOT NULL," +
                    "FOREIGN KEY (memoID) REFERENCES memoNameTable(memoID));";
            stmt.executeUpdate(sql);
            //System.out.println("MemoContent Table created.");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        /* Ignored */
        /* Ignored */
    }

}
