package yxy.flyinggame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import yxy.flyinggame.beans.Background;
import yxy.flyinggame.beans.Bullet;
import yxy.flyinggame.beans.Diamond;
import yxy.flyinggame.beans.Enemy1;
import yxy.flyinggame.beans.Enemy2;
import yxy.flyinggame.beans.Enemy3;
import yxy.flyinggame.beans.EnemyBullet;
import yxy.flyinggame.beans.FlyingObjectAbstract;
import yxy.flyinggame.beans.Heart;
import yxy.flyinggame.beans.Hero;

public class PlayingPanel extends JPanel {

    public Timer timer; // 定时器触发游戏运行各种操作
    public static Background background; // 游戏背景
    public static Hero hero = new Hero(); // 英雄机
    public static int flycount = 0; // 飞行物数量
    public static int bulletcount = 0; // 子弹数量
    public static int enemybulletcount = 0; // 子弹数量
    public static long score = 0; // 得分

    private FlyingObjectAbstract[] flyobjects = {}; // 飞行物数组
    private Enemy3[] enemy3s = {}; // 发射子弹的敌人数组
    private Bullet[] bullets = {}; // 子弹数组
    private EnemyBullet[] enemy3bullets = {}; // 敌人子弹数组
    private int bulletspeed = 30; // 子弹速度
    private int enemybulletspeed = 60; // 子弹速度
    private int time = 10; // 定时器触发时间
    private int createflytime = 100; // 产生飞行物的间隔时间为createflytime-等级*25，等级越高产生飞行物越快
    private int randnum = 30; // 随机产生概率
    private long starttime; // 开始游戏时间
    private long playingtime; // 游戏时间
    private long startpausetime; // 开始暂停时间
    private long nowtime; // 当前时间
    private long pausetime; // 暂停时间

    public PlayingPanel() {
        background = new Background();
    }
    
    public void LeveOfJudge() {
    	if(score<=100) {
    		GamePanel.state=GamePanel.EASY;
    		GamePanel.nowlevel=GamePanel.EASY;
    	}
    	else if(100<score && score<=200) {
    		GamePanel.state=GamePanel.MIDDLE;
    		GamePanel.nowlevel=GamePanel.MIDDLE;
    	}else {
    		GamePanel.state=GamePanel.DIFFICULT;
    		GamePanel.nowlevel=GamePanel.DIFFICULT;
    	}
    	switch (GamePanel.nowlevel) { // 根据不同难度设置
        case GamePanel.EASY:
            bulletspeed = 20;
            enemybulletspeed = 50;
            randnum = 30;
            break;
        case GamePanel.MIDDLE:
            bulletspeed = 25;
            enemybulletspeed = 40;
            randnum = 40;
            break;
        case GamePanel.DIFFICULT:
            bulletspeed = 25;
            enemybulletspeed = 30;
            randnum = 100;
            break;
    	}
    }

    // 产生下一飞行物的类型
    public FlyingObjectAbstract createNextObject() {
        Random rand = new Random();
        int flytype = rand.nextInt(randnum); // 生成0到49的随机数
        if (flytype == 0) { // 为0时产生红心
            return new Heart();
        } else if (flytype == 1) { // 为1时产生钻石
            return new Diamond();
        } else if (flytype % 3 == 0) { // 产生敌人1号
            return new Enemy1();
        } else if (flytype % 3 == 1) {// 产生敌人2号
            return new Enemy2();
        } else if (flytype % 3 == 2 && flytype <= 30) { // 产生敌人3号
            return new Enemy3();
        } else {
            return new Enemy1();
        }
    }

