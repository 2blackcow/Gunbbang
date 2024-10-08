package gunbbang;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

// player 객체가 맵을 인식하도록 하는 클래스입니다.
// backgroundMapService 이미지를 바탕으로 맵의 색깔을 확인합니다.
public class BackgroundPlayerService implements Runnable {

	private BufferedImage image;
	private Player player;

	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public void run() {
		// while문을 통해 충돌을 확인합니다.
		while (true) {
			// 색상
			Color leftColor = new Color(image.getRGB(player.getX(), player.getY() + 45));
			Color rightColor = new Color(image.getRGB(player.getX() + 90, player.getY() + 45));
			int bottomColor = image.getRGB(player.getX() + 35, player.getY() + 90)
					+ image.getRGB(player.getX() + 90 - 15, player.getY() + 90);

			// 바닥 충돌 확인
			if (bottomColor != -2) { // bottomColor가 하얀색이 아니라면
				player.setDown(false);
			} else { // bottomColor == -2, 하얀색이라면 내려감
				if (!player.isUp() && !player.isDown()) {
					player.down();
				}
			}

			// 외벽 충돌 확인
			if (leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				player.setLeftWallCrash(true);
				player.setLeft(false);
			} else if (rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				player.setRightWallCrash(true);
				player.setRight(false);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
