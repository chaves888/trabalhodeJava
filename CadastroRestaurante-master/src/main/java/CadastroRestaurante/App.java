package CadastroRestaurante;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            var tela = new TelaCadastro();
            tela.setVisible(true);
        });
    }
}
