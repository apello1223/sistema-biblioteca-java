package sistemabiblioteca;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * PROJETO INTEGRADOR - ETAPA 4
 * Funcionalidades: Login, Cadastro de Livros e Cadastro de Leitores.
 * Conexão definitiva com banco de dados MySQL (XAMPP).
 */

// --- CLASSE DE CONEXÃO (Pode ficar no mesmo arquivo ou separado) ---
class Conexao {
    public static Connection getConexao() {
        try {
            // Configuração padrão do XAMPP: banco "biblioteca", user "root", senha vazia
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", "");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco! Verifique se o MySQL no XAMPP está ligado.\n" + e.getMessage());
            return null;
        }
    }
}

// --- CLASSE PRINCIPAL DO SISTEMA ---
public class SistemaBiblioteca extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;

    public SistemaBiblioteca() {
        setTitle("Sistema de Biblioteca - PI Etapa 3");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Adicionar telas ao CardLayout
        mainPanel.add(telaLogin(), "login");
        mainPanel.add(telaDashboard(), "dashboard");
        mainPanel.add(telaCadastroLivros(), "cadastroLivros");
        mainPanel.add(telaCadastroLeitores(), "cadastroLeitores");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    private JPanel telaLogin() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#f5f5f5"));

        JLabel titulo = new JLabel("Login", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(200, 50, 200, 40);
        panel.add(titulo);

        JTextField usuario = new JTextField("admin");
        usuario.setBounds(180, 120, 240, 30);
        panel.add(usuario);

        JPasswordField senha = new JPasswordField("123");
        senha.setBounds(180, 170, 240, 30);
        panel.add(senha);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(Color.decode("#4CAF50"));
        btnLogin.setForeground(Color.white);
        btnLogin.setBounds(180, 220, 240, 35);
        btnLogin.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        panel.add(btnLogin);

        return panel;
    }

    private JPanel telaDashboard() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#f5f5f5"));

        JLabel titulo = new JLabel("Dashboard", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(200, 30, 200, 40);
        panel.add(titulo);

        JButton btnLivros = new JButton("Cadastro de Livros");
        btnLivros.setBounds(180, 100, 240, 35);
        btnLivros.setBackground(Color.decode("#4CAF50"));
        btnLivros.setForeground(Color.white);
        btnLivros.addActionListener(e -> cardLayout.show(mainPanel, "cadastroLivros"));
        panel.add(btnLivros);

        JButton btnLeitores = new JButton("Cadastro de Leitores");
        btnLeitores.setBounds(180, 150, 240, 35);
        btnLeitores.setBackground(Color.decode("#4CAF50"));
        btnLeitores.setForeground(Color.white);
        btnLeitores.addActionListener(e -> cardLayout.show(mainPanel, "cadastroLeitores"));
        panel.add(btnLeitores);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(180, 200, 240, 35);
        btnSair.setBackground(Color.decode("#f44336"));
        btnSair.setForeground(Color.white);
        btnSair.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        panel.add(btnSair);

        return panel;
    }

    private JPanel telaCadastroLivros() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#f5f5f5"));

        JLabel titulo = new JLabel("Cadastro de Livros", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(180, 30, 240, 40);
        panel.add(titulo);

        JTextField campoTitulo = new JTextField("Título do Livro");
        campoTitulo.setBounds(180, 100, 240, 30);
        panel.add(campoTitulo);

        JTextField campoAutor = new JTextField("Autor");
        campoAutor.setBounds(180, 140, 240, 30);
        panel.add(campoAutor);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 200, 110, 35);
        btnSalvar.setBackground(Color.decode("#4CAF50"));
        btnSalvar.setForeground(Color.white);
        btnSalvar.addActionListener(e -> {
            // LÓGICA DEFINITIVA DE BANCO DE DADOS
            String sql = "INSERT INTO livros (titulo, autor) VALUES (?, ?)";
            try (Connection conn = Conexao.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, campoTitulo.getText());
                stmt.setString(2, campoAutor.getText());
                stmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Livro '" + campoTitulo.getText() + "' salvo no banco!");
                campoTitulo.setText(""); campoAutor.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar livro: " + ex.getMessage());
            }
        });
        panel.add(btnSalvar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(310, 200, 110, 35);
        btnVoltar.setBackground(Color.decode("#9E9E9E"));
        btnVoltar.setForeground(Color.white);
        btnVoltar.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        panel.add(btnVoltar);

        return panel;
    }

    private JPanel telaCadastroLeitores() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.decode("#f5f5f5"));

        JLabel titulo = new JLabel("Cadastro de Leitores", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setBounds(180, 30, 240, 40);
        panel.add(titulo);

        JTextField campoNome = new JTextField("Nome do Leitor");
        campoNome.setBounds(180, 100, 240, 30);
        panel.add(campoNome);

        JTextField campoEmail = new JTextField("Email");
        campoEmail.setBounds(180, 140, 240, 30);
        panel.add(campoEmail);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 200, 110, 35);
        btnSalvar.setBackground(Color.decode("#4CAF50"));
        btnSalvar.setForeground(Color.white);
        btnSalvar.addActionListener(e -> {
            // LÓGICA DEFINITIVA DE BANCO DE DADOS
            String sql = "INSERT INTO leitores (nome, email) VALUES (?, ?)";
            try (Connection conn = Conexao.getConexao();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, campoNome.getText());
                stmt.setString(2, campoEmail.getText());
                stmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "Leitor '" + campoNome.getText() + "' cadastrado com sucesso!");
                campoNome.setText(""); campoEmail.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar leitor: " + ex.getMessage());
            }
        });
        panel.add(btnSalvar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(310, 200, 110, 35);
        btnVoltar.setBackground(Color.decode("#9E9E9E"));
        btnVoltar.setForeground(Color.white);
        btnVoltar.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        panel.add(btnVoltar);

        return panel;
    }

    public static void main(String[] args) {
        // Garante que o driver JDBC seja carregado
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado!");
        }

        SwingUtilities.invokeLater(() -> {
            new SistemaBiblioteca().setVisible(true);
        });
    }
}