package yxy.flyinggame.beans;

import java.util.Random;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;

//敌人类型――敌机1号
public class Enemy1 extends FlyingObjectAbstract implements EnemyInterface {
	private int Step = 2; // y方向移动步数

	// 初始化
	public Enemy1() {
		Image = GamePanel.enemy1Img;
		Height = Image.getHeight();
		Width = Image.getWidth();
		Random rand = new Random();
		x = rand.nextInt(GameFrame.WIDTH - this.Width); // 产生的x坐标范围
		y = -Height;
		life = 1;
	}

	// 击败得分
	@Override
	public int getScore() {
		return 3;
	}

	// 敌机1号的走位
	@Override
	public void move() {
		y += Step;

	}

	// 超出边界的判断
	@Override
	public boolean outOfBounds() {
		return this.y > GameFrame.HEIGHT;
	}

}
