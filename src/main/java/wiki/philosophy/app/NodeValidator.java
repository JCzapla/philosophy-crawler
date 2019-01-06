package wiki.philosophy.app;


import org.jsoup.nodes.Node;


public class NodeValidator {
    private boolean isInParenthesis;


    public NodeValidator(){
        this.isInParenthesis = false;
    }


    public boolean isValid(Node node){
        if(node.nodeName().equals("#text")) {
            findIfIsInParenthesis(node.toString());
            if (isInParenthesis) {
                return false;
            }
        }
        if(node.nodeName().equals("a") &&
                containsValidAttrs(node) &&
                !isInParenthesis){
            return true;
        }
        return false;
    }


    private boolean containsValidAttrs(Node node){
        if(node.attributes().get("href").contains("cite_note") ||
                node.attributes().get("title").contains("(page does not exist)")){
            return false;
        }
        else return true;
    }

    private void findIfIsInParenthesis(String content){
        if((content.contains("(") || content.contains("[")) &&
                (content.contains(")") || content.contains("]"))){
            isInParenthesis = false;
        }
        else if(isInParenthesis && (content.contains(")") || content.contains("]"))){
            isInParenthesis = false;
        }
        else if(content.contains("(") || content.contains("[")){
            isInParenthesis = true;
        }
    }
}
