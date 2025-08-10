// Aplicação LavaRápido - Gestão de Lava-Jato
class LavaRapidoApp {
    constructor() {
        this.clientes = this.loadFromStorage('clientes') || [];
        this.servicos = this.loadFromStorage('servicos') || this.getDefaultServicos();
        this.vendas = this.loadFromStorage('vendas') || [];
        this.currentEditId = null;
        this.currentEditType = null;
        
        this.initializeApp();
    }
    
    // Inicialização da aplicação
    initializeApp() {
        this.updateDashboard();
        this.renderClientes();
        this.renderServicos();
        this.renderVendas();
        this.setupEventListeners();
        this.updateFormSelects();
        
        // Se não há serviços, criar alguns padrões
        if (this.servicos.length === 0) {
            this.servicos = this.getDefaultServicos();
            this.saveToStorage('servicos', this.servicos);
            this.renderServicos();
        }
    }
    
    // Serviços padrões
    getDefaultServicos() {
        return [
            { id: 1, nome: 'Lavagem Simples', descricao: 'Lavagem externa básica', preco: 15.00, tempo: 30 },
            { id: 2, nome: 'Lavagem Completa', descricao: 'Lavagem externa e interna', preco: 25.00, tempo: 45 },
            { id: 3, nome: 'Enceramento', descricao: 'Lavagem + cera protetora', preco: 35.00, tempo: 60 },
            { id: 4, nome: 'Detalhamento', descricao: 'Lavagem completa + detalhes', preco: 50.00, tempo: 90 }
        ];
    }
    
    // LocalStorage helpers
    saveToStorage(key, data) {
        try {
            localStorage.setItem(`lavarapido_${key}`, JSON.stringify(data));
        } catch (e) {
            console.error('Erro ao salvar no localStorage:', e);
        }
    }
    
    loadFromStorage(key) {
        try {
            const data = localStorage.getItem(`lavarapido_${key}`);
            return data ? JSON.parse(data) : null;
        } catch (e) {
            console.error('Erro ao carregar do localStorage:', e);
            return null;
        }
    }
    
    // Event Listeners
    setupEventListeners() {
        // Formulários
        document.getElementById('clienteForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveCliente();
        });
        
