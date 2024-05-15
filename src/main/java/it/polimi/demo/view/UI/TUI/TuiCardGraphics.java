package it.polimi.demo.view.UI.TUI;
public class TuiCardGraphics {

    // Sequenze di escape ANSI per colorare il testo e lo sfondo
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    // Rappresentazione grafica della carta
    private static final String[] CARD_GRAPHICS = {
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


    public void printCard(String Color) {
        System.out.println(ANSI_RESET + CARD_GRAPHICS[0] + ANSI_RESET);
        for (int i = 1; i < CARD_GRAPHICS.length - 1; i++) {
            if (i == 1){
                String row = String.format(CARD_GRAPHICS[i], corner1, corner2);
                System.out.println(ANSI_RESET  + row.substring(0,4) + Color+ row.substring(4,12)+ Color+ ANSI_RESET + row.substring(12,16) + ANSI_RESET);
            }
            else if(i== 3){
                String row3 = String.format(CARD_GRAPHICS[i], corner3, corner4);
                //String colored_row3 = coloraRiga(String.format(CARD_GRAPHICS[i], corner3, corner4), Color, 2, 3);
                System.out.println(ANSI_RESET  + row3.substring(0,4) + Color+ row3.substring(4,12)+ Color+ ANSI_RESET + row3.substring(12,16) + ANSI_RESET);
            } else{
                String row4 = String.format(CARD_GRAPHICS[i]);
                System.out.println(ANSI_RESET  + row4.substring(0,2) + Color+ row4.substring(2,14)+ Color+ ANSI_RESET + row4.substring(14,16) + ANSI_RESET);
            }
        }
        System.out.println(ANSI_RESET + CARD_GRAPHICS[4] + ANSI_RESET);
        System.out.println("\n");
    }


    public static void main(String[] args) {
         String ANSI_COLOR_BACKGROUND = "\u001B[42m";

        TuiCardGraphics carta = new TuiCardGraphics("R", "B", "R", "T");
        carta.printCard(ANSI_CYAN_BACKGROUND); // Stampa la carta con sfondo colorato
    }
}