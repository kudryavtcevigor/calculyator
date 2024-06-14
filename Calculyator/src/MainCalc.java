import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainCalc extends JFrame {
	private final String[] buttonText = { "AC", "+/-", "%", "÷", "7", "8", "9", "X", "4", "5", "6", "-", "1", "2", "3",
			"+", "0", ",", "=" }; // 19

	private JMenuItem itemRef;
	private JMenuItem itemExit;
	private JMenu menu;
	private JTextField mainText;

	// Переменные для работы с вычислениями:
	private double number1 = 0; // операнд 1
	private double number2 = 0; // операнд 2
	char operator = 0; // номер оператора. Всего 5: +,-,*,/
	boolean clear = true; // флаг проверки статуса очистки текстового поля, изначально очищено
	public MainCalc() {
		// ---------------------------/Начало оформления/---------------------------//
		super("Калькулятор");
		setSize(300, 530);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		// Добавляем менюбар
		JMenuBar bar = new JMenuBar();
		menu = new JMenu("Menu");
		itemRef = new JMenuItem("Reference");
		itemExit = new JMenuItem("Exit");

		itemRef.addActionListener(listener);
		itemExit.addActionListener(listener);

		menu.add(itemRef);
		menu.add(itemExit);
		bar.add(menu);
		setJMenuBar(bar);
		// Конец менюбара

		// Добавляем основную панель, на которой будут располагаться другие
		// БЕЗ НЕЕ НЕ ПОЛУЧИЛАЛОСЬ
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Делаем расположение BoxLayout - расположение
		getContentPane().add(mainPanel);
		// Конец основной панели

		// Добавляем верхнюю панель, в которой будет отображаться число
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.red);
		topPanel.setPreferredSize(new Dimension(400, 180));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		JPanel inTop = new JPanel(); // пришлось добавить еще одну вложенную панель в верхнюю, чтобы придавить
										// текстовое поле вниз
		inTop.setBackground(Color.red);
		inTop.setPreferredSize(new Dimension(200, 230));
		inTop.setBackground(Color.red);

		mainText = new JTextField("0");
		mainText.setEditable(false); // запрещаем вводить текст с клавиатуры
		mainText.setHorizontalAlignment(SwingConstants.RIGHT); // текст справа налево
		mainText.setPreferredSize(new Dimension(200, 170));
		mainText.setBorder(BorderFactory.createEmptyBorder()); // делаем JTextField без рамки
		mainText.setFont(new Font("Arial", Font.PLAIN, 52));
		mainText.setForeground(Color.WHITE);
		mainText.setBackground(Color.BLACK);

		topPanel.add(inTop);
		topPanel.add(mainText);
		// Конец верхней панели

		JPanel botPanel = new JPanel();
		botPanel.setBackground(Color.black);
		botPanel.setPreferredSize(new Dimension(400, 350));
		botPanel.setLayout(null);
		int xPos = 10;
		int yPos = 5;
		CircleButton but = null;
		String color = null;
		for (int i = 1; i <= buttonText.length; i++) {
			if (i < 4) {
				color = "gray";
			}
			if (i % 4 == 0) {
				color = "orange";
			}
			if (i != 17) {
				but = new CircleButton(buttonText[i - 1], color, 0);
				but.setSize(53, 53);
			} else {
				but = new CircleButton(buttonText[i - 1], color, 1);
				but.setSize(123, 53);
			}

			color = "darkgray";
			but.setLocation(xPos, yPos);
			if (i % 4 == 0) {
				yPos += 58;
				xPos = 10;
			} else {

				xPos += 70;
			}

			if (i == 17) {
				xPos += 70;
			}
			botPanel.add(but);
			but.addActionListener(listener);
		}
		mainPanel.add(topPanel);
		mainPanel.add(botPanel);
		setVisible(true);
	}
	// ---------------------------/Конец оформления/---------------------------//

	ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == itemRef) {
				String ref = "ПРИВЕТ :)";
				JOptionPane.showMessageDialog(menu, ref);
			} else if (e.getSource() == itemExit) {
				System.exit(0);
			} else { // начало обработки нажатия кпонок
				JButton source = (JButton) e.getSource();
				;// получаем объект события и кастуем в JButton
				String bText = source.getText(); // получаем текст кнопки

				switch (bText) {
				case "0":
				case "1":
				case "2":
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
				case "8":
				case "9":
					writeText(bText); // если цифра - то записываем её сразу
					break;
				case ",":
					if (!checkComma()) { // если запятой нет, записываем ее
						writeText("" + bText);
					}
					break;
				case "+":
				case "-":
				case "X":
				case "÷":
					checkOperator();
					setOperator(bText.charAt(0));
					break;
				case "+/-":
					setMinus();
					break;
				case "%":
					doPercent();
					break;
				case "AC":
					setDefault();
					break;
				case "=":
					getResult();
					break;
				}

			} // конец обработки нажатия кнопок
		}
	};

	void writeText(String btnText) { // я свел запись любого символа "0-9", "-", "," в одну функцию
		if (!clear) { // если необходимо очистить
			clear(); // очищаем
		}
		if (mainText.getText().equals("0") && !btnText.equals(",")) { // если в поле 0 и не нажали кнопку запятая
			mainText.setText("");
		} // в противном случае добавляем все введенные символы в строку
		mainText.setText(mainText.getText() + btnText);
		if (operator == 0) {
			number1 = Double.parseDouble(mainText.getText().replace(',', '.')); // парсим в double подкидывая точку
																				// вместо запятой
		} else {
			String temp = mainText.getText();
			number2 = Double.parseDouble(temp.replace(',', '.'));
		}

	}

	boolean checkComma() {
		if (mainText.getText().contains(",") || mainText.getText().contains(".") ) {
			return true;
		}
		return false;
	}

	void setMinus() {
		String temp = mainText.getText();
		StringBuffer buffer = new StringBuffer(temp); // создаем буффер строки
		if (mainText.getText().contains("-")) { // если есть минус в строке то убираем его
			buffer.deleteCharAt(0); // удаляем символ минус по индексу из строки
		} else { // если нет минуса в строке то добавляем его
			buffer.insert(0, "-");
			; // добавляем в начало '-'
		}
		clear();
		writeText(buffer.toString());
	}

	void setDefault() { // настройки поумолчанию
		clear();
		number1 = 0;
		number2 = 0;
		operator = 0;

	}

	int getOperator() {
		return operator;
	}

	void clear() { // если не очищено очистить
		mainText.setText("0");
		clear = true; // очищено
	}

	void getResult() {
		double result = 0;
		switch (operator) {
		case '+': // "+"
			result = number1 + number2;
			break;
		case '-': // "-"
			result = number1 - number2;
			break;
		case 'X': // "X"
			result = number1 * number2;
			break;
		case '÷': // "/"
			if(number2 == 0) {
				setDefault();
			}
			result = number1 / number2;
			break;
		}
		number1 = result;
		number2 = 0;
		operator = 0;
		clear = false;
		writeText("" + (result));
	}

	void checkOperator() {
		if (operator > 0) {
			getResult();
		}
	}
	
	void setOperator(char c) {
		this.operator = c;
		clear = false;
	}

	void doPercent() {
		if(operator==0) {
			number1 = number1/100;
			System.out.println(number1);
			clear = false;
			writeText("" + number1);
		}
		else {
			number2 = (number1*number2)/100;
			System.out.println(number2);
			clear = false;
			writeText("" + number2);
		}
	}
	
	public static void main(String[] args) {
		new MainCalc();
	}

}
