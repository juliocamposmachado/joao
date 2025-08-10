package com.example.lavarapido;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    
    private TextView tvTotalGeral, tvSemHistorico;
    private RecyclerView rvHistorico;
    private Button btnVoltar;
    
    private DatabaseHelper dbHelper;
    private FechamentosAdapter adapter;
    private List<Fechamento> fechamentos;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        
        initializeViews();
        initializeDatabase();
        setupRecyclerView();
        setupButtons();
        loadHistorico();
    }
    
    private void initializeViews() {
        tvTotalGeral = findViewById(R.id.tv_total_geral);
        tvSemHistorico = findViewById(R.id.tv_sem_historico);
        rvHistorico = findViewById(R.id.rv_historico);
        btnVoltar = findViewById(R.id.btn_voltar_historico);
    }
    
    private void initializeDatabase() {
        dbHelper = new DatabaseHelper(this);
    }
    
    private void setupRecyclerView() {
        rvHistorico.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setupButtons() {
        btnVoltar.setOnClickListener(v -> finish());
    }
    
    private void loadHistorico() {
        fechamentos = dbHelper.getAllFechamentos();
        
        if (fechamentos.isEmpty()) {
            // Mostrar mensagem quando não há histórico
            rvHistorico.setVisibility(View.GONE);
            tvSemHistorico.setVisibility(View.VISIBLE);
            tvTotalGeral.setText("R$ 0,00");
        } else {
            // Mostrar lista de fechamentos
            tvSemHistorico.setVisibility(View.GONE);
            rvHistorico.setVisibility(View.VISIBLE);
            
            if (adapter == null) {
                adapter = new FechamentosAdapter(fechamentos);
                rvHistorico.setAdapter(adapter);
            } else {
                adapter.updateFechamentos(fechamentos);
            }
            
            // Calcular e mostrar total geral
            double totalGeral = 0.0;
            for (Fechamento fechamento : fechamentos) {
                totalGeral += fechamento.getTotal();
            }
            tvTotalGeral.setText(String.format("R$ %.2f", totalGeral));
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadHistorico(); // Recarregar histórico quando voltar para a tela
    }
}
