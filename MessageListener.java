package me.henry.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        String[] botArray = SpaceSplitter(content);
        String botCommand = botArray[0];
        String botContent = botArray[1];

        if (botCommand.equals("!ping")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }

        if (botCommand.equals("!shutdown")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Exiting!").queue();
            event.getJDA().shutdown();
        }

        if(botCommand.equals("!newmemo")) {
            System.out.println("\n");
            MessageChannel channel = event.getChannel();
            if(botContent==null){
                channel.sendMessage("!newmemo <MemoName> <Object1>, <Object2>, <Object...>, ...").queue();
                return;
            }
            if(botContent.equals("help")){
                channel.sendMessage("!newmemo <MemoName> <Object1>, <Object2>, <Object...>, ...").queue();
            }
            else{ //otherwise, content has something. split content into memoName and memoContent
                String[] memoArray = SpaceSplitter(botContent);
                String memoName = memoArray[0];
                String memoContent = memoArray[1];
                if(memoContent!=null){ //if memoContent is not empty, make a new memo
                    memoArray = CommaSplitter(memoContent);
                    String responseString = "New Memo: " + memoName + " - ";
                    responseString = responseString + BotDatabaseMemoQueries.NewMemo(memoName, memoArray);
                    channel.sendMessage(responseString).queue();
                }
                else{ //else if memoContent is empty, display the content of the memo
                    String responseString = BotDatabaseMemoQueries.DisplayMemo(memoName);
                    channel.sendMessage(responseString).queue();
                }
            }
        }

        if(botCommand.equals("!memo")){
            System.out.println("\n");
            MessageChannel channel = event.getChannel();
            if(botContent==null){
                channel.sendMessage("!memo <MemoName>").queue();
                return;
            }
            if(botContent.equals("help")){
                channel.sendMessage("This command displays the contents of the specified memo. \n!memo <MemoName>").queue();
            }
            else{ //otherwise, content has something. isolate memoName
                String[] memoArray = SpaceSplitter(botContent);
                String memoName = memoArray[0];
                String responseString = BotDatabaseMemoQueries.DisplayMemo(memoName);
                channel.sendMessage(responseString).queue();
            }
        }

        if(botCommand.equals("!delmemo")){
            System.out.println("\n");
            MessageChannel channel = event.getChannel();
            if(botContent==null){
                channel.sendMessage("Please specify the memo you would like to delete.").queue();
                return;
            }
            if(botContent.equals("help")){
                channel.sendMessage("!delmemo <name of memo to be deleted>").queue();
            }
            else{ //otherwise, content has something. isolate memoName
                String[] memoArray = SpaceSplitter(botContent);
                String memoName = memoArray[0];
                    String responseString = BotDatabaseMemoQueries.DeleteMemo(memoName);
                    channel.sendMessage(responseString).queue();
            }
        }

        if(botCommand.equals("!memo+")){
            System.out.println("\n");
            MessageChannel channel = event.getChannel();
            if(botContent==null){
                channel.sendMessage("Please specify the memo you would like to update.").queue();
                return;
            }
            if(botContent.equals("help")){
                channel.sendMessage("!memo+ <name of memo to be updated> <objects to add>").queue();
            }
            else{ //otherwise, content has something. split content into memoName and memoContent
                String[] memoArray = SpaceSplitter(botContent);
                String memoName = memoArray[0];
                String memoContent = memoArray[1];
                if(memoContent!=null){ //if memoContent is not empty, update memo
                    memoArray = CommaSplitter(memoContent);
                    String responseString = BotDatabaseMemoQueries.AddMemo(memoName, memoArray);
                    channel.sendMessage(responseString).queue();
                }
            }
        }

        if(botCommand.equals("!memo-")){
            System.out.println("\n");
            MessageChannel channel = event.getChannel();
            if(botContent==null){
                channel.sendMessage("Please specify the memo you would like to update.").queue();
                return;
            }
            if(botContent.equals("help")){
                channel.sendMessage("!memo+ <name of memo to be updated> <objects to delete>").queue();
            }
            else{ //otherwise, content has something. split content into memoName and memoContent
                String[] memoArray = SpaceSplitter(botContent);
                String memoName = memoArray[0];
                String memoContent = memoArray[1];
                if(memoContent!=null){ //if memoContent is not empty, update memo
                    memoArray = CommaSplitter(memoContent);
                    String responseString = BotDatabaseMemoQueries.SubMemo(memoName, memoArray);
                    channel.sendMessage(responseString).queue();
                }
            }
        }

    }



    public String[] SpaceSplitter(String original){
        String first;
        String second = null;
        if(original.contains(" ")){
            first = original.substring(0, original.indexOf(" "));
            second = original.substring(original.indexOf(" ")+1);
        }
        else{
            first = original;
        }
        return new String[] {first, second};
    }

    public String[] CommaSplitter(String original){
        return original.trim().split("\\s*,\\s*");
    }
}
