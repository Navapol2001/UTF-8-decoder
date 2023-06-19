package th.co.geniustre.intern.unicode;

import java.util.Map;

public class Unicode {
    private Map<Integer,Character> unicodeChars = Map.of(3585,'ก',3586,'ข',3588,'ค', 3591,'ง');
    public Character getChar(int codePoint){
        return unicodeChars.get(codePoint);
    }

}
