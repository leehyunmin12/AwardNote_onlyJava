import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.Buffer;

public class swingtest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("메모장");
        JTextArea textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("파일");
        JMenuItem openItem = new JMenuItem("열기");
        JMenuItem saveItem = new JMenuItem("저장");

        // 파일 열기
        openItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if(chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
                try (BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()))){
                    textArea.read(br, null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 파일 저장
        saveItem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if(chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(chooser.getSelectedFile()))) {
                    textArea.write(bw);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
