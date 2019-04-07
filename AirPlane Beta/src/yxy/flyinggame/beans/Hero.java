package yxy.flyinggame.beans;

import java.awt.image.BufferedImage;

import yxy.flyinggame.ui.GamePanel;

//英雄机
public class Hero extends FlyingObjectAbstract {
	private int doubleFire; // 火力值
	public static BufferedImage[] images;
	public int index;

	// 初始化
	public Hero() {
		Image = GamePanel.heroImg;
		Width = Image.getWidth();
		Height = Image.getHeight();
		x = 150;
		y = 400;
		life = 20;
		doubleFire = 0; // 单倍火力
		images = new BufferedImage[] { GamePanel.heroImg, GamePanel.herobeatedImg };
		index = 0;
	}

	public void move() {

	}

	// 英雄机状态图片改变
	public void change() {
		if (life > 0) {
			if (index != 0) {
				Image = images[index--/10 % 2];
			} else {
				Image = images[0];
			}
		} else {
			Image = GamePanel.herodiedImg;
		}
	}

	// 子弹射击
	public Bullet[] shoot() {
		int xStep = this.Width / 4;
		if (doubleFire > 0) { // 双发
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(this.x + 1 * xStep, this.y - 20);
			bullets[1] = new Bullet(this.x + 3 * xStep, this.y - 20);
			doubleFire -= 2; // 双倍火力的持续时间
			return bullets;
		} else { // 单发
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(this.x + 2 * xStep, this.y - 20);
			return bullets;
		}
	}

	// 移动位置
	public void moveTo(int x, int y) {
		this.x = x - this.Width / 2;
		this.y = y - this.Height / 2;
	}

	public boolean outOfBounds() {
		return false; // 英雄机永不越界
	}

	// 加命
	public void addLife() {
		life++;
	}

	// 获取命
	public int getLife() {
		return life;
	}

	public void addDoubleFire() {
		doubleFire += 20;
	}

	// 活力值清零
	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}

	// 英雄机撞敌人
	public boolean hited(FlyingObjectAbstract other) {
		int x1 = other.x - this.Width / 2;
		int x2 = other.x + other.Width + this.Width / 2;
		//int y1 = other.y - this.Height / 2;
		int y1 = other.y ;
		//	int y2 = other.y + other.Height + this.Height / 2;
		int y2 = other.y + other.Height ;
		int hx = this.x + this.Width / 2;
		//	int hy = this.y + this.Height / 2;
		int hy = this.y ;
//		int x1 = other.x - other.Width / 2;
//		int x2 = other.x + other.Width /2;
//		int y1 = other.y - other.Height / 2;
//		int y2 = other.y + other.Height / 2;
//		int hx = this.x;
//		int hy = this.y - this.Height / 2;

		return hx > x1 && hx < x2 && hy > y1 && hy < y2 && other.life > 0;
	}

	// 英雄机撞子弹
	public boolean shootedBybullet(EnemyBullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.Width;
		int y1 = this.y;
		int y2 = this.y + this.Height;
		int x = bullet.x;
		int y = bullet.y;
		return x > x1 && x < x2 && y > y1 && y < y2;
	}

	// 减命
	public void loseLife() {
		life--;
	}
}
