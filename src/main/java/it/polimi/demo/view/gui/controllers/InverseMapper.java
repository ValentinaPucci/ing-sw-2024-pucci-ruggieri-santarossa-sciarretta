package it.polimi.demo.view.gui.controllers;
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

        inverseMappableAreas.put("-12,6", new int[]{820, -180});
        inverseMappableAreas.put("-11,6", new int[]{820, -140});
        inverseMappableAreas.put("-10,6", new int[]{820, -100});
        inverseMappableAreas.put("-9,6", new int[]{820, -60});
        inverseMappableAreas.put("-8,6", new int[]{820, -20});
        inverseMappableAreas.put("-7,6", new int[]{820, 20});
        inverseMappableAreas.put("-6,6", new int[]{820, 60});
        inverseMappableAreas.put("-5,6", new int[]{820, 100});
        inverseMappableAreas.put("-4,6", new int[]{820, 140});
        inverseMappableAreas.put("-3,6", new int[]{820, 180});
        inverseMappableAreas.put("-2,6", new int[]{820, 220});
        inverseMappableAreas.put("-1,6", new int[]{820, 260});
        inverseMappableAreas.put("0,6", new int[]{820, 300});
        inverseMappableAreas.put("1,6", new int[]{820, 340});
        inverseMappableAreas.put("2,6", new int[]{820, 380});
        inverseMappableAreas.put("3,6", new int[]{820, 420});
        inverseMappableAreas.put("4,6", new int[]{820, 460});
        inverseMappableAreas.put("5,6", new int[]{820, 500});
        inverseMappableAreas.put("6,6", new int[]{820, 540});
        inverseMappableAreas.put("7,6", new int[]{820, 580});
        inverseMappableAreas.put("8,6", new int[]{820, 620});
        inverseMappableAreas.put("9,6", new int[]{820, 660});
        inverseMappableAreas.put("10,6", new int[]{820, 700});
        inverseMappableAreas.put("11,6", new int[]{820, 740});
        inverseMappableAreas.put("12,6", new int[]{820, 780});



        inverseMappableAreas.put("-12,7", new int[]{890, -180});
        inverseMappableAreas.put("-11,7", new int[]{890, -140});
        inverseMappableAreas.put("-10,7", new int[]{890, -100});
        inverseMappableAreas.put("-9,7", new int[]{890, -60});
        inverseMappableAreas.put("-8,7", new int[]{890, -20});
        inverseMappableAreas.put("-7,7", new int[]{890, 20});
        inverseMappableAreas.put("-6,7", new int[]{890, 60});
        inverseMappableAreas.put("-5,7", new int[]{890, 100});
        inverseMappableAreas.put("-4,7", new int[]{890, 140});
        inverseMappableAreas.put("-3,7", new int[]{890, 180});
        inverseMappableAreas.put("-2,7", new int[]{890, 220});
        inverseMappableAreas.put("-1,7", new int[]{890, 260});
        inverseMappableAreas.put("0,7", new int[]{890, 300});
        inverseMappableAreas.put("1,7", new int[]{890, 340});
        inverseMappableAreas.put("2,7", new int[]{890, 380});
        inverseMappableAreas.put("3,7", new int[]{890, 420});
        inverseMappableAreas.put("4,7", new int[]{890, 460});
        inverseMappableAreas.put("5,7", new int[]{890, 500});
        inverseMappableAreas.put("6,7", new int[]{890, 540});
        inverseMappableAreas.put("7,7", new int[]{890, 580});
        inverseMappableAreas.put("8,7", new int[]{890, 620});
        inverseMappableAreas.put("9,7", new int[]{890, 660});
        inverseMappableAreas.put("10,7", new int[]{890, 700});
        inverseMappableAreas.put("11,7", new int[]{890, 740});
        inverseMappableAreas.put("12,7", new int[]{890, 780});

        inverseMappableAreas.put("-12,8", new int[]{960, -180});
        inverseMappableAreas.put("-11,8", new int[]{960, -140});
        inverseMappableAreas.put("-10,8", new int[]{960, -100});
        inverseMappableAreas.put("-9,8", new int[]{960, -60});
        inverseMappableAreas.put("-8,8", new int[]{960, -20});
        inverseMappableAreas.put("-7,8", new int[]{960, 20});
        inverseMappableAreas.put("-6,8", new int[]{960, 60});
        inverseMappableAreas.put("-5,8", new int[]{960, 100});
        inverseMappableAreas.put("-4,8", new int[]{960, 140});
        inverseMappableAreas.put("-3,8", new int[]{960, 180});
        inverseMappableAreas.put("-2,8", new int[]{960, 220});
        inverseMappableAreas.put("-1,8", new int[]{960, 260,});
        inverseMappableAreas.put("0,8", new int[]{960, 300});
        inverseMappableAreas.put("1,8", new int[]{960, 340});
        inverseMappableAreas.put("2,8", new int[]{960, 380});
        inverseMappableAreas.put("3,8", new int[]{960, 420});
        inverseMappableAreas.put("4,8", new int[]{960, 460});
        inverseMappableAreas.put("5,8", new int[]{960, 500});
        inverseMappableAreas.put("6,8", new int[]{960, 540});
        inverseMappableAreas.put("7,8", new int[]{960, 580});
        inverseMappableAreas.put("8,8", new int[]{960, 620});
        inverseMappableAreas.put("9,8", new int[]{960, 660});
        inverseMappableAreas.put("10,8", new int[]{960, 700});
        inverseMappableAreas.put("11,8", new int[]{960, 740});
        inverseMappableAreas.put("12,8", new int[]{960, 780});

        inverseMappableAreas.put("-12,9", new int[]{1030, -180});
        inverseMappableAreas.put("-11,9", new int[]{1030, -140});
        inverseMappableAreas.put("-10,9", new int[]{1030, -100});
        inverseMappableAreas.put("-9,9", new int[]{1030, -60});
        inverseMappableAreas.put("-8,9", new int[]{1030, -20});
        inverseMappableAreas.put("-7,9", new int[]{1030, 20});
        inverseMappableAreas.put("-6,9", new int[]{1030, 60});
        inverseMappableAreas.put("-5,9", new int[]{1030, 100});
        inverseMappableAreas.put("-4,9", new int[]{1030, 140});
        inverseMappableAreas.put("-3,9", new int[]{1030, 180});
        inverseMappableAreas.put("-2,9", new int[]{1030, 220});
        inverseMappableAreas.put("-1,9", new int[]{1030, 260});
        inverseMappableAreas.put("0,9", new int[]{1030, 300});
        inverseMappableAreas.put("1,9", new int[]{1030, 340});
        inverseMappableAreas.put("2,9", new int[]{1030, 380});
        inverseMappableAreas.put("3,9", new int[]{1030, 420});
        inverseMappableAreas.put("4,9", new int[]{1030, 460});
        inverseMappableAreas.put("5,9", new int[]{1030, 500});
        inverseMappableAreas.put("6,9", new int[]{1030, 540});
        inverseMappableAreas.put("7,9", new int[]{1030, 580});
        inverseMappableAreas.put("8,9", new int[]{1030, 620});
        inverseMappableAreas.put("9,9", new int[]{1030, 660});
        inverseMappableAreas.put("10,9", new int[]{1030, 700});
        inverseMappableAreas.put("11,9", new int[]{1030, 740});
        inverseMappableAreas.put("12,9", new int[]{1030, 780});


        inverseMappableAreas.put("-12,10", new int[]{1100, -180});
        inverseMappableAreas.put("-11,10", new int[]{1100, -140});
        inverseMappableAreas.put("-10,10", new int[]{1100, -100});
        inverseMappableAreas.put("-9,10", new int[]{1100, -60});
        inverseMappableAreas.put("-8,10", new int[]{1100, -20});
        inverseMappableAreas.put("-7,10", new int[]{1100, 20});
        inverseMappableAreas.put("-6,10", new int[]{1100, 60});
        inverseMappableAreas.put("-5,10", new int[]{1100, 100});
        inverseMappableAreas.put("-4,10", new int[]{1100, 140});
        inverseMappableAreas.put("-3,10", new int[]{1100, 180});
        inverseMappableAreas.put("-2,10", new int[]{1100, 220});
        inverseMappableAreas.put("-1,10", new int[]{1100, 260});
        inverseMappableAreas.put("0,10", new int[]{1100, 300});
        inverseMappableAreas.put("1,10", new int[]{1100, 340});
        inverseMappableAreas.put("2,10", new int[]{1100, 380});
        inverseMappableAreas.put("3,10", new int[]{1100, 420});
        inverseMappableAreas.put("4,10", new int[]{1100, 460});
        inverseMappableAreas.put("5,10", new int[]{1100, 500});
        inverseMappableAreas.put("6,10", new int[]{1100, 540});
        inverseMappableAreas.put("7,10", new int[]{1100, 580});
        inverseMappableAreas.put("8,10", new int[]{1100, 620});
        inverseMappableAreas.put("9,10", new int[]{1100, 660});
        inverseMappableAreas.put("10,10", new int[]{1100, 700});
        inverseMappableAreas.put("11,10", new int[]{1100, 740});
        inverseMappableAreas.put("12,10", new int[]{1100, 780});


        inverseMappableAreas.put("-12,11", new int[]{1170, -180});
        inverseMappableAreas.put("-11,11", new int[]{1170, -140});
        inverseMappableAreas.put("-10,11", new int[]{1170, -100});
        inverseMappableAreas.put("-9,11", new int[]{1170, -60});
        inverseMappableAreas.put("-8,11", new int[]{1170, -20});
        inverseMappableAreas.put("-7,11", new int[]{1170, 20});
        inverseMappableAreas.put("-6,11", new int[]{1170, 60});
        inverseMappableAreas.put("-5,11", new int[]{1170, 100});
        inverseMappableAreas.put("-4,11", new int[]{1170, 140});
        inverseMappableAreas.put("-3,11", new int[]{1170, 180});
        inverseMappableAreas.put("-2,11", new int[]{1170, 220});
        inverseMappableAreas.put("-1,11", new int[]{1170, 260});
        inverseMappableAreas.put("0,11", new int[]{1170, 300});
        inverseMappableAreas.put("1,11", new int[]{1170, 340});
        inverseMappableAreas.put("2,11", new int[]{1170, 380});
        inverseMappableAreas.put("3,11", new int[]{1170, 420});
        inverseMappableAreas.put("4,11", new int[]{1170, 460});
        inverseMappableAreas.put("5,11", new int[]{1170, 500});
        inverseMappableAreas.put("6,11", new int[]{1170, 540});
        inverseMappableAreas.put("7,11", new int[]{1170, 580});
        inverseMappableAreas.put("8,11", new int[]{1170, 620});
        inverseMappableAreas.put("9,11", new int[]{1170, 660});
        inverseMappableAreas.put("10,11", new int[]{1170, 700});
        inverseMappableAreas.put("11,11", new int[]{1170, 740});
        inverseMappableAreas.put("12,11", new int[]{1170, 780});


        inverseMappableAreas.put("-12,12", new int[]{1240, -180});
        inverseMappableAreas.put("-11,12", new int[]{1240, -140});
        inverseMappableAreas.put("-10,12", new int[]{1240, -100});
        inverseMappableAreas.put("-9,12", new int[]{1240, -60});
        inverseMappableAreas.put("-8,12", new int[]{1240, -20});
        inverseMappableAreas.put("-7,12", new int[]{1240, 20});
        inverseMappableAreas.put("-6,12", new int[]{1240, 60});
        inverseMappableAreas.put("-5,12", new int[]{1240, 100});
        inverseMappableAreas.put("-4,12", new int[]{1240, 140});
        inverseMappableAreas.put("-3,12", new int[]{1240, 180});
        inverseMappableAreas.put("-2,12", new int[]{1240, 220});
        inverseMappableAreas.put("-1,12", new int[]{1240, 260});
        inverseMappableAreas.put("0,12", new int[]{1240, 300});
        inverseMappableAreas.put("1,12", new int[]{1240, 340});
        inverseMappableAreas.put("2,12", new int[]{1240, 380});
        inverseMappableAreas.put("3,12", new int[]{1240, 420});
        inverseMappableAreas.put("4,12", new int[]{1240, 460});
        inverseMappableAreas.put("5,12", new int[]{1240, 500});
        inverseMappableAreas.put("6,12", new int[]{1240, 540});
        inverseMappableAreas.put("7,12", new int[]{1240, 580});
        inverseMappableAreas.put("8,12", new int[]{1240, 620});
        inverseMappableAreas.put("9,12", new int[]{1240, 660});
        inverseMappableAreas.put("10,12", new int[]{1240, 700});
        inverseMappableAreas.put("11,12", new int[]{1240, 740});
        inverseMappableAreas.put("12,12", new int[]{1240, 780});


