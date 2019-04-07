package yxy.flyinggame.beans;

import java.util.Random;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;

//敌人类型――敌机3号
public class Enemy3 extends FlyingObjectAbstract implements EnemyInterface {
	private int Step = 2; // y方向移动步数

	public Enemy3() {
		Image = GamePanel.enemy3Img;
		Height = Image.getHeight();
		Width = Image.getWidth();
		Random rand = new Random();
		x = rand.nextInt(GameFrame.WIDTH - this.Width);// 产生的x坐标范围
		y = -Height;
		life = 3;
	}

	// 击败得分
	@Override
	public int getScore() {
		return 10;
	}

	// 敌机3号的走位
	@Override
	public void move() {
		y += Step;
	}

	// 超出边界的判断
	@Override
	public boolean outOfBounds() {
		return this.y > GameFrame.HEIGHT;
	}

	// 发射子弹
	public EnemyBullet shoot() {
		EnemyBullet bullet = new EnemyBullet(this.x + this.Width / 2, this.y + this.Height);
		return bullet;
	}
}
