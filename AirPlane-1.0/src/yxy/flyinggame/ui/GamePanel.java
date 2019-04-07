package yxy.flyinggame.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {

    // 游戏状态
    public static int state;
    public static final int START = 0;
    public static final int EASY = 1;
    public static final int MIDDLE = 2;
    public static final int DIFFICULT = 3;
    public static final int PAUSE = 4;
    public static final int GAME_OVER = 5;

    // 开始界面
    public static JPanel StartJp = new JPanel();
    public JLabel StartBg = new JLabel(); // 开始界面背景
    public JButton startBtn = new JButton("开始");
    public JButton exitBtn = new JButton("退出");
    public JLabel InfoLabel = new JLabel(); // 提示信息框
    public static JLayeredPane StartJlp = new JLayeredPane(); // 难度选择

    // 静态图片资源
    public static BufferedImage startbgImg; // 开始背景
    public static BufferedImage background1Img; // 简单背景
    public static BufferedImage background2Img;// 中等背景
    public static BufferedImage background3Img;// 困难背景
    public static BufferedImage heroImg; // 英雄机
    public static BufferedImage herobeatedImg; // 英雄机被打
    public static BufferedImage herodiedImg; // 英雄机死亡
    public static BufferedImage bulletImg; // 英雄机子弹
    public static BufferedImage bullet1Img; // 敌机子弹
    public static BufferedImage enemy1Img; // 敌机一号
    public static BufferedImage enemy1diedImg; // 敌机一号死亡
    public static BufferedImage enemy2Img;// 敌机二号
    public static BufferedImage enemy2diedImg;// 敌机二号死亡
    public static BufferedImage enemy3Img;// 敌机三号
    public static BufferedImage enemy3beatedImg;// 敌机三号被打
    public static BufferedImage enemy3diedImg;// 敌机三号死亡
    public static BufferedImage heartImg; // 红心
    public static BufferedImage diamondImg; // 钻石
    public static BufferedImage pauseImg; // 暂停
    public static BufferedImage gameoverImg; // 游戏结束

    public static GamePanel gp; // 开始界面
    public static PlayingPanel pp; // 游戏界面
    public static int nowlevel; // 当前游戏状态
    //public static String PlayerName; // 英雄名

    // 初始加载图片资源
    static {
        try {
            startbgImg = ImageIO.read(new File("src/yxy/flyinggame/images/startbg.jpg"));
            heroImg = ImageIO.read(new File("src/yxy/flyinggame/images/hero.png"));
            herobeatedImg = ImageIO.read(new File("src/yxy/flyinggame/images/hero_beated.png"));
            herodiedImg = ImageIO.read(new File("src/yxy/flyinggame/images/hero_died.png"));
            bulletImg = ImageIO.read(new File("src/yxy/flyinggame/images/bullet.png"));
            bullet1Img = ImageIO.read(new File("src/yxy/flyinggame/images/bullet1.png"));
            enemy1Img = ImageIO.read(new File("src/yxy/flyinggame/images/enemy1.png"));
            enemy1diedImg = ImageIO.read(new File("src/yxy/flyinggame/images/enemy1_died.png"));
            enemy2Img = ImageIO.read(new File("src/yxy/flyinggame/images/enemy2.png"));
            enemy2diedImg = ImageIO.read(new File("src/yxy/flyinggame/images/enemy2_died.png"));
            enemy3Img = ImageIO.read(new File("src/yxy/flyinggame/images/enemy3.png"));
            enemy3beatedImg = ImageIO.read(new File("src/yxy/flyinggame/images/enemy3_beated.png"));
            enemy3diedImg = ImageIO.read(new File("src/yxy/flyinggame/images/enemy3_died.png"));
            pauseImg = ImageIO.read(new File("src/yxy/flyinggame/images/pause.png"));
            background1Img = ImageIO.read(new File("src/yxy/flyinggame/images/background1.jpg"));
            background2Img = ImageIO.read(new File("src/yxy/flyinggame/images/background2.jpg"));
            background3Img = ImageIO.read(new File("src/yxy/flyinggame/images/background3.jpg"));
            heartImg = ImageIO.read(new File("src/yxy/flyinggame/images/heart.png"));
            diamondImg = ImageIO.read(new File("src/yxy/flyinggame/images/diamond.png"));
            gameoverImg = ImageIO.read(new File("src/yxy/flyinggame/images/gameover.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取系统当前时间字符串
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(System.currentTimeMillis()));
        return time;
    }

    // 玩家登入游戏
    public void enterGame() {
            //state = level; //根据等级设置游戏状态
            //nowlevel = level; //设置当前游戏等级
            //StartJlp.setVisible(false); //隐藏开始界面

            //进入游戏界面
            pp = new PlayingPanel();
            pp.setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
            GameFrame.gf.add(pp);
            pp.start();
    }

    // 初始化开始面板
    void initStartUI() {
        //开始界面背景
        StartBg = new JLabel(new ImageIcon(startbgImg));
        StartBg.setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);

        //开始界面控件
        StartJp.setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
        StartJp.setLayout(null);
        StartJp.setOpaque(false);
        StartJp.add(startBtn);
        StartJp.add(exitBtn);
        InfoLabel.setBounds(90, 220, 150, 30);
        startBtn.setBounds(100, 280, 100, 30);
        exitBtn.setBounds(100, 340, 100, 30);

        //分层面板
        StartJlp.setBounds(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
        StartJlp.add(StartBg, JLayeredPane.DEFAULT_LAYER);
        StartJlp.add(StartJp, JLayeredPane.MODAL_LAYER);

        //简单按钮
        startBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                enterGame();
            }
        });

        //退出按钮
        exitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //退出程序
            }
        });

    }

    //开始
    public void start() {
        initStartUI();
    }

}
