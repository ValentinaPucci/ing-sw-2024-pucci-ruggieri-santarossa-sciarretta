package it.polimi.demo.view.UI.TUI;
public class TuiCardGraphics {

    // Sequenze di escape ANSI per colorare il testo e lo sfondo
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m"; // Colore di sfondo personalizzabile
    public static final String ANSI_RED_BACKGROUND = "\u001B[31m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[35m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[36m";
    public static final String ANSI_COLOR_CORNER = "\u001B[41m"; // Colore degli angoli personalizzabile

    // Rappresentazione grafica della carta
    private static final String[] GRAFICA_CARTA = {
            "╭──────────────╮",
            "│ %s          %s │",
            "│              │",
            "│ %s          %s │",
            "╰──────────────╯"
    };

    private String corner1;
    private String corner2;
    private String corner3;
    private String corner4;


    public TuiCardGraphics(String corner1, String corner2, String corner3, String corner4) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
    }

    public void stampaCarta(String Color) {
        System.out.println(ANSI_RESET + ANSI_BLACK + Color + GRAFICA_CARTA[0] + ANSI_RESET);
        for (int i = 1; i < GRAFICA_CARTA.length - 1; i++) {
            if (i < 3)
                System.out.println(ANSI_RESET + ANSI_BLACK + Color + String.format(GRAFICA_CARTA[i], corner1, corner2) + ANSI_RESET);
            else
                System.out.println(ANSI_RESET + ANSI_BLACK + Color + String.format(GRAFICA_CARTA[i], corner3, corner4) + ANSI_RESET);
        }
        System.out.println(ANSI_RESET + ANSI_BLACK + Color + GRAFICA_CARTA[4] + ANSI_RESET);
        System.out.println("\n");
    }


    public static void main(String[] args) {
         String ANSI_COLOR_BACKGROUND = "\u001B[42m";

        TuiCardGraphics carta = new TuiCardGraphics("R", "B", "R", "T");
        carta.stampaCarta(ANSI_WHITE_BACKGROUND); // Stampa la carta con sfondo colorato
    }
}