# 🚗 LavaRápido - Sistema de Gestão de Lava-Jato

Sistema completo de gestão para lava-jatos desenvolvido por **João Lucas** da **Like Look Solutions**.

## 🆕 NOVIDADE: Chat IA Integrado!
Agora com assistente virtual especializado em lava rápido usando a tecnologia **Google Gemini**!

## 🚀 Funcionalidades

### 📊 Dashboard
- Faturamento diário e mensal
- Quantidade de serviços realizados
- Total de clientes cadastrados
- Atividades recentes

### 👥 Gestão de Clientes
- Cadastro completo de clientes
- Informações de contato e veículo
- Busca e filtragem
- Histórico de serviços

### 🔧 Gestão de Serviços
- Catálogo de serviços oferecidos
- Preços e tempo de execução
- Descrições detalhadas
- Controle de ativo/inativo

### 💰 Controle de Vendas
- Registro de vendas/serviços
- Controle de status
- Associação cliente-serviço
- Histórico completo

### 📈 Relatórios Avançados
- Faturamento por período
- Serviços mais solicitados
- Clientes mais frequentes
- Exportação em Excel e PDF

### 🤖 Chat IA - Assistente Virtual
- **Especialista em lava rápido** com IA do Google Gemini
- Análises inteligentes de dados em tempo real
- Consultoria personalizada sobre:
  - 💰 Análise de caixa e faturamento
  - 📊 Interpretação de relatórios
  - 📈 Estratégias de vendas
  - 🚗 Dicas para melhorar o serviço
  - 👥 Gestão de clientes
  - ⚙️ Otimização de processos

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
- **Chat IA**: Assistente virtual para análises e consultoria
- **Responsivo**: Funciona perfeitamente no celular

### 5. Como Usar o Chat IA
1. **Acesse a aba "Chat IA"**
2. **Use perguntas rápidas** - Clique nos botões para consultas comuns
3. **Digite perguntas personalizadas** - O assistente entende contexto
4. **Receba insights detalhados** - Análises baseadas nos seus dados
5. **Implemente sugestões** - Dicas práticas para melhorar o negócio

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
- ✅ **Offline**: Funciona sem internet (exceto Chat IA)
- ✅ **PWA Ready**: Pode ser instalado como app

## 🛠️ Tecnologias Utilizadas

- **HTML5** - Estrutura semântica
- **CSS3** - Design responsivo e moderno
- **JavaScript ES6+** - Lógica e interatividade
- **LocalStorage** - Persistência de dados local
- **Google Gemini API** - Inteligência Artificial
- **Font Awesome** - Ícones
- **XLSX.js** - Exportação Excel
- **jsPDF** - Exportação PDF

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

## 👨‍💻 Desenvolvedor

**João Lucas**
- 📱 Telefone: +55 44 8833-2218
- 🏢 Empresa: Like Look Solutions
- 📞 Comercial: +55 11 99294-6628
- 🌐 Site: [likelook.wixsite.com/solutions](https://likelook.wixsite.com/solutions)

### 🏢 Like Look Solutions
**CNPJ:** 36.992.198/0001-84

**Especialidades:**
- ✅ Soluções de TI
- ✅ Field Support
- ✅ Alocação de Profissionais
- ✅ Cabling
- ✅ Rollout
- ✅ Inventário

## 🔄 Versionamento

- **v1.0.0** - Versão inicial com funcionalidades básicas
- **v1.1.0** - Adição do sistema de relatórios
- **v1.2.0** - Implementação do Chat IA com Google Gemini
- **v1.3.0** - Melhorias na interface e responsividade

## 🚀 Próximas Funcionalidades

- [ ] Integração com WhatsApp Business
- [ ] Sistema de notificações push
- [ ] Backup em nuvem
- [ ] Multi-usuário
- [ ] Dashboard em tempo real
- [ ] Integração com sistemas de pagamento

## 📞 Suporte

Este é um sistema independente. Para suporte:
1. Verifique este README
2. Teste em navegador atualizado
3. Consulte logs no Console (F12)
4. Entre em contato: +55 44 8833-2218

## 📄 Licença

Sistema desenvolvido exclusivamente para uso comercial.
© 2024 Like Look Solutions - Todos os direitos reservados.

---

**Desenvolvido com ❤️ por João Lucas - Like Look Solutions**
**Sistema completo para pequenos negócios que precisam de gestão simples e eficiente! 🚗💧**
