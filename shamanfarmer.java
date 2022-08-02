package usacostuff;

import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class shamanfarmer {
static int toggle = 0;
static long[] timer = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
static int[] stacks = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
static int[] cantcast = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
static long plague = 0;

static void timeout(Robot robo, int value) {
	robo.delay(value);
}

public static void main(String args[]) {
Robot bot = null;
try {
bot = new Robot();
} catch (Exception failed) {
System.err.println("Failed instantiating Robot: " + failed);
}

int mask = InputEvent.BUTTON1_DOWN_MASK;
bot.keyPress(KeyEvent.VK_ALT);
bot.keyPress(KeyEvent.VK_TAB);
bot.keyRelease(KeyEvent.VK_ALT);
bot.keyRelease(KeyEvent.VK_TAB);
bot.delay(1000);

while (MouseInfo.getPointerInfo().getLocation().y > 100 || MouseInfo.getPointerInfo().getLocation().x < 1500) {
	if(toggle == 1) {
		
		if(System.currentTimeMillis() - plague > 3000 && bot.getPixelColor(5*(477)/4, 5*(742)/4).getRed() > -240 && bot.getPixelColor(5*420/4, 5*805/4).getGreen() > 40){
			int plagueatts = 20;
			while(bot.getPixelColor(5*420/4, 5*805/4).getGreen() > 40 && plagueatts > 0) {
				bot.keyPress(KeyEvent.VK_Q);
				timeout(bot, 30);
				bot.keyRelease(KeyEvent.VK_Q);
				if(plagueatts < 9) {
					timeout(bot, 70);
					bot.keyPress(KeyEvent.VK_TAB);
					timeout(bot, 30);
					bot.keyRelease(KeyEvent.VK_TAB);
					timeout(bot, 70);
				}
				plagueatts--;
			}
			if(plagueatts > 0) {
				plague = System.currentTimeMillis();
			}
		}
		
		int[] healths = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for(int i = 0; i < 10; i++) {
			if(System.currentTimeMillis() - timer[i] > 12000) {
				stacks[i] = 0;
			}
			
			Rectangle bounds = new Rectangle(5*38/4,5*(44+42*i)/4,160*5/4,1);
			BufferedImage image = bot.createScreenCapture(bounds);
			
			if(Math.abs(((image.getRGB(2 * 0, 0) >> 16) & 0x000000FF) - 51) > 3 || Math.abs(((image.getRGB(2 * 0, 0) >> 8) & 0x000000FF) - 209) > 3) {
				healths[i] = 1000;
				continue;
			}
			for(int j = 0; j < 100; j++) {
				healths[i]++;
				if(Math.abs(((image.getRGB(2 * j, 0) >> 16) & 0x000000FF) - 51) > 3 || Math.abs(((image.getRGB(2 * j, 0) >> 8) & 0x000000FF) - 209) > 3) {
					break;
				}
			}
			if(Math.abs(((image.getRGB(2 * 98, 0) >> 16) & 0x000000FF) - 51) < 3 && Math.abs(((image.getRGB(2 * 98, 0) >> 8) & 0x000000FF) - 209) < 3) {
				if(bot.getPixelColor(5*(477)/4, 5*(742)/4).getRed() > 240) {
					healths[i] = 400;
				}
				else {
					healths[i] = 1000;
				}
			}
			healths[i] += 18 * stacks[i];
			if(bot.getPixelColor(5*(10)/4, 5*(54+i*42)/4).getRed() > bot.getPixelColor(5*(10)/4, 5*(54+i*42)/4).getGreen() && bot.getPixelColor(5*(10)/4, 5*(54+i*42)/4).getGreen() > bot.getPixelColor(5*(10)/4, 5*(54+1*42)/4).getBlue()) {
				healths[i] += 55;
			}
			if(cantcast[i] > 0) {
				healths[i] += cantcast[i]*100;
			}
		}
		
		for(int i = 0; i < 10; i++) {
			cantcast[i]--;
		}
		
		for(int i = 0; i < 10; i++) {
			int partyindex = -1;
			int minhealth = 500;
			for(int j = 0; j < 10; j++) {
				if(healths[j] < minhealth && (stacks[j] <= 2 || System.currentTimeMillis() - timer[j] > 10000)) {
					minhealth = healths[j];
					partyindex = j;
				}
			}
			if(i > 0) {
				//System.out.println(partyindex);
			}
			if(partyindex != -1 && plague != 0 && (System.currentTimeMillis() - plague < 3000 || System.currentTimeMillis() - plague > 8000)) {
			//if(partyindex != -1) {
				bot.mouseMove(0, 0);
				bot.mouseMove(60, 60 + 42 * partyindex);
				timeout(bot, 30);
				bot.mousePress(mask);
				timeout(bot, 30);
				bot.mouseRelease(mask);
				bot.mouseMove(0, 0);
				timeout(bot, 30);
				if(stacks[partyindex] > 2 && System.currentTimeMillis() - timer[partyindex] < 10000) {
					/*
					if(bot.getPixelColor(5*420/4, 5*805/4).getGreen() > 40) {
						bot.keyPress(KeyEvent.VK_2);
						timeout(bot, 30);
						bot.keyRelease(KeyEvent.VK_2);
						timeout(bot, 400);
						if (bot.getPixelColor(5*470/4, 5*805/4).getGreen() < 40) {
							break;
						}
						else {
							healths[partyindex] = 1000;
							cantcast[partyindex] = 2;
						}
					}
					*/
				}
				else if(bot.getPixelColor(5*420/4, 5*805/4).getGreen() > 40){
					bot.keyPress(KeyEvent.VK_1);
					timeout(bot, 30);
					bot.keyRelease(KeyEvent.VK_1);
					timeout(bot, 400);
					if (bot.getPixelColor(5*420/4, 5*805/4).getGreen() < 40) {
						timer[partyindex] = System.currentTimeMillis();
						stacks[partyindex]++;
						break;
					}
					else {
						healths[partyindex] = 1000;
						cantcast[partyindex] = 2;
					}
				}
			}
		}
		if(MouseInfo.getPointerInfo().getLocation().x > 300) {
			toggle = 0;
		}
	}
	if(MouseInfo.getPointerInfo().getLocation().x < 20 && toggle == 0) {
		toggle = 1;
	}
	timeout(bot, 30);
}
int mx = MouseInfo.getPointerInfo().getLocation().x;
int my = MouseInfo.getPointerInfo().getLocation().y;
for(int i = 0; i < 20; i++) {
	bot.mouseMove(mx, my);
	bot.delay(50);
}
System.out.println("Stopped");
}
}
