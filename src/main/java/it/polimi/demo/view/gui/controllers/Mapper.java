package it.polimi.demo.view.gui.controllers;

import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private static final Map<String, int[]> mappableAreas = new HashMap<>();

    static {
        // Define the mappable areas based on the provided image
        mappableAreas.put("0,0", new int[]{400, 300, 418, 322});
        mappableAreas.put("1,0", new int[]{470, 300, 488, 322});
        mappableAreas.put("1,-1", new int[]{470, 340, 488, 362});
        mappableAreas.put("0,-1", new int[]{400, 340, 418, 362});
        mappableAreas.put("0,1", new int[]{400, 260, 418, 288});
        mappableAreas.put("-1,1", new int[]{330, 260, 348, 288});
        mappableAreas.put("-1,0", new int[]{330, 300, 348, 322});
        mappableAreas.put("-1,-1", new int[]{330, 340, 348, 362});
        mappableAreas.put("-2,-1", new int[]{260, 340, 278, 362});
        mappableAreas.put("-2,0", new int[]{260, 300, 278, 322});
        mappableAreas.put("-2,1", new int[]{260, 260, 278, 288});
        mappableAreas.put("0,2", new int[]{400, 220, 418, 242});
        mappableAreas.put("-1,2", new int[]{330, 220, 348, 242});
        mappableAreas.put("-1,3", new int[]{330, 180, 348, 202});
        mappableAreas.put("0,3", new int[]{400, 180, 418, 202});
        mappableAreas.put("1,2", new int[]{470, 220, 488, 242});
        mappableAreas.put("1,1", new int[]{470, 260, 488, 288});
        mappableAreas.put("2,1", new int[]{540, 260, 558, 288});
        mappableAreas.put("2,0", new int[]{540, 300, 558, 322});
        mappableAreas.put("2,-1", new int[]{540, 340, 558, 362});
        mappableAreas.put("2,-2", new int[]{540, 380, 558, 402});
        mappableAreas.put("1,-2", new int[]{470, 380, 488, 402});
        mappableAreas.put("0,-2", new int[]{400, 380, 418, 402});
        mappableAreas.put("-1,-2", new int[]{330, 380, 348, 402});
        mappableAreas.put("-2,-2", new int[]{260, 380, 278, 402});
        mappableAreas.put("2,2", new int[]{540, 220, 558, 242});
        mappableAreas.put("3,2", new int[]{610, 220, 628, 242});
        mappableAreas.put("3,1", new int[]{610, 260, 628, 288});
        mappableAreas.put("3,0", new int[]{610, 300, 628, 322});
        mappableAreas.put("3,-1", new int[]{610, 340, 628, 362});
        mappableAreas.put("3,-2", new int[]{610, 380, 628, 402});
        mappableAreas.put("3,-3", new int[]{610, 420, 628, 442});
        mappableAreas.put("2,-3", new int[]{540, 420, 558, 442});
        mappableAreas.put("1,-3", new int[]{470, 420, 488, 442});
        mappableAreas.put("0,-3", new int[]{400, 420, 418, 442});
        mappableAreas.put("-1,-3", new int[]{330, 420, 348, 442});
        mappableAreas.put("-2,-3", new int[]{260, 420, 278, 442});
        mappableAreas.put("-2,-4", new int[]{260, 460, 278, 482});
        mappableAreas.put("-1,-4", new int[]{330, 460, 348, 482});
        mappableAreas.put("0,-4", new int[]{400, 460, 418, 482});
        mappableAreas.put("1,-4", new int[]{470, 460, 488, 482});
        mappableAreas.put("1,-5", new int[]{470, 500, 488, 522});
        mappableAreas.put("2,-5", new int[]{540, 500, 558, 522});
        mappableAreas.put("3,-5", new int[]{610, 500, 628, 522});
        mappableAreas.put("3,-4", new int[]{610, 460, 628, 482});
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
