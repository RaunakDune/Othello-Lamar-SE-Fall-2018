package othello;

import java.util.ArrayList;

public class Game {
    
    private  int rows = 8;
    private  int cols = 8;
    private  int userCont = 0;
    private  int user2Cont = 0;
    private  int computerCont = 0;
    
    public GameBoard gameCells[][];
    
   
    public Game()
    {
        int mid;
        mid = rows / 2;
        gameCells = new GameBoard[8][8];
        for(int i = 0; i < rows; ++i)
            gameCells[i] = new GameBoard[8];
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < cols; ++j)
            {   
                gameCells[i][j] = new GameBoard();
                char c = (char) (97 + j);
                if((i == mid-1) && (j == mid-1)){
                    gameCells[i][j].setPosition(c, 'X', i+1);
                }
                else if((i == mid-1) && (j == mid))
                {
                    gameCells[i][j].setPosition(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid-1))
                {
                    gameCells[i][j].setPosition(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid))
                {
                    gameCells[i][j].setPosition(c, 'X', i+1);
                }
                else		
                {
                    gameCells[i][j].setPosition(c, '.', i+1);                    
                }
            }	
        }  
    }

    public Game(Game newGame)
    {
        int y;
        char c, x;
        gameCells = new GameBoard[8][8];
        for(int i = 0; i < rows; ++i)
            gameCells[i] = new GameBoard[8];
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < cols; ++j)
            {   
                gameCells[i][j] = new GameBoard();
                
                c = newGame.gameCells[i][j].getCh();
                y = newGame.gameCells[i][j].getCorY();
                x = newGame.gameCells[i][j].getCorX();
                gameCells[i][j].setPosition(x, c, y);
            }
        }
    }
    public void findValidMoves(ArrayList <Integer> arr)
    {
        int status;
        int change,max = 0; 
        change = 0;
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    int numberOfMoves[] = new int[1];
                    move(i,j,change,'X','O',numberOfMoves);
                    if(numberOfMoves[0] != 0)
                    {
                        arr.add(i);
                        arr.add(j);
                    }    
                } 
            }
        }
    }
    public void findValidMovesWhite(ArrayList <Integer> arr)
    {
        int status;
        int change,max = 0; 
        change = 0;
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    int numberOfMoves[] = new int[1];
                    move(i,j,change,'O','X',numberOfMoves);
                    if(numberOfMoves[0] != 0)
                    {
                        arr.add(i);
                        arr.add(j);
                    }    
                } 
            }
        }
    }
    public int playComputer()
    {  
        int change,max = 0,mX = 0,mY = 0,sum;;    
        change = 0;
        int numberOfMoves[] = new int[1];

        for (int i = 0; i < rows; ++i) 
        {
            for (int j = 0; j < cols; ++j)
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    move(i,j,change,'O','X',numberOfMoves);
                    if(max < numberOfMoves[0])
                    { 
                        max = numberOfMoves[0];
                        mX = i; mY = j;
                    }
                }
            }
        }	
        computerCont = max;
        if (computerCont == 0)
        {
            computerCont = -1;
            return -1;
        }
        if(computerCont != 0)
        {
            change = 1;
            move(mX,mY,change,'O','X',numberOfMoves);		
        }
        return 0;
    }

    public int playBlack(int xCor,int yCor) 
    {  
        int status;
        int change,max = 0; 
        int numberOfMoves[] = new int[1];
        change = 0;
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    move(i,j,change,'X','O',numberOfMoves);
                    if(max < numberOfMoves[0])
                        max = numberOfMoves[0];
                }
            }
        }
        userCont = max;
        if(userCont == 0) 
        { 
            userCont = -1;
            return -1;
        }
        if(userCont != 0)
        {	
            change = 1;
            if(!(gameCells[xCor][yCor].getCh() == '.'))         
                return 1; 
                
            status = move(xCor,yCor,change,'X','O',numberOfMoves);
            if(status == -1)
                return 1; 
        }
        return 0;
    }
    public int playWhite(int xCor,int yCor) 
    {  
        int status;
        int change,max = 0; 
        int numberOfMoves[] = new int[1];
        change = 0;

        for (int i = 0; i < rows; ++i) 
        {
            for (int j = 0; j < cols; ++j)
            {
                if(gameCells[i][j].getCh() == '.')
                {
                    move(i,j,change,'O','X',numberOfMoves);
                    if(max < numberOfMoves[0])
                        max = numberOfMoves[0];
                }
            }
        }	
        user2Cont = max;
        if (user2Cont == 0)
        {
            user2Cont = -1;
            return -1;
        }
        if(user2Cont != 0)
        {
            change = 1;
            if(!(gameCells[xCor][yCor].getCh() == '.'))         
                return 1;

            status = move(xCor,yCor,change,'O','X',numberOfMoves);	
                if(status == -1)
                    return 1; 
            	
        }
        return 0;
    }
    public int endOfGame() 
    {
        int[] arr = new int[3];
        int black, white, blank ;
        calculateScore(arr);
        black = arr[0];
        white = arr[1];
        blank = arr[2];
        
        if( (userCont == -1 && user2Cont == -1) || blank == 0)
        {
            if(userCont == -1 && user2Cont == -1)
                return 0;
            if(white > black)
                return 1;
            else if(black > white)
                return 2;
            else if(black == 0)
                return 3;
            else if(white == 0)
                return 4;
            else
              return 5;
        }
        return -1;
    }
    public void calculateScore(int arr[] )
    {
        int black = 0, point = 0, white = 0;

        int max = 0,numberOfMoves=0;
        for (int i = 0; i < rows; ++i)
        {
            for (int j = 0; j < cols; ++j)
            {
                if(gameCells[i][j].getCh() == 'X')
                    black++;
                else if (gameCells[i][j].getCh() == 'O')
                    white++;
                else if(gameCells[i][j].getCh() == '.')
                    point++;
            }
        } 
        arr[0] = black; arr[1] = white; arr[2] = point;
    }

    public void reset()
    {
        int mid = rows / 2;
        for(int i = 0; i < rows; ++i)
        {
            for(int j = 0; j < cols; ++j)
            {   
                char c = (char) (97 + j);
                if((i == mid-1) && (j == mid-1))
                {
                    gameCells[i][j].setPosition(c, 'X', i+1);
                }
                else if((i == mid-1) && (j == mid))
                {
                    gameCells[i][j].setPosition(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid-1))
                {
                    gameCells[i][j].setPosition(c, 'O', i+1);
                }
                else if((i == mid) && (j == mid))
                {
                    gameCells[i][j].setPosition(c, 'X', i+1);
                }
                else		
                {
                    gameCells[i][j].setPosition(c, '.', i+1);                    
                }
            }
        }
    }


    int move(int xCor, int yCor,int change,char initials,char finals,int []numberOfMoves)
    {
	    int cont,st2=0,st=0;
	    int status = -1,corX,corY,temp;
        char str;
        int ix,y,x;
        
        x = xCor; y = yCor;
        numberOfMoves[0] = 0;
        
        /*
            Simple Right
        */

	    if((x+1 < rows) && ( gameCells[x+1][y].getCh() == finals))
	    {	
            cont = x;
            while((cont < rows) && (st2 != -1) && (st != 2))
            {
                cont++;
                if(cont < rows){
                    if(gameCells[cont][y].getCh() == finals)
                        st = 1;
                    else if(gameCells[cont][y].getCh() == initials)
                        st = 2;
                    else
                        st2 = -1;	
                }
            }
            if (st == 2)
            {
                temp = cont - x - 1;
                numberOfMoves[0] += temp;
            }	
            if(st == 2 && change == 1)
            {
                for (int i = x; i < cont; ++i)
                {
                    str = gameCells[i][y].getCorX();
                    ix = gameCells[i][y].getCorY();
                    gameCells[i][y].setPosition(str,initials,ix);
                }
                status = 0;
            }
            st=0;st2=0;
        }

        /*
            Simple Left
        */

        if((x-1 >= 0) && (gameCells[x-1][y].getCh() == finals))
        {
                cont = x;
                while((cont >= 0) && (st2 != -1) && (st != 2))
                {
                    cont--;
                    if(cont >= 0){
                        if(gameCells[cont][y].getCh() == finals)
                            st = 1;
                        else if(gameCells[cont][y].getCh() == initials)
                            st = 2;
                        else 
                            st2 = -1;
                    }			
                }	
                if (st == 2)
                {
                    temp = x - cont - 1;
                    numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    for (int i = cont; i <= x; ++i)
                    {
                        str = gameCells[i][y].getCorX();
                        ix = gameCells[i][y].getCorY();
                        gameCells[i][y].setPosition(str,initials,ix);
                    }
                    status = 0;
                }		
                st=0;st2=0;
        }

        /*
            Simple Bottom
        */

        if((y+1 < cols) && (gameCells[x][y+1].getCh() == finals))
        {
                cont = y;
                while((cont < cols) && (st2 != -1) && (st != 2))
                {
                    cont++;
                    if(cont < cols){
                        if(gameCells[x][cont].getCh() == finals)
                            st = 1;
                        else if(gameCells[x][cont].getCh() == initials)
                            st = 2;
                        else 
                            st2 = -1;	
                    }	
                }	
                if (st == 2)
                {
                        temp = cont - y - 1;
                        numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    for (int i = y; i < cont; ++i)
                    {
                        str = gameCells[x][i].getCorX();
                        ix = gameCells[x][i].getCorY();
                        gameCells[x][i].setPosition(str,initials,ix);
                    }
                    status = 0;
                }
                st=0;st2=0;
        }

        /*
            Simple Top
        */

        if((y-1 >= 0) && (gameCells[x][y-1].getCh() == finals))
        {
                cont = y;
                while((cont >= 0) && (st2 != -1) && (st != 2))
                {
                    cont--;
                    if(cont >= 0){
                        if(gameCells[x][cont].getCh() == finals)
                            st = 1;
                        else if(gameCells[x][cont].getCh() == initials)
                            st = 2;
                        else 
                            st2 = -1;	
                    }		
                }	
                if (st == 2)
                {
                        temp = y - cont - 1;
                        numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    for (int i = cont; i <= y; ++i)
                    {
                        str = gameCells[x][i].getCorX();
                        ix = gameCells[x][i].getCorY();
                        gameCells[x][i].setPosition(str,initials,ix);
                    }
                    status = 0;
                }
                st=0;st2=0;	
        }

        /*
            Diagonal Top Right
        */

        if((x-1 >= 0) && (y+1 < cols) && (gameCells[x-1][y+1].getCh() == finals))
        {
                corY = y;
                corX = x;
                while((corX >= 0) && (corY < cols) && (st2 != -1) && (st != 2))
                {
                    corX--;
                    corY++;
                    if((corX >= 0) && (corY < cols)){
                        if(gameCells[corX][corY].getCh() == finals)
                            st = 1;
                        else if(gameCells[corX][corY].getCh() == initials)
                            st = 2;
                        else 
                            st2 = -1;
                    }			
                }	
                if (st == 2)
                {
                        temp = x - corX - 1;
                        numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    while((corX <= x) && (y < corY))
                    {
                        corX++;
                        corY--;
                        if((corX <= x) && (y <= corY)){
                            str = gameCells[corX][corY].getCorX();
                            ix = gameCells[corX][corY].getCorY();
                            gameCells[corX][corY].setPosition(str,initials,ix);
                        }
                    }
                    status = 0;
                }
                st=0;st2=0;		
        }

        /*
            Diagonal Top Left
        */

        if((x-1 >= 0) && (y-1 >= 0) && (gameCells[x-1][y-1].getCh() == finals))
        {
                corY = y;
                corX = x;
                while((corX >= 0) && (corY >= 0) && (st2 != -1) && (st != 2))
                {
                    corX--;
                    corY--;
                    if((corX >= 0) && (corY >= 0)){
                        if(gameCells[corX][corY].getCh() == finals)
                            st = 1;
                        else if(gameCells[corX][corY].getCh() == initials)
                            st = 2;
                        else 
                            st2 = -1;	
                    }		
                }	
                if (st == 2)
                {
                        temp = x - corX - 1;
                        numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    while((corX <= x) && (corY <= y))
                    {
                        corX++;
                        corY++;
                        if((corX <= x) && (corY <= y)){
                            str = gameCells[corX][corY].getCorX();
                            ix = gameCells[corX][corY].getCorY();
                            gameCells[corX][corY].setPosition(str,initials,ix);
                        }
                    }
                    status = 0;
                }
                st=0;st2=0;	
        }

        /*
            Diagonal Bottom Right
        */

        if((x+1 < rows) && (y+1 < cols) && (gameCells[x+1][y+1].getCh() == finals))
        {
                corY = y;
                corX = x;
                while((corX < rows) && (corY < cols) && (st2 != -1) && (st != 2))
                {
                    corX++;
                    corY++;
                    if((corX < rows) && (corY < cols)){
                        if(gameCells[corX][corY].getCh() == finals)
                                st = 1;
                        else if(gameCells[corX][corY].getCh() == initials)
                                st = 2;
                        else 
                                st2 = -1;		
                    }	
                }	
                if (st == 2)
                {
                    temp = corX - x - 1;
                    numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    while((corX >= x) && (corY >= y))
                    {
                        corX--;
                        corY--;
                        if((corX >= x) && (corY >= y)){
                            str = gameCells[corX][corY].getCorX();
                            ix = gameCells[corX][corY].getCorY();
                            gameCells[corX][corY].setPosition(str,initials,ix);
                        }
                    }
                    status = 0;
                }
                st=0;st2=0;
        }

        /*
            Diagonal Bottom Left
        */

        if((x+1 < rows) && (y-1 >= 0) && (gameCells[x+1][y-1].getCh() == finals))
        {
                corY = y;
                corX = x;
                while((corX < rows) && (corY >= 0) && (st2 != -1) && (st != 2))
                {
                        corX++;
                        corY--;
                        if((corX < rows) && (corY >= 0)){
                            if(gameCells[corX][corY].getCh() == finals)
                                st = 1;
                            else if(gameCells[corX][corY].getCh() == initials)
                                st = 2;
                            else 
                                st2 = -1;	
                    }		
                }	
                if (st == 2)
                {
                    temp = corX - x - 1;
                    numberOfMoves[0] += temp;             
                }	
                if(st == 2 && change == 1)
                {
                    while((corX >= x) && (corY <= y))
                    {
                        corX--;
                        corY++;
                        if((corX >= x) && (corY <= y)){
                            str = gameCells[corX][corY].getCorX();
                            ix = gameCells[corX][corY].getCorY();
                            gameCells[corX][corY].setPosition(str,initials,ix);
                        }
                    }
                    status = 0;
                }
                st=0;st2=0;		
        }


        if(status == 0)
                return 0;
        else
                return -1;			
    }
}
