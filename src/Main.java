import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) throws IOException {

        Parser parser = new Parser();

        parser.initializeReservedWords();
        parser.getIdentifiers();

        System.out.println("reservedWords List");
        parser.reservedWordsBST.printTree();

        System.out.println("identifiers List");
        parser.identifiersBST.printTree();

    }


}