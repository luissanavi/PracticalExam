import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {

    private JTextField textPanel;
    private Toolbar toolbar;
    private double temp;
    private double lastNumber;
    private String operator;
    private boolean operatorSelected;
    private boolean replaceable;
    private Dimension dimension;


    MainFrame(){
        super("Calculator");
        setLayout(new BorderLayout());
        textPanel = new JTextField();
        toolbar = new Toolbar();

        dimension = new Dimension();

        dimension.height = getSize().height/6;
        textPanel.setPreferredSize(dimension);
        textPanel.setText("0");
        textPanel.setHorizontalAlignment(SwingConstants.RIGHT);

        // EVENT LISTENERS
        toolbar.setStringListener(new StringListener() {
            @Override
            public void sendText(String text) {
                if ((Double.valueOf(textPanel.getText()) == 0 && textPanel.getText().indexOf(".") == -1) || replaceable)
                    textPanel.setText("");
                textPanel.setText(textPanel.getText() + text);
                replaceable = false;
            }

            @Override
            public void operation(String text) {
                switch (text){
                    case "CE":
                        textPanel.setText("0");
                        break;
                    case "C":
                        textPanel.setText("0");
                        temp = 0;
                        operator = "";
                        operatorSelected = false;
                        replaceable = false;
                        break;
                    case "←":
                        if (textPanel.getText().length() == 1)
                            textPanel.setText("0");
                        else
                            textPanel.setText(textPanel.getText().substring(0, textPanel.getText().length() - 1));
                        break;
                    default:
                        if (replaceable && operatorSelected) {
                            operator = text;
                            System.out.println("1");
                        } else if (!(replaceable) && operatorSelected){
                            performOperation();
                            operator = text;
                            temp = Double.valueOf(textPanel.getText());
                            replaceable = true;
                            System.out.println("2");
                        }  else {
                            temp = Double.valueOf(textPanel.getText());
                            operator = text;
                            operatorSelected = true;
                            replaceable = true;
                            System.out.println("4");
                        }
                        break;
                    case "+/-":
                        textPanel.setText(String.valueOf(fmt(Double.valueOf(textPanel.getText()) * -1)));
                        replaceable = false;
                        break;
                    case ".":
                        if(textPanel.getText().indexOf(".") == -1)
                            textPanel.setText(textPanel.getText() + text);
                        break;
                    case "=":
                        if (operatorSelected){
                            lastNumber = Double.valueOf(textPanel.getText());
                            operatorSelected = false;
                        }
                        performOperationFromEquals();
                        temp = Double.valueOf(textPanel.getText());
                        replaceable = true;
                        break;
                }
            }
        });

        add(toolbar, BorderLayout.CENTER);
        add(textPanel,BorderLayout.NORTH);
        setMinimumSize(new Dimension(350,500));
        setSize(350,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                dimension.height = (getSize().height/6);
                textPanel.setPreferredSize(dimension);
                for (JButton b: toolbar.buttons) {
                    b.setFont(new Font(b.getFont().getName(), Font.PLAIN, (int)(b.getSize().height * .25)));
                }
                textPanel.setFont(new Font(textPanel.getFont().getName(), Font.PLAIN, (int)(textPanel.getSize().height * .25)));
            }
        });
    }

    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }

    public void performOperation(){
        switch (operator){
            case "/":
                temp /= Double.valueOf(textPanel.getText());
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "X":
                temp *= Double.valueOf(textPanel.getText());
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "+":
                temp += Double.valueOf(textPanel.getText());
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "—":
                temp -= Double.valueOf(textPanel.getText());
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
        }
    }

    public void performOperationFromEquals(){
        switch (operator){
            case "/":
                temp /= lastNumber;
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "X":
                temp *= lastNumber;
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "+":
                temp += lastNumber;
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
            case "—":
                temp -= lastNumber;
                textPanel.setText(String.valueOf(fmt(temp)));
                break;
        }
    }
}
