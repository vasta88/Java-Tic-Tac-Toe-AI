package tictactoe;

/**
 *
 * @author Marissa Solomon
 */
public class TicTacNode {
    public CellVal[][] state = new CellVal[3][3];
    public int move;
    public TermState val;
    
    //constructor takes a board state
    TicTacNode(CellVal[][] s){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                this.state[i][j] = s[i][j];
        }
        this.move = -1;
        this.val = TermState.UNKNOWN;
    }
    
    TicTacNode findMax(){
        TicTacNode retNode = new TicTacNode(this.state);
        TicTacNode temp = new TicTacNode(this.state);
        retNode.val = terminalTest(this.state);
               
        if (retNode.val != TermState.UNKNOWN) 
            return retNode;
        
        //not terminal state
        for (int i = 0; i < 9; i++) {
            //copy state array
            for (int m = 0; m < 3; m++) {
                for (int k = 0; k < 3; k++) 
                    temp.state[m][k] = this.state[m][k];
            }
            if (this.state[i/3][i%3] == CellVal.EMPTY) {
                temp.state[i/3][i%3] = CellVal.O; //put move in next state
                temp = temp.findMin();
                temp.move = i;
                if (betterVal(temp.val, retNode.val)) 
                    retNode = temp;
            }
        }
        return retNode;
    }
    
    TicTacNode findMin() {
        TicTacNode retNode = new TicTacNode(this.state);
        TicTacNode temp = new TicTacNode(this.state);
        retNode.val = terminalTest(this.state);
               
        if (retNode.val != TermState.UNKNOWN) 
            return retNode;
        
        //not terminal state
        for (int i = 0; i < 9; i++) {
            //copy state array
            for (int m = 0; m < 3; m++) {
                for (int k = 0; k < 3; k++) 
                    temp.state[m][k] = this.state[m][k];
            }
            if (this.state[i/3][i%3] == CellVal.EMPTY) {
                temp.state[i/3][i%3] = CellVal.X; //put move in next state
                temp = temp.findMax();
                temp.move = i;
                if (worseVal(temp.val, retNode.val)) 
                    retNode = temp;
            }
        }
        return retNode;
    }
    
    boolean betterVal(TermState temp, TermState ret) {
        if (ret == TermState.WIN) //can't be any better
            return false;
        if (ret == TermState.DRAW && temp == TermState.WIN) 
            return true;
        if (ret == TermState.LOSE && temp != TermState.LOSE)
            return true;
        if (ret == TermState.UNKNOWN && temp != TermState.UNKNOWN)
            return true;
        return false;
    }
    
    boolean worseVal(TermState temp, TermState ret) {
        if (ret == TermState.LOSE) //can't get any worse
            return false;
        if (ret == TermState.DRAW && temp == TermState.LOSE)
            return true;
        if (ret == TermState.WIN && temp != TermState.WIN)
            return true;
        if (ret == TermState.UNKNOWN && temp != TermState.UNKNOWN)
            return true;
        return false;
    }
    TermState terminalTest(CellVal[][] tempState){
        int[] row = {0, 0, 0};
        int[] col = {0, 0, 0};
        int[] diag = {0, 0};
        int nFree = 0;
        
        //search rows and columns
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tempState[i][j] == CellVal.X) {
                    row[i]--;
                    col[j]--;
                }
                else if (tempState[i][j] == CellVal.O){
                    row[i]++;
                    col[j]++;
                }
                else
                    nFree++;
            }
        }
        
       //check diagonals
        for (int i = 0; i < 3; i++) {
            if (tempState[i][i] == CellVal.X) {
                diag[0]--;
                if (i == 1)
                    diag[1]--;
            }
            else if (tempState[i][i] == CellVal.O){
                diag[0]++;
                if (i == 1)
                    diag[1]++;
            }
            for (int j = 0; j < 3; j++) {
                if ( (j == 2 && i == 0) || (j == 0 && i == 2)) {
                    if (tempState[i][j] == CellVal.X)
                        diag[1]--;
                    else if (tempState[i][j] == CellVal.O)
                        diag[1]++;
                }
            }
        }
        
        //see if the game is over
        for (int i = 0; i < 3; i++) {
            if(row[i] == 3) return TermState.WIN;
            if (row[i] == -3) return TermState.LOSE;
            if (col[i] == 3) return TermState.WIN;
            if (col[i] == -3) return TermState.LOSE;
            if (i < 2) {
                if (diag[i] == 3) return TermState.WIN;
                if (diag[i] == -3) return TermState.LOSE;
            }
        }
        if (nFree == 0) return TermState.DRAW;
        
        //game not over yet
        return TermState.UNKNOWN;
    }
}
