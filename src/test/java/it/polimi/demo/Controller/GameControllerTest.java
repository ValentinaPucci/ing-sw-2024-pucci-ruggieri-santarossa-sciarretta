package it.polimi.demo.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
public class GameControllerTest {

    private GameController GameController;

    @BeforeEach
    public void setUp() {
        Model gameModel = new Model();
        Player p = new Player("Player1");
        Integer id = gameModel.getGameId();
        //this.GameController = new GameController(id, 2, p);
        //this.GameController = new GameController(gameModel);
    }*/

//
//    @Test
//    public void getGameModelTest() {
//        Model gameModel = new Model();
//        Player p = new Player("Player1");
//        Integer id = gameModel.getGameId();
//        //GameController = new GameController(gameModel);
//        Model actualGameModel = (Model) GameController.getModel();
//        assertEquals(gameModel, actualGameModel);
//    }


    // Methods that is not coherent alone
//    @Test
//    public void startGameTest() {
//        Player p = new Player("Player1");
//        Player p2 = new Player("Player2");
//
//        GameController GameController = new GameController(1, 2, p);
//        GameController.addPlayer(p2.getNickname());
//        GameController.addPlayer(p.getNickname());
//        GameController.setPlayerAsConnected(p);
//        GameController.setPlayerAsConnected(p2);
//
//        System.out.println(" "+ GameController.getNumPlayersToPlay());
//
//        GameController.startGame();
//        assertEquals(GameController.getStatus(), GameStatus.READY_TO_START);
//    }


//}