    // 显示除英雄机的所有对象
    public void showOtherObject() {
        flycount++; // 显示飞行物
        if (GamePanel.state != 5 && GamePanel.state != 0 && flycount % (createflytime - GamePanel.state * 25) == 0) {
            FlyingObjectAbstract fo = createNextObject(); // 产生新的飞行物
            flyobjects = Arrays.copyOf(flyobjects, flyobjects.length + 1);
            flyobjects[flyobjects.length - 1] = fo;// 将新产生的飞行物保存到flyobjects数组
            if (flyobjects[flyobjects.length - 1] instanceof Enemy3) {
                enemy3s = Arrays.copyOf(enemy3s, enemy3s.length + 1);
                enemy3s[enemy3s.length - 1] = (Enemy3) (flyobjects[flyobjects.length - 1]);
            }
        }

        enemybulletcount++;// 显敌机子弹
        if (enemybulletcount % enemybulletspeed == 0) {
            for (int i = 0; i < enemy3s.length; i++) {
                EnemyBullet eb = enemy3s[i].shoot();
                enemy3bullets = Arrays.copyOf(enemy3bullets, enemy3bullets.length + 1);
                enemy3bullets[enemy3bullets.length - 1] = eb;
            }
        }

        bulletcount++;// 显示子弹
        if (bulletcount % bulletspeed == 0) { // 每bulletspeed秒发射一次子弹
            Bullet[] bs = hero.shoot();// 获取子弹对象
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
        for (int i = 0; i < flyobjects.length; i++) {
            flyobjects[i].move(); // 敌人移动
        }
        for (int i = 0; i < bullets.length; i++) {
            bullets[i].move(); // 子弹移动
        }
        for (int i = 0; i < enemy3bullets.length; i++) {
            enemy3bullets[i].move(); // 敌人子弹移动
        }

    }

    // 删除越界飞行物
    public void deleteOutBoundsObject() {
        int temp = 0; // 删除越界飞行物
        FlyingObjectAbstract[] flyingLives = new FlyingObjectAbstract[flyobjects.length];
        for (int i = 0; i < flyobjects.length; i++) {
            FlyingObjectAbstract f = flyobjects[i];
            if (!f.outOfBounds()) {
                flyingLives[temp] = f;// 不越界，将其装入flyingLives[]数组中
                temp++;
            }
        }
        flyobjects = Arrays.copyOf(flyingLives, temp);

        temp = 0;// 删除越界英雄机发射子弹
        Bullet[] bulletsLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet bs = bullets[i];
            if (!bs.outOfBounds()) {
                bulletsLives[temp] = bs;// 不越界，将其装入bulletsLives[]数组中
                temp++;
            }
        }
        bullets = Arrays.copyOf(bulletsLives, temp);

        temp = 0;// 删除越界敌机发射子弹
        EnemyBullet[] enemy3bulletsLives = new EnemyBullet[enemy3bullets.length];
        for (int i = 0; i < enemy3bullets.length; i++) {
            EnemyBullet eb = enemy3bullets[i];
            if (!eb.outOfBounds()) {
                enemy3bulletsLives[temp] = eb;// 不越界，将其装入enemy3bulletsLives[]数组中
                temp++;
            }
        }
        enemy3bullets = Arrays.copyOf(enemy3bulletsLives, temp);
    }

    // 删除死亡的对象
    public void deletedied() {
        for (int i = 0; i < flyobjects.length; i++) { // 删除死亡飞行物
            if (flyobjects[i].life == 0) {
                flyobjects[i].life--;
            } else if (flyobjects[i].life <= -1) {
                FlyingObjectAbstract t = flyobjects[i];
                flyobjects[i] = flyobjects[flyobjects.length - 1];
                flyobjects[flyobjects.length - 1] = t;
                flyobjects = Arrays.copyOf(flyobjects, flyobjects.length - 1);
            }

        }
        for (int i = 0; i < enemy3s.length; i++) { // 删除死亡敌机三号
            if (enemy3s[i].life <= -1) {
                Enemy3 t = enemy3s[i];
                enemy3s[i] = enemy3s[enemy3s.length - 1];
                enemy3s[enemy3s.length - 1] = t;
                enemy3s = Arrays.copyOf(enemy3s, enemy3s.length - 1);
            }
        }
    }

