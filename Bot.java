package me.henry.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Bot extends ListenerAdapter {
    public static void main(String[] arguments)
    {
        try {
            BotDatabase.CreateTable(); //create database tables

            String token = ""; //token here
            JDA api = JDABuilder.createDefault(token).build();
            api.addEventListener(new MessageListener());
        } catch (Exception e)
        {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }


}



