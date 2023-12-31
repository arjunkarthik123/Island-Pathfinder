package ca.mcmaster.cas.se2aa4.a3.island.Heatmaps;

import ca.mcmaster.cas.se2aa4.a3.island.ShapeAdts.MyShape;

import java.awt.*;

public class ElevationPainter extends HeatmapPainter{
    @Override
    public Color determineColor(MyShape s) {
        int elevation = s.getElevation();

        return switch ((elevation == -1) ? 6 :
                (0 <= elevation && elevation <= 10) ? 0 :
                (11 <= elevation && elevation <= 20) ? 1 :
                        (21 <= elevation && elevation <= 30) ? 2 :
                                (31 <= elevation && elevation <= 50) ? 3 :
                                        (51 <= elevation && elevation <= 75) ? 4 : 5
                ) {
            case 0 -> new Color(210, 240, 255);
            case 1 -> new Color(140, 220, 255);
            case 2 -> new Color(60, 210, 253);
            case 3 -> new Color(30, 175, 255);
            case 4 -> new Color(0, 100, 230);
            case 5 -> new Color(0, 40, 200);
            case 6 -> new Color(0, 39, 54);
            default -> Color.BLACK;
        };
    }
}
