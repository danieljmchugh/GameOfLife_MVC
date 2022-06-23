public class Life {
    public static void main(String[] args) {
        new LifeController(new GameState(), new GameGui(20, 20)); 
   }
}
