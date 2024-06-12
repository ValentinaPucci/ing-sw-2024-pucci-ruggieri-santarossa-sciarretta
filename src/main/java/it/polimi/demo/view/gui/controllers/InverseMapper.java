package it.polimi.demo.view.gui.controllers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InverseMapper {
    private static final Map<String,int[]> inverseMappableAreas = new HashMap<>();

    static {
        inverseMappableAreas.put("0,0", new int[]{400, 300});
        inverseMappableAreas.put("0,1", new int[]{470, 300});
        inverseMappableAreas.put("1,1",new int[]{470, 340});
        inverseMappableAreas.put("1,0",new int[]{400, 340});
        inverseMappableAreas.put("-1,0", new int[]{400, 260});
        inverseMappableAreas.put("-1,-1",new int[]{330, 260});
        inverseMappableAreas.put("0,-1",new int[]{330, 300});
        inverseMappableAreas.put("1,-1",new int[]{330, 340});
        inverseMappableAreas.put("1,-2", new int[]{260, 340});
        inverseMappableAreas.put("0,-2", new int[]{260, 300});
        inverseMappableAreas.put("-1,-2", new int[]{260, 260});
        inverseMappableAreas.put("-2,0", new int[]{400, 220});
        inverseMappableAreas.put("-2,-1", new int[]{330, 220});
        inverseMappableAreas.put("-3,-1", new int[]{330, 180});
        inverseMappableAreas.put("-3,0", new int[]{400, 180});
        inverseMappableAreas.put("-2,1", new int[]{470, 220});
        inverseMappableAreas.put("-1,1",new int[]{470, 260});
        inverseMappableAreas.put("-1,2", new int[]{540, 260});
        inverseMappableAreas.put("0,2", new int[]{540, 300});
        inverseMappableAreas.put("1,2", new int[]{540, 340});
        inverseMappableAreas.put("2,2", new int[]{540, 380});
        inverseMappableAreas.put("2,1", new int[]{470, 380});
        inverseMappableAreas.put("2,0", new int[]{400, 380});
        inverseMappableAreas.put("2,-1", new int[]{330, 380});
        inverseMappableAreas.put("2,-2", new int[]{260, 380});
        inverseMappableAreas.put("-2,2", new int[]{540, 220});
        inverseMappableAreas.put("-2,3", new int[]{610, 220});
        inverseMappableAreas.put("-1,3", new int[]{610, 260});
        inverseMappableAreas.put("0,3", new int[]{610, 300});
        inverseMappableAreas.put("1,3", new int[]{610, 340});
        inverseMappableAreas.put("2,3", new int[]{610, 380});
        inverseMappableAreas.put("3,3", new int[]{610, 420});
        inverseMappableAreas.put("3,2", new int[]{540, 420});
        inverseMappableAreas.put("3,1", new int[]{470, 420});
        inverseMappableAreas.put("3,0", new int[]{400, 420});
        inverseMappableAreas.put("3,-1", new int[]{330, 420});
        inverseMappableAreas.put("3,-2", new int[]{260, 420});
        inverseMappableAreas.put("4,-2", new int[]{260, 460});
        inverseMappableAreas.put("4,-1", new int[]{330, 460});
        inverseMappableAreas.put("4,0", new int[]{400, 460});
        inverseMappableAreas.put("4,1", new int[]{470, 460});
        inverseMappableAreas.put("5,1", new int[]{470, 500});
        inverseMappableAreas.put("5,2", new int[]{540, 500});
        inverseMappableAreas.put("5,3", new int[]{610, 500});
        inverseMappableAreas.put("4,3", new int[]{610, 460});

    }


    public int[] getInverseMappedPosition(String key) {
        return inverseMappableAreas.get(key);
    }

}
