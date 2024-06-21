package it.polimi.demo.view.gui.controllers;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InverseMapper {
    private static final Map<String,int[]> inverseMappableAreas = new HashMap<>();

    static {
        inverseMappableAreas.put("-5,0", new int[]{400, 100});
        inverseMappableAreas.put("-5,-1", new int[]{330, 100});
        inverseMappableAreas.put("-5,-2", new int[]{260, 100});
        inverseMappableAreas.put("-5,1", new int[]{470, 100});
        inverseMappableAreas.put("-5,2", new int[]{540, 100});
        inverseMappableAreas.put("-5,3", new int[]{610, 100});
        inverseMappableAreas.put("-5,4", new int[]{680, 100});

        inverseMappableAreas.put("-4,4", new int[]{680, 140});
        inverseMappableAreas.put("-3,4", new int[]{680, 180});
        inverseMappableAreas.put("-2,4", new int[]{680, 220});
        inverseMappableAreas.put("-1,4", new int[]{680, 260});

        inverseMappableAreas.put("0,4", new int[]{680, 300});
        inverseMappableAreas.put("1,4", new int[]{680, 340});
        inverseMappableAreas.put("2,4", new int[]{680, 380});
        inverseMappableAreas.put("3,4", new int[]{680, 420});
        inverseMappableAreas.put("4,4", new int[]{680, 460});
        inverseMappableAreas.put("5,4", new int[]{680, 500});

        inverseMappableAreas.put("6,0", new int[]{400, 540});
        inverseMappableAreas.put("6,1", new int[]{470, 540});
        inverseMappableAreas.put("6,2", new int[]{540, 540});
        inverseMappableAreas.put("6,3", new int[]{610, 540});
        inverseMappableAreas.put("5,3", new int[]{610, 500});

        inverseMappableAreas.put("5,2", new int[]{540, 500});
        inverseMappableAreas.put("5,1", new int[]{470, 500});
        inverseMappableAreas.put("5,0", new int[]{400, 500});
        inverseMappableAreas.put("5,-1", new int[]{330, 500});
        inverseMappableAreas.put("5,-2", new int[]{260, 500});

        inverseMappableAreas.put("4,-2", new int[]{260, 460});
        inverseMappableAreas.put("4,-1", new int[]{330, 460});
        inverseMappableAreas.put("4,0", new int[]{400, 460});
        inverseMappableAreas.put("4,1", new int[]{470, 460});
        inverseMappableAreas.put("4,2", new int[]{540, 460});
        inverseMappableAreas.put("4,3", new int[]{610, 460});

        inverseMappableAreas.put("3,-2", new int[]{260, 420});
        inverseMappableAreas.put("3,-1", new int[]{330, 420});
        inverseMappableAreas.put("3,0", new int[]{400, 420});
        inverseMappableAreas.put("3,1", new int[]{470, 420});
        inverseMappableAreas.put("3,2", new int[]{540, 420});
        inverseMappableAreas.put("3,3", new int[]{610, 420});

        inverseMappableAreas.put("2,-2", new int[]{260, 380});
        inverseMappableAreas.put("2,-1", new int[]{330, 380});
        inverseMappableAreas.put("2,0", new int[]{400, 380});
        inverseMappableAreas.put("2,1", new int[]{470, 380});
        inverseMappableAreas.put("2,2", new int[]{540, 380});
        inverseMappableAreas.put("2,3", new int[]{610, 380});

        inverseMappableAreas.put("1,-2", new int[]{260, 340});
        inverseMappableAreas.put("1,-1", new int[]{330, 340});
        inverseMappableAreas.put("1,0", new int[]{400, 340});
        inverseMappableAreas.put("1,1", new int[]{470, 340});
        inverseMappableAreas.put("1,2", new int[]{540, 340});
        inverseMappableAreas.put("1,3", new int[]{610, 340});

        inverseMappableAreas.put("0,-2", new int[]{260, 300});
        inverseMappableAreas.put("0,-1", new int[]{330, 300});
        inverseMappableAreas.put("0,0", new int[]{400, 300});
        inverseMappableAreas.put("0,1", new int[]{470, 300});
        inverseMappableAreas.put("0,2", new int[]{540, 300});
        inverseMappableAreas.put("0,3", new int[]{610, 300});

        inverseMappableAreas.put("-1,-2", new int[]{260, 260});
        inverseMappableAreas.put("-1,-1", new int[]{330, 260});
        inverseMappableAreas.put("-1,0", new int[]{400, 260});
        inverseMappableAreas.put("-1,1", new int[]{470, 260});
        inverseMappableAreas.put("-1,2", new int[]{540, 260});
        inverseMappableAreas.put("-1,3", new int[]{610, 260});

        inverseMappableAreas.put("-2,-2", new int[]{260, 220});
        inverseMappableAreas.put("-2,-1", new int[]{330, 220});
        inverseMappableAreas.put("-2,0", new int[]{400, 220});
        inverseMappableAreas.put("-2,1", new int[]{470, 220});
        inverseMappableAreas.put("-2,2", new int[]{540, 220});
        inverseMappableAreas.put("-2,3", new int[]{610, 220});

        inverseMappableAreas.put("-3,-1", new int[]{330, 180});
        inverseMappableAreas.put("-3,0", new int[]{400, 180});
        inverseMappableAreas.put("-3,1", new int[]{470, 180});
        inverseMappableAreas.put("-3,2", new int[]{540, 180});
        inverseMappableAreas.put("-3,3", new int[]{610, 180});

        inverseMappableAreas.put("-4,-1", new int[]{330, 140});
        inverseMappableAreas.put("-4,0", new int[]{400, 140});
        inverseMappableAreas.put("-4,1", new int[]{470, 140});
        inverseMappableAreas.put("-4,2", new int[]{540, 140});
        inverseMappableAreas.put("-4,3", new int[]{610, 140});

        //nuove
        inverseMappableAreas.put("-5,-3", new int[]{190, 100});
        inverseMappableAreas.put("-5,-4", new int[]{120, 100});

        inverseMappableAreas.put("6,-1", new int[]{330, 540});
        inverseMappableAreas.put("6,-2", new int[]{260, 540});
        inverseMappableAreas.put("6,-3", new int[]{190, 540});
        inverseMappableAreas.put("6,-4", new int[]{120, 540});

        inverseMappableAreas.put("-4,-3", new int[]{190, 140});
        inverseMappableAreas.put("-3,-3", new int[]{190, 180});
        inverseMappableAreas.put("-2,-3", new int[]{190, 220});
        inverseMappableAreas.put("-1,-3", new int[]{190, 260});
        inverseMappableAreas.put("0,-3", new int[]{190, 300});
        inverseMappableAreas.put("1,-3", new int[]{190, 340});
        inverseMappableAreas.put("2,-3", new int[]{190, 380});
        inverseMappableAreas.put("3,-3", new int[]{190, 420});
        inverseMappableAreas.put("4,-3", new int[]{190, 460});
        inverseMappableAreas.put("5,-3", new int[]{190, 500});

        inverseMappableAreas.put("-4,-4", new int[]{120, 140});
        inverseMappableAreas.put("-3,-4", new int[]{120, 180});
        inverseMappableAreas.put("-2,-4", new int[]{120, 220});
        inverseMappableAreas.put("-1,-4", new int[]{120, 260});
        inverseMappableAreas.put("0,-4", new int[]{120, 300});
        inverseMappableAreas.put("1,-4", new int[]{120, 340});
        inverseMappableAreas.put("2,-4", new int[]{120, 380});
        inverseMappableAreas.put("3,-4", new int[]{120, 420});
        inverseMappableAreas.put("4,-4", new int[]{120, 460});
        inverseMappableAreas.put("5,-4", new int[]{120, 500});

        inverseMappableAreas.put("-4,-2", new int[]{260, 140});
        inverseMappableAreas.put("-3,-2", new int[]{260, 180});
        inverseMappableAreas.put("6,4", new int[]{680, 540});

        inverseMappableAreas.put("-5,5", new int[]{750, 100});
        inverseMappableAreas.put("-4,5", new int[]{750, 140});
        inverseMappableAreas.put("-3,5", new int[]{750, 180});
        inverseMappableAreas.put("-2,5", new int[]{750, 220});
        inverseMappableAreas.put("-1,5", new int[]{750, 260});
        inverseMappableAreas.put("0,5", new int[]{750, 300});
        inverseMappableAreas.put("1,5", new int[]{750, 340});
        inverseMappableAreas.put("2,5", new int[]{750, 380});
        inverseMappableAreas.put("3,5", new int[]{750, 420});
        inverseMappableAreas.put("4,5", new int[]{750, 460});
        inverseMappableAreas.put("5,5", new int[]{750, 500});
        inverseMappableAreas.put("6,5", new int[]{750, 540});


        //nuove
        inverseMappableAreas.put("7,-4", new int[]{120, 580});
        inverseMappableAreas.put("7,-3", new int[]{190, 580});
        inverseMappableAreas.put("7,-2", new int[]{260, 580});
        inverseMappableAreas.put("7,-1", new int[]{330, 580});
        inverseMappableAreas.put("7,0", new int[]{400, 580});
        inverseMappableAreas.put("7,1", new int[]{470, 580});
        inverseMappableAreas.put("7,2", new int[]{540, 580});
        inverseMappableAreas.put("7,3", new int[]{610, 580});
        inverseMappableAreas.put("7,4", new int[]{680, 580});
        inverseMappableAreas.put("7,5", new int[]{750, 580});

        inverseMappableAreas.put("8,-4", new int[]{120, 620});
        inverseMappableAreas.put("8,-3", new int[]{190, 620});
        inverseMappableAreas.put("8,-2", new int[]{260, 620});
        inverseMappableAreas.put("8,-1", new int[]{330, 620});
        inverseMappableAreas.put("8,0", new int[]{400, 620});
        inverseMappableAreas.put("8,1", new int[]{470, 620});
        inverseMappableAreas.put("8,2", new int[]{540, 620});
        inverseMappableAreas.put("8,3", new int[]{610, 620});
        inverseMappableAreas.put("8,4", new int[]{680, 620});
        inverseMappableAreas.put("8,5", new int[]{750, 620});

        inverseMappableAreas.put("9,-4", new int[]{120, 660});
        inverseMappableAreas.put("9,-3", new int[]{190, 660});
        inverseMappableAreas.put("9,-2", new int[]{260, 660});
        inverseMappableAreas.put("9,-1", new int[]{330, 660});
        inverseMappableAreas.put("9,0", new int[]{400, 660});
        inverseMappableAreas.put("9,1", new int[]{470, 660});
        inverseMappableAreas.put("9,2", new int[]{540, 660});
        inverseMappableAreas.put("9,3", new int[]{610, 660});
        inverseMappableAreas.put("9,4", new int[]{680, 660});
        inverseMappableAreas.put("9,5", new int[]{750, 660});

        inverseMappableAreas.put("10,-4", new int[]{120, 700});
        inverseMappableAreas.put("10,-3", new int[]{190, 700});
        inverseMappableAreas.put("10,-2", new int[]{260, 700});
        inverseMappableAreas.put("10,-1", new int[]{330, 700});
        inverseMappableAreas.put("10,0", new int[]{400, 700});
        inverseMappableAreas.put("10,1", new int[]{470, 700});
        inverseMappableAreas.put("10,2", new int[]{540, 700});
        inverseMappableAreas.put("10,3", new int[]{610, 700});
        inverseMappableAreas.put("10,4", new int[]{680, 700});
        inverseMappableAreas.put("10,5", new int[]{750, 700});

        inverseMappableAreas.put("11,-4", new int[]{120, 740});
        inverseMappableAreas.put("11,-3", new int[]{190, 740});
        inverseMappableAreas.put("11,-2", new int[]{260, 740});
        inverseMappableAreas.put("11,-1", new int[]{330, 740});
        inverseMappableAreas.put("11,0", new int[]{400, 740});
        inverseMappableAreas.put("11,1", new int[]{470, 740});
        inverseMappableAreas.put("11,2", new int[]{540, 740});
        inverseMappableAreas.put("11,3", new int[]{610, 740});
        inverseMappableAreas.put("11,4", new int[]{680, 740});
        inverseMappableAreas.put("11,5", new int[]{750, 740});

        inverseMappableAreas.put("12,-4", new int[]{120, 780});
        inverseMappableAreas.put("12,-3", new int[]{190, 780});
        inverseMappableAreas.put("12,-2", new int[]{260, 780});
        inverseMappableAreas.put("12,-1", new int[]{330, 780});
        inverseMappableAreas.put("12,0", new int[]{400, 780});
        inverseMappableAreas.put("12,1", new int[]{470, 780});
        inverseMappableAreas.put("12,2", new int[]{540, 780});
        inverseMappableAreas.put("12,3", new int[]{610, 780});
        inverseMappableAreas.put("12,4", new int[]{680, 780});
        inverseMappableAreas.put("12,5", new int[]{750, 780});


        inverseMappableAreas.put("-6,-4", new int[]{120, 60});
        inverseMappableAreas.put("-6,-3", new int[]{190, 60});
        inverseMappableAreas.put("-6,-2", new int[]{260, 60});
        inverseMappableAreas.put("-6,-1", new int[]{330, 60});
        inverseMappableAreas.put("-6,0", new int[]{400, 60});
        inverseMappableAreas.put("-6,1", new int[]{470, 60});
        inverseMappableAreas.put("-6,2", new int[]{540, 60});
        inverseMappableAreas.put("-6,3", new int[]{610, 60});
        inverseMappableAreas.put("-6,4", new int[]{680, 60});
        inverseMappableAreas.put("-6,5", new int[]{750, 60});

        inverseMappableAreas.put("-7,-4", new int[]{120, 20});
        inverseMappableAreas.put("-7,-3", new int[]{190, 20});
        inverseMappableAreas.put("-7,-2", new int[]{260, 20});
        inverseMappableAreas.put("-7,-1", new int[]{330, 20});
        inverseMappableAreas.put("-7,0", new int[]{400, 20});
        inverseMappableAreas.put("-7,1", new int[]{470, 20});
        inverseMappableAreas.put("-7,2", new int[]{540, 20});
        inverseMappableAreas.put("-7,3", new int[]{610, 20});
        inverseMappableAreas.put("-7,4", new int[]{680, 20});
        inverseMappableAreas.put("-7,5", new int[]{750, 20});

        inverseMappableAreas.put("-8,-4", new int[]{120, -20});
        inverseMappableAreas.put("-8,-3", new int[]{190, -20});
        inverseMappableAreas.put("-8,-2", new int[]{260, -20});
        inverseMappableAreas.put("-8,-1", new int[]{330, -20});
        inverseMappableAreas.put("-8,0", new int[]{400, -20});
        inverseMappableAreas.put("-8,1", new int[]{470, -20});
        inverseMappableAreas.put("-8,2", new int[]{540, -20});
        inverseMappableAreas.put("-8,3", new int[]{610, -20});
        inverseMappableAreas.put("-8,4", new int[]{680, -20});
        inverseMappableAreas.put("-8,5", new int[]{750, -20});

        inverseMappableAreas.put("-9,-4", new int[]{120, -60});
        inverseMappableAreas.put("-9,-3", new int[]{190, -60});
        inverseMappableAreas.put("-9,-2", new int[]{260, -60});
        inverseMappableAreas.put("-9,-1", new int[]{330, -60});
        inverseMappableAreas.put("-9,0", new int[]{400, -60});
        inverseMappableAreas.put("-9,1", new int[]{470, -60});
        inverseMappableAreas.put("-9,2", new int[]{540, -60});
        inverseMappableAreas.put("-9,3", new int[]{610, -60});
        inverseMappableAreas.put("-9,4", new int[]{680, -60});
        inverseMappableAreas.put("-9,5", new int[]{750, -60});

        inverseMappableAreas.put("-10,-4", new int[]{120, -100});
        inverseMappableAreas.put("-10,-3", new int[]{190, -100});
        inverseMappableAreas.put("-10,-2", new int[]{260, -100});
        inverseMappableAreas.put("-10,-1", new int[]{330, -100});
        inverseMappableAreas.put("-10,0", new int[]{400, -100});
        inverseMappableAreas.put("-10,1", new int[]{470, -100});
        inverseMappableAreas.put("-10,2", new int[]{540, -100});
        inverseMappableAreas.put("-10,3", new int[]{610, -100});
        inverseMappableAreas.put("-10,4", new int[]{680, -100});
        inverseMappableAreas.put("-10,5", new int[]{750, -100});

        inverseMappableAreas.put("-11,-4", new int[]{120, -140});
        inverseMappableAreas.put("-11,-3", new int[]{190, -140});
        inverseMappableAreas.put("-11,-2", new int[]{260, -140});
        inverseMappableAreas.put("-11,-1", new int[]{330, -140});
        inverseMappableAreas.put("-11,0", new int[]{400, -140});
        inverseMappableAreas.put("-11,1", new int[]{470, -140});
        inverseMappableAreas.put("-11,2", new int[]{540, -140});
        inverseMappableAreas.put("-11,3", new int[]{610, -140});
        inverseMappableAreas.put("-11,4", new int[]{680, -140});
        inverseMappableAreas.put("-11,5", new int[]{750, -140});

        inverseMappableAreas.put("-12,-4", new int[]{120, -180});
        inverseMappableAreas.put("-12,-3", new int[]{190, -180});
        inverseMappableAreas.put("-12,-2", new int[]{260, -180});
        inverseMappableAreas.put("-12,-1", new int[]{330, -180});
        inverseMappableAreas.put("-12,0", new int[]{400, -180});
        inverseMappableAreas.put("-12,1", new int[]{470, -180});
        inverseMappableAreas.put("-12,2", new int[]{540, -180});
        inverseMappableAreas.put("-12,3", new int[]{610, -180});
        inverseMappableAreas.put("-12,4", new int[]{680, -180});
        inverseMappableAreas.put("-12,5", new int[]{750, -180});

    }


    public int[] getInverseMappedPosition(String key) {
        return inverseMappableAreas.get(key);
    }

}
