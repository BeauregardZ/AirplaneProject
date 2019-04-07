package yxy.flyinggame.beans;

import java.util.Random;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;


//奖励类型――钻石
public class Diamond extends FlyingObjectAbstract{
	private int xStep = 2; //x坐标走步步数
	private int yStep = 2; //y坐标走步步数

	public Diamond() {
		Image = GamePanel.diamondImg;
		Height = Image.getHeight();
		Width = Image.getWidth();
		Random rand = new Random();
		x = rand.nextInt(GameFrame.WIDTH - this.Width);
		y = -Height;
		life = 2;
	}


	//钻石的走位
	@Override
	public void move() {
		if (x >= GameFrame.WIDTH - this.Width) {
			xStep = -2;
		}
		if (x <= 0) {
			xStep = 2;
		}
		x += xStep;
		y += yStep;

	}

	//超出边界的判断
	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}

}
