import javax.swing.*;
import java.awt.*;

public class swingtest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("로그인 창");
        JPanel panel = new JPanel(new GridLayout(3,2,5,5));

        JLabel idLabel = new JLabel("아이디:");
        JTextField idField = new JTextField();
        JLabel pwLabel = new JLabel("비밀번호:");
        JPasswordField pwField = new JPasswordField();
        JButton loginBtn = new JButton("로그인");
        JButton cancelBtn = new JButton("취소");

        panel.add(idLabel); panel.add(idField);
        panel.add(pwField); panel.add(pwField);
        panel.add(loginBtn); panel.add(cancelBtn);

        frame.add(panel);
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


    }
}
