package CadastroRestaurante;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;
import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class TelaCadastro extends JFrame {
    public static final String BEBIDA = "Bebida";
    public static final String SOBREMESA = "Sobremesa";
    public static final String PRATO = "Prato";
    private JTextField campoPrato;
    private JTextField campoSobremesa;
    private JTextField campoBebida;
    private JTextField campoPratoPreco;
    private JTextField campoSobremesaPreco;
    private JTextField campoBebidaPreco;
    private JButton buttonLimpar;
    private JButton buttonCadastrar;

    private Boolean falha = Boolean.FALSE;


    public TelaCadastro() {
        setTitle("Cadastro do Cardápio");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        painel.setBackground(new Color(237,237,237));

        JLabel labelTitulo = new JLabel("Nomes");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(new Color(96,97,97));
        addComponent(painel, labelTitulo, constraints, 1, 0, 1, 1);

        JLabel labelValores = new JLabel("Valores");
        labelValores.setFont(new Font("Arial", Font.BOLD, 18));
        labelValores.setForeground(new Color(96,97,97));
        addComponent(painel, labelValores, constraints, 5, 0, 1, 1);

        JLabel labelPrato = new JLabel(PRATO);
        addComponent(painel, labelPrato, constraints, 0, 1, 1, 1);
        campoPrato = new JTextField(15);
        PromptSupport.setPrompt("Digite o nome do prato", campoPrato);
        addComponent(painel, campoPrato, constraints, 1, 1, 1, 1);

        JLabel labelPratoPreco = new JLabel("R$: ");
        addComponent(painel, labelPratoPreco, constraints, 4, 1, 1, 1);
        campoPratoPreco = new JTextField(10);
        PromptSupport.setPrompt("Digite o preço", campoPratoPreco);
        addComponent(painel, campoPratoPreco, constraints, 5, 1, 1, 1);


        JLabel labelSobremesa = new JLabel(SOBREMESA);
        addComponent(painel, labelSobremesa, constraints, 0, 2, 1, 1);
        campoSobremesa = new JTextField(15);
        PromptSupport.setPrompt("Digite o nome da sobremesa", campoSobremesa);
        addComponent(painel, campoSobremesa, constraints, 1, 2, 1, 1);
        JLabel labelSobremesaPreco = new JLabel("R$: ");
        addComponent(painel, labelSobremesaPreco, constraints, 4, 2, 1, 1);
        campoSobremesaPreco = new JTextField(10);
        PromptSupport.setPrompt("Digite o preço", campoSobremesaPreco);
        addComponent(painel, campoSobremesaPreco, constraints, 5, 2, 1, 1);


        JLabel labelBebida = new JLabel(BEBIDA);
        addComponent(painel, labelBebida, constraints, 0, 3, 1, 1);
        campoBebida = new JTextField(15);
        PromptSupport.setPrompt("Digite o nome da bebida", campoBebida);
        addComponent(painel, campoBebida, constraints, 1, 3, 1, 1);
        JLabel labelBebidaPreco = new JLabel("R$: ");
        addComponent(painel, labelBebidaPreco, constraints, 4, 3, 1, 1);
        campoBebidaPreco = new JTextField(10);
        PromptSupport.setPrompt("Digite o preço", campoBebidaPreco);
        addComponent(painel, campoBebidaPreco, constraints, 5, 3, 1, 1);


        buttonCadastrar = new JButton("Cadastrar");
        buttonCadastrar.addActionListener(e -> cadastrar());
        buttonCadastrar.setBackground(new Color(96,97,97));
        buttonCadastrar.setForeground(new Color(255,255,255));
        addComponent(painel, buttonCadastrar, constraints, 1, 4, 3, 1);

        buttonLimpar = new JButton("Limpar");
        buttonLimpar.addActionListener(e -> limparButton());
        buttonLimpar.setBackground(new Color(96,97,97));
        buttonLimpar.setForeground(new Color(255,255,255));
        addComponent(painel, buttonLimpar, constraints, 3, 4, 3, 1);

        List<String> lista = readerFile("Cardapio.txt");
        for (int i = 1; i < lista.size(); i++) {
            System.out.println(lista.get(i));
            //JTextArea texto = new JTextArea(10, 2);
            //texto.insert(lista.get(i), texto.getCaretPosition());
            //texto.append("\n");
            //addComponent(painel, texto, constraints, 0, 5, 3, 1);
        }

        add(painel);
        setLocationRelativeTo(null);
    }
    private List<String> readerFile(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            return reader.lines().collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private void addComponent(Container container, Component component, GridBagConstraints constraints, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        container.add(component, constraints);
    }

    private boolean verificarString(String valorStr) {
        return valorStr.matches("[a-zA-Z\\s]+");
    }
    public String verificarVazio(String valorStr, String campo) {
        try {
            if (valorStr.isEmpty()) throw new RuntimeException("O valor do campo " + campo + " não pode ser vazio!");
            if (valorStr.isBlank()) throw new RuntimeException("O valor do " + campo + " não pode ser espaços vazios no campo");
            if (!verificarString(valorStr)) throw new RuntimeException("O valor do campo " + campo + " não é válido. Informe apenas texto.");

            return valorStr;
        } catch (RuntimeException e) {
            showMessageDialog(this, e.getMessage());
            this.falha = Boolean.TRUE;
        }
        return valorStr;
    }

    public Float converter(String valorStr, String campo) {
        try {
            if (valorStr.isEmpty()) throw new RuntimeException("O valor do preço do " + campo + " não pode ser vazio!");
            if (valorStr.isBlank()) throw new RuntimeException("O valor do " + campo + " não pode ser espaços vazios no campo");

            return Float.parseFloat(valorStr);
        } catch (NumberFormatException n) {
            showMessageDialog(this, "O valor do preço do " + campo + " não é valido, informar apenas números inteiros", "erro", JOptionPane.ERROR_MESSAGE);
            this.falha = Boolean.TRUE;
            return 0F;
        }
    }

    public void cadastrar() {

        try {
            var prato = verificarVazio(campoPrato.getText(), PRATO);
            var sobremesa = verificarVazio(campoSobremesa.getText(), SOBREMESA);
            var bebida = verificarVazio(campoBebida.getText(), BEBIDA);

            if (this.falha) {
                this.falha = Boolean.FALSE;
                return;
            }

            var pratoPreco = converter(campoPratoPreco.getText(), PRATO);
            var SobremesaPreco = converter(campoSobremesaPreco.getText(), SOBREMESA);
            var BebidaPreco = converter(campoBebidaPreco.getText(), BEBIDA);

            if (this.falha) {
                this.falha = Boolean.FALSE;
                return;
            }

            var cardapio = "Prato: " + prato + " valor: R$: " + pratoPreco +
                    ",Sobremesa: " + sobremesa + " valor: R$: " + SobremesaPreco +
                    ",Bebida: " + bebida + " valor: R$: " + BebidaPreco;


            String message = "Deseja cadastrar?";
            String title = "Confirmação";
            int mostrarOption = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);

            if (mostrarOption == JOptionPane.YES_OPTION) {
                salvar(cardapio);
                showMessageDialog(this, "Cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limpar();
            } else {
                showMessageDialog(this, "Erro ao cadastrar!", "Erro", JOptionPane.ERROR_MESSAGE);
                limpar();
            }
        } catch (RuntimeException e) {
            showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void limpar() {
        campoPrato.setText("");
        campoSobremesa.setText("");
        campoBebida.setText("");
        campoPratoPreco.setText("");
        campoSobremesaPreco.setText("");
        campoBebidaPreco.setText("");
    }

    public void limparButton() {
        campoPrato.setText("");
        campoSobremesa.setText("");
        campoBebida.setText("");
        campoPratoPreco.setText("");
        campoSobremesaPreco.setText("");
        campoBebidaPreco.setText("");

        showMessageDialog(this, "Limpado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void salvar(String valor) {
        var diretorioProjeto = System.getProperty("user.dir");
        var nomeArquivo = "//Cardapio.txt";
        var arquivo = new File(diretorioProjeto, nomeArquivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.newLine();
            writer.write(valor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