//inverseMappableAreas.put("7,-4", new int[]{120, 580, 138, 602});
//nuove negative
        inverseMappableAreas.put("-12,-5", new int[]{50, -180});
        inverseMappableAreas.put("-11,-5", new int[]{50, -140});
        inverseMappableAreas.put("-10,-5", new int[]{50, -100});
        inverseMappableAreas.put("-9,-5", new int[]{50, -60});
        inverseMappableAreas.put("-8,-5", new int[]{50, -20});
        inverseMappableAreas.put("-7,-5", new int[]{50, 20});
        inverseMappableAreas.put("-6,-5", new int[]{50, 60});
        inverseMappableAreas.put("-5,-5", new int[]{50, 100});
        inverseMappableAreas.put("-4,-5", new int[]{50, 140});
        inverseMappableAreas.put("-3,-5", new int[]{50, 180});
        inverseMappableAreas.put("-2,-5", new int[]{50, 220});
        inverseMappableAreas.put("-1,-5", new int[]{50, 260});
        inverseMappableAreas.put("0,-5", new int[]{50, 300});
        inverseMappableAreas.put("1,-5", new int[]{50, 340});
        inverseMappableAreas.put("2,-5", new int[]{50, 380});
        inverseMappableAreas.put("3,-5", new int[]{50, 420});
        inverseMappableAreas.put("4,-5", new int[]{50, 460});
        inverseMappableAreas.put("5,-5", new int[]{50, 500});
        inverseMappableAreas.put("6,-5", new int[]{50, 540});
        inverseMappableAreas.put("7,-5", new int[]{50, 580});
        inverseMappableAreas.put("8,-5", new int[]{50, 620});
        inverseMappableAreas.put("9,-5", new int[]{50, 660});
        inverseMappableAreas.put("10,-5", new int[]{50, 700});
        inverseMappableAreas.put("11,-5", new int[]{50, 740});
        inverseMappableAreas.put("12,-5", new int[]{50, 780});


        inverseMappableAreas.put("-12,-6", new int[]{-20, -180});
        inverseMappableAreas.put("-11,-6", new int[]{-20, -140});
        inverseMappableAreas.put("-10,-6", new int[]{-20, -100});
        inverseMappableAreas.put("-9,-6", new int[]{-20, -60});
        inverseMappableAreas.put("-8,-6", new int[]{-20, -20});
        inverseMappableAreas.put("-7,-6", new int[]{-20, 20});
        inverseMappableAreas.put("-6,-6", new int[]{-20, 60});
        inverseMappableAreas.put("-5,-6", new int[]{-20, 100});
        inverseMappableAreas.put("-4,-6", new int[]{-20, 140});
        inverseMappableAreas.put("-3,-6", new int[]{-20, 180});
        inverseMappableAreas.put("-2,-6", new int[]{-20, 220});
        inverseMappableAreas.put("-1,-6", new int[]{-20, 260});
        inverseMappableAreas.put("0,-6", new int[]{-20, 300});
        inverseMappableAreas.put("1,-6", new int[]{-20, 340});
        inverseMappableAreas.put("2,-6", new int[]{-20, 380});
        inverseMappableAreas.put("3,-6", new int[]{-20, 420});
        inverseMappableAreas.put("4,-6", new int[]{-20, 460});
        inverseMappableAreas.put("5,-6", new int[]{-20, 500});
        inverseMappableAreas.put("6,-6", new int[]{-20, 540});
        inverseMappableAreas.put("7,-6", new int[]{-20, 580});
        inverseMappableAreas.put("8,-6", new int[]{-20, 620});
        inverseMappableAreas.put("9,-6", new int[]{-20, 660});
        inverseMappableAreas.put("10,-6", new int[]{-20, 700});
        inverseMappableAreas.put("11,-6", new int[]{-20, 740});
        inverseMappableAreas.put("12,-6", new int[]{-20, 780});

        inverseMappableAreas.put("-12,-7", new int[]{-90, -180});
        inverseMappableAreas.put("-11,-7", new int[]{-90, -140});
        inverseMappableAreas.put("-10,-7", new int[]{-90, -100});
        inverseMappableAreas.put("-9,-7", new int[]{-90, -60});
        inverseMappableAreas.put("-8,-7", new int[]{-90, -20});
        inverseMappableAreas.put("-7,-7", new int[]{-90, 20});
        inverseMappableAreas.put("-6,-7", new int[]{-90, 60});
        inverseMappableAreas.put("-5,-7", new int[]{-90, 100});
        inverseMappableAreas.put("-4,-7", new int[]{-90, 140});
        inverseMappableAreas.put("-3,-7", new int[]{-90, 180});
        inverseMappableAreas.put("-2,-7", new int[]{-90, 220});
        inverseMappableAreas.put("-1,-7", new int[]{-90, 260});
        inverseMappableAreas.put("0,-7", new int[]{-90, 300});
        inverseMappableAreas.put("1,-7", new int[]{-90, 340});
        inverseMappableAreas.put("2,-7", new int[]{-90, 380});
        inverseMappableAreas.put("3,-7", new int[]{-90, 420});
        inverseMappableAreas.put("4,-7", new int[]{-90, 460});
        inverseMappableAreas.put("5,-7", new int[]{-90, 500});
        inverseMappableAreas.put("6,-7", new int[]{-90, 540});
        inverseMappableAreas.put("7,-7", new int[]{-90, 580});
        inverseMappableAreas.put("8,-7", new int[]{-90, 620});
        inverseMappableAreas.put("9,-7", new int[]{-90, 660});
        inverseMappableAreas.put("10,-7", new int[]{-90, 700});
        inverseMappableAreas.put("11,-7", new int[]{-90, 740});
        inverseMappableAreas.put("12,-7", new int[]{-90, 780});

        inverseMappableAreas.put("-12,-8", new int[]{-160, -180});
        inverseMappableAreas.put("-11,-8", new int[]{-160, -140});
        inverseMappableAreas.put("-10,-8", new int[]{-160, -100});
        inverseMappableAreas.put("-9,-8", new int[]{-160, -60});
        inverseMappableAreas.put("-8,-8", new int[]{-160, -20});
        inverseMappableAreas.put("-7,-8", new int[]{-160, 20});
        inverseMappableAreas.put("-6,-8", new int[]{-160, 60});
        inverseMappableAreas.put("-5,-8", new int[]{-160, 100});
        inverseMappableAreas.put("-4,-8", new int[]{-160, 140});
        inverseMappableAreas.put("-3,-8", new int[]{-160, 180});
        inverseMappableAreas.put("-2,-8", new int[]{-160, 220});
        inverseMappableAreas.put("-1,-8", new int[]{-160, 260});
        inverseMappableAreas.put("0,-8", new int[]{-160, 300});
        inverseMappableAreas.put("1,-8", new int[]{-160, 340});
        inverseMappableAreas.put("2,-8", new int[]{-160, 380});
        inverseMappableAreas.put("3,-8", new int[]{-160, 420});
        inverseMappableAreas.put("4,-8", new int[]{-160, 460});
        inverseMappableAreas.put("5,-8", new int[]{-160, 500});
        inverseMappableAreas.put("6,-8", new int[]{-160, 540});
        inverseMappableAreas.put("7,-8", new int[]{-160, 580});
        inverseMappableAreas.put("8,-8", new int[]{-160, 620});
        inverseMappableAreas.put("9,-8", new int[]{-160, 660});
        inverseMappableAreas.put("10,-8", new int[]{-160, 700});
        inverseMappableAreas.put("11,-8", new int[]{-160, 740});
        inverseMappableAreas.put("12,-8", new int[]{-160, 780});


        inverseMappableAreas.put("-12,-9", new int[]{-230, -180});
        inverseMappableAreas.put("-11,-9", new int[]{-230, -140});
        inverseMappableAreas.put("-10,-9", new int[]{-230, -100});
        inverseMappableAreas.put("-9,-9", new int[]{-230, -60});
        inverseMappableAreas.put("-8,-9", new int[]{-230, -20});
        inverseMappableAreas.put("-7,-9", new int[]{-230, 20});
        inverseMappableAreas.put("-6,-9", new int[]{-230, 60});
        inverseMappableAreas.put("-5,-9", new int[]{-230, 100});
        inverseMappableAreas.put("-4,-9", new int[]{-230, 140});
        inverseMappableAreas.put("-3,-9", new int[]{-230, 180});
        inverseMappableAreas.put("-2,-9", new int[]{-230, 220});
        inverseMappableAreas.put("-1,-9", new int[]{-230, 260});
        inverseMappableAreas.put("0,-9", new int[]{-230, 300});
        inverseMappableAreas.put("1,-9", new int[]{-230, 340});
        inverseMappableAreas.put("2,-9", new int[]{-230, 380});
        inverseMappableAreas.put("3,-9", new int[]{-230, 420});
        inverseMappableAreas.put("4,-9", new int[]{-230, 460});
        inverseMappableAreas.put("5,-9", new int[]{-230, 500});
        inverseMappableAreas.put("6,-9", new int[]{-230, 540});
        inverseMappableAreas.put("7,-9", new int[]{-230, 580});
        inverseMappableAreas.put("8,-9", new int[]{-230, 620});
        inverseMappableAreas.put("9,-9", new int[]{-230, 660});
        inverseMappableAreas.put("10,-9", new int[]{-230, 700});
        inverseMappableAreas.put("11,-9", new int[]{-230, 740});
        inverseMappableAreas.put("12,-9", new int[]{-230, 780});

        inverseMappableAreas.put("-12,-10", new int[]{-300, -180});
        inverseMappableAreas.put("-11,-10", new int[]{-300, -140});
        inverseMappableAreas.put("-10,-10", new int[]{-300, -100});
        inverseMappableAreas.put("-9,-10", new int[]{-300, -60});
        inverseMappableAreas.put("-8,-10", new int[]{-300, -20});
        inverseMappableAreas.put("-7,-10", new int[]{-300, 20});
        inverseMappableAreas.put("-6,-10", new int[]{-300, 60});
        inverseMappableAreas.put("-5,-10", new int[]{-300, 100});
        inverseMappableAreas.put("-4,-10", new int[]{-300, 140});
        inverseMappableAreas.put("-3,-10", new int[]{-300, 180});
        inverseMappableAreas.put("-2,-10", new int[]{-300, 220});
        inverseMappableAreas.put("-1,-10", new int[]{-300, 260});
        inverseMappableAreas.put("0,-10", new int[]{-300, 300});
        inverseMappableAreas.put("1,-10", new int[]{-300, 340});
        inverseMappableAreas.put("2,-10", new int[]{-300, 380});
        inverseMappableAreas.put("3,-10", new int[]{-300, 420});
        inverseMappableAreas.put("4,-10", new int[]{-300, 460});
        inverseMappableAreas.put("5,-10", new int[]{-300, 500});
        inverseMappableAreas.put("6,-10", new int[]{-300, 540});
        inverseMappableAreas.put("7,-10", new int[]{-300, 580});
        inverseMappableAreas.put("8,-10", new int[]{-300, 620});
        inverseMappableAreas.put("9,-10", new int[]{-300, 660});
        inverseMappableAreas.put("10,-10", new int[]{-300, 700});
        inverseMappableAreas.put("11,-10", new int[]{-300, 740});
        inverseMappableAreas.put("12,-10", new int[]{-300, 780});

        inverseMappableAreas.put("-12,-11", new int[]{-370, -180});
        inverseMappableAreas.put("-11,-11", new int[]{-370, -140});
        inverseMappableAreas.put("-10,-11", new int[]{-370, -100});
        inverseMappableAreas.put("-9,-11", new int[]{-370, -60});
        inverseMappableAreas.put("-8,-11", new int[]{-370, -20});
        inverseMappableAreas.put("-7,-11", new int[]{-370, 20});
        inverseMappableAreas.put("-6,-11", new int[]{-370, 60});
        inverseMappableAreas.put("-5,-11", new int[]{-370, 100});
        inverseMappableAreas.put("-4,-11", new int[]{-370, 1402});
        inverseMappableAreas.put("-3,-11", new int[]{-370, 180});
        inverseMappableAreas.put("-2,-11", new int[]{-370, 220});
        inverseMappableAreas.put("-1,-11", new int[]{-370, 260});
        inverseMappableAreas.put("0,-11", new int[]{-370, 300});
        inverseMappableAreas.put("1,-11", new int[]{-370, 340});
        inverseMappableAreas.put("2,-11", new int[]{-370, 380});
        inverseMappableAreas.put("3,-11", new int[]{-370, 420});
        inverseMappableAreas.put("4,-11", new int[]{-370, 460});
        inverseMappableAreas.put("5,-11", new int[]{-370, 500});
        inverseMappableAreas.put("6,-11", new int[]{-370, 540});
        inverseMappableAreas.put("7,-11", new int[]{-370, 580});
        inverseMappableAreas.put("8,-11", new int[]{-370, 620});
        inverseMappableAreas.put("9,-11", new int[]{-370, 660});
        inverseMappableAreas.put("10,-11", new int[]{-370, 700});
        inverseMappableAreas.put("11,-11", new int[]{-370, 740});
        inverseMappableAreas.put("12,-11", new int[]{-370, 780});

        inverseMappableAreas.put("-12,-12", new int[]{-440, -180});
        inverseMappableAreas.put("-11,-12", new int[]{-440, -140});
        inverseMappableAreas.put("-10,-12", new int[]{-440, -100});
        inverseMappableAreas.put("-9,-12", new int[]{-440, -60});
        inverseMappableAreas.put("-8,-12", new int[]{-440, -20});
        inverseMappableAreas.put("-7,-12", new int[]{-440, 20});
        inverseMappableAreas.put("-6,-12", new int[]{-440, 60});
        inverseMappableAreas.put("-5,-12", new int[]{-440, 100});
        inverseMappableAreas.put("-4,-12", new int[]{-440, 140});
        inverseMappableAreas.put("-3,-12", new int[]{-440, 180});
        inverseMappableAreas.put("-2,-12", new int[]{-440, 220});
        inverseMappableAreas.put("-1,-12", new int[]{-440, 260});
        inverseMappableAreas.put("0,-12", new int[]{-440, 300});
        inverseMappableAreas.put("1,-12", new int[]{-440, 340});
        inverseMappableAreas.put("2,-12", new int[]{-440, 380});
        inverseMappableAreas.put("3,-12", new int[]{-440, 420});
        inverseMappableAreas.put("4,-12", new int[]{-440, 460});
        inverseMappableAreas.put("5,-12", new int[]{-440, 500});
        inverseMappableAreas.put("6,-12", new int[]{-440, 540});
        inverseMappableAreas.put("7,-12", new int[]{-440, 580});
        inverseMappableAreas.put("8,-12", new int[]{-440, 620});
        inverseMappableAreas.put("9,-12", new int[]{-440, 660});
        inverseMappableAreas.put("10,-12", new int[]{-440, 700});
        inverseMappableAreas.put("11,-12", new int[]{-440, 740});
        inverseMappableAreas.put("12,-12", new int[]{-440, 780});






    }


    public int[] getInverseMappedPosition(String key) {
        return inverseMappableAreas.get(key);
    }

}
