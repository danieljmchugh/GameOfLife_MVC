public class Life {
    public static void main(String[] args) {
        new LifeController(new GameState(40, 40), new GameGui(40, 40)); 
   }
}
