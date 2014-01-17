/**
 * @author Naomi Pentrel
 * @date Dec 9, 2012
 *
 * Class representing a Tic-Tac-Toe game including the GUI
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Naomi Pentrel
 *
 * Class representing a Tic-Tac-Toe game including the GUI
 *
 */
public class TicTacToe {
        private final static String[] symbols = {"X", "O"};
        private int current = 0;
        JButton[] buttons = new JButton[9];
        JButton lastButton;
        JButton display = new JButton("");
        JButton retry = new JButton("New Game?");
        JButton undo = new JButton("Undo last move");
        JFrame frame = new JFrame("Tic-Tac-Toe");
        JFormattedTextField turn = new JFormattedTextField("");
        
        /**
         * 
         * @author Naomi Pentrel
         *
         * Replaces the labels with the respective symbol.
         */

        private class ButtonClicked implements ActionListener{
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e){
                        JButton btn = (JButton) e.getSource();
                        String label = btn.getLabel();
                        if(label.equals(" ")){
                                btn.setLabel(symbols[current]);
                                if(current==0) {
                                        current = 1;                 //changes who'se turn it is
                                        turn.setText("It's O's turn");
                                }
                                else {
                                        current = 0;
                                        turn.setText("It's X's turn");
                                }                        
                                lastButton = btn;                //saves the button 
                        }
                        if(!undo.isEnabled()) undo.setEnabled(true); //if undo was pressed enable the button again
                        checkWinner();                
                }
        }
        
        /**
         * @author Naomi Pentrel
         * Resets the game field.
         */
        public class NewGameClicked implements ActionListener{
                public void actionPerformed(ActionEvent e){
                        resetGame();
                }
        }
        /**
         * @author Naomi Pentrel
         *        Undoes the last action.
         */
        public class UndoClicked implements ActionListener{
                @SuppressWarnings("deprecation")
                public void actionPerformed(ActionEvent e){
                                lastButton.setLabel(" ");
                                display.setLabel("");
                                if(turn.getText().equalsIgnoreCase("It's X's turn")) {
                                        turn.setText("It's O's turn");
                                        current = 1;
                                }
                                else {
                                        turn.setText("It's X's turn");
                                        current = 0;
                                }
                                undo.setEnabled(false); //button cannot be clicked twice!
                }
        }
        /**
         * Resets all buttons.
         */
        @SuppressWarnings("deprecation")
        public void resetGame() {
                for(int i=0; i < buttons.length; ++i) {
                        JButton btn = (JButton) buttons[i];
                        btn.setLabel(" ");
                        display.setLabel("");
                }
        }
        /**
         * Checks for a winner, by creating an array out of all 
         * the entries and checking for horizontal, vertical, and
         * diagonal matches. If we have a winner that is displayed.
         */
        @SuppressWarnings("deprecation")
        public void checkWinner() {
                int[] matrix = new int [9];
                for (int i = 0; i < matrix.length; i++){ //creates the array
                        if(buttons[i].getLabel()=="X"){
                                matrix[i] = 0;
                        }
                        else{
                                if(buttons[i].getLabel()=="O"){
                                        matrix[i] = 1;
                                }        
                                else{
                                        matrix[i] = -1;
                                }
                        }
                }
                for(int i=0;i<(matrix.length-2);i+=3) {   //checks horizontally
                        if(matrix[i]==matrix[i+1] && matrix[i+1]==matrix[i+2]){
                                if(matrix[i]==0){
                                        display.setLabel("X won!");
                                }
                                if(matrix[i]==1){
                                        display.setLabel("O won!");                                
                                }
                        }
                }
                for(int i=0;i<(matrix.length-6);i++) {                //checks vertically
                        if(matrix[i]==matrix[i+3] && matrix[i+3]==matrix[i+6]){
                                if(matrix[i]==0){
                                        display.setLabel("X won!");
                                }
                                if(matrix[i]==1){
                                        display.setLabel("O won!");                                
                                }
                        }
                }
                if(matrix[0]==matrix[4] && matrix[4]==matrix[8]){ //checks diagonal 1
                        if(matrix[0]==0){
                                display.setLabel("X won!");
                        }
                        if(matrix[0]==1){
                                display.setLabel("O won!");                                
                        }
                }
                if(matrix[2]==matrix[4] && matrix[4]==matrix[6]){ //checks diagonal 2
                        if(matrix[2]==0){
                                display.setLabel("X won!");
                        }
                        if(matrix[2]==1){
                                display.setLabel("O won!");                                
                        }
                }
                if(display.getLabel().length()>2) {                                //If there is a winner that is diplayed
                        String winner = display.getLabel().substring(0, 1);
                        int some = JOptionPane.showConfirmDialog(null, winner + " won! \nDo you want to play again?","Congratulations " + winner, current, current);
                        if (some==0){
                                resetGame();
                        }
                }                
        }
        /**
         * Starts the game by creating the window of the game
         * and adding a several layered GridLayouts that contain
         * all the buttons.
         */
                
        public void startGame() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JPanel game_panel = new JPanel();
                JPanel options_panel = new JPanel();
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new GridLayout(1,2));
                game_panel.setLayout(new GridLayout(3,3));
                options_panel.setLayout(new GridLayout(3,1));
                turn.setHorizontalAlignment(JFormattedTextField.CENTER);
                
                for(int i=0; i < buttons.length; ++i) {
                        buttons[i] = new JButton(" ");
                        buttons[i].addActionListener(new ButtonClicked());
                        game_panel.add(buttons[i]);
                }

                retry.addActionListener(new NewGameClicked());
                undo.addActionListener(new UndoClicked());
                turn.setText("It's X's turn");
                
                options_panel.add(retry);
                options_panel.add(undo);
                options_panel.add(turn);
                mainPanel.add(options_panel);
                mainPanel.add(game_panel);
                
                frame.add(mainPanel);
                frame.pack();
                frame.setVisible(true);
                                
        }
        
        /**
         * @param args
         */
        public static void main(String[] args) {
                // create a game instance
                TicTacToe game = new TicTacToe();
                
                // generate the game
                game.startGame();
        }
}