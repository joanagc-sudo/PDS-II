package visao;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dao.EstudanteDAO;
import modelo.Estudante;

public class JanelaEstudante extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCurso;
	private JTextField txtNota;
	private JTextField txtBusca;
	private JTable tabela;
	private JLabel lblStatus;
	private DefaultTableModel modelo;
	private final EstudanteDAO dao = new EstudanteDAO();
	private int idSelecionado = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEstudante frame = new JanelaEstudante();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JanelaEstudante() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
		setContentPane(contentPane);

		// Configuração do GridBagLayout nativo
		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {0, 0, 0, 0};
		gbl.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl.columnWeights = new double[] {0.0, 1.0, 0.0, 0.0};
		gbl.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
		contentPane.setLayout(gbl);

		// --- LINHA 0 ---
		JLabel lblNome = new JLabel("Nome:");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		contentPane.add(lblNome, gbc_lblNome);

		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(0, 0, 5, 5);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 0;
		contentPane.add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		GridBagConstraints gbc_btnCadastrar = new GridBagConstraints();
		gbc_btnCadastrar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCadastrar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCadastrar.gridx = 2;
		gbc_btnCadastrar.gridy = 0;
		contentPane.add(btnCadastrar, gbc_btnCadastrar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		GridBagConstraints gbc_btnLimpar = new GridBagConstraints();
		gbc_btnLimpar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLimpar.insets = new Insets(0, 0, 5, 0);
		gbc_btnLimpar.gridx = 3;
		gbc_btnLimpar.gridy = 0;
		contentPane.add(btnLimpar, gbc_btnLimpar);

		// --- LINHA 1 ---
		JLabel lblCurso = new JLabel("Curso:");
		GridBagConstraints gbc_lblCurso = new GridBagConstraints();
		gbc_lblCurso.anchor = GridBagConstraints.EAST;
		gbc_lblCurso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurso.gridx = 0;
		gbc_lblCurso.gridy = 1;
		contentPane.add(lblCurso, gbc_lblCurso);

		txtCurso = new JTextField();
		GridBagConstraints gbc_txtCurso = new GridBagConstraints();
		gbc_txtCurso.insets = new Insets(0, 0, 5, 5);
		gbc_txtCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurso.gridx = 1;
		gbc_txtCurso.gridy = 1;
		contentPane.add(txtCurso, gbc_txtCurso);
		txtCurso.setColumns(10);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		GridBagConstraints gbc_btnAlterar = new GridBagConstraints();
		gbc_btnAlterar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAlterar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAlterar.gridx = 2;
		gbc_btnAlterar.gridy = 1;
		contentPane.add(btnAlterar, gbc_btnAlterar);

		JButton btnListar = new JButton("Listar todos");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBusca.setText("");
				listar();
			}
		});
		GridBagConstraints gbc_btnListar = new GridBagConstraints();
		gbc_btnListar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnListar.insets = new Insets(0, 0, 5, 0);
		gbc_btnListar.gridx = 3;
		gbc_btnListar.gridy = 1;
		contentPane.add(btnListar, gbc_btnListar);

		// --- LINHA 2 ---
		JLabel lblNota = new JLabel("Nota:");
		GridBagConstraints gbc_lblNota = new GridBagConstraints();
		gbc_lblNota.anchor = GridBagConstraints.EAST;
		gbc_lblNota.insets = new Insets(0, 0, 5, 5);
		gbc_lblNota.gridx = 0;
		gbc_lblNota.gridy = 2;
		contentPane.add(lblNota, gbc_lblNota);

		txtNota = new JTextField();
		GridBagConstraints gbc_txtNota = new GridBagConstraints();
		gbc_txtNota.insets = new Insets(0, 0, 5, 5);
		gbc_txtNota.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNota.gridx = 1;
		gbc_txtNota.gridy = 2;
		contentPane.add(txtNota, gbc_txtNota);
		txtNota.setColumns(10);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnExcluir.insets = new Insets(0, 0, 5, 5);
		gbc_btnExcluir.gridx = 2;
		gbc_btnExcluir.gridy = 2;
		contentPane.add(btnExcluir, gbc_btnExcluir);

		// --- LINHA 3 ---
		JLabel lblBuscar = new JLabel("Buscar:");
		GridBagConstraints gbc_lblBuscar = new GridBagConstraints();
		gbc_lblBuscar.anchor = GridBagConstraints.EAST;
		gbc_lblBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscar.gridx = 0;
		gbc_lblBuscar.gridy = 3;
		contentPane.add(lblBuscar, gbc_lblBuscar);

		txtBusca = new JTextField();
		GridBagConstraints gbc_txtBusca = new GridBagConstraints();
		gbc_txtBusca.insets = new Insets(0, 0, 5, 5);
		gbc_txtBusca.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBusca.gridx = 1;
		gbc_txtBusca.gridy = 3;
		contentPane.add(txtBusca, gbc_txtBusca);
		txtBusca.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 3;
		contentPane.add(btnBuscar, gbc_btnBuscar);

		// --- TABELA ---
		tabela = new JTable();
		modelo = new DefaultTableModel(
				new String[] {"ID", "Nome", "Curso", "Nota"}, 0
		);
		tabela.setModel(modelo);
		tabela.setRowHeight(22);
		tabela.setDefaultEditor(Object.class, null);

		// Tabela envolvida num JScrollPane para exibir o cabeçalho
		JScrollPane scrollPane = new JScrollPane(tabela);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		contentPane.add(scrollPane, gbc_scrollPane);

		// --- STATUS ---
		lblStatus = new JLabel("<html><b>0 estudante(s) na tabela.</b></html>");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.anchor = GridBagConstraints.WEST;
		gbc_lblStatus.gridwidth = 4;
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 5;
		contentPane.add(lblStatus, gbc_lblStatus);

		listar();

		tabela.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							carregarSelecionado();
						}
					}
				}
		);
	}
