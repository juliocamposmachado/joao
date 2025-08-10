package com.example.lavarapido;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FechamentosAdapter extends RecyclerView.Adapter<FechamentosAdapter.FechamentoViewHolder> {
    
    private List<Fechamento> fechamentos;
    
    public FechamentosAdapter(List<Fechamento> fechamentos) {
        this.fechamentos = fechamentos;
    }
    
    @NonNull
    @Override
    public FechamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fechamento, parent, false);
        return new FechamentoViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull FechamentoViewHolder holder, int position) {
        Fechamento fechamento = fechamentos.get(position);
        holder.bind(fechamento);
    }
    
    @Override
    public int getItemCount() {
        return fechamentos.size();
    }
    
    public void updateFechamentos(List<Fechamento> newFechamentos) {
        this.fechamentos = newFechamentos;
        notifyDataSetChanged();
    }
    
    class FechamentoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvData, tvValor;
        
        public FechamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tv_data_fechamento);
            tvValor = itemView.findViewById(R.id.tv_valor_fechamento);
        }
        
        public void bind(Fechamento fechamento) {
            tvData.setText(fechamento.getData());
            tvValor.setText(String.format("R$ %.2f", fechamento.getTotal()));
        }
    }
}
