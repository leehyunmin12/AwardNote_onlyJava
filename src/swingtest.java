import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class swingtest extends JFrame {
    private ArrayList<String> favorites = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> favoriteList = new JList<>(listModel);
    private JTextField inputField = new JTextField(20);
    private JButton addButton = new JButton("추가");
    private JButton removeButton = new JButton("삭제");

    public swingtest() {
        setTitle("즐겨찾기");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 상단: 입력창 + 추가 버튼
        JPanel topPanel = new JPanel();
        topPanel.add(inputField);
        topPanel.add(addButton);

        // 중앙: 리스트
        JScrollPane scrollPane = new JScrollPane(favoriteList);

        // 하단: 삭제 버튼
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(removeButton);

        // 레이아웃
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // 이벤트 처리
        addButton.addActionListener(e -> {
            String item = inputField.getText().trim();
            if (!item.isEmpty() && !favorites.contains(item)) {
                favorites.add(item);
                listModel.addElement(item);
                inputField.setText("");
            }
        });

        removeButton.addActionListener(e -> {
            int index = favoriteList.getSelectedIndex();
            if (index != -1) {
                favorites.remove(index);
                listModel.remove(index);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new swingtest().setVisible(true);
        });
    }
}
