package Utils;
import org.neo4j.driver.*;

public class NeoManager {
    private NeoManager(){
    }

    private static class NeoManagerHolder{
        private static final NeoManager instance = new NeoManager();
    }

    public static NeoManager getInstance(){
        return NeoManagerHolder.instance;
    }

}
