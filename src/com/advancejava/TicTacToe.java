package com.advancejava;

import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        try {
            char[][] board = new char[3][3];
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    board[row][col] = ' ';
                }
            }
            Scanner sc = new Scanner(System.in);
            boolean gameOver = false;
            char player = 'X';

            while (!gameOver) {
                printBoard(board);
                System.out.println("Player " + player + "'s Turn🤩: ");
                int row = sc.nextInt();
                int col = sc.nextInt();

                if (board[row][col] == ' ') {
                    board[row][col] = player;
                    gameOver = haveWon(board, player);

                    if (gameOver) {
                        System.out.println("Player " + player + " has won!🥳");
                    } else {
                        player = (player == 'X') ? '0' : 'X';
                    }
                } else {
                    System.out.println("Invalid move.. Try again!");
                }
            }
            printBoard(board);
            System.out.println("Press 1 to play again or 0 to exit: ");
            int n = sc.nextInt();
            playAgain(n);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean haveWon(char[][] board, char player){

        // check for rows
        for(int row=0; row<board.length; row++){
            if(board[row][0] == player && board[row][1] == player && board[row][2] == player){
                return true;
            }
        }

        // check for columns
        for(int col=0; col<board[0].length; col++){
            if(board[0][col] == player && board[1][col] == player && board[2][col] == player){
                return true;
            }
        }

        // for diagonal check
        if(board[0][0] == player && board[1][1] == player && board[2][2] == player){
            return true;
        }

        if(board[0][2] == player && board[1][1] == player && board[2][0] == player){
            return true;
        }
        return false;
    }

    public static void printBoard(char[][] board){
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    System.out.print(board[row][col] + "|");
                }
                System.out.println();
            }
    }

    public static void playAgain(int n) {
        String[] arguments = { };
        while (true) {
            switch (n) {
                case 1:
                    main(arguments);
                    break;
                case 0: return;

                default:
                    System.out.println("Invalid Input!!");
                    break;
            }
        }
    }
}