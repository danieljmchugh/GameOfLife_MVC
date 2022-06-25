public class Life {
    public static void main(String[] args) {
        int gameSize = 20;
        new LifeController(new GameModel(gameSize, gameSize), new GameView(gameSize, gameSize)); 
   }
}