    // 一个子弹与敌人撞
    public int bang(Bullet b) {
        int temp = -1;// 被撞敌人的索引
        for (int i = 0; i < flyobjects.length; i++) {// 遍历所有的敌人
            if (flyobjects[i].shootBy(b)) {// 判断是否撞上
                temp = i; // 记录被撞敌人的索引
                break;
            }
        }
        if (temp != -1) {// 撞上了
            FlyingObjectAbstract flyobject = flyobjects[temp];
            if (flyobject instanceof Enemy1) {
                flyobject.life--;
                if (flyobject.life == 0) {
                    score += ((Enemy1) flyobject).getScore();
                    flyobject.Image = GamePanel.enemy1diedImg;
                }
            } else if (flyobject instanceof Enemy2) {
                flyobject.life--;
                if (flyobject.life == 0) {
                    flyobject.Image = GamePanel.enemy2diedImg;
                    score += ((Enemy2) flyobject).getScore();
                }
            } else if (flyobject instanceof Enemy3) {
                flyobject.life--;
                if (flyobject.life == 1) {
                    flyobject.Image = GamePanel.enemy3beatedImg;
                } else if (flyobject.life == 0) {
                    flyobject.Image = GamePanel.enemy3diedImg;
                    score += ((Enemy3) flyobject).getScore();
                }

            } else if (flyobject instanceof Diamond) {
                flyobject.life--;
                hero.addDoubleFire(); // 英雄机增加火力

            } else if (flyobject instanceof Heart) {
                flyobject.life--;
                hero.addLife(); // 英雄机增加生命

            }
            return 1; // 子弹触碰到飞行物

        } else {
            return 0; // 子弹未触碰到飞行物
        }
    }

    // 删除击中敌机的子弹
    public void bangAction() {
        for (int i = 0; i < bullets.length; i++) {
            if (bang(bullets[i]) == 1) {
                bullets[i] = bullets[bullets.length - 1];
                bullets = Arrays.copyOf(bullets, bullets.length - 1);
            }
        }
    }

    // 游戏结束
    public void checkGameOverAction() {
        if (isGameOver()) { // 结束游戏
            GamePanel.state = GamePanel.GAME_OVER;
        }
    }

    // 判断游戏是否结束
    public boolean isGameOver() {
        for (int i = 0; i < enemy3bullets.length; i++) { // 撞上了，
            EnemyBullet eb = enemy3bullets[i];
            if (hero.shootedBybullet(eb)) {
                hero.loseLife();
                hero.index = 50;
                // 相撞之后，交换缩容
                EnemyBullet e = enemy3bullets[i];
                enemy3bullets[i] = enemy3bullets[enemy3bullets.length - 1];
                enemy3bullets[enemy3bullets.length - 1] = e;
                enemy3bullets = Arrays.copyOf(enemy3bullets, enemy3bullets.length - 1);
            }
        }

        for (int i = 0; i < flyobjects.length; i++) { // 撞上了，
            if (hero.hited(flyobjects[i])) {
                if (flyobjects[i] instanceof Heart) { // 英雄被红心撞击
                    hero.addLife(); // 英雄机增加生命
                } else if (flyobjects[i] instanceof Diamond) { // 英雄被钻石撞击
                    hero.addDoubleFire(); // 增加火力
                } else if (flyobjects[i] instanceof Enemy3) {
                    hero.loseLife(); // 生命减1
                    hero.setDoubleFire(0); // 火力值清零
                    hero.index = 50;
                    flyobjects[i].life = -1;
                } else { // 英雄被敌机撞击
                    hero.loseLife(); // 生命减1
                    hero.setDoubleFire(0); // 火力值清零
                    hero.index = 50;
                }

                // 相撞之后，交换缩容
                FlyingObjectAbstract t = flyobjects[i];
                flyobjects[i] = flyobjects[flyobjects.length - 1];
                flyobjects[flyobjects.length - 1] = t;
                flyobjects = Arrays.copyOf(flyobjects, flyobjects.length - 1);
            }
        }
        return hero.getLife() <= 0; // 英雄机的命<=0,游戏结束
    }

    // 开始
    public void start() {
    	GamePanel.state=GamePanel.EASY;
		GamePanel.nowlevel=GamePanel.EASY;
        starttime = System.currentTimeMillis(); // 开始时间
        MouseAdapter Ma = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (GamePanel.state == GamePanel.EASY || GamePanel.state == GamePanel.MIDDLE
                        || GamePanel.state == GamePanel.DIFFICULT) { // 运行状态下执行
                    int x = e.getX(); // 鼠标X坐标
                    int y = e.getY(); // 鼠标Y坐标
                    hero.moveTo(x, y); // 英雄机随着鼠标移动而移动
                }
            }

