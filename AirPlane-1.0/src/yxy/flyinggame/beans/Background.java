package yxy.flyinggame.beans;

import java.awt.image.BufferedImage;

import yxy.flyinggame.ui.GameFrame;
import yxy.flyinggame.ui.GamePanel;

//背景
public class Background {
	public BufferedImage Image; // 背景对应图片
	public int x = 0; // 背景x坐标
	public int y = -GameFrame.HEIGHT; // 背景y坐标
	private int Step = 1; // 背景移动步数

	public Background() {
		switch (GamePanel.state) { // 根据不同难度等级设置背景
			case GamePanel.EASY:
				Image = GamePanel.background1Img;
				break;
			case GamePanel.MIDDLE:
				Image = GamePanel.background2Img;
				break;
			case GamePanel.DIFFICULT:
				Image = GamePanel.background3Img;
				break;
		}
	}

	public void move() { // 背景移动
		y += Step;
		if (y == 0) { // 循环移动
			y = -GameFrame.HEIGHT;
		}

	}

}
