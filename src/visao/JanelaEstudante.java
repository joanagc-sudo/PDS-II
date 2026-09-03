package visao;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.EstudanteDAO;
import net.miginfocom.swing.MigLayout;

public class JanelaEstudante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCurso;
	private JTextField txtNota;
	private JTextField txtBusca;
	private JTable tabela;
	
	private DefaultTableModel modelo;
	// Ponte com o banco: um unico objeto serve a janela inteira.
	private final EstudanteDAO dao = new EstudanteDAO();
	// Id do estudante selecionado na tabela. Zero = nenhum selecionado.
	private int idSelecionado = 0;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEstudante frame = new JanelaEstudante();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaEstudante() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[87.00px,grow][grow][][]", "[24.00px][24.00px][24.00px][24.00px][grow][]"));
		
		JLabel lblNome = new JLabel("Nome:");
		contentPane.add(lblNome, "cell 0 0,alignx center,growy");
		
		txtNome = new JTextField();
		contentPane.add(txtNome, "cell 1 0,growx");
		txtNome.setColumns(10);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		btnCadastrar.setBackground(new Color(240, 240, 240));
		contentPane.add(btnCadastrar, "cell 2 0,growx");
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		contentPane.add(btnLimpar, "cell 3 0,growx");
		
		JLabel lblCurso = new JLabel("Curso:");
		contentPane.add(lblCurso, "cell 0 1,alignx center,growy");
		
		txtCurso = new JTextField();
		contentPane.add(txtCurso, "cell 1 1,growx");
		txtCurso.setColumns(10);
		
		JButton btnAlterar = new JButton("Alterar");
		contentPane.add(btnAlterar, "cell 2 1,growx");
		
		JButton btnListar = new JButton("Listar todos");
		contentPane.add(btnListar, "cell 3 1,growx");
		
		JLabel lblNota = new JLabel("Nota:");
		contentPane.add(lblNota, "cell 0 2,alignx center,growy");
		
		txtNota = new JTextField();
		contentPane.add(txtNota, "cell 1 2,growx");
		txtNota.setColumns(10);
		
		JButton btnExcluir = new JButton("Excluir");
		contentPane.add(btnExcluir, "cell 2 2,growx");
		
		JLabel lblBuscar = new JLabel("Buscar:");
		contentPane.add(lblBuscar, "cell 0 3,alignx center,growy");
		
		txtBusca = new JTextField();
		contentPane.add(txtBusca, "cell 1 3,growx");
		txtBusca.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBusca.setText("");
				 listar();

			}
		});
		contentPane.add(btnBuscar, "cell 2 3,growx");
		
		tabela = new JTable();
		contentPane.add(tabela, "cell 0 4 4 1,grow");
		
		JLabel lblStatus = new JLabel("<html><b>3 estudante(s) na tabela.</b></html>");
		contentPane.add(lblStatus, "cell 0 5 4 1,alignx left");
		
		modelo = new DefaultTableModel(new String[] { "ID", "Nome", "Curso", "Nota" }, 0);
		 tabela.setModel(modelo);
		 tabela.setRowHeight(22);
		// Impede a edicao direta na celula: alterar passa pelo formulario.
		 tabela.setDefaultEditor(Object.class, null);
		 listar(); // a tabela ja abre preenchida
		 
	}
	
	private void listar() {
		try {
		 preencherTabela(dao.listar());
		 } catch (SQLException ex) {
		 erro("Erro ao listar", ex);
		 }
		 }
		private void buscar() {
		try {
		 preencherTabela(dao.buscarPorNome(txtBusca.getText().trim()));
		 } catch (SQLException ex) {
		 erro("Erro ao buscar", ex);
		 }
		 }
		private void preencherTabela(List<Estudante> lista) {
		 modelo.setRowCount(0); // SEM ISTO A TABELA DUPLICA
		for (Estudante e : lista) {
		 modelo.addRow(new Object[] {
		 e.getId(), e.getNome(), e.getCurso(), e.getNota() });
		 }
		 lblStatus.setText(lista.size() + " estudante(s) na tabela.");
		 }

		private Estudante lerFormulario() {
			 String nome = txtNome.getText().trim();
			 String curso = txtCurso.getText().trim();
			if (nome.isEmpty()) {
			 JOptionPane.showMessageDialog(this, "Preencha o nome!",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			 txtNome.requestFocus();
			return null;
			 }
			double nota;
			try {
			 nota = Double.parseDouble(txtNota.getText().trim().replace(",", "."));
			 } catch (NumberFormatException ex) {
			 JOptionPane.showMessageDialog(this, "Nota deve ser um numero!",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			 txtNota.requestFocus();
			return null;
			 }
			if (nota < 0 || nota > 10) {
			 JOptionPane.showMessageDialog(this, "A nota deve estar entre 0 e 10.",
			"Aviso", JOptionPane.WARNING_MESSAGE);
			 txtNota.requestFocus();
			return null;
			 }
			return new Estudante(nome, curso, nota);
			 }
			private void cadastrar() {
			 Estudante e = lerFormulario();
			if (e == null) return; // invalido: a mensagem ja apareceu
			try {
			 dao.inserir(e);
			 JOptionPane.showMessageDialog(this,
			"Estudante cadastrado com o id " + e.getId() + ".");
			 limpar();
			 listar();
			 } catch (SQLException ex) {
			 erro("Erro ao cadastrar", ex);
			 }
			 }
			private void limpar() {
			 idSelecionado = 0;
			 txtNome.setText("");
			 txtCurso.setText("");
			 txtNota.setText("");
			 tabela.clearSelection();
			 txtNome.requestFocus();
			 lblStatus.setText("Formulario limpo.");
			 }
			private void erro(String contexto, SQLException ex) {
			 JOptionPane.showMessageDialog(this,
			 contexto + ": " + ex.getMessage(),
			"Erro", JOptionPane.ERROR_MESSAGE);
			 lblStatus.setText(contexto + ".");
			 }

	public JTextField getTxtNome() {
		return txtNome;
	}
	public JTextField getTxtCurso() {
		return txtCurso;
	}
	public JTextField getTxtNota() {
		return txtNota;
	}
	public JTextField getTxtBuca() {
		return txtBusca;
	}
}