            // 鼠标的点击事件
            public void mouseClicked(MouseEvent e) {
                switch (GamePanel.state) {
                    case GamePanel.GAME_OVER:
                        hero = new Hero();// 清理现场
                        flyobjects = new FlyingObjectAbstract[0];
                        bullets = new Bullet[0];
                        //score = 0;
                        //playingtime = 0;
                        GamePanel.StartJlp.setVisible(true);
                        GamePanel.state = GamePanel.START;
                        GamePanel.pp.setVisible(false);
                        timer.cancel();
                        break;
                }
            }

            // 鼠标进入事件
            public void mouseEntered(MouseEvent e) {
                if (GamePanel.state == GamePanel.PAUSE) {
                    GamePanel.state = GamePanel.nowlevel;
                }
            }

            // 鼠标出去事件
            public void mouseExited(MouseEvent e) {
                if (GamePanel.state == GamePanel.nowlevel) {
                    startpausetime = System.currentTimeMillis();
                    GamePanel.state = GamePanel.PAUSE;
                }
            }
        };

        this.addMouseListener(Ma);
        this.addMouseMotionListener(Ma);

        // 定时器循环运行
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                nowtime = System.currentTimeMillis(); // 当前时间
                if (GamePanel.state == GamePanel.nowlevel) {
                    playingtime = nowtime - starttime - pausetime; // 游戏时间
                    background.move(); // 背景移动
                    LeveOfJudge();
                    showOtherObject();
                    deleteOutBoundsObject();
                    bangAction();
                    checkGameOverAction();
                    hero.change(); // 英雄机状态图改变
                    deletedied();

                } else if (GamePanel.state == GamePanel.PAUSE) {
                    pausetime = nowtime - startpausetime;
                }
                repaint();

            }
        }, time, time);

    }

    // 绘制背景
    public void paintBackground(Graphics g) {
        g.drawImage(background.Image, background.x, background.y, null);
    }

    // 绘制英雄机
    public void paintHero(Graphics g) {
        g.drawImage(hero.Image, hero.x, hero.y, null);
    }

    // 绘制飞行物
    public void paintFlyingObject(Graphics g) {
        for (int i = 0; i < flyobjects.length; i++) {
            FlyingObjectAbstract f = flyobjects[i];
            g.drawImage(f.Image, f.x, f.y, null);
        }
    }

    // 绘制子弹
    public void paintBullet(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.Image, b.x, b.y, null);
        }
        for (int i = 0; i < enemy3bullets.length; i++) {
            EnemyBullet b = enemy3bullets[i];
            g.drawImage(b.Image, b.x, b.y, null);
        }
    }

    // 绘制游戏信息
    public void paintInfo(Graphics g) {
        g.setColor(new Color(0xFF0000));
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g.drawString("SCORE: " + score, 20, 45);
        g.drawString("LIFE: " + hero.getLife(), 20, 65);
        g.drawString("TIME: " + playingtime / 1000 + "s", 180, 45);
        switch (GamePanel.nowlevel) {
            case GamePanel.EASY:
                g.drawString("LEVEL: easy", 180, 25);
                break;
            case GamePanel.MIDDLE:
                g.drawString("LEVEL: middle", 180, 25);
                break;
            case GamePanel.DIFFICULT:
                g.drawString("LEVEL: difficult", 180, 25);
                break;
        }

    }

    // 绘制状态图
    public void paintState(Graphics g) {
        if (GamePanel.state == GamePanel.PAUSE) {
            g.drawImage(GamePanel.pauseImg, 0, 0, null);
        }
        if (GamePanel.state == GamePanel.GAME_OVER) {
            g.drawImage(GamePanel.gameoverImg, 0, 0, null);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBackground(g);
        paintHero(g);
        paintFlyingObject(g);
        paintBullet(g);
        paintInfo(g);
        paintState(g);
    }
}