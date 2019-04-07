package yxy.flyinggame.beans;

import yxy.flyinggame.ui.GamePanel;

//英雄机子弹
public class Bullet extends FlyingObjectAbstract {
	private int Step = 3; // 移动步数

	// 初始化
	public Bullet(int x, int y) {
		Image = GamePanel.bulletImg;
		Height = Image.getHeight();
		Width = Image.getWidth();
		this.x = x;
		this.y = y;
	}

	@Override
	public void move() {
		y -= Step;
	}

	@Override
	public boolean outOfBounds() {
		return this.y < 0;
	}
}
