import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Toolbar extends JPanel implements ActionListener {

    ArrayList<JButton> buttons = new ArrayList<>();

    private StringListener textListener;

    Toolbar(){
        setBorder(BorderFactory.createEtchedBorder());

        buttons.add(new JButton("CE"));
        buttons.add(new JButton("C"));
        buttons.add(new JButton("←"));
        buttons.add(new JButton("/"));
        buttons.add(new JButton("7"));
        buttons.add(new JButton("8"));
        buttons.add(new JButton("9"));
        buttons.add(new JButton("X"));
        buttons.add(new JButton("4"));
        buttons.add(new JButton("5"));
        buttons.add(new JButton("6"));
        buttons.add(new JButton("—"));
        buttons.add(new JButton("1"));
        buttons.add(new JButton("2"));
        buttons.add(new JButton("3"));
        buttons.add(new JButton("+"));
        buttons.add(new JButton("+/-"));
        buttons.add(new JButton("0"));
        buttons.add(new JButton("."));
        buttons.add(new JButton("="));


        for (JButton button: buttons) {
            button.setBackground(Color.LIGHT_GRAY);
            button.addActionListener(this);
            add(button);
        }


        setLayout(new GridLayout(5,4));
    }

    public void setStringListener(StringListener textListener){
        this.textListener=textListener;
    }

    public void changeColor(Color color){
        setBackground(color);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (this.textListener!=null){
            if (isNumeric(clicked.getText()))
                this.textListener.sendText(clicked.getText());
            else
                this.textListener.operation(clicked.getText());
        }
    }
}
