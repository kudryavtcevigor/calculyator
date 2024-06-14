import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {
	private String color;
	private int type;

	public CircleButton(String text, String color, int type) {

		super(text);
		this.color = color;
		this.type = type;
		setFocusPainted(false); // убираем выделение текста при нажатии
		setBorderPainted(false); // убираем рамку вокруг кнопки
		Dimension size = new Dimension(50, 50);
		setPreferredSize(size); // устанавливаем свои размеры
		setContentAreaFilled(false); //разрешаем закрашивать кнопку нашим цветом - это обязательный метод когда рисуем кнопку 
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		g.setPaintMode();
		g.setFont(new Font("TimesRoman", Font.BOLD, 13));
		Color push = null, release = null, foreground = null;
		if (color.equalsIgnoreCase("orange")) {
			push = MyColor.myLightOrange;
			release = MyColor.myOrange;
			foreground = Color.WHITE;
		} else if (color.equalsIgnoreCase("gray")) {
			push = Color.WHITE;
			release = MyColor.myLightGray;
			foreground = Color.black;
		} else if (color.equalsIgnoreCase("darkgray")) {
			push = MyColor.myMiddleGray;
			release = MyColor.myGray;
			foreground = Color.WHITE;
		}

		if (getModel().isArmed()) { // если кнопка нажата и удерживается
			g.setColor(push);
			setForeground(foreground);
		} else { // если отпустили кнопку
			g.setColor(release);
			setForeground(foreground);
		}
		if(type==0) {
			g.fillOval(0, 0, getSize().width, getSize().height); // рисуем круг
		}
		if(type==1) {
			g.fillRoundRect(0, 0, getSize().width, getSize().height, 50, 50);
		}
		super.paintComponent(g); // это обязательный метод, мы передаем в него то, 
								 //что мы сделали и он дорисовывает за нас  
								 //когда мы перерисовываем существующий компонент
	}

}
