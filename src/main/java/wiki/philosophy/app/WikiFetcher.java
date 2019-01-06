package wiki.philosophy.app;


import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WikiFetcher {
   private long lastRequestTime = -1;
   private long minInterval = 1000;


   public Elements fetchWikipedia(String url) throws IOException {
      sleepIfNeeded();

      Connection conn = Jsoup.connect(url);
      Document doc = conn.get();

      Element content = doc.getElementById("mw-content-text");
      return content.select("p:not(table  p)");
   }


   private void sleepIfNeeded() {
      if (lastRequestTime != -1) {
         long currentTime = System.currentTimeMillis();
         long nextRequestTime = lastRequestTime + minInterval;
         if (currentTime < nextRequestTime) {
            try {
               Thread.sleep(nextRequestTime - currentTime);
            } catch (InterruptedException e) {
               System.err.println("Sleep interrupted");
            }
         }
      }
      lastRequestTime = System.currentTimeMillis();
   }
}