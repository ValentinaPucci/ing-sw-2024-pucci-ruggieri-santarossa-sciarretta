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

        mappableAreas.put("6,-7", new int[]{50, 540, 68, 562});
        mappableAreas.put("6,-6", new int[]{120, 540, 138, 562});
        mappableAreas.put("6,-5", new int[]{190, 540, 208, 562});

        mappableAreas.put("7,-7", new int[]{50, 580, 68, 602});
        mappableAreas.put("7,-6", new int[]{120, 580, 138, 602});
        mappableAreas.put("7,-5", new int[]{190, 580, 208, 602});
        mappableAreas.put("7,-4", new int[]{260, 580, 278, 602});
        mappableAreas.put("7,-3", new int[]{330, 580, 348, 602});
        mappableAreas.put("7,-2", new int[]{400, 580, 418, 602});
        mappableAreas.put("7,-1", new int[]{470, 580, 488, 602});
        mappableAreas.put("7,0", new int[]{540, 580, 558, 602});
        mappableAreas.put("7,1", new int[]{610, 580, 628, 602});
        mappableAreas.put("7,2", new int[]{680, 580, 698, 602});
        mappableAreas.put("7,3", new int[]{750, 580, 768, 602});
        mappableAreas.put("7,4", new int[]{820, 580, 838, 602});
        mappableAreas.put("7,5", new int[]{890, 580, 908, 602});

        mappableAreas.put("-6,-4", new int[]{260, 460, 278, 482});
        mappableAreas.put("-6,-3", new int[]{330, 460, 348, 482});
        mappableAreas.put("-6,-2", new int[]{400, 460, 418, 482});
        mappableAreas.put("-6,-1", new int[]{470, 460, 488, 482});
        mappableAreas.put("-6,0", new int[]{540, 460, 558, 482});
        mappableAreas.put("-6,1", new int[]{610, 460, 628, 482});
        mappableAreas.put("-6,2", new int[]{680, 460, 698, 482});
        mappableAreas.put("-6,3", new int[]{750, 460, 768, 482});
        mappableAreas.put("-6,4", new int[]{820, 460, 838, 482});
        mappableAreas.put("-6,5", new int[]{890, 460, 908, 482});



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