dsfsd
	private void listar() {
		try {
			preencherTabela(dao.listar());
		} catch (SQLException ex) {
			erro("Erro ao listar", ex);
		}
	}

	private void buscar() {
		try {
			preencherTabela(
					dao.buscarPorNome(txtBusca.getText().trim())
			);
		} catch (SQLException ex) {
			erro("Erro ao buscar", ex);
		}
	}

	private void preencherTabela(List<Estudante> lista) {
		modelo.setRowCount(0);

		for (Estudante e : lista) {
			modelo.addRow(new Object[] {
					e.getId(),
					e.getNome(),
					e.getCurso(),
					e.getNota()
			});
		}

		lblStatus.setText(
				lista.size() + " estudante(s) na tabela."
		);
	}

	private Estudante lerFormulario() {
		String nome = txtNome.getText().trim();
		String curso = txtCurso.getText().trim();

		if (nome.isEmpty()) {
			JOptionPane.showMessageDialog(
					this,
					"Preencha o nome!",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			txtNome.requestFocus();
			return null;
		}

		double nota;

		try {
			nota = Double.parseDouble(
					txtNota.getText().trim().replace(",", ".")
			);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(
					this,
					"Nota deve ser um numero!",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			txtNota.requestFocus();
			return null;
		}

		if (nota < 0 || nota > 10) {
			JOptionPane.showMessageDialog(
					this,
					"A nota deve estar entre 0 e 10.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			txtNota.requestFocus();
			return null;
		}

		return new Estudante(nome, curso, nota);
	}

	private void cadastrar() {
		Estudante e = lerFormulario();

		if (e == null)
			return;

		try {
			dao.inserir(e);

			JOptionPane.showMessageDialog(
					this,
					"Estudante cadastrado com o id " + e.getId() + "."
			);

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
		JOptionPane.showMessageDialog(
				this,
				contexto + ": " + ex.getMessage(),
				"Erro",
				JOptionPane.ERROR_MESSAGE
		);

		lblStatus.setText(contexto + ".");
	}

	private void carregarSelecionado() {
		int linha = tabela.getSelectedRow();

		if (linha < 0)
			return;

		idSelecionado = (int) modelo.getValueAt(linha, 0);

		txtNome.setText(
				String.valueOf(modelo.getValueAt(linha, 1))
		);

		txtCurso.setText(
				String.valueOf(modelo.getValueAt(linha, 2))
		);

		txtNota.setText(
				String.valueOf(modelo.getValueAt(linha, 3))
		);

		lblStatus.setText(
				"Editando o estudante de id "
				+ idSelecionado
				+ ". Altere os campos e clique em Alterar."
		);
	}

	private void alterar() {
		if (idSelecionado == 0) {
			JOptionPane.showMessageDialog(
					this,
					"Selecione primeiro uma linha da tabela.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		Estudante e = lerFormulario();

		if (e == null)
			return;

		e.setId(idSelecionado);

		try {
			dao.alterar(e);

			JOptionPane.showMessageDialog(
					this,
					"Estudante alterado."
			);

			limpar();
			listar();

		} catch (SQLException ex) {
			erro("Erro ao alterar", ex);
		}
	}

	private void excluir() {
		if (idSelecionado == 0) {
			JOptionPane.showMessageDialog(
					this,
					"Selecione primeiro uma linha da tabela.",
					"Aviso",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		int opcao = JOptionPane.showConfirmDialog(
				this,
				"Excluir o estudante " + txtNome.getText() + "?",
				"Confirmacao",
				JOptionPane.YES_NO_OPTION
		);

		if (opcao != JOptionPane.YES_OPTION)
			return;

		try {
			dao.excluir(idSelecionado);

			JOptionPane.showMessageDialog(
					this,
					"Estudante excluido."
			);

			limpar();
			listar();

		} catch (SQLException ex) {
			erro("Erro ao excluir", ex);
		}
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

	public JTextField getTxtBusca() {
		return txtBusca;
	}
}