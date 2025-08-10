package com.example.lavarapido;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class ServicosActivity extends AppCompatActivity implements ServicosGerenciarAdapter.OnServicoActionListener {
    
    private TextInputEditText etNomeServico, etPrecoServico;
    private Button btnSalvarServico, btnCancelar, btnVoltar;
    private RecyclerView rvServicosGerenciar;
    
    private DatabaseHelper dbHelper;
    private ServicosGerenciarAdapter adapter;
    private List<Servico> servicos;
    private Servico servicoEditando = null; // Para controlar se está editando
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);
        
        initializeViews();
        initializeDatabase();
        setupRecyclerView();
        setupButtons();
        loadServicos();
    }
    
    private void initializeViews() {
        etNomeServico = findViewById(R.id.et_nome_servico);
        etPrecoServico = findViewById(R.id.et_preco_servico);
        btnSalvarServico = findViewById(R.id.btn_salvar_servico);
        btnCancelar = findViewById(R.id.btn_cancelar);
        btnVoltar = findViewById(R.id.btn_voltar);
        rvServicosGerenciar = findViewById(R.id.rv_servicos_gerenciar);
    }
    
    private void initializeDatabase() {
        dbHelper = new DatabaseHelper(this);
    }
    
    private void setupRecyclerView() {
        rvServicosGerenciar.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupButtons() {
        btnSalvarServico.setOnClickListener(v -> salvarServico());
        btnCancelar.setOnClickListener(v -> cancelarEdicao());
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    private void loadServicos() {
        servicos = dbHelper.getAllServicos();
        
        if (adapter == null) {
            adapter = new ServicosGerenciarAdapter(servicos, this);
            rvServicosGerenciar.setAdapter(adapter);
        } else {
            adapter.updateServicos(servicos);
        }
    }
    
    private void salvarServico() {
        String nome = etNomeServico.getText().toString().trim();
        String precoStr = etPrecoServico.getText().toString().trim();
        
        if (TextUtils.isEmpty(nome)) {
            etNomeServico.setError("Nome é obrigatório");
            return;
        }
        
        if (TextUtils.isEmpty(precoStr)) {
            etPrecoServico.setError("Preço é obrigatório");
            return;
        }
        
        double preco;
        try {
            preco = Double.parseDouble(precoStr);
            if (preco <= 0) {
                etPrecoServico.setError("Preço deve ser maior que zero");
                return;
            }
        } catch (NumberFormatException e) {
            etPrecoServico.setError("Preço inválido");
            return;
        }
        
        if (servicoEditando == null) {
            // Adicionar novo serviço
            Servico novoServico = new Servico(nome, preco);
            long result = dbHelper.addServico(novoServico);
            
            if (result > 0) {
                Toast.makeText(this, "Serviço adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                limparFormulario();
                loadServicos();
            } else {
                Toast.makeText(this, "Erro ao adicionar serviço", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Editar serviço existente
            servicoEditando.setNome(nome);
            servicoEditando.setPreco(preco);
            
            int result = dbHelper.updateServico(servicoEditando);
            
            if (result > 0) {
                Toast.makeText(this, "Serviço atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                cancelarEdicao();
                loadServicos();
            } else {
                Toast.makeText(this, "Erro ao atualizar serviço", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void cancelarEdicao() {
        servicoEditando = null;
        limparFormulario();
        btnSalvarServico.setText("Salvar");
    }
    
    private void limparFormulario() {
        etNomeServico.setText("");
        etPrecoServico.setText("");
        etNomeServico.clearFocus();
        etPrecoServico.clearFocus();
    }
    
    @Override
    public void onEditarClick(Servico servico) {
        servicoEditando = servico;
        etNomeServico.setText(servico.getNome());
        etPrecoServico.setText(String.valueOf(servico.getPreco()));
        btnSalvarServico.setText("Atualizar");
        
        Toast.makeText(this, "Editando: " + servico.getNome(), Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onExcluirClick(Servico servico) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Serviço")
                .setMessage("Deseja excluir o serviço \"" + servico.getNome() + "\"?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    dbHelper.deleteServico(servico.getId());
                    
                    // Se estava editando este serviço, cancelar edição
                    if (servicoEditando != null && servicoEditando.getId() == servico.getId()) {
                        cancelarEdicao();
                    }
                    
                    loadServicos();
                    Toast.makeText(this, "Serviço excluído!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
