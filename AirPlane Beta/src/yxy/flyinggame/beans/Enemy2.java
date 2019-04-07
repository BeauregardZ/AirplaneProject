package yxy.flyinggame.beans;

import java.util.Random;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;

//敌人类型――敌机2号
public class Enemy2 extends FlyingObjectAbstract implements EnemyInterface {
	private int Step = 3; // y方向移动步数

	public Enemy2() {
		Image = GamePanel.enemy2Img;
		Height = Image.getHeight();
		Width = Image.getWidth();
		Random rand = new Random();
		x = rand.nextInt(GameFrame.WIDTH - this.Width);// 产生的x坐标范围
		y = -Height;
		life = 2;
	}

	// 击败得分
	@Override
	public int getScore() {
		return 5;
	}

	// 敌机2号的走位
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
