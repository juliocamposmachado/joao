# LavaRápido - Sistema Web de Gestão

Sistema completo de gestão para lava-jatos desenvolvido em HTML, CSS e JavaScript com armazenamento local (LocalStorage).

## 🚀 Funcionalidades

### Dashboard
- **Estatísticas em tempo real**
  - Faturamento do dia
  - Número de serviços realizados hoje
  - Total de clientes cadastrados  
  - Faturamento do mês atual
- **Atividades recentes** - Últimas 5 vendas realizadas

### Gestão de Clientes
- ✅ Cadastro completo (nome, telefone, email, veículo)
- ✅ Edição e exclusão
- ✅ Busca por nome, telefone, email ou veículo
- ✅ Listagem com paginação automática

### Gestão de Serviços  
- ✅ Cadastro de serviços (nome, descrição, preço, tempo)
- ✅ Edição e exclusão
- ✅ Serviços padrões pré-cadastrados
- ✅ Preços flexíveis

### Registro de Vendas
- ✅ Associação cliente + serviço
- ✅ Data e hora personalizáveis
- ✅ Valor automático baseado no serviço
- ✅ Campo para observações
- ✅ Status de vendas (concluído, pendente, cancelado)

### Relatórios Gerenciais
- 📊 **Faturamento por período** - Total, quantidade e ticket médio
- 📊 **Serviços mais solicitados** - Ranking com faturamento
- 📊 **Top 10 clientes frequentes** - Visitas e valor gasto

## 💻 Como Usar

### 1. Instalação
Não há instalação necessária! Basta abrir o arquivo `index.html` no navegador.

### 2. Primeiro Acesso
- O sistema criará automaticamente 4 serviços padrões
- Dashboard iniciará zerado
- Comece cadastrando seus primeiros clientes

### 3. Fluxo Recomendado
1. **Cadastre clientes** na aba "Clientes"
2. **Configure serviços** na aba "Serviços" (ou use os padrões)
3. **Registre vendas** na aba "Vendas" 
4. **Acompanhe resultados** no Dashboard
5. **Gere relatórios** na aba "Relatórios"

### 4. Funcionalidades Avançadas
- **Busca de clientes**: Digite qualquer informação na barra de busca
- **Edição rápida**: Clique em "Editar" em qualquer tabela
- **Relatórios filtrados**: Use as datas para relatórios específicos
- **Responsivo**: Funciona perfeitamente no celular

## 💾 Armazenamento de Dados

Os dados são salvos automaticamente no **LocalStorage** do navegador:
- ✅ Não precisa de internet após carregar
- ✅ Dados persistem ao fechar/abrir navegador  
- ✅ Privacidade total - dados ficam só no seu computador
- ⚠️ **Backup**: Dados são perdidos se limpar histórico/cookies

### Fazer Backup dos Dados
Para preservar seus dados:
1. Abra o Console do navegador (F12)
2. Execute: `localStorage.getItem('lavarapido_clientes')`
3. Copie e salve o resultado em arquivo texto
4. Repita para `lavarapido_servicos` e `lavarapido_vendas`

### Restaurar Backup
1. Abra o Console do navegador (F12)  
2. Execute: `localStorage.setItem('lavarapido_clientes', '[DADOS_SALVOS]')`
3. Recarregue a página

## 🎨 Personalização

### Cores e Visual
Edite o arquivo `css/style.css` para:
- Alterar cores principais (procure por `#667eea` e `#764ba2`)
- Modificar fontes (propriedade `font-family`)
- Ajustar tamanhos e espaçamentos

### Serviços Padrões
Edite a função `getDefaultServicos()` no arquivo `js/app.js` para personalizar os serviços iniciais.

## 📱 Compatibilidade

- ✅ **Desktop**: Chrome, Firefox, Safari, Edge
- ✅ **Mobile**: Totalmente responsivo
- ✅ **Offline**: Funciona sem internet
- ✅ **PWA Ready**: Pode ser instalado como app

## 🔧 Estrutura de Arquivos

```
LavaRapidoWeb/
├── index.html          # Página principal
├── css/
│   └── style.css       # Estilos da aplicação  
├── js/
│   └── app.js          # Lógica da aplicação
├── assets/             # Imagens (vazio por ora)
└── README.md          # Este arquivo
```

## 🚨 Limitações

- Máximo de itens limitado pela memória do navegador
- Não há sincronização entre dispositivos  
- Sem backup automático
- Não há controle de acesso/usuários

## 🆘 Solução de Problemas

### Dados não aparecem
1. Verifique se JavaScript está habilitado
2. Abra Console (F12) e veja se há erros
3. Teste em outro navegador

### Performance lenta  
1. Limite-se a ~1000 vendas para melhor performance
2. Faça backup e limpe dados antigos periodicamente

### Backup dos dados
É recomendado fazer backup regular dos dados importantes, pois eles ficam apenas no navegador local.

## 📞 Suporte

Este é um sistema independente. Para suporte:
1. Verifique este README
2. Teste em navegador atualizado
3. Consulte logs no Console (F12)

---

**Desenvolvido para pequenos negócios que precisam de gestão simples e eficiente! 🚗💧**
