# Lava Rápido - Aplicativo de Controle de Caixa

Um aplicativo Android simples e funcional para controle de caixa de lava-rápido, funcionando 100% offline.

## 🚀 Funcionalidades

### ✅ Tabela de Preços Personalizada
- Cadastrar novos serviços com nome e valor
- Editar serviços existentes
- Excluir serviços não utilizados
- Serviços padrão pré-cadastrados (Lavagem Simples, Lavagem Completa, Enceramento, Higienização Interna)

### 💰 Registro de Vendas Diárias
- Lista visual de todos os serviços disponíveis
- Botão "Adicionar" para cada serviço
- Total do caixa atualizado automaticamente
- Feedback visual ao adicionar serviços

### 🔒 Fechamento de Caixa
- Botão "Fechar Caixa" destacado
- Confirmação antes do fechamento
- Salva automaticamente no histórico com data
- Reseta o caixa para o próximo dia

### 📊 Histórico Diário
- Lista de todos os fechamentos por data
- Total acumulado de todos os fechamentos
- Ordenação por data (mais recentes primeiro)
- Visualização clara dos valores

### 🔄 Controle Automático de Datas
- Detecta automaticamente mudança de data
- Reseta o caixa para R$ 0,00 em novo dia
- Mantém histórico anterior preservado
- Salva estado atual durante o uso

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **Framework**: Android SDK
- **Banco de Dados**: SQLite (local, sem necessidade de internet)
- **Interface**: Material Design Components
- **Armazenamento**: SharedPreferences para dados temporários

## 📱 Interface

### Tela Principal
- Header com nome do aplicativo
- Data atual
- Total do caixa em destaque
- Lista de serviços com botões de adicionar
- Botões para gerenciar serviços e ver histórico
- Botão destacado para fechar caixa

### Tela de Gerenciamento de Serviços
- Formulário para adicionar/editar serviços
- Lista de serviços com botões editar/excluir
- Validação de campos obrigatórios

### Tela de Histórico
- Total acumulado em destaque
- Lista cronológica de fechamentos
- Valores bem visíveis

## 🔧 Como Compilar

1. **Pré-requisitos:**
   - Android Studio instalado
   - Android SDK com API level 21 ou superior
   - Java 8 ou superior

2. **Passos:**
   ```bash
   # Abrir o Android Studio
   # File -> Open -> Selecionar a pasta LavaRapidoApp
   # Aguardar sincronização do Gradle
   # Build -> Make Project
   # Run -> Run 'app'
   ```

3. **Instalação no dispositivo:**
   - Conectar dispositivo Android via USB
   - Ativar "Depuração USB" no dispositivo
   - Executar o projeto pelo Android Studio
   - Ou gerar APK: Build -> Build Bundle(s) / APK(s) -> Build APK(s)

## 📦 Estrutura do Projeto

```
LavaRapidoApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/lavarapido/
│   │   │   ├── MainActivity.java          # Tela principal
│   │   │   ├── ServicosActivity.java      # Gerenciamento de serviços
│   │   │   ├── HistoricoActivity.java     # Histórico de fechamentos
│   │   │   ├── DatabaseHelper.java        # Gerenciador do SQLite
│   │   │   ├── Servico.java              # Modelo de dados - Serviço
│   │   │   ├── Fechamento.java           # Modelo de dados - Fechamento
│   │   │   ├── ServicosAdapter.java       # Adapter para lista principal
│   │   │   ├── ServicosGerenciarAdapter.java # Adapter para gerenciamento
│   │   │   └── FechamentosAdapter.java    # Adapter para histórico
│   │   ├── res/
│   │   │   ├── layout/                   # Layouts das telas
│   │   │   ├── values/                   # Cores, strings, estilos
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## 💡 Como Usar

1. **Primeiro uso:**
   - Abrir o aplicativo
   - Serviços padrão já estarão disponíveis
   - Começar a registrar vendas clicando em "Adicionar" nos serviços

2. **Registrar venda:**
   - Selecionar o serviço prestado
   - Clicar em "Adicionar"
   - Ver o valor sendo somado ao total do caixa

3. **Gerenciar serviços:**
   - Clicar em "Gerenciar Serviços"
   - Adicionar novos serviços preenchendo nome e preço
   - Editar serviços existentes clicando em "Editar"
   - Excluir serviços não utilizados

4. **Fechar o caixa:**
   - Ao final do dia, clicar em "FECHAR CAIXA"
   - Confirmar o fechamento
   - O total será salvo no histórico
   - Caixa será zerado para o próximo dia

5. **Ver histórico:**
   - Clicar em "Histórico"
   - Ver total acumulado e fechamentos por data

## 🎯 Características Especiais

- **100% Offline**: Funciona sem conexão com internet
- **Interface Simples**: Botões grandes, textos legíveis
- **Automático**: Detecta novo dia e reseta caixa automaticamente
- **Seguro**: Confirmações para ações importantes
- **Persistente**: Dados salvos no banco local SQLite

## 🐛 Solução de Problemas

- **App não inicia**: Verificar se o dispositivo tem Android 5.0+ (API 21)
- **Dados perdidos**: Verificar se não foi feita limpeza de dados do app
- **Total errado**: Verificar se não mudou a data do dispositivo

## 📄 Licença

Este projeto foi desenvolvido para uso livre e educacional.

---
**Desenvolvido para simplificar o controle de caixa de lava-rápidos** 🚗💨
