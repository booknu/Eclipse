package codersHigh16;
import java.util.Scanner;
 
public class D_블록_게임 {
 
    static char[][] board;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
 
        Scanner cs = new Scanner(System.in);
         
        int N = cs.nextInt(); // X
        int M = cs.nextInt(); // Y
        double K = cs.nextDouble(); // 출발 위치
        cs.nextLine();
         
        board = new char[2 * M + 1][2 * N + 1];
        boolean left = true;
        boolean up = true;
        for(int i = 0; i < board.length; i++)
        {
            String line = cs.nextLine();
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = line.charAt(j);
        }
             
        int x = (int)(K * 2), y = board.length - 1;
        int xSpeed = -1;
        int ySpeed = -1;
        int score = 0;
        while(!(ySpeed == 1 && y == board.length - 1))
        {
            if(board[y][x] == 'B')
            {
                score++;
                removeBlock(y, x);
            }           
            else if(board[y][x] == '.' || board[y][x] == '|')
            {
                // 왼쪽에 블럭 있음
                if(x != 0 && board[y][x - 1] == 'B')
                {
                    score++;
                    removeBlock(y, x - 1);
                }
                // 오른쪽에 블럭 있음
                if(x != board[0].length - 1 && board[y][x + 1] == 'B')
                {
                    score++;
                    removeBlock(y, x + 1);
                }
                 
            }
            if(x == 0 || x == board[0].length - 1)  // 벽
                xSpeed = -xSpeed;
            if(y == 0)
                ySpeed = -ySpeed;
            x += xSpeed;
            y += ySpeed;
        }
        System.out.println(score);
    }
     
    public static void removeBlock(int y, int x)
    {
        try{
            if(board[y][x] != 'B')
                return;
            board[y][x] = 'O';
            // 위 블럭이랑 연결됨
            if(board[y - 1][x] == '.')
            {
                board[y - 1][x] = '-';
                removeBlock(y - 2, x);
            }
            // 아래 블럭
            if(board[y + 1][x] == '.')
            {
                board[y + 1][x] = '-';
                removeBlock(y + 2, x);
            }
            // 왼쪽 블럭
            if(board[y][x - 1] == '.')
            {
                board[y][x - 1] = '|';
                removeBlock(y, x - 2);
            }
            // 오른쪽 블럭
            if(board[y][x + 1] == '.')
            {
                board[y][x + 1] = '|';
                removeBlock(y, x + 2);
            }
        } catch(Exception e)
        {
             
        }
         
    }
     
    public static void printAll()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[0].length; j++)
                System.out.print(board[i][j]);
            System.out.println();
        }
             
    }
}