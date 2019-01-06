package wiki.philosophy.app;


import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;


public class AnchorFetcher {
    private final NodeValidator nv;


    public AnchorFetcher(){
        this.nv = new NodeValidator();
    }


    public Node fetchValidAnchor(Elements paragraphs) {
        for (Node paragraph : paragraphs) {
            Iterable<Node> iterator = new WikiNodeIterable(paragraph);
            for (Node node : iterator) {
                if(nv.isValid(node)){
                    return node;
                }
            }
        }
        return null;
    }
}
