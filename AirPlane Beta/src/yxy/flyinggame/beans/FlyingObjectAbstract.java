package yxy.flyinggame.beans;

import java.awt.image.BufferedImage;

//抽象类――飞行物类
public abstract class FlyingObjectAbstract {
	public BufferedImage Image; // 飞行物对应图片
	public int Width; // 飞行物宽
	public int Height; // 飞行物高
	public int x; // 飞行物x坐标
	public int y; // 飞行物y坐标
	public int life; // 飞行物生命

	// 飞行物走位
	public abstract void move();

	// 超出边界的判断
	public abstract boolean outOfBounds();

	// 敌人被子弹撞
	public boolean shootBy(Bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.Width;
		int y1 = this.y;
		int y2 = this.y + this.Height;
		int x = bullet.x;
		int y = bullet.y;
		return x > x1 && x < x2 && y > y1 && y < y2; //判断敌人是否被子弹撞击
	}
}
