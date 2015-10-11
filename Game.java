public class Game
{
    private BoardLocation board;
    protected static final BoardLocation GO = new CornerSquare();

    public Game()
    {
    }

    public static BoardLocation getGo()
    {
        return GO;
    }
}
