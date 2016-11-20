package utilities;

	import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import AllCPs.CP;

	public class HealthPanel extends JPanel{
		private static final long serialVersionUID = -365404306118347804L;
		private CP cp;
		
		private final String title = "Health Left";
		final int border = 0;
		
		public HealthPanel(CP cp)
		{
			this.cp = cp;
			repaint();
		}
		
		public void refresh(CP cp){
			this.cp = cp;
			repaint();
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			int w = this.getWidth();
			int h = this.getHeight();

			double total = (int)cp.getMaxHealth();
			double curr = (int)cp.getHealth();

			int frameX = border;
			int frameY = border;// + g.getFontMetrics(font).getHeight();
			int frameW = w-border-border;
			int frameH = h-border-border;//-g.getFontMetrics(font).getHeight();
			g.drawRect(frameX, frameY, frameW+1, frameH+1);
			
			int healthyWidth = (int) ((curr/total)*frameW);
			int unhealthyWidth = frameW-healthyWidth;
			
			g.setColor(client.Constants.HEALTHY_COLOR);
			g.fillRect(frameX, frameY, healthyWidth, frameH);
			g.setColor(client.Constants.UNHEALTHY_COLOR);
			g.fillRect(frameX + healthyWidth, frameY, unhealthyWidth, frameH);
			
			Font font = client.Constants.GAMEFONT;
			g.setFont(new Font("Courier", Font.BOLD, 20));
			String healthLeft = (int)curr+"/"+(int)total;
			
			g.setColor(Color.BLACK);
			//g.drawString(perProgress, (int)(frameX * 1.5), (int)(frameY*1.5));
			if(curr>0){
				g.drawString(healthLeft, (int)(frameX+frameW / 2-30), (int)(frameY+(frameH/2)+5));	
			}		
		}
		
	}


