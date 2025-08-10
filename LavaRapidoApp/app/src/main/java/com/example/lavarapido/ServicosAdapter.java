package com.example.lavarapido;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.ServicoViewHolder> {
    
    private List<Servico> servicos;
    private OnServicoClickListener listener;
    
    public interface OnServicoClickListener {
        void onServicoClick(Servico servico);
    }
    
    public ServicosAdapter(List<Servico> servicos, OnServicoClickListener listener) {
        this.servicos = servicos;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ServicoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servico, parent, false);
        return new ServicoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ServicoViewHolder holder, int position) {
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
    
    class ServicoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome, tvPreco;
        private Button btnAdicionar;
        
        public ServicoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_servico);
            tvPreco = itemView.findViewById(R.id.tv_preco_servico);
            btnAdicionar = itemView.findViewById(R.id.btn_adicionar);
        }
        
        public void bind(Servico servico) {
            tvNome.setText(servico.getNome());
            tvPreco.setText(String.format("R$ %.2f", servico.getPreco()));
            
            btnAdicionar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onServicoClick(servico);
                }
            });
        }
    }
}
