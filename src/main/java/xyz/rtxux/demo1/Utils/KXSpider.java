package xyz.rtxux.demo1.Utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.concurrent.TimeUnit;

public class KXSpider {

    private final String baseUrl = "http://www.chinadmd.com/file/awseaiuotpioaoivwszuesio_%d.html";
    public StringBuilder sb = new StringBuilder();

    public int getPage(int page) {
        System.out.println("正在抓取第" + page + "页");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(String.format(baseUrl, page)).get().build();
        try {
            Document document = Jsoup.parse(
                    client.newCall(request).execute().body().string());
            Element element1 = document.body().getElementsByClass("tofu-txt").first();
            for (Element element2 : element1.children()) {
                sb.append(element2.ownText());
                sb.append(" \r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
