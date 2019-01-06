package wiki.philosophy.app;

import java.io.IOException;
import java.util.*;


import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class WikiPhilosophy {

    private final static Set<String> visited = new LinkedHashSet<>();


    public static void main(String[] args) throws IOException {
        String source = "https://en.wikipedia.org/wiki/Ski_jumping_at_the_1994_Winter_Olympics_%E2%80%93_Normal_hill_individual";
        int limit = 20;
        if(!findPhilosophy( source, limit)){
            System.out.println("You have reached the limit!");
            for (String page : visited) {
                System.out.println(page);
            }
        }
    }


    public static boolean findPhilosophy(String source, int limit) throws IOException {
        WikiFetcher wf = new WikiFetcher();
        AnchorFetcher af = new AnchorFetcher();

        Elements paragraphs = wf.fetchWikipedia(source);
        for(int i = 0; i < limit; i++){
            Node node = af.fetchValidAnchor(paragraphs);
            if(node == null){
                System.out.println("You've reached a dead end!");
                for (String page : visited) {
                    System.out.println(page);
                }
                return true;
            }
            paragraphs = wf.fetchWikipedia("https://en.wikipedia.org/" + node.attributes().get("href"));
            if(!visited.add(node.attr("title"))){
                for (String page : visited) {
                    System.out.println(page);
                }
                System.out.println("You can not find Philosophy page from "
                        + source + " you've entered a loop");
                return true;
            }
            if(node.attr("title").equals("Philosophy") ||
                    node.attr("title").equals("Philosophical")){
                System.out.println("Philosophy form " + source + " found in " + i + " steps:");
                for (String page : visited) {
                    System.out.println(page);
                }
                return true;
            }
        }
        return false;
    }
}
