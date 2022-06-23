public class Life {
    public static void main(String[] args) {
        new LifeController(new GameModel(40, 40), new GameView(40, 40)); 
   }
}