        document.getElementById('servicoForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveServico();
        });
        
        document.getElementById('vendaForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveVenda();
        });
        
        // Update valor quando seleciona serviço
        document.getElementById('vendaServico').addEventListener('change', () => {
            this.updateVendaValor();
        });
        
        // Data padrão no formulário de venda
        const now = new Date();
        now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
        document.getElementById('vendaData').value = now.toISOString().slice(0, 16);
    }
    
    // Navegação entre abas
    showTab(tabName) {
        // Esconder todas as abas
        document.querySelectorAll('.tab-content').forEach(tab => {
            tab.classList.remove('active');
        });
        
        // Remover active de todos os botões
        document.querySelectorAll('.tab-btn').forEach(btn => {
            btn.classList.remove('active');
        });
        
        // Mostrar aba selecionada
        document.getElementById(tabName).classList.add('active');
        event.target.classList.add('active');
        
        // Atualizar dashboard se necessário
        if (tabName === 'dashboard') {
            this.updateDashboard();
        }
    }
    
    // Dashboard
    updateDashboard() {
        const hoje = new Date().toDateString();
        const mesAtual = new Date().getMonth();
        const anoAtual = new Date().getFullYear();
        
        // Vendas de hoje
        const vendasHoje = this.vendas.filter(venda => 
            new Date(venda.data).toDateString() === hoje && 
            venda.status !== 'cancelado'
        );
        
        // Vendas do mês
        const vendasMes = this.vendas.filter(venda => {
            const dataVenda = new Date(venda.data);
            return dataVenda.getMonth() === mesAtual && 
                   dataVenda.getFullYear() === anoAtual && 
                   venda.status !== 'cancelado';
        });
        
        // Atualizar estatísticas
        const faturamentoHoje = vendasHoje.reduce((total, venda) => total + venda.valor, 0);
        const faturamentoMes = vendasMes.reduce((total, venda) => total + venda.valor, 0);
        
        document.getElementById('faturamentoHoje').textContent = this.formatCurrency(faturamentoHoje);
        document.getElementById('servicosHoje').textContent = vendasHoje.length;
        document.getElementById('totalClientes').textContent = this.clientes.length;
        document.getElementById('faturamentoMes').textContent = this.formatCurrency(faturamentoMes);
        
        // Atividades recentes
        this.renderAtividadesRecentes();
    }
    
    renderAtividadesRecentes() {
        const container = document.getElementById('atividadesRecentes');
        const vendasRecentes = this.vendas
            .sort((a, b) => new Date(b.data) - new Date(a.data))
            .slice(0, 5);
        
        if (vendasRecentes.length === 0) {
            container.innerHTML = '<p class="no-data">Nenhuma atividade registrada</p>';
            return;
        }
        
        container.innerHTML = vendasRecentes.map(venda => {
            const cliente = this.clientes.find(c => c.id === venda.clienteId);
            const servico = this.servicos.find(s => s.id === venda.servicoId);
            const data = new Date(venda.data);
            
            return `
                <div class="activity-item">
                    <strong>${cliente?.nome || 'Cliente removido'}</strong> - ${servico?.nome || 'Serviço removido'}
                    <div class="activity-time">${this.formatDateTime(data)}</div>
                    <div>Valor: ${this.formatCurrency(venda.valor)}</div>
                </div>
            `;
        }).join('');
    }
    
    // Gestão de Clientes
    showClienteForm(clienteId = null) {
        this.currentEditId = clienteId;
        this.currentEditType = 'cliente';
        
        const modal = document.getElementById('clienteModal');
        const form = document.getElementById('clienteForm');
        const title = document.getElementById('clienteModalTitle');
        
        if (clienteId) {
            const cliente = this.clientes.find(c => c.id === clienteId);
            title.textContent = 'Editar Cliente';
            document.getElementById('clienteNome').value = cliente.nome;
            document.getElementById('clienteTelefone').value = cliente.telefone;
            document.getElementById('clienteEmail').value = cliente.email || '';
            document.getElementById('clienteVeiculo').value = cliente.veiculo || '';
        } else {
            title.textContent = 'Novo Cliente';
            form.reset();
        }
        
        modal.classList.add('show');
    }
    
    saveCliente() {
        const nome = document.getElementById('clienteNome').value;
        const telefone = document.getElementById('clienteTelefone').value;
        const email = document.getElementById('clienteEmail').value;
        const veiculo = document.getElementById('clienteVeiculo').value;
        
        if (!nome || !telefone) {
            alert('Nome e telefone são obrigatórios!');
            return;
        }
        
        const clienteData = {
            nome,
            telefone,
            email,
            veiculo,
            dataCadastro: new Date().toISOString()
        };
        
        if (this.currentEditId) {
            // Editar cliente existente
            const index = this.clientes.findIndex(c => c.id === this.currentEditId);
            this.clientes[index] = { ...this.clientes[index], ...clienteData };
        } else {
            // Novo cliente
            clienteData.id = Date.now();
            this.clientes.push(clienteData);
        }
        
        this.saveToStorage('clientes', this.clientes);
        this.renderClientes();
        this.updateFormSelects();
        this.updateDashboard();
        this.closeModal('clienteModal');
        
        this.showMessage('Cliente salvo com sucesso!', 'success');
    }
    
    renderClientes() {
        const tbody = document.getElementById('clientesTableBody');
        
        if (this.clientes.length === 0) {
            tbody.innerHTML = '<tr class="no-data"><td colspan="5">Nenhum cliente cadastrado</td></tr>';
            return;
        }
        
        tbody.innerHTML = this.clientes.map(cliente => `
            <tr>
                <td>${cliente.nome}</td>
                <td>${cliente.telefone}</td>
                <td>${cliente.email || '-'}</td>
                <td>${cliente.veiculo || '-'}</td>
                <td>
                    <button class="btn btn-small btn-secondary" onclick="app.showClienteForm(${cliente.id})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-small btn-danger" onclick="app.deleteCliente(${cliente.id})">
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </td>
            </tr>
        `).join('');
    }
    
    deleteCliente(id) {
        if (confirm('Tem certeza que deseja excluir este cliente?')) {
            this.clientes = this.clientes.filter(c => c.id !== id);
            this.saveToStorage('clientes', this.clientes);
            this.renderClientes();
            this.updateFormSelects();
            this.updateDashboard();
            this.showMessage('Cliente excluído com sucesso!', 'success');
        }
    }
    
    searchClientes() {
        const search = document.getElementById('searchCliente').value.toLowerCase();
        const tbody = document.getElementById('clientesTableBody');
        
        const filteredClientes = this.clientes.filter(cliente => 
            cliente.nome.toLowerCase().includes(search) ||
            cliente.telefone.includes(search) ||
            (cliente.email && cliente.email.toLowerCase().includes(search)) ||
            (cliente.veiculo && cliente.veiculo.toLowerCase().includes(search))
        );
        
        if (filteredClientes.length === 0) {
            tbody.innerHTML = '<tr class="no-data"><td colspan="5">Nenhum cliente encontrado</td></tr>';
            return;
        }
        
        tbody.innerHTML = filteredClientes.map(cliente => `
            <tr>
                <td>${cliente.nome}</td>
                <td>${cliente.telefone}</td>
                <td>${cliente.email || '-'}</td>
                <td>${cliente.veiculo || '-'}</td>
                <td>
                    <button class="btn btn-small btn-secondary" onclick="app.showClienteForm(${cliente.id})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-small btn-danger" onclick="app.deleteCliente(${cliente.id})">
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </td>
            </tr>
        `).join('');
    }
    
    // Gestão de Serviços
    showServicoForm(servicoId = null) {
        this.currentEditId = servicoId;
        this.currentEditType = 'servico';
        
        const modal = document.getElementById('servicoModal');
        const form = document.getElementById('servicoForm');
        const title = document.getElementById('servicoModalTitle');
        
        if (servicoId) {
            const servico = this.servicos.find(s => s.id === servicoId);
            title.textContent = 'Editar Serviço';
            document.getElementById('servicoNome').value = servico.nome;
            document.getElementById('servicoDescricao').value = servico.descricao || '';
            document.getElementById('servicoPreco').value = servico.preco;
            document.getElementById('servicoTempo').value = servico.tempo || '';
        } else {
            title.textContent = 'Novo Serviço';
            form.reset();
        }
        
        modal.classList.add('show');
    }
    
    saveServico() {
        const nome = document.getElementById('servicoNome').value;
        const descricao = document.getElementById('servicoDescricao').value;
        const preco = parseFloat(document.getElementById('servicoPreco').value);
        const tempo = parseInt(document.getElementById('servicoTempo').value) || 0;
        
        if (!nome || !preco) {
            alert('Nome e preço são obrigatórios!');
            return;
        }
        
        const servicoData = {
            nome,
            descricao,
            preco,
            tempo
        };
        
        if (this.currentEditId) {
            // Editar serviço existente
            const index = this.servicos.findIndex(s => s.id === this.currentEditId);
            this.servicos[index] = { ...this.servicos[index], ...servicoData };
        } else {
            // Novo serviço
            servicoData.id = Date.now();
            this.servicos.push(servicoData);
        }
        
        this.saveToStorage('servicos', this.servicos);
        this.renderServicos();
        this.updateFormSelects();
        this.closeModal('servicoModal');
        
        this.showMessage('Serviço salvo com sucesso!', 'success');
    }
    
    renderServicos() {
        const tbody = document.getElementById('servicosTableBody');
        
        if (this.servicos.length === 0) {
            tbody.innerHTML = '<tr class="no-data"><td colspan="5">Nenhum serviço cadastrado</td></tr>';
            return;
        }
        
        tbody.innerHTML = this.servicos.map(servico => `
            <tr>
                <td>${servico.nome}</td>
                <td>${servico.descricao || '-'}</td>
                <td>${this.formatCurrency(servico.preco)}</td>
                <td>${servico.tempo ? servico.tempo + ' min' : '-'}</td>
                <td>
                    <button class="btn btn-small btn-secondary" onclick="app.showServicoForm(${servico.id})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-small btn-danger" onclick="app.deleteServico(${servico.id})">
                        <i class="fas fa-trash"></i> Excluir
                    </button>
                </td>
            </tr>
        `).join('');
    }
    
    deleteServico(id) {
        if (confirm('Tem certeza que deseja excluir este serviço?')) {
            this.servicos = this.servicos.filter(s => s.id !== id);
            this.saveToStorage('servicos', this.servicos);
            this.renderServicos();
            this.updateFormSelects();
            this.showMessage('Serviço excluído com sucesso!', 'success');
        }
    }
    
    // Gestão de Vendas
    showVendaForm(vendaId = null) {
        this.currentEditId = vendaId;
        this.currentEditType = 'venda';
        
        const modal = document.getElementById('vendaModal');
        const form = document.getElementById('vendaForm');
        const title = document.getElementById('vendaModalTitle');
        
        if (vendaId) {
            const venda = this.vendas.find(v => v.id === vendaId);
            title.textContent = 'Editar Venda';
            document.getElementById('vendaCliente').value = venda.clienteId;
            document.getElementById('vendaServico').value = venda.servicoId;
            document.getElementById('vendaData').value = new Date(venda.data).toISOString().slice(0, 16);
            document.getElementById('vendaValor').value = venda.valor;
            document.getElementById('vendaObservacoes').value = venda.observacoes || '';
        } else {
            title.textContent = 'Nova Venda';
            form.reset();
            const now = new Date();
            now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
            document.getElementById('vendaData').value = now.toISOString().slice(0, 16);
        }
        
        modal.classList.add('show');
    }
    
    updateVendaValor() {
        const servicoId = parseInt(document.getElementById('vendaServico').value);
        const servico = this.servicos.find(s => s.id === servicoId);
        
        if (servico) {
            document.getElementById('vendaValor').value = servico.preco;
        }
    }
    
    saveVenda() {
        const clienteId = parseInt(document.getElementById('vendaCliente').value);
        const servicoId = parseInt(document.getElementById('vendaServico').value);
        const data = document.getElementById('vendaData').value;
        const valor = parseFloat(document.getElementById('vendaValor').value);
        const observacoes = document.getElementById('vendaObservacoes').value;
        
        if (!clienteId || !servicoId || !data || !valor) {
            alert('Todos os campos obrigatórios devem ser preenchidos!');
            return;
        }
        
        const vendaData = {
            clienteId,
            servicoId,
            data: new Date(data).toISOString(),
            valor,
            observacoes,
            status: 'concluido'
        };
        
        if (this.currentEditId) {
            // Editar venda existente
            const index = this.vendas.findIndex(v => v.id === this.currentEditId);
            this.vendas[index] = { ...this.vendas[index], ...vendaData };
        } else {
            // Nova venda
            vendaData.id = Date.now();
            this.vendas.push(vendaData);
        }
        
        this.saveToStorage('vendas', this.vendas);
        this.renderVendas();
        this.updateDashboard();
        this.closeModal('vendaModal');
        
        this.showMessage('Venda salva com sucesso!', 'success');
    }
    
    renderVendas() {
        const tbody = document.getElementById('vendasTableBody');
        
        if (this.vendas.length === 0) {
            tbody.innerHTML = '<tr class="no-data"><td colspan="6">Nenhuma venda registrada</td></tr>';
            return;
        }
        
        const vendasOrdenadas = this.vendas.sort((a, b) => new Date(b.data) - new Date(a.data));
        
        tbody.innerHTML = vendasOrdenadas.map(venda => {
            const cliente = this.clientes.find(c => c.id === venda.clienteId);
            const servico = this.servicos.find(s => s.id === venda.servicoId);
            
            return `
                <tr>
                    <td>${this.formatDateTime(new Date(venda.data))}</td>
                    <td>${cliente?.nome || 'Cliente removido'}</td>
                    <td>${servico?.nome || 'Serviço removido'}</td>
                    <td>${this.formatCurrency(venda.valor)}</td>
                    <td><span class="status-badge status-${venda.status}">${venda.status}</span></td>
                    <td>
                        <button class="btn btn-small btn-secondary" onclick="app.showVendaForm(${venda.id})">
                            <i class="fas fa-edit"></i> Editar
                        </button>
                        <button class="btn btn-small btn-danger" onclick="app.deleteVenda(${venda.id})">
                            <i class="fas fa-trash"></i> Excluir
                        </button>
                    </td>
                </tr>
            `;
        }).join('');
    }
    
    deleteVenda(id) {
        if (confirm('Tem certeza que deseja excluir esta venda?')) {
            this.vendas = this.vendas.filter(v => v.id !== id);
            this.saveToStorage('vendas', this.vendas);
            this.renderVendas();
            this.updateDashboard();
            this.showMessage('Venda excluída com sucesso!', 'success');
        }
    }
    
    // Relatórios
    gerarRelatorioFaturamento() {
        const dataInicio = document.getElementById('dataInicio').value;
        const dataFim = document.getElementById('dataFim').value;
        
        if (!dataInicio || !dataFim) {
            alert('Selecione o período para o relatório');
            return;
        }
        
        const inicio = new Date(dataInicio);
        const fim = new Date(dataFim);
        fim.setHours(23, 59, 59, 999);
        
        const vendasPeriodo = this.vendas.filter(venda => {
            const dataVenda = new Date(venda.data);
            return dataVenda >= inicio && dataVenda <= fim && venda.status !== 'cancelado';
        });
        
        const total = vendasPeriodo.reduce((sum, venda) => sum + venda.valor, 0);
        const container = document.getElementById('relatorioFaturamento');
        
        container.innerHTML = `
            <h4>Período: ${this.formatDate(inicio)} a ${this.formatDate(fim)}</h4>
            <p><strong>Total de Vendas:</strong> ${vendasPeriodo.length}</p>
            <p><strong>Faturamento Total:</strong> ${this.formatCurrency(total)}</p>
            <p><strong>Ticket Médio:</strong> ${this.formatCurrency(vendasPeriodo.length ? total / vendasPeriodo.length : 0)}</p>
        `;
    }
    
    gerarRelatorioServicos() {
        const servicosCount = {};
        
        this.vendas.forEach(venda => {
            if (venda.status !== 'cancelado') {
                const servico = this.servicos.find(s => s.id === venda.servicoId);
                if (servico) {
                    if (!servicosCount[servico.nome]) {
                        servicosCount[servico.nome] = { count: 0, valor: 0 };
                    }
                    servicosCount[servico.nome].count++;
                    servicosCount[servico.nome].valor += venda.valor;
                }
            }
        });
        
        const servicosOrdenados = Object.entries(servicosCount)
            .sort(([,a], [,b]) => b.count - a.count);
        
        const container = document.getElementById('relatorioServicos');
        
        if (servicosOrdenados.length === 0) {
            container.innerHTML = '<p>Nenhum serviço encontrado</p>';
            return;
        }
        
        container.innerHTML = `
            <table style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr>
                        <th style="border: 1px solid #ddd; padding: 8px;">Serviço</th>
                        <th style="border: 1px solid #ddd; padding: 8px;">Quantidade</th>
                        <th style="border: 1px solid #ddd; padding: 8px;">Faturamento</th>
                    </tr>
                </thead>
                <tbody>
                    ${servicosOrdenados.map(([nome, dados]) => `
                        <tr>
                            <td style="border: 1px solid #ddd; padding: 8px;">${nome}</td>
                            <td style="border: 1px solid #ddd; padding: 8px;">${dados.count}</td>
                            <td style="border: 1px solid #ddd; padding: 8px;">${this.formatCurrency(dados.valor)}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
    }
    
    gerarRelatorioClientes() {
        const clientesCount = {};
        
        this.vendas.forEach(venda => {
            if (venda.status !== 'cancelado') {
                const cliente = this.clientes.find(c => c.id === venda.clienteId);
                if (cliente) {
                    if (!clientesCount[cliente.nome]) {
                        clientesCount[cliente.nome] = { count: 0, valor: 0 };
                    }
                    clientesCount[cliente.nome].count++;
                    clientesCount[cliente.nome].valor += venda.valor;
                }
            }
        });
        
        const clientesOrdenados = Object.entries(clientesCount)
            .sort(([,a], [,b]) => b.count - a.count)
            .slice(0, 10);
        
        const container = document.getElementById('relatorioClientes');
        
        if (clientesOrdenados.length === 0) {
            container.innerHTML = '<p>Nenhum cliente encontrado</p>';
            return;
        }
        
        container.innerHTML = `
            <table style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr>
                        <th style="border: 1px solid #ddd; padding: 8px;">Cliente</th>
                        <th style="border: 1px solid #ddd; padding: 8px;">Visitas</th>
                        <th style="border: 1px solid #ddd; padding: 8px;">Total Gasto</th>
                    </tr>
                </thead>
                <tbody>
                    ${clientesOrdenados.map(([nome, dados]) => `
                        <tr>
                            <td style="border: 1px solid #ddd; padding: 8px;">${nome}</td>
                            <td style="border: 1px solid #ddd; padding: 8px;">${dados.count}</td>
                            <td style="border: 1px solid #ddd; padding: 8px;">${this.formatCurrency(dados.valor)}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
    }
    
    // Helpers
    updateFormSelects() {
        // Atualizar select de clientes
        const clienteSelect = document.getElementById('vendaCliente');
        clienteSelect.innerHTML = '<option value="">Selecione um cliente</option>' +
            this.clientes.map(cliente => 
                `<option value="${cliente.id}">${cliente.nome}</option>`
            ).join('');
        
        // Atualizar select de serviços
        const servicoSelect = document.getElementById('vendaServico');
        servicoSelect.innerHTML = '<option value="">Selecione um serviço</option>' +
            this.servicos.map(servico => 
                `<option value="${servico.id}">${servico.nome} - ${this.formatCurrency(servico.preco)}</option>`
            ).join('');
    }
    
    formatCurrency(value) {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(value);
    }
    
    formatDate(date) {
        return new Intl.DateTimeFormat('pt-BR').format(date);
    }
    
    formatDateTime(date) {
        return new Intl.DateTimeFormat('pt-BR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        }).format(date);
    }
    
    closeModal(modalId) {
        document.getElementById(modalId).classList.remove('show');
        this.currentEditId = null;
        this.currentEditType = null;
    }
    
    showMessage(message, type) {
        // Criar elemento de mensagem
        const messageDiv = document.createElement('div');
        messageDiv.className = `message message-${type}`;
        messageDiv.textContent = message;
        
        // Adicionar ao início do container
        const container = document.querySelector('.container');
        container.insertBefore(messageDiv, container.firstChild);
        
        // Remover após 3 segundos
        setTimeout(() => {
            if (messageDiv.parentNode) {
                messageDiv.parentNode.removeChild(messageDiv);
            }
        }, 3000);
    }
}

// Funções globais para serem chamadas pelo HTML
let app;

function showTab(tabName) {
    app.showTab(tabName);
}

function showClienteForm(id) {
    app.showClienteForm(id);
}

function showServicoForm(id) {
    app.showServicoForm(id);
}

function showVendaForm(id) {
    app.showVendaForm(id);
}

function closeModal(modalId) {
    app.closeModal(modalId);
}

function searchClientes() {
    app.searchClientes();
}

function gerarRelatorioFaturamento() {
    app.gerarRelatorioFaturamento();
}

function gerarRelatorioServicos() {
    app.gerarRelatorioServicos();
}

function gerarRelatorioClientes() {
    app.gerarRelatorioClientes();
}

// Inicializar aplicação quando DOM estiver carregado
document.addEventListener('DOMContentLoaded', function() {
    app = new LavaRapidoApp();
});

// Fechar modal quando clicar fora dele
window.addEventListener('click', function(event) {
    if (event.target.classList.contains('modal')) {
        event.target.classList.remove('show');
    }
});
