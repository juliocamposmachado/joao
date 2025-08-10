package com.example.lavarapido;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ServicosAdapter.OnServicoClickListener {
    
    private TextView tvDataAtual, tvTotalCaixa;
    private RecyclerView rvServicos;
    private Button btnGerenciarServicos, btnHistorico, btnFecharCaixa;
    
    private DatabaseHelper dbHelper;
    private ServicosAdapter adapter;
    private List<Servico> servicos;
    private double totalCaixa = 0.0;
    private String dataAtual;
    
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "CaixaPrefs";
    private static final String KEY_TOTAL_CAIXA = "totalCaixa";
    private static final String KEY_DATA_CAIXA = "dataCaixa";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        initializeDatabase();
        initializePreferences();
        setupRecyclerView();
        setupButtons();
        loadData();
        updateUI();
    }
    
    private void initializeViews() {
        tvDataAtual = findViewById(R.id.tv_data_atual);
        tvTotalCaixa = findViewById(R.id.tv_total_caixa);
        rvServicos = findViewById(R.id.rv_servicos);
        btnGerenciarServicos = findViewById(R.id.btn_gerenciar_servicos);
        btnHistorico = findViewById(R.id.btn_historico);
        btnFecharCaixa = findViewById(R.id.btn_fechar_caixa);
    }
    
    private void initializeDatabase() {
        dbHelper = new DatabaseHelper(this);
    }
    
    private void initializePreferences() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        // Obter data atual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dataAtual = sdf.format(new Date());
        
        // Verificar se é um novo dia
        String ultimaData = sharedPreferences.getString(KEY_DATA_CAIXA, "");
        if (!dataAtual.equals(ultimaData)) {
            // Novo dia - resetar caixa
            totalCaixa = 0.0;
            savePreferences();
        } else {
            // Mesmo dia - carregar total salvo
            totalCaixa = Double.parseDouble(sharedPreferences.getString(KEY_TOTAL_CAIXA, "0.0"));
        }
    }
    
    private void setupRecyclerView() {
        rvServicos.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupButtons() {
        btnGerenciarServicos.setOnClickListener(v -> {
            Intent intent = new Intent(this, ServicosActivity.class);
            startActivity(intent);
        });
        
        btnHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoricoActivity.class);
            startActivity(intent);
        });
        
        btnFecharCaixa.setOnClickListener(v -> fecharCaixa());
    }
    
    private void loadData() {
        servicos = dbHelper.getAllServicos();
        
        if (adapter == null) {
            adapter = new ServicosAdapter(servicos, this);
            rvServicos.setAdapter(adapter);
        } else {
            adapter.updateServicos(servicos);
        }
    }
    
    private void updateUI() {
        tvDataAtual.setText("Data: " + dataAtual);
        tvTotalCaixa.setText(String.format("R$ %.2f", totalCaixa));
    }
    
    @Override
    public void onServicoClick(Servico servico) {
        totalCaixa += servico.getPreco();
        savePreferences();
        updateUI();
        
        Toast.makeText(this, 
            String.format("+ R$ %.2f - %s", servico.getPreco(), servico.getNome()), 
            Toast.LENGTH_SHORT).show();
    }
    
    private void fecharCaixa() {
        if (totalCaixa <= 0) {
            Toast.makeText(this, "Não há valor para fechar o caixa!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fechar Caixa")
                .setMessage(String.format("Deseja fechar o caixa com o total de R$ %.2f?", totalCaixa))
                .setPositiveButton("Sim", (dialog, which) -> confirmarFechamento())
                .setNegativeButton("Não", null)
                .show();
    }
    
    private void confirmarFechamento() {
        // Salvar no histórico
        Fechamento fechamento = new Fechamento(dataAtual, totalCaixa);
        dbHelper.addFechamento(fechamento);
        
        // Mostrar resultado
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Caixa Fechado!")
                .setMessage(String.format("Total do dia %s: R$ %.2f\n\nO caixa foi salvo no histórico.", dataAtual, totalCaixa))
                .setPositiveButton("OK", (dialog, which) -> resetarCaixa())
                .setCancelable(false)
                .show();
    }
    
    private void resetarCaixa() {
        totalCaixa = 0.0;
        savePreferences();
        updateUI();
        Toast.makeText(this, "Caixa resetado para novo dia!", Toast.LENGTH_SHORT).show();
    }
    
    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOTAL_CAIXA, String.valueOf(totalCaixa));
        editor.putString(KEY_DATA_CAIXA, dataAtual);
        editor.apply();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Recarregar serviços quando voltar de outras telas
    }
}
