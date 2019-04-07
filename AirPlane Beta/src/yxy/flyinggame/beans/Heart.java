package yxy.flyinggame.beans;

import java.util.Random;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;

//奖励类型――红心
public class Heart extends FlyingObjectAbstract {
	private int xStep = 1; // x坐标走步步数
	private int yStep = 2; // y坐标走步步数

	public Heart() {
		Image = GamePanel.heartImg;
		Height = Image.getHeight();
		Width = Image.getWidth();
		Random rand = new Random();
		x = rand.nextInt(GameFrame.WIDTH - this.Width);
		y = 0;
		life = 1;
	}

	//红心的走位
	@Override
	public void move() {
		if (x >= GameFrame.WIDTH - this.Width) {
			xStep = -1;
		}
		if (x <= 0) {
			xStep = 1;
		}
		x += xStep;
		y += yStep;
	}

	//超出边界的判断
	@Override
	public boolean outOfBounds() {
		return this.y > GameFrame.HEIGHT;
	}
}
