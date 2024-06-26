package it.polimi.demo.view.gui.controllers;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private static final Map<String, int[]> mappableAreas = new HashMap<>();

    static {
        // Define the mappable areas based on the provided image
        mappableAreas.put("-5,0", new int[]{400, 100, 418, 122});
        mappableAreas.put("-5,-1", new int[]{330, 100, 348, 122});
        mappableAreas.put("-5,-2", new int[]{260, 100, 278, 122});
        mappableAreas.put("-5,1", new int[]{470, 100, 488, 122});
        mappableAreas.put("-5,2", new int[]{540, 100, 558, 122});
        mappableAreas.put("-5,3", new int[]{610, 100, 628, 122});
        mappableAreas.put("-5,4", new int[]{680, 100, 698, 122});

        mappableAreas.put("-4,4", new int[]{680, 140, 698, 162});
        mappableAreas.put("-3,4", new int[]{680, 180, 698, 202});
        mappableAreas.put("-2,4", new int[]{680, 220, 698, 242});
        mappableAreas.put("-1,4", new int[]{680, 260, 698, 288});
        mappableAreas.put("0,4", new int[]{680, 300, 698, 322});
        mappableAreas.put("1,4", new int[]{680, 340, 698, 362});
        mappableAreas.put("2,4", new int[]{680, 380, 698, 402});
        mappableAreas.put("3,4", new int[]{680, 420, 698, 442});
        mappableAreas.put("4,4", new int[]{680, 460, 698, 482});
        mappableAreas.put("5,4", new int[]{680, 500, 698, 522});

        mappableAreas.put("6,0", new int[]{400, 540, 418, 562});
        mappableAreas.put("6,1", new int[]{470, 540, 488, 562});
        mappableAreas.put("6,2", new int[]{540, 540, 558, 562});
        mappableAreas.put("6,3", new int[]{610, 540, 628, 562});
        mappableAreas.put("5,3", new int[]{610, 500, 628, 522});

        mappableAreas.put("5,2", new int[]{540, 500, 558, 522});
        mappableAreas.put("5,1", new int[]{470, 500, 488, 522});
        mappableAreas.put("5,0", new int[]{400, 500, 418, 522});
        mappableAreas.put("5,-1", new int[]{330, 500, 348, 522});
        mappableAreas.put("5,-2", new int[]{260, 500, 278, 522});

        mappableAreas.put("4,-2", new int[]{260, 460, 278, 482});
        mappableAreas.put("4,-1", new int[]{330, 460, 348, 482});
        mappableAreas.put("4,0", new int[]{400, 460, 418, 482});

        mappableAreas.put("4,1", new int[]{470, 460, 488, 482});
        mappableAreas.put("4,2", new int[]{540, 460, 558, 482});
        mappableAreas.put("4,3", new int[]{610, 460, 628, 482});

        mappableAreas.put("3,-2", new int[]{260, 420, 278, 442});
        mappableAreas.put("3,-1", new int[]{330, 420, 348, 442});
        mappableAreas.put("3,0", new int[]{400, 420, 418, 442});
        mappableAreas.put("3,1", new int[]{470, 420, 488, 442});
        mappableAreas.put("3,2", new int[]{540, 420, 558, 442});
        mappableAreas.put("3,3", new int[]{610, 420, 628, 442});


        mappableAreas.put("2,-2", new int[]{260, 380, 278, 402});
        mappableAreas.put("2,-1", new int[]{330, 380, 348, 402});
        mappableAreas.put("2,0", new int[]{400, 380, 418, 402});
        mappableAreas.put("2,1", new int[]{470, 380, 488, 402});
        mappableAreas.put("2,2", new int[]{540, 380, 558, 402});
        mappableAreas.put("2,3", new int[]{610, 380, 628, 402});

        mappableAreas.put("1,-2", new int[]{260, 340, 278, 362});
        mappableAreas.put("1,-1", new int[]{330, 340, 348, 362});
        mappableAreas.put("1,0", new int[]{400, 340, 418, 362});
        mappableAreas.put("1,1", new int[]{470, 340, 488, 362});
        mappableAreas.put("1,2", new int[]{540, 340, 558, 362});
        mappableAreas.put("1,3", new int[]{610, 340, 628, 362});

        mappableAreas.put("0,-2", new int[]{260, 300, 278, 322});
        mappableAreas.put("0,-1", new int[]{330, 300, 348, 322});
        mappableAreas.put("0,0", new int[]{400, 300, 418, 322});
        mappableAreas.put("0,1", new int[]{470, 300, 488, 322});
        mappableAreas.put("0,2", new int[]{540, 300, 558, 322});
        mappableAreas.put("0,3", new int[]{610, 300, 628, 322});

        mappableAreas.put("-1,-2", new int[]{260, 260, 278, 288});
        mappableAreas.put("-1,-1", new int[]{330, 260, 348, 288});

        mappableAreas.put("-1,0", new int[]{400, 260, 418, 288});
        mappableAreas.put("-1,1", new int[]{470, 260, 488, 288});
        mappableAreas.put("-1,2", new int[]{540, 260, 558, 288});
        mappableAreas.put("-1,3", new int[]{610, 260, 628, 288});


        mappableAreas.put("-2,-2", new int[]{260, 220, 278, 242});
        mappableAreas.put("-2,-1", new int[]{330, 220, 348, 242});
        mappableAreas.put("-2,0", new int[]{400, 220, 418, 242});
        mappableAreas.put("-2,1", new int[]{470, 220, 488, 242});
        mappableAreas.put("-2,2", new int[]{540, 220, 558, 242});
        mappableAreas.put("-2,3", new int[]{610, 220, 628, 242});

        mappableAreas.put("-3,-1", new int[]{330, 180, 348, 202});
        mappableAreas.put("-3,0", new int[]{400, 180, 418, 202});
        mappableAreas.put("-3,1", new int[]{470, 180, 488, 202});
        mappableAreas.put("-3,2", new int[]{540, 180, 558, 202});
        mappableAreas.put("-3,3", new int[]{610, 180, 628, 202});

        mappableAreas.put("-4,-1", new int[]{330, 140, 348, 162});
        mappableAreas.put("-4,0", new int[]{400, 140, 418, 162});
        mappableAreas.put("-4,1", new int[]{470, 140, 488, 162});
        mappableAreas.put("-4,2", new int[]{540, 140, 558, 162});
        mappableAreas.put("-4,3", new int[]{610, 140, 628, 162});

        mappableAreas.put("-5,-3", new int[]{190, 100, 208, 122});
        mappableAreas.put("-5,-4", new int[]{120, 100, 138, 122});

        mappableAreas.put("6,-1", new int[]{330, 540, 348, 562});
        mappableAreas.put("6,-2", new int[]{260, 540, 278, 562});
        mappableAreas.put("6,-3", new int[]{190, 540, 208, 562});
        mappableAreas.put("6,-4", new int[]{120, 540, 138, 562});

        mappableAreas.put("-4,-3", new int[]{190, 140, 208, 162});
        mappableAreas.put("-3,-3", new int[]{190, 180, 208, 202});
        mappableAreas.put("-2,-3", new int[]{190, 220, 208, 242});
        mappableAreas.put("-1,-3", new int[]{190, 260, 208, 288});
        mappableAreas.put("0,-3", new int[]{190, 300, 208, 322});
        mappableAreas.put("1,-3", new int[]{190, 340, 208, 362});
        mappableAreas.put("2,-3", new int[]{190, 380, 208, 402});
        mappableAreas.put("3,-3", new int[]{190, 420, 208, 442});
        mappableAreas.put("4,-3", new int[]{190, 460, 208, 482});
        mappableAreas.put("5,-3", new int[]{190, 500, 208, 522});

        mappableAreas.put("-4,-4", new int[]{120, 140, 138, 162});
        mappableAreas.put("-3,-4", new int[]{120, 180, 138, 202});
        mappableAreas.put("-2,-4", new int[]{120, 220, 138, 242});
        mappableAreas.put("-1,-4", new int[]{120, 260, 138, 288});
        mappableAreas.put("0,-4", new int[]{120, 300, 138, 322});
        mappableAreas.put("1,-4", new int[]{120, 340, 138, 362});
        mappableAreas.put("2,-4", new int[]{120, 380, 138, 402});
        mappableAreas.put("3,-4", new int[]{120, 420, 138, 442});
        mappableAreas.put("4,-4", new int[]{120, 460, 138, 482});
        mappableAreas.put("5,-4", new int[]{120, 500, 138, 522});

        mappableAreas.put("-4,-2", new int[]{260, 140, 278, 162});
        mappableAreas.put("-3,-2", new int[]{260, 180, 278, 202});
        mappableAreas.put("6,4", new int[]{680, 540, 698, 562});

        mappableAreas.put("-5,5", new int[]{750, 100, 768, 122});
        mappableAreas.put("-4,5", new int[]{750, 140, 768, 162});
        mappableAreas.put("-3,5", new int[]{750, 180, 768, 202});
        mappableAreas.put("-2,5", new int[]{750, 220, 768, 242});
        mappableAreas.put("-1,5", new int[]{750, 260, 768, 288});
        mappableAreas.put("0,5", new int[]{750, 300, 768, 322});
        mappableAreas.put("1,5", new int[]{750, 340, 768, 362});
        mappableAreas.put("2,5", new int[]{750, 380, 768, 402});
        mappableAreas.put("3,5", new int[]{750, 420, 768, 442});
        mappableAreas.put("4,5", new int[]{750, 460, 768, 482});
        mappableAreas.put("5,5", new int[]{750, 500, 768, 522});
        mappableAreas.put("6,5", new int[]{750, 540, 768, 562});

        //nuove:
        mappableAreas.put("7,-4", new int[]{120, 580, 138, 602});
        mappableAreas.put("7,-3", new int[]{190, 580, 208, 602});
        mappableAreas.put("7,-2", new int[]{260, 580, 278, 602});
        mappableAreas.put("7,-1", new int[]{330, 580, 348, 602});
        mappableAreas.put("7,0", new int[]{400, 580, 418, 602});
        mappableAreas.put("7,1", new int[]{470, 580, 488, 602});
        mappableAreas.put("7,2", new int[]{540, 580, 558, 602});
        mappableAreas.put("7,3", new int[]{610, 580, 628, 602});
        mappableAreas.put("7,4", new int[]{680, 580, 698, 602});
        mappableAreas.put("7,5", new int[]{750, 580, 768, 602});

        mappableAreas.put("8,-4", new int[]{120, 620, 138, 642});
        mappableAreas.put("8,-3", new int[]{190, 620, 208, 642});
        mappableAreas.put("8,-2", new int[]{260, 620, 278, 642});
        mappableAreas.put("8,-1", new int[]{330, 620, 348, 642});
        mappableAreas.put("8,0", new int[]{400, 620, 418, 642});
        mappableAreas.put("8,1", new int[]{470, 620, 488, 642});
        mappableAreas.put("8,2", new int[]{540, 620, 558, 642});
        mappableAreas.put("8,3", new int[]{610, 620, 628, 642});
        mappableAreas.put("8,4", new int[]{680, 620, 698, 642});
        mappableAreas.put("8,5", new int[]{750, 620, 768, 642});

        mappableAreas.put("9,-4", new int[]{120, 660, 138, 682});
        mappableAreas.put("9,-3", new int[]{190, 660, 208, 682});
        mappableAreas.put("9,-2", new int[]{260, 660, 278, 682});
        mappableAreas.put("9,-1", new int[]{330, 660, 348, 682});
        mappableAreas.put("9,0", new int[]{400, 660, 418, 682});
        mappableAreas.put("9,1", new int[]{470, 660, 488, 682});
        mappableAreas.put("9,2", new int[]{540, 660, 558, 682});
        mappableAreas.put("9,3", new int[]{610, 660, 628, 682});
        mappableAreas.put("9,4", new int[]{680, 660, 698, 682});
        mappableAreas.put("9,5", new int[]{750, 660, 768, 682});

        mappableAreas.put("10,-4", new int[]{120, 700, 138, 722});
        mappableAreas.put("10,-3", new int[]{190, 700, 208, 722});
        mappableAreas.put("10,-2", new int[]{260, 700, 278, 722});
        mappableAreas.put("10,-1", new int[]{330, 700, 348, 722});
        mappableAreas.put("10,0", new int[]{400, 700, 418, 722});
        mappableAreas.put("10,1", new int[]{470, 700, 488, 722});
        mappableAreas.put("10,2", new int[]{540, 700, 558, 722});
        mappableAreas.put("10,3", new int[]{610, 700, 628, 722});
        mappableAreas.put("10,4", new int[]{680, 700, 698, 722});
        mappableAreas.put("10,5", new int[]{750, 700, 768, 722});

        mappableAreas.put("11,-4", new int[]{120, 740, 138, 762});
        mappableAreas.put("11,-3", new int[]{190, 740, 208, 762});
        mappableAreas.put("11,-2", new int[]{260, 740, 278, 762});
        mappableAreas.put("11,-1", new int[]{330, 740, 348, 762});
        mappableAreas.put("11,0", new int[]{400, 740, 418, 762});
        mappableAreas.put("11,1", new int[]{470, 740, 488, 762});
        mappableAreas.put("11,2", new int[]{540, 740, 558, 762});
        mappableAreas.put("11,3", new int[]{610, 740, 628, 762});
        mappableAreas.put("11,4", new int[]{680, 740, 698, 762});
        mappableAreas.put("11,5", new int[]{750, 740, 768, 762});

        mappableAreas.put("12,-4", new int[]{120, 780, 138, 802});
        mappableAreas.put("12,-3", new int[]{190, 780, 208, 802});
        mappableAreas.put("12,-2", new int[]{260, 780, 278, 802});
        mappableAreas.put("12,-1", new int[]{330, 780, 348, 802});
        mappableAreas.put("12,0", new int[]{400, 780, 418, 802});
        mappableAreas.put("12,1", new int[]{470, 780, 488, 802});
        mappableAreas.put("12,2", new int[]{540, 780, 558, 802});
        mappableAreas.put("12,3", new int[]{610, 780, 628, 802});
        mappableAreas.put("12,4", new int[]{680, 780, 698, 802});
        mappableAreas.put("12,5", new int[]{750, 780, 768, 802});


        mappableAreas.put("-6,-4", new int[]{120, 60, 138, 82});
        mappableAreas.put("-6,-3", new int[]{190, 60, 208, 82});
        mappableAreas.put("-6,-2", new int[]{260, 60, 278, 82});
        mappableAreas.put("-6,-1", new int[]{330, 60, 348, 82});
        mappableAreas.put("-6,0", new int[]{400, 60, 418, 82});
        mappableAreas.put("-6,1", new int[]{470, 60, 488, 82});
        mappableAreas.put("-6,2", new int[]{540, 60, 558, 82});
        mappableAreas.put("-6,3", new int[]{610, 60, 628, 82});
        mappableAreas.put("-6,4", new int[]{680, 60, 698, 82});
        mappableAreas.put("-6,5", new int[]{750, 60, 768, 82});

        mappableAreas.put("-7,-4", new int[]{120, 20, 138, 42});
        mappableAreas.put("-7,-3", new int[]{190, 20, 208, 42});
        mappableAreas.put("-7,-2", new int[]{260, 20, 278, 42});
        mappableAreas.put("-7,-1", new int[]{330, 20, 348, 42});
        mappableAreas.put("-7,0", new int[]{400, 20, 418, 42});
        mappableAreas.put("-7,1", new int[]{470, 20, 488, 42});
        mappableAreas.put("-7,2", new int[]{540, 20, 558, 42});
        mappableAreas.put("-7,3", new int[]{610, 20, 628, 42});
        mappableAreas.put("-7,4", new int[]{680, 20, 698, 42});
        mappableAreas.put("-7,5", new int[]{750, 20, 768, 42});

        mappableAreas.put("-8,-4", new int[]{120, -20, 138, 2});
        mappableAreas.put("-8,-3", new int[]{190, -20, 208, 2});
        mappableAreas.put("-8,-2", new int[]{260, -20, 278, 2});
        mappableAreas.put("-8,-1", new int[]{330, -20, 348, 2});
        mappableAreas.put("-8,0", new int[]{400, -20, 418, 2});
        mappableAreas.put("-8,1", new int[]{470, -20, 488, 2});
        mappableAreas.put("-8,2", new int[]{540, -20, 558, 2});
        mappableAreas.put("-8,3", new int[]{610, -20, 628, 2});
        mappableAreas.put("-8,4", new int[]{680, -20, 698, 2});
        mappableAreas.put("-8,5", new int[]{750, -20, 768, 2});

        mappableAreas.put("-9,-4", new int[]{120, -60, 138, -38});
        mappableAreas.put("-9,-3", new int[]{190, -60, 208, -38});
        mappableAreas.put("-9,-2", new int[]{260, -60, 278, -38});
        mappableAreas.put("-9,-1", new int[]{330, -60, 348, -38});
        mappableAreas.put("-9,0", new int[]{400, -60, 418, -38});
        mappableAreas.put("-9,1", new int[]{470, -60, 488, -38});
        mappableAreas.put("-9,2", new int[]{540, -60, 558, -38});
        mappableAreas.put("-9,3", new int[]{610, -60, 628, -38});
        mappableAreas.put("-9,4", new int[]{680, -60, 698, -38});
        mappableAreas.put("-9,5", new int[]{750, -60, 768, -38});

        mappableAreas.put("-10,-4", new int[]{120, -100, 138, -78});
        mappableAreas.put("-10,-3", new int[]{190, -100, 208, -78});
        mappableAreas.put("-10,-2", new int[]{260, -100, 278, -78});
        mappableAreas.put("-10,-1", new int[]{330, -100, 348, -78});
        mappableAreas.put("-10,0", new int[]{400, -100, 418, -78});
        mappableAreas.put("-10,1", new int[]{470, -100, 488, -78});
        mappableAreas.put("-10,2", new int[]{540, -100, 558, -78});
        mappableAreas.put("-10,3", new int[]{610, -100, 628, -78});
        mappableAreas.put("-10,4", new int[]{680, -100, 698, -78});
        mappableAreas.put("-10,5", new int[]{750, -100, 768, -78});

        mappableAreas.put("-11,-4", new int[]{120, -140, 138, -118});
        mappableAreas.put("-11,-3", new int[]{190, -140, 208, -118});
        mappableAreas.put("-11,-2", new int[]{260, -140, 278, -118});
        mappableAreas.put("-11,-1", new int[]{330, -140, 348, -118});
        mappableAreas.put("-11,0", new int[]{400, -140, 418, -118});
        mappableAreas.put("-11,1", new int[]{470, -140, 488, -118});
        mappableAreas.put("-11,2", new int[]{540, -140, 558, -118});
        mappableAreas.put("-11,3", new int[]{610, -140, 628, -118});
        mappableAreas.put("-11,4", new int[]{680, -140, 698, -118});
        mappableAreas.put("-11,5", new int[]{750, -140, 768, -118});

        mappableAreas.put("-12,-4", new int[]{120, -180, 138, -158});
        mappableAreas.put("-12,-3", new int[]{190, -180, 208, -158});
        mappableAreas.put("-12,-2", new int[]{260, -180, 278, -158});
        mappableAreas.put("-12,-1", new int[]{330, -180, 348, -158});
        mappableAreas.put("-12,0", new int[]{400, -180, 418, -158});
        mappableAreas.put("-12,1", new int[]{470, -180, 488, -158});
        mappableAreas.put("-12,2", new int[]{540, -180, 558, -158});
        mappableAreas.put("-12,3", new int[]{610, -180, 628, -158});
        mappableAreas.put("-12,4", new int[]{680, -180, 698, -158});
        mappableAreas.put("-12,5", new int[]{750, -180, 768, -158});

        mappableAreas.put("-12,6", new int[]{820, -180, 838, -158});
        mappableAreas.put("-11,6", new int[]{820, -140, 838, -118});
        mappableAreas.put("-10,6", new int[]{820, -100, 838, -78});
        mappableAreas.put("-9,6", new int[]{820, -60, 838, -38});
        mappableAreas.put("-8,6", new int[]{820, -20, 838, 2});
        mappableAreas.put("-7,6", new int[]{820, 20, 838, 42});
        mappableAreas.put("-6,6", new int[]{820, 60, 838, 82});
        mappableAreas.put("-5,6", new int[]{820, 100, 838, 122});
        mappableAreas.put("-4,6", new int[]{820, 140, 838, 162});
        mappableAreas.put("-3,6", new int[]{820, 180, 838, 202});
        mappableAreas.put("-2,6", new int[]{820, 220, 838, 242});
        mappableAreas.put("-1,6", new int[]{820, 260, 838, 282});
        mappableAreas.put("0,6", new int[]{820, 300, 838, 322});
        mappableAreas.put("1,6", new int[]{820, 340, 838, 362});
        mappableAreas.put("2,6", new int[]{820, 380, 838, 402});
        mappableAreas.put("3,6", new int[]{820, 420, 838, 442});
        mappableAreas.put("4,6", new int[]{820, 460, 838, 482});
        mappableAreas.put("5,6", new int[]{820, 500, 838, 522});
        mappableAreas.put("6,6", new int[]{820, 540, 838, 562});
        mappableAreas.put("7,6", new int[]{820, 580, 838, 602});
        mappableAreas.put("8,6", new int[]{820, 620, 838, 642});
        mappableAreas.put("9,6", new int[]{820, 660, 838, 682});
        mappableAreas.put("10,6", new int[]{820, 700, 838, 722});
        mappableAreas.put("11,6", new int[]{820, 740, 838, 762});
        mappableAreas.put("12,6", new int[]{820, 780, 838, 802});

        mappableAreas.put("-12,7", new int[]{890, -180, 908, -158});
        mappableAreas.put("-11,7", new int[]{890, -140, 908, -118});
        mappableAreas.put("-10,7", new int[]{890, -100, 908, -78});
        mappableAreas.put("-9,7", new int[]{890, -60, 908, -38});
        mappableAreas.put("-8,7", new int[]{890, -20, 908, 2});
        mappableAreas.put("-7,7", new int[]{890, 20, 908, 42});
        mappableAreas.put("-6,7", new int[]{890, 60, 908, 82});
        mappableAreas.put("-5,7", new int[]{890, 100, 908, 122});
        mappableAreas.put("-4,7", new int[]{890, 140, 908, 162});
        mappableAreas.put("-3,7", new int[]{890, 180, 908, 202});
        mappableAreas.put("-2,7", new int[]{890, 220, 908, 242});
        mappableAreas.put("-1,7", new int[]{890, 260, 908, 282});
        mappableAreas.put("0,7", new int[]{890, 300, 908, 322});
        mappableAreas.put("1,7", new int[]{890, 340, 908, 362});
        mappableAreas.put("2,7", new int[]{890, 380, 908, 402});
        mappableAreas.put("3,7", new int[]{890, 420, 908, 442});
        mappableAreas.put("4,7", new int[]{890, 460, 908, 482});
        mappableAreas.put("5,7", new int[]{890, 500, 908, 522});
        mappableAreas.put("6,7", new int[]{890, 540, 908, 562});
        mappableAreas.put("7,7", new int[]{890, 580, 908, 602});
        mappableAreas.put("8,7", new int[]{890, 620, 908, 642});
        mappableAreas.put("9,7", new int[]{890, 660, 908, 682});
        mappableAreas.put("10,7", new int[]{890, 700, 908, 722});
        mappableAreas.put("11,7", new int[]{890, 740, 908, 762});
        mappableAreas.put("12,7", new int[]{890, 780, 908, 802});

        mappableAreas.put("-12,8", new int[]{960, -180, 978, -158});
        mappableAreas.put("-11,8", new int[]{960, -140, 978, -118});
        mappableAreas.put("-10,8", new int[]{960, -100, 978, -78});
        mappableAreas.put("-9,8", new int[]{960, -60, 978, -38});
        mappableAreas.put("-8,8", new int[]{960, -20, 978, 2});
        mappableAreas.put("-7,8", new int[]{960, 20, 978, 42});
        mappableAreas.put("-6,8", new int[]{960, 60, 978, 82});
        mappableAreas.put("-5,8", new int[]{960, 100, 978, 122});
        mappableAreas.put("-4,8", new int[]{960, 140, 978, 162});
        mappableAreas.put("-3,8", new int[]{960, 180, 978, 202});
        mappableAreas.put("-2,8", new int[]{960, 220, 978, 242});
        mappableAreas.put("-1,8", new int[]{960, 260, 978, 282});
        mappableAreas.put("0,8", new int[]{960, 300, 978, 322});
        mappableAreas.put("1,8", new int[]{960, 340, 978, 362});
        mappableAreas.put("2,8", new int[]{960, 380, 978, 402});
        mappableAreas.put("3,8", new int[]{960, 420, 978, 442});
        mappableAreas.put("4,8", new int[]{960, 460, 978, 482});
        mappableAreas.put("5,8", new int[]{960, 500, 978, 522});
        mappableAreas.put("6,8", new int[]{960, 540, 978, 562});
        mappableAreas.put("7,8", new int[]{960, 580, 978, 602});
        mappableAreas.put("8,8", new int[]{960, 620, 978, 642});
        mappableAreas.put("9,8", new int[]{960, 660, 978, 682});
        mappableAreas.put("10,8", new int[]{960, 700, 978, 722});
        mappableAreas.put("11,8", new int[]{960, 740, 978, 762});
        mappableAreas.put("12,8", new int[]{960, 780, 978, 802});

        mappableAreas.put("-12,9", new int[]{1030, -180, 1048, -158});
        mappableAreas.put("-11,9", new int[]{1030, -140, 1048, -118});
        mappableAreas.put("-10,9", new int[]{1030, -100, 1048, -78});
        mappableAreas.put("-9,9", new int[]{1030, -60, 1048, -38});
        mappableAreas.put("-8,9", new int[]{1030, -20, 1048, 2});
        mappableAreas.put("-7,9", new int[]{1030, 20, 1048, 42});
        mappableAreas.put("-6,9", new int[]{1030, 60, 1048, 82});
        mappableAreas.put("-5,9", new int[]{1030, 100, 1048, 122});
        mappableAreas.put("-4,9", new int[]{1030, 140, 1048, 162});
        mappableAreas.put("-3,9", new int[]{1030, 180, 1048, 202});
        mappableAreas.put("-2,9", new int[]{1030, 220, 1048, 242});
        mappableAreas.put("-1,9", new int[]{1030, 260, 1048, 282});
        mappableAreas.put("0,9", new int[]{1030, 300, 1048, 322});
        mappableAreas.put("1,9", new int[]{1030, 340, 1048, 362});
        mappableAreas.put("2,9", new int[]{1030, 380, 1048, 402});
        mappableAreas.put("3,9", new int[]{1030, 420, 1048, 442});
        mappableAreas.put("4,9", new int[]{1030, 460, 1048, 482});
        mappableAreas.put("5,9", new int[]{1030, 500, 1048, 522});
        mappableAreas.put("6,9", new int[]{1030, 540, 1048, 562});
        mappableAreas.put("7,9", new int[]{1030, 580, 1048, 602});
        mappableAreas.put("8,9", new int[]{1030, 620, 1048, 642});
        mappableAreas.put("9,9", new int[]{1030, 660, 1048, 682});
        mappableAreas.put("10,9", new int[]{1030, 700, 1048, 722});
        mappableAreas.put("11,9", new int[]{1030, 740, 1048, 762});
        mappableAreas.put("12,9", new int[]{1030, 780, 1048, 802});

        mappableAreas.put("-12,10", new int[]{1100, -180, 1118, -158});
        mappableAreas.put("-11,10", new int[]{1100, -140, 1118, -118});
        mappableAreas.put("-10,10", new int[]{1100, -100, 1118, -78});
        mappableAreas.put("-9,10", new int[]{1100, -60, 1118, -38});
        mappableAreas.put("-8,10", new int[]{1100, -20, 1118, 2});
        mappableAreas.put("-7,10", new int[]{1100, 20, 1118, 42});
        mappableAreas.put("-6,10", new int[]{1100, 60, 1118, 82});
        mappableAreas.put("-5,10", new int[]{1100, 100, 1118, 122});
        mappableAreas.put("-4,10", new int[]{1100, 140, 1118, 162});
        mappableAreas.put("-3,10", new int[]{1100, 180, 1118, 202});
        mappableAreas.put("-2,10", new int[]{1100, 220, 1118, 242});
        mappableAreas.put("-1,10", new int[]{1100, 260, 1118, 282});
        mappableAreas.put("0,10", new int[]{1100, 300, 1118, 322});
        mappableAreas.put("1,10", new int[]{1100, 340, 1118, 362});
        mappableAreas.put("2,10", new int[]{1100, 380, 1118, 402});
        mappableAreas.put("3,10", new int[]{1100, 420, 1118, 442});
        mappableAreas.put("4,10", new int[]{1100, 460, 1118, 482});
        mappableAreas.put("5,10", new int[]{1100, 500, 1118, 522});
        mappableAreas.put("6,10", new int[]{1100, 540, 1118, 562});
        mappableAreas.put("7,10", new int[]{1100, 580, 1118, 602});
        mappableAreas.put("8,10", new int[]{1100, 620, 1118, 642});
        mappableAreas.put("9,10", new int[]{1100, 660, 1118, 682});
        mappableAreas.put("10,10", new int[]{1100, 700, 1118, 722});
        mappableAreas.put("11,10", new int[]{1100, 740, 1118, 762});
        mappableAreas.put("12,10", new int[]{1100, 780, 1118, 802});

        mappableAreas.put("-12,11", new int[]{1170, -180, 1188, -158});
        mappableAreas.put("-11,11", new int[]{1170, -140, 1188, -118});
        mappableAreas.put("-10,11", new int[]{1170, -100, 1188, -78});
        mappableAreas.put("-9,11", new int[]{1170, -60, 1188, -38});
        mappableAreas.put("-8,11", new int[]{1170, -20, 1188, 2});
        mappableAreas.put("-7,11", new int[]{1170, 20, 1188, 42});
        mappableAreas.put("-6,11", new int[]{1170, 60, 1188, 82});
        mappableAreas.put("-5,11", new int[]{1170, 100, 1188, 122});
        mappableAreas.put("-4,11", new int[]{1170, 140, 1188, 162});
        mappableAreas.put("-3,11", new int[]{1170, 180, 1188, 202});
        mappableAreas.put("-2,11", new int[]{1170, 220, 1188, 242});
        mappableAreas.put("-1,11", new int[]{1170, 260, 1188, 282});
        mappableAreas.put("0,11", new int[]{1170, 300, 1188, 322});
        mappableAreas.put("1,11", new int[]{1170, 340, 1188, 362});
        mappableAreas.put("2,11", new int[]{1170, 380, 1188, 402});
        mappableAreas.put("3,11", new int[]{1170, 420, 1188, 442});
        mappableAreas.put("4,11", new int[]{1170, 460, 1188, 482});
        mappableAreas.put("5,11", new int[]{1170, 500, 1188, 522});
        mappableAreas.put("6,11", new int[]{1170, 540, 1188, 562});
        mappableAreas.put("7,11", new int[]{1170, 580, 1188, 602});
        mappableAreas.put("8,11", new int[]{1170, 620, 1188, 642});
        mappableAreas.put("9,11", new int[]{1170, 660, 1188, 682});
        mappableAreas.put("10,11", new int[]{1170, 700, 1188, 722});
        mappableAreas.put("11,11", new int[]{1170, 740, 1188, 762});
        mappableAreas.put("12,11", new int[]{1170, 780, 1188, 802});

        mappableAreas.put("-12,12", new int[]{1240, -180, 1258, -158});
        mappableAreas.put("-11,12", new int[]{1240, -140, 1258, -118});
        mappableAreas.put("-10,12", new int[]{1240, -100, 1258, -78});
        mappableAreas.put("-9,12", new int[]{1240, -60, 1258, -38});
        mappableAreas.put("-8,12", new int[]{1240, -20, 1258, 2});
        mappableAreas.put("-7,12", new int[]{1240, 20, 1258, 42});
        mappableAreas.put("-6,12", new int[]{1240, 60, 1258, 82});
        mappableAreas.put("-5,12", new int[]{1240, 100, 1258, 122});
        mappableAreas.put("-4,12", new int[]{1240, 140, 1258, 162});
        mappableAreas.put("-3,12", new int[]{1240, 180, 1258, 202});
        mappableAreas.put("-2,12", new int[]{1240, 220, 1258, 242});
        mappableAreas.put("-1,12", new int[]{1240, 260, 1258, 282});
        mappableAreas.put("0,12", new int[]{1240, 300, 1258, 322});
        mappableAreas.put("1,12", new int[]{1240, 340, 1258, 362});
        mappableAreas.put("2,12", new int[]{1240, 380, 1258, 402});
        mappableAreas.put("3,12", new int[]{1240, 420, 1258, 442});
        mappableAreas.put("4,12", new int[]{1240, 460, 1258, 482});
        mappableAreas.put("5,12", new int[]{1240, 500, 1258, 522});
        mappableAreas.put("6,12", new int[]{1240, 540, 1258, 562});
        mappableAreas.put("7,12", new int[]{1240, 580, 1258, 602});
        mappableAreas.put("8,12", new int[]{1240, 620, 1258, 642});
        mappableAreas.put("9,12", new int[]{1240, 660, 1258, 682});
        mappableAreas.put("10,12", new int[]{1240, 700, 1258, 722});
        mappableAreas.put("11,12", new int[]{1240, 740, 1258, 762});
        mappableAreas.put("12,12", new int[]{1240, 780, 1258, 802});



//mappableAreas.put("7,-4", new int[]{120, 580, 138, 602});
//nuove negative
        mappableAreas.put("-12,-5", new int[]{50, -180, 68, -158});
        mappableAreas.put("-11,-5", new int[]{50, -140, 68, -118});
        mappableAreas.put("-10,-5", new int[]{50, -100, 68, -78});
        mappableAreas.put("-9,-5", new int[]{50, -60, 68, -38});
        mappableAreas.put("-8,-5", new int[]{50, -20, 68, 2});
        mappableAreas.put("-7,-5", new int[]{50, 20, 68, 42});
        mappableAreas.put("-6,-5", new int[]{50, 60, 68, 82});
        mappableAreas.put("-5,-5", new int[]{50, 100, 68, 122});
        mappableAreas.put("-4,-5", new int[]{50, 140, 68, 162});
        mappableAreas.put("-3,-5", new int[]{50, 180, 68, 202});
        mappableAreas.put("-2,-5", new int[]{50, 220, 68, 242});
        mappableAreas.put("-1,-5", new int[]{50, 260, 68, 282});
        mappableAreas.put("0,-5", new int[]{50, 300, 68, 322});
        mappableAreas.put("1,-5", new int[]{50, 340, 68, 362});
        mappableAreas.put("2,-5", new int[]{50, 380, 68, 402});
        mappableAreas.put("3,-5", new int[]{50, 420, 68, 442});
        mappableAreas.put("4,-5", new int[]{50, 460, 68, 482});
        mappableAreas.put("5,-5", new int[]{50, 500, 68, 522});
        mappableAreas.put("6,-5", new int[]{50, 540, 68, 562});
        mappableAreas.put("7,-5", new int[]{50, 580, 68, 602});
        mappableAreas.put("8,-5", new int[]{50, 620, 68, 642});
        mappableAreas.put("9,-5", new int[]{50, 660, 68, 682});
        mappableAreas.put("10,-5", new int[]{50, 700, 68, 722});
        mappableAreas.put("11,-5", new int[]{50, 740, 68, 762});
        mappableAreas.put("12,-5", new int[]{50, 780, 68, 802});

        mappableAreas.put("-12,-6", new int[]{-20, -180, -2, -158});
        mappableAreas.put("-11,-6", new int[]{-20, -140, -2, -118});
        mappableAreas.put("-10,-6", new int[]{-20, -100, -2, -78});
        mappableAreas.put("-9,-6", new int[]{-20, -60, -2, -38});
        mappableAreas.put("-8,-6", new int[]{-20, -20, -2, 2});
        mappableAreas.put("-7,-6", new int[]{-20, 20, -2, 42});
        mappableAreas.put("-6,-6", new int[]{-20, 60, -2, 82});
        mappableAreas.put("-5,-6", new int[]{-20, 100, -2, 122});
        mappableAreas.put("-4,-6", new int[]{-20, 140, -2, 162});
        mappableAreas.put("-3,-6", new int[]{-20, 180, -2, 202});
        mappableAreas.put("-2,-6", new int[]{-20, 220, -2, 242});
        mappableAreas.put("-1,-6", new int[]{-20, 260, -2, 282});
        mappableAreas.put("0,-6", new int[]{-20, 300, -2, 322});
        mappableAreas.put("1,-6", new int[]{-20, 340, -2, 362});
        mappableAreas.put("2,-6", new int[]{-20, 380, -2, 402});
        mappableAreas.put("3,-6", new int[]{-20, 420, -2, 442});
        mappableAreas.put("4,-6", new int[]{-20, 460, -2, 482});
        mappableAreas.put("5,-6", new int[]{-20, 500, -2, 522});
        mappableAreas.put("6,-6", new int[]{-20, 540, -2, 562});
        mappableAreas.put("7,-6", new int[]{-20, 580, -2, 602});
        mappableAreas.put("8,-6", new int[]{-20, 620, -2, 642});
        mappableAreas.put("9,-6", new int[]{-20, 660, -2, 682});
        mappableAreas.put("10,-6", new int[]{-20, 700, -2, 722});
        mappableAreas.put("11,-6", new int[]{-20, 740, -2, 762});
        mappableAreas.put("12,-6", new int[]{-20, 780, -2, 802});

        mappableAreas.put("-12,-7", new int[]{-90, -180, -72, -158});
        mappableAreas.put("-11,-7", new int[]{-90, -140, -72, -118});
        mappableAreas.put("-10,-7", new int[]{-90, -100, -72, -78});
        mappableAreas.put("-9,-7", new int[]{-90, -60, -72, -38});
        mappableAreas.put("-8,-7", new int[]{-90, -20, -72, 2});
        mappableAreas.put("-7,-7", new int[]{-90, 20, -72, 42});
        mappableAreas.put("-6,-7", new int[]{-90, 60, -72, 82});
        mappableAreas.put("-5,-7", new int[]{-90, 100, -72, 122});
        mappableAreas.put("-4,-7", new int[]{-90, 140, -72, 162});
        mappableAreas.put("-3,-7", new int[]{-90, 180, -72, 202});
        mappableAreas.put("-2,-7", new int[]{-90, 220, -72, 242});
        mappableAreas.put("-1,-7", new int[]{-90, 260, -72, 282});
        mappableAreas.put("0,-7", new int[]{-90, 300, -72, 322});
        mappableAreas.put("1,-7", new int[]{-90, 340, -72, 362});
        mappableAreas.put("2,-7", new int[]{-90, 380, -72, 402});
        mappableAreas.put("3,-7", new int[]{-90, 420, -72, 442});
        mappableAreas.put("4,-7", new int[]{-90, 460, -72, 482});
        mappableAreas.put("5,-7", new int[]{-90, 500, -72, 522});
        mappableAreas.put("6,-7", new int[]{-90, 540, -72, 562});
        mappableAreas.put("7,-7", new int[]{-90, 580, -72, 602});
        mappableAreas.put("8,-7", new int[]{-90, 620, -72, 642});
        mappableAreas.put("9,-7", new int[]{-90, 660, -72, 682});
        mappableAreas.put("10,-7", new int[]{-90, 700, -72, 722});
        mappableAreas.put("11,-7", new int[]{-90, 740, -72, 762});
        mappableAreas.put("12,-7", new int[]{-90, 780, -72, 802});

        mappableAreas.put("-12,-8", new int[]{-160, -180, -142, -158});
        mappableAreas.put("-11,-8", new int[]{-160, -140, -142, -118});
        mappableAreas.put("-10,-8", new int[]{-160, -100, -142, -78});
        mappableAreas.put("-9,-8", new int[]{-160, -60, -142, -38});
        mappableAreas.put("-8,-8", new int[]{-160, -20, -142, 2});
        mappableAreas.put("-7,-8", new int[]{-160, 20, -142, 42});
        mappableAreas.put("-6,-8", new int[]{-160, 60, -142, 82});
        mappableAreas.put("-5,-8", new int[]{-160, 100, -142, 122});
        mappableAreas.put("-4,-8", new int[]{-160, 140, -142, 162});
        mappableAreas.put("-3,-8", new int[]{-160, 180, -142, 202});
        mappableAreas.put("-2,-8", new int[]{-160, 220, -142, 242});
        mappableAreas.put("-1,-8", new int[]{-160, 260, -142, 282});
        mappableAreas.put("0,-8", new int[]{-160, 300, -142, 322});
        mappableAreas.put("1,-8", new int[]{-160, 340, -142, 362});
        mappableAreas.put("2,-8", new int[]{-160, 380, -142, 402});
        mappableAreas.put("3,-8", new int[]{-160, 420, -142, 442});
        mappableAreas.put("4,-8", new int[]{-160, 460, -142, 482});
        mappableAreas.put("5,-8", new int[]{-160, 500, -142, 522});
        mappableAreas.put("6,-8", new int[]{-160, 540, -142, 562});
        mappableAreas.put("7,-8", new int[]{-160, 580, -142, 602});
        mappableAreas.put("8,-8", new int[]{-160, 620, -142, 642});
        mappableAreas.put("9,-8", new int[]{-160, 660, -142, 682});
        mappableAreas.put("10,-8", new int[]{-160, 700, -142, 722});
        mappableAreas.put("11,-8", new int[]{-160, 740, -142, 762});
        mappableAreas.put("12,-8", new int[]{-160, 780, -142, 802});

        mappableAreas.put("-12,-9", new int[]{-230, -180, -212, -158});
        mappableAreas.put("-11,-9", new int[]{-230, -140, -212, -118});
        mappableAreas.put("-10,-9", new int[]{-230, -100, -212, -78});
        mappableAreas.put("-9,-9", new int[]{-230, -60, -212, -38});
        mappableAreas.put("-8,-9", new int[]{-230, -20, -212, 2});
        mappableAreas.put("-7,-9", new int[]{-230, 20, -212, 42});
        mappableAreas.put("-6,-9", new int[]{-230, 60, -212, 82});
        mappableAreas.put("-5,-9", new int[]{-230, 100, -212, 122});
        mappableAreas.put("-4,-9", new int[]{-230, 140, -212, 162});
        mappableAreas.put("-3,-9", new int[]{-230, 180, -212, 202});
        mappableAreas.put("-2,-9", new int[]{-230, 220, -212, 242});
        mappableAreas.put("-1,-9", new int[]{-230, 260, -212, 282});
        mappableAreas.put("0,-9", new int[]{-230, 300, -212, 322});
        mappableAreas.put("1,-9", new int[]{-230, 340, -212, 362});
        mappableAreas.put("2,-9", new int[]{-230, 380, -212, 402});
        mappableAreas.put("3,-9", new int[]{-230, 420, -212, 442});
        mappableAreas.put("4,-9", new int[]{-230, 460, -212, 482});
        mappableAreas.put("5,-9", new int[]{-230, 500, -212, 522});
        mappableAreas.put("6,-9", new int[]{-230, 540, -212, 562});
        mappableAreas.put("7,-9", new int[]{-230, 580, -212, 602});
        mappableAreas.put("8,-9", new int[]{-230, 620, -212, 642});
        mappableAreas.put("9,-9", new int[]{-230, 660, -212, 682});
        mappableAreas.put("10,-9", new int[]{-230, 700, -212, 722});
        mappableAreas.put("11,-9", new int[]{-230, 740, -212, 762});
        mappableAreas.put("12,-9", new int[]{-230, 780, -212, 802});

        mappableAreas.put("-12,-10", new int[]{-300, -180, -282, -158});
        mappableAreas.put("-11,-10", new int[]{-300, -140, -282, -118});
        mappableAreas.put("-10,-10", new int[]{-300, -100, -282, -78});
        mappableAreas.put("-9,-10", new int[]{-300, -60, -282, -38});
        mappableAreas.put("-8,-10", new int[]{-300, -20, -282, 2});
        mappableAreas.put("-7,-10", new int[]{-300, 20, -282, 42});
        mappableAreas.put("-6,-10", new int[]{-300, 60, -282, 82});
        mappableAreas.put("-5,-10", new int[]{-300, 100, -282, 122});
        mappableAreas.put("-4,-10", new int[]{-300, 140, -282, 162});
        mappableAreas.put("-3,-10", new int[]{-300, 180, -282, 202});
        mappableAreas.put("-2,-10", new int[]{-300, 220, -282, 242});
        mappableAreas.put("-1,-10", new int[]{-300, 260, -282, 282});
        mappableAreas.put("0,-10", new int[]{-300, 300, -282, 322});
        mappableAreas.put("1,-10", new int[]{-300, 340, -282, 362});
        mappableAreas.put("2,-10", new int[]{-300, 380, -282, 402});
        mappableAreas.put("3,-10", new int[]{-300, 420, -282, 442});
        mappableAreas.put("4,-10", new int[]{-300, 460, -282, 482});
        mappableAreas.put("5,-10", new int[]{-300, 500, -282, 522});
        mappableAreas.put("6,-10", new int[]{-300, 540, -282, 562});
        mappableAreas.put("7,-10", new int[]{-300, 580, -282, 602});
        mappableAreas.put("8,-10", new int[]{-300, 620, -282, 642});
        mappableAreas.put("9,-10", new int[]{-300, 660, -282, 682});
        mappableAreas.put("10,-10", new int[]{-300, 700, -282, 722});
        mappableAreas.put("11,-10", new int[]{-300, 740, -282, 762});
        mappableAreas.put("12,-10", new int[]{-300, 780, -282, 802});

        mappableAreas.put("-12,-11", new int[]{-370, -180, -352, -158});
        mappableAreas.put("-11,-11", new int[]{-370, -140, -352, -118});
        mappableAreas.put("-10,-11", new int[]{-370, -100, -352, -78});
        mappableAreas.put("-9,-11", new int[]{-370, -60, -352, -38});
        mappableAreas.put("-8,-11", new int[]{-370, -20, -352, 2});
        mappableAreas.put("-7,-11", new int[]{-370, 20, -352, 42});
        mappableAreas.put("-6,-11", new int[]{-370, 60, -352, 82});
        mappableAreas.put("-5,-11", new int[]{-370, 100, -352, 122});
        mappableAreas.put("-4,-11", new int[]{-370, 140, -352, 162});
        mappableAreas.put("-3,-11", new int[]{-370, 180, -352, 202});
        mappableAreas.put("-2,-11", new int[]{-370, 220, -352, 242});
        mappableAreas.put("-1,-11", new int[]{-370, 260, -352, 282});
        mappableAreas.put("0,-11", new int[]{-370, 300, -352, 322});
        mappableAreas.put("1,-11", new int[]{-370, 340, -352, 362});
        mappableAreas.put("2,-11", new int[]{-370, 380, -352, 402});
        mappableAreas.put("3,-11", new int[]{-370, 420, -352, 442});
        mappableAreas.put("4,-11", new int[]{-370, 460, -352, 482});
        mappableAreas.put("5,-11", new int[]{-370, 500, -352, 522});
        mappableAreas.put("6,-11", new int[]{-370, 540, -352, 562});
        mappableAreas.put("7,-11", new int[]{-370, 580, -352, 602});
        mappableAreas.put("8,-11", new int[]{-370, 620, -352, 642});
        mappableAreas.put("9,-11", new int[]{-370, 660, -352, 682});
        mappableAreas.put("10,-11", new int[]{-370, 700, -352, 722});
        mappableAreas.put("11,-11", new int[]{-370, 740, -352, 762});
        mappableAreas.put("12,-11", new int[]{-370, 780, -352, 802});

        mappableAreas.put("-12,-12", new int[]{-440, -180, -422, -158});
        mappableAreas.put("-11,-12", new int[]{-440, -140, -422, -118});
        mappableAreas.put("-10,-12", new int[]{-440, -100, -422, -78});
        mappableAreas.put("-9,-12", new int[]{-440, -60, -422, -38});
        mappableAreas.put("-8,-12", new int[]{-440, -20, -422, 2});
        mappableAreas.put("-7,-12", new int[]{-440, 20, -422, 42});
        mappableAreas.put("-6,-12", new int[]{-440, 60, -422, 82});
        mappableAreas.put("-5,-12", new int[]{-440, 100, -422, 122});
        mappableAreas.put("-4,-12", new int[]{-440, 140, -422, 162});
        mappableAreas.put("-3,-12", new int[]{-440, 180, -422, 202});
        mappableAreas.put("-2,-12", new int[]{-440, 220, -422, 242});
        mappableAreas.put("-1,-12", new int[]{-440, 260, -422, 282});
        mappableAreas.put("0,-12", new int[]{-440, 300, -422, 322});
        mappableAreas.put("1,-12", new int[]{-440, 340, -422, 362});
        mappableAreas.put("2,-12", new int[]{-440, 380, -422, 402});
        mappableAreas.put("3,-12", new int[]{-440, 420, -422, 442});
        mappableAreas.put("4,-12", new int[]{-440, 460, -422, 482});
        mappableAreas.put("5,-12", new int[]{-440, 500, -422, 522});
        mappableAreas.put("6,-12", new int[]{-440, 540, -422, 562});
        mappableAreas.put("7,-12", new int[]{-440, 580, -422, 602});
        mappableAreas.put("8,-12", new int[]{-440, 620, -422, 642});
        mappableAreas.put("9,-12", new int[]{-440, 660, -422, 682});
        mappableAreas.put("10,-12", new int[]{-440, 700, -422, 722});
        mappableAreas.put("11,-12", new int[]{-440, 740, -422, 762});
        mappableAreas.put("12,-12", new int[]{-440, 780, -422, 802});
    }

    public int[] getMappedPosition(double x, double y) {
        for (Map.Entry<String, int[]> entry : mappableAreas.entrySet()) {
            int[] coords = entry.getValue();
            if (isWithinArea(x, y, coords)) {
                String[] pos = entry.getKey().split(",");
                return new int[]{Integer.parseInt(pos[0]), Integer.parseInt(pos[1])};
            }
        }
        return null;
    }

    public int[] getMinCorner(double x, double y) {
        for (Map.Entry<String, int[]> entry : mappableAreas.entrySet()) {
            int[] coords = entry.getValue();
            if (isWithinArea(x, y, coords)) {
                return new int[]{coords[0], coords[1]};
            }
        }
        return null;
    }

    private boolean isWithinArea(double x, double y, int[] coords) {
        return x >= coords[0] && x <= coords[2] && y >= coords[1] && y <= coords[3];
    }
}
