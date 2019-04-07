package yxy.flyinggame.beans;

import yxy.flyinggame.ui.GamePanel;

//敌机子弹
public class EnemyBullet extends FlyingObjectAbstract {
	private int Step = 6; //移动步数

	// 初始化
	public EnemyBullet(int x, int y) {
		Image = GamePanel.bullet1Img;
		Height = Image.getHeight();
		Width = Image.getWidth();
		this.x = x;
		this.y = y;
	}

	@Override
	public void move() {
		y += Step;
	}

	@Override
	public boolean outOfBounds() {
		// return this.y+this.Height > GamePanel.HEIGHT;
		return this.y < 0;
	}
}
