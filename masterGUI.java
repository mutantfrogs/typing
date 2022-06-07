package com.company;

import com.sun.tools.jconsole.JConsoleContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class masterGUI {

    // objects used in all GUIs
    public JFrame frame = new JFrame();
    private CardLayout cl = new CardLayout(0, 0);
    public App app = new App();

    // objects used in MenuGUI
    public JPanel mainMasterPanel = new JPanel();
    public JPanel menuMasterPanel = new JPanel();
    public JPanel menuNorthPanel = new JPanel();
    public JPanel menuCenterPanel = new JPanel();
    public JPanel menuSouthPanel = new JPanel();
    public JButton menuStartButton = new JButton("START");
    public JButton menuQuitButton = new JButton("QUIT");
    public JButton menuEasyButton = new JButton("EASY");
    public JButton menuMediumButton = new JButton("MEDIUM");
    public JButton menuHardButton = new JButton("HARD");
    public JLabel menuTitleLabel = new JLabel("10Type");

    // objects used in GameGUI
    public JPanel gameMasterPanel = new JPanel();
    public JTextField gameTextField = new JTextField("", 8);
    public JPanel gameNorthPanel = new JPanel();
    public JPanel gameCenterPanel = new JPanel();
    public JPanel gameSouthPanel = new JPanel();
    public JLabel gameUserWordsLeft = new JLabel("");
    public JLabel gameEnemyWordsLeft = new JLabel("");
    public JTextArea gameWordDisplay = new JTextArea();
    public JLabel gameTitleLabel = new JLabel();

    // objects used in ResultsGUI
    public JPanel resultsMasterPanel = new JPanel();
    public JPanel resultsNorthPanel = new JPanel();
    public JPanel resultsSouthPanel = new JPanel();
    public JLabel resultsPlacement = new JLabel("");
    public JLabel resultsWPM = new JLabel("");
    public JLabel resultsAccuracy = new JLabel("");
    public JButton resultsMenu = new JButton("MENU");
    public JButton resultsQuit = new JButton("QUIT");

    /**
     * Variable used to determine difficulty.
     */
    public int difficulty = 0;
    /**
     * Flag used to determine whether user has pressed start.
     */
    public boolean gameStart = false;
    public int allWordsLeft = 20;

    /**
     * Sets settings for the frame and builds the menu, game, and results screen.
     */
    public void buildGUI() {
        frame.setSize(new Dimension(500, 300));
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        buildMenu();
        buildGame();
        buildResults();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Builds menu screen by running methods that build the panels.
     */
    public void buildMenu() {
        frame.add(menuMasterPanel);
        buildMainPanel();
        buildMenuNorthPanel();
        buildMenuCenterPanel();
        buildMenuSouthPanel();
        BoxLayout boxlayout = new BoxLayout(menuMasterPanel, BoxLayout.Y_AXIS);
        menuMasterPanel.setLayout(boxlayout);
        frame.pack();
    }

    /**
     * Builds north panel for menu which houses the title.
     */
    public void buildMenuNorthPanel() {
        menuMasterPanel.add(menuNorthPanel);
        menuNorthPanel.add(menuTitleLabel);
        menuTitleLabel.setFont(new Font("Courier", Font.BOLD, 80));
    }

    /**
     * Builds center panel for menu which houses the difficulty buttons.
     */
    public void buildMenuCenterPanel() {
        menuMasterPanel.add(menuCenterPanel);
        menuCenterPanel.add(menuEasyButton);
        menuCenterPanel.add(menuMediumButton);
        menuCenterPanel.add(menuHardButton);
        menuEasyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
                menuStartButton.setEnabled(true);
                menuEasyButton.setEnabled(false);
                menuMediumButton.setEnabled(true);
                menuHardButton.setEnabled(true);
            }
        });

        menuMediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 2;
                menuStartButton.setEnabled(true);
                menuEasyButton.setEnabled(true);
                menuMediumButton.setEnabled(false);
                menuHardButton.setEnabled(true);
            }
        });

        menuHardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 3;
                menuStartButton.setEnabled(true);
                menuEasyButton.setEnabled(true);
                menuMediumButton.setEnabled(true);
                menuHardButton.setEnabled(false);
            }
        });
    }
    /**
     * Builds south panel for menu which houses the start and quit buttons.
     */
    public void buildMenuSouthPanel() {
        menuMasterPanel.add(menuSouthPanel);
        // button to start the game
        menuSouthPanel.add(menuStartButton);
        menuStartButton.setEnabled(false);
        menuStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(2);
                app.set_difficulty(difficulty);
                gameTextField.setText("");
                //app.reset_game();
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        app.playGame();
                    }
                });
                t1.start();
                app.run();
            }
        });

        // button to quit the application
        menuSouthPanel.add(menuQuitButton);
        menuQuitButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Builds game screen by running methods that build the panels and starts the game.
     */
    public void buildGame() {
        buildGameNorthPanel();
        buildGameCenterPanel();
        buildGameSouthPanel();
        try {
            app.game(gameTitleLabel, gameUserWordsLeft, allWordsLeft, gameTextField);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds north panel for game screen which houses the amount of words the user and bot have.
     */
    public void buildGameNorthPanel() {
        gameMasterPanel.add(gameNorthPanel, BorderLayout.NORTH);
        gameNorthPanel.add(gameUserWordsLeft);
        gameNorthPanel.add(gameEnemyWordsLeft);
        app.set_enemy(gameEnemyWordsLeft);
        gameUserWordsLeft.setFont(new Font("Courier", Font.BOLD, 40));
        gameEnemyWordsLeft.setFont(new Font("Courier", Font.BOLD, 40));
    }

    /**
     * Builds center panel for game screen which houses the current word the user needs to type.
     */
    public void buildGameCenterPanel() {
        gameMasterPanel.add(gameCenterPanel, BorderLayout.CENTER);
        gameCenterPanel.add(gameTitleLabel);
        gameTitleLabel.setFont(new Font("Courier", Font.BOLD, 40));
    }

    /**
     * Builds south panel for game screen which houses the user entry space.
     */
    public void buildGameSouthPanel() {
        gameMasterPanel.add(gameSouthPanel, BorderLayout.SOUTH);
        gameSouthPanel.add(gameTextField);
        gameTextField.setFont(new Font("Courier", Font.BOLD, 60));
        gameTextField.setHorizontalAlignment(JTextField.CENTER);
        gameTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputString = gameTextField.getText();
                if(inputString.length() == 0){
                    if(app.resultsFlag){
                        changeScreen(3);
                        app.get_WPM(resultsWPM, resultsAccuracy);
                    }
                }
                else if(!app.resultsFlag){
                    app.get_characters(app.randomWord, inputString, gameTitleLabel);
                    app.change_flag();
                    gameTextField.setText("");
                }
                else
                {
                    changeScreen(3);
                    app.get_WPM(resultsWPM, resultsAccuracy);
                }
            }
        });
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Builds results screen by running methods that build the panels.
     */
    public void buildResults()
    {
        buildResultsNorthPanel();
        buildResultsSouthPanel();
    }

    /**
     * Builds north panel for results screen which houses the WPM and Accuracy.
     */
    public void buildResultsNorthPanel()
    {
        resultsMasterPanel.add(resultsNorthPanel);
        resultsNorthPanel.add(resultsWPM);
        resultsNorthPanel.add(resultsAccuracy);
        app.get_WPM(resultsWPM, resultsAccuracy);

        resultsWPM.setFont(new Font("Courier", Font.BOLD, 30));
        resultsAccuracy.setFont(new Font("Courier", Font.BOLD, 30));
    }

    /**
     * Builds south panel for results screen that houses the try again and quit buttons.
     */
    public void buildResultsSouthPanel()
    {
        resultsMasterPanel.add(resultsSouthPanel, BorderLayout.SOUTH);
        resultsSouthPanel.add(resultsMenu);
        resultsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeScreen(1);
            }
        });
        resultsSouthPanel.add(resultsQuit);
        resultsQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Changes what screen the program is on.
     * @param screen holds number that is linked to what screen the program is on.
     */
    public void changeScreen(int screen) {
        switch (screen) {
            case 1 -> cl.show(mainMasterPanel, "menu");
            case 2 -> cl.show(mainMasterPanel, "game");
            case 3 -> cl.show(mainMasterPanel, "results");

        }
    }

    /**
     * Builds panel that holds menu, game, and results screen.
     */
    public void buildMainPanel() {
        mainMasterPanel.setLayout(cl);
        mainMasterPanel.add(menuMasterPanel, "menu");
        mainMasterPanel.add(gameMasterPanel, "game");
        mainMasterPanel.add(resultsMasterPanel, "results");
        frame.add(mainMasterPanel);
        changeScreen(1);
    }
}
