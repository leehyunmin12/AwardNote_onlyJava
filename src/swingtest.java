import javax.swing.*;
import java.awt.*;

public class swingtest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("계산기");
        JTextField input1 = new JTextField();
        JTextField input2 = new JTextField();
        JButton addBtn = new JButton("+");
        JLabel result = new JLabel("결과");

        addBtn.addActionListener(e -> {
            try{
                int a = Integer.parseInt(input1.getText());
                int b = Integer.parseInt(input2.getText());
                result.setText("결과: "+ (a+b));
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "숫자를 입력하세요!");
            }
        });

        JPanel panel = new JPanel(new GridLayout(3,2,5,5));
        panel.add(new JLabel("숫자1:")); panel.add(input1);
        panel.add(new JLabel("숫자2:")); panel.add(input2);
        panel.add(addBtn); panel.add(result);

        frame.add(panel);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
