import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    static Document page;

    static {
        try {
            page = getPage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document getPage() throws IOException{
        String url = "https://www.kaskelenec.kz/weather";
        Document page = Jsoup.parse(new URL(url),5000);
        return page;
    }
    public static String[] ZakatRassvetInfo(){
        String info = page.select("div[class=weather-line-mini__info]").text();
        String[] infoMassiv =info.split(" ");
        return infoMassiv;
    }
    public static String[] PhotoUrl(){
        String info = page.select("img").text();
        String[] infoMassiv =info.split(" ");
        return infoMassiv;
    }

    public static ArrayList<String> AllDegree(){
        String info = page.select("div[class=weather-line__temp]").text();
        String[] infoMassiv =info.split(" ");
        ArrayList<String> infoM = new ArrayList<>();
        for(int i=0;i<infoMassiv.length;i+=2){
            infoM.add(infoMassiv[i]+infoMassiv[i+1]);
        }
        return infoM;

    }
    public static String[] AllDataAboutWeather(){
        String info = page.select("div[class=weather-line__info]").text();
        String[] infoMassiv =info.split("рт.ст.");
        return infoMassiv;

    }
    public static String[] DaysNumberNameWeek(){
        String info = page.select("div[class=tabs-item__day]").text();
        String[] infoMassiv =info.split(" ");
        return infoMassiv;

    }
    public static String[] DaysNumber(){
        String info = page.select("span[class=block-text]").text();
        String[] infoMassiv = info.split(" ");
        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < infoMassiv.length; i++) {
            if (infoMassiv[i].matches("\\d+")) {
                dates.add(infoMassiv[i] + " " + infoMassiv[i+1]);
                i++; // пропустить следующий элемент, так как мы уже добавили его
            } else {
                dates.add(infoMassiv[i]);
            }
        }
        return dates.toArray(new String[0]);
    }

    public static void savePhotos(String urlphoto) throws IOException{
        String filename = "src/main/java/images.jpg";
        FileOutputStream fout = null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream((new URL(urlphoto)).openStream());
            fout =new FileOutputStream(filename);
            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data,0,1024))!=-1){
                fout.write(data,0,count);
                fout.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fout.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {

//        String data = page.select("div[class=f4]").first().text();
//        Elements dayInfoZakatRassvet = page.select("td[]");
//        System.out.println(page);
//        String[] linkPhotos = page.select("");
//        for (String i:)
//        String urlphoto = "https://media.cdnandroid.com/b3/e0/59/dc/imagen-sphoto-0thumb.jpg";
        System.out.println(Arrays.toString(PhotoUrl()));
    }
}








