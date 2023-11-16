import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class SimpleBot extends TelegramLongPollingBot {
    static String[] todayNumber = Parser.DaysNumber();
    static String[] todayNumberNamesWeek = Parser.DaysNumberNameWeek();
    static String[] AllDataAboutWeather =Parser.AllDataAboutWeather();
    static ArrayList<String> Degrees =Parser.AllDegree();
    static  String[] ZakatRassvetInf = Parser.ZakatRassvetInfo();
    static String day = todayNumber[0]+" "+"("+todayNumberNamesWeek[1]+")"+"-"+todayNumber[6]+" "+"("+todayNumberNamesWeek[6]+")";
    static int id = 0;



    public void onUpdateReceived(Update update) {
        //System.out.println(update.getMessage().getFrom().getFirstName());
        String command =update.getMessage().getText();
        SendMessage responce = new SendMessage();
        responce.setChatId(update.getMessage().getChatId().toString());
        if(command.equals("/start")){
            String message = "Привет,"+update.getMessage().getFrom().getFirstName()+"!";
            responce.setText(message);
            setButtons(responce);
            try {
                execute(responce);
            }catch (TelegramApiException E){
                E.printStackTrace();
            }
        }


        if (update.getMessage().getText().equals("/today") || update.getMessage().getText().equals("Today")) {
            String day ="📅 " +todayNumber[0]+" "+"("+todayNumberNamesWeek[0]+")";
            String[] daydate ={"🌃 Ночью :","🌅 Утром :","☀️ Днем :","🌉 Вечером :"};
            StringBuilder messageWeather = new StringBuilder();
            messageWeather.append("\n").append(day).append("\n");
            messageWeather.append("\n");
            for(int i=0;i<4;i++){
                messageWeather.append(daydate[i]).append(Degrees.get(i)).append(" ").append(AllDataAboutWeather[i]).append("\n").append("\n");
            }
            responce.setText(messageWeather.toString());
            try {
                execute(responce);
            }catch (TelegramApiException E){
                E.printStackTrace();
            }




            String[] ZakatRassvetInfWords = {"☀️ Восход : ", "🌇 Закат : ", "✨ Видимость луны : ", "🌛 Фаза луны : "};
            int k = 0;
            StringBuilder messageZakatRass = new StringBuilder();
            for (String i : ZakatRassvetInfWords) {
                messageZakatRass.append(i).append(ZakatRassvetInf[k++]).append("\n");
            }
            responce.setText(messageZakatRass.toString());
                try {
                    execute(responce);
                }catch (TelegramApiException E){
                    E.printStackTrace();
                }

        }
        if (update.getMessage().getText().equals("/tomorrow") || update.getMessage().getText().equals("Tomorrow")) {
            String day ="📅 " + todayNumber[1]+" "+"("+todayNumberNamesWeek[1]+")";
            String[] daydate ={"🌃 Ночью :","🌅 Утром :","☀️ Днем :","🌉 Вечером :"};
            int k=0;
            StringBuilder messageWeather = new StringBuilder();
            messageWeather.append("\n").append(day).append("\n").append("\n");
            for(int i=4;i<8;i++){
                messageWeather.append(daydate[k++]).append(Degrees.get(i)).append(" ").append(AllDataAboutWeather[i]).append("\n").append("\n");
            }
            responce.setText(messageWeather.toString());
            try {
                execute(responce);
            }catch (TelegramApiException E){
                E.printStackTrace();
            }


            String[] ZakatRassvetInfWords = {"☀️ Восход : ", "🌇 Закат : ", "✨ Видимость луны : ", "🌛 Фаза луны : "};
            k = 4;

            StringBuilder message = new StringBuilder();
            for (String i : ZakatRassvetInfWords) {
                message.append(i).append(ZakatRassvetInf[k++]).append("\n");
            }
            responce.setText(message.toString());
                try {
                    execute(responce);
                }catch (TelegramApiException E){
                    E.printStackTrace();
                }

        }
        if(update.getMessage().getText().equals("/week") || update.getMessage().getText().equals("Week")){
            String[] WeekPrint = weekPrint(todayNumber,todayNumberNamesWeek, Degrees,AllDataAboutWeather);
            for(String i:WeekPrint){
                responce.setText(i);
                try {
                    execute(responce);
                }catch (TelegramApiException E){
                    E.printStackTrace();
                }
            }
        }
        if(command.equals("/about")){
            responce.setText("Hello my dear,"+update.getMessage().getFrom().getFirstName()+"!"+"\n");
            responce.setText("I am Ruslan.It's my pet project.This bot gets data from kaskelenec.kz/weather and send you.I am very happy because you use my project!)");
            try {
                execute(responce);
            }catch (TelegramApiException E){
                E.printStackTrace();
            }
        }
    }
    public static String[] weekPrint(String[] todayNumber, String[] todayNumberNamesWeek, ArrayList<String> Degrees, String[] AllDataAboutWeather) {
        String[] daydate ={"🌃 Ночью :","🌅 Утром :","☀️ Днем :","🌉 Вечером :"};
        StringBuilder messageWeather = new StringBuilder();
        int cs16 = 0;
        int k = 0;
        while (cs16<7 && k!=21){
            String day ="📅 " + todayNumber[cs16]+" ("+todayNumberNamesWeek[cs16]+")";
            messageWeather.append("\n");
            messageWeather.append(day).append("\n");
            messageWeather.append("\n");
            for(int j = 0; j < 4; j++) {
                messageWeather.append(daydate[j]).append(Degrees.get(k)).append(" ").append(AllDataAboutWeather[k]).append("\n").append("\n");
                k+=1;
            }
            cs16++;
            messageWeather.append("xx");
        }
        String[] messageWeatherMain = messageWeather.toString().split("xx");
        return messageWeatherMain;
    }
    public synchronized void setButtons(SendMessage sendMessage) {
        // Create a keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Create a list of keyboard rows
        List<KeyboardRow> keyboard = new ArrayList<>();
        // First keyboard row
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        // Add buttons to the first keyboard row
        keyboardFirstRow.add(new KeyboardButton("Today"));
        keyboardFirstRow.add(new KeyboardButton("Tomorrow"));

        // Second keyboard row
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        // Add the buttons to the second keyboard row
        keyboardThirdRow.add(new KeyboardButton("Week"));


        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardThirdRow);
        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
    }




    @Override
    public String getBotUsername() {
        // TODO
        return "KaskelenWeatherBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6888670279:AAE6qaT4gAusoMMU1zPh9Pl8MrgtjUIbo8Q";
    }
}
