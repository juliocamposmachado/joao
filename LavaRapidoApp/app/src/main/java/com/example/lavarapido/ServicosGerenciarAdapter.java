package com.example.lavarapido;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServicosGerenciarAdapter extends RecyclerView.Adapter<ServicosGerenciarAdapter.ServicoGerenciarViewHolder> {
    
    private List<Servico> servicos;
    private OnServicoActionListener listener;
    
    public interface OnServicoActionListener {
        void onEditarClick(Servico servico);
        void onExcluirClick(Servico servico);
    }
    
    public ServicosGerenciarAdapter(List<Servico> servicos, OnServicoActionListener listener) {
        this.servicos = servicos;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ServicoGerenciarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servico_gerenciar, parent, false);
        return new ServicoGerenciarViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ServicoGerenciarViewHolder holder, int position) {
        Servico servico = servicos.get(position);
        holder.bind(servico);
    }
    
    @Override
    public int getItemCount() {
        return servicos.size();
    }
    
    public void updateServicos(List<Servico> newServicos) {
        this.servicos = newServicos;
        notifyDataSetChanged();
    }
    
    class ServicoGerenciarViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome, tvPreco;
        private Button btnEditar, btnExcluir;
        
        public ServicoGerenciarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_servico_gerenciar);
            tvPreco = itemView.findViewById(R.id.tv_preco_servico_gerenciar);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnExcluir = itemView.findViewById(R.id.btn_excluir);
        }
        
        public void bind(Servico servico) {
            tvNome.setText(servico.getNome());
            tvPreco.setText(String.format("R$ %.2f", servico.getPreco()));
            
            btnEditar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditarClick(servico);
                }
            });
            
            btnExcluir.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onExcluirClick(servico);
                }
            });
        }
    }
}
