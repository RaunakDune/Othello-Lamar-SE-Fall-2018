package othello;

public class GameBoard 
{
    
    private int corY;
    private char corX;
    private char ch;
    public GameBoard(char x, int y, char c)
    {
        corY = y;
        corX = x;
        ch = c;  
    }

    GameBoard() {}
    char getCorX()
    {
        return corX; 
    }
    int getCorY() 
    { 
        return corY; 
    }
    char getCh() 
    {
        return ch; 
    }
    void setPosition(char x, char c, int y)
    {
        corX = x;
        corY = y;
        ch = c;
    }
}
