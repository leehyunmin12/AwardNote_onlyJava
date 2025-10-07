import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Item {
    String name;
    boolean favorite;

    Item(String name) {
        this.name = name;
        this.favorite = false;
    }

    @Override
    public String toString() {
        return (favorite ? "⭐ " : "☆ ") + name;
    }
}

public class swingtest extends JFrame {
    private ArrayList<Item> items = new ArrayList<>();
    private DefaultListModel<Item> listModel = new DefaultListModel<>();
    private JList<Item> itemList = new JList<>(listModel);

    private JButton toggleFavButton = new JButton("⭐ 즐겨찾기 토글");
    private JButton showFavButton = new JButton("즐겨찾기만 보기");
    private JButton showAllButton = new JButton("전체 보기");

    public swingtest() {
        setTitle("즐겨찾기 리스트");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 초기 데이터
        items.add(new Item("구글"));
        items.add(new Item("네이버"));
        items.add(new Item("유튜브"));
        items.add(new Item("깃허브"));

        refreshList(items);

        // UI 배치
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleFavButton);
        buttonPanel.add(showFavButton);
        buttonPanel.add(showAllButton);

        add(new JScrollPane(itemList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ⭐ 토글 이벤트
        toggleFavButton.addActionListener(e -> {
            int index = itemList.getSelectedIndex();
            if (index != -1) {
                Item item = listModel.get(index);
                item.favorite = !item.favorite;
                itemList.repaint(); // 리스트 다시 그리기
            }
        });

        // 즐겨찾기만 보기
        showFavButton.addActionListener(e -> {
            ArrayList<Item> favs = new ArrayList<>();
            for (Item i : items) {
                if (i.favorite) favs.add(i);
            }
            refreshList(favs);
        });

        // 전체 보기
        showAllButton.addActionListener(e -> {
            refreshList(items);
        });
    }

    private void refreshList(ArrayList<Item> list) {
        listModel.clear();
        for (Item i : list) {
            listModel.addElement(i);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new swingtest().setVisible(true));
    }
}
