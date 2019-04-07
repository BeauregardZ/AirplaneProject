package yxy.flyinggame.ui;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public static GameFrame gf = new GameFrame();
    public static GamePanel gp;

    // 游戏窗口
    public static final int WIDTH = 320; // 窗口的宽
    public static final int HEIGHT = 550; // 窗口的高

    public void start() {
        gf.setLayout(null); // 设置为空布局，根据像素确定位置
        gf.setTitle("飞机大战");
        gf.setAlwaysOnTop(true); // 窗口总是在最上
        gf.setSize(GameFrame.WIDTH, GameFrame.HEIGHT);
        gf.setLocationRelativeTo(null); // 窗口居中
        gf.setResizable(false); // 固定窗口大小，不能被用户修改

        gp = new GamePanel();
        gp.start();

        gf.add(gp.StartJlp);
        gf.setVisible(true);
    }

    public static void main(String[] args) {
        gf.start();
    }
}
