public class Life {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Life <dimention>");
            System.exit(0);
        } else{
            int dimention = Integer.valueOf(args[0]);
            new LifeController(new GameModel(dimention), new GameView(dimention)); 
        }
   }
}
