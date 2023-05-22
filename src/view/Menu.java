package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

import static view.ChessboardComponent.mainFrame;

public class Menu extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;

    public Menu(int width, int height) {
        this.WIDTH=width;
        this.HEIGTH=height;
        this.setVisible(true);
        this.setSize(this.WIDTH,this.HEIGTH);
        this.setLocationRelativeTo(null);
        this.setTitle("斗兽棋");
        this.setLayout(null);
        startButton();
        loadButton();
        addLabel();
        exitButton();
    }
    private void addLabel() {
        JLabel winLabel = new JLabel("斗兽棋");
        winLabel.setLocation(430, HEIGTH/10+30);
        winLabel.setSize(400, 100);
        winLabel.setFont(new Font("Rockwell", Font.BOLD, 70));
        add(winLabel);
    }
    private void startButton() {
        JButton button = new JButton("Start");
        button.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                mainFrame= new ChessGameFrame(1100, 810);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                mainFrame.setGameController(gameController);
                gameController.setStatusLabel(mainFrame.getStatusLabel());
            });
        });
        button.setLocation(450, HEIGTH / 10+200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void loadButton() {
        JButton button = new JButton("Load");
        button.addActionListener((e) -> {
            this.dispose();

        });
        button.setLocation(450, HEIGTH / 10+350);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void exitButton() {
        JButton button = new JButton("Exit");
        button.addActionListener((e) -> {
            this.dispose();
        });
        button.setLocation(450, HEIGTH / 10+500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

}
