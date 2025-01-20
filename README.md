# **Movie Catalog App**

Este é um aplicativo Android que consome a [OMDB API](https://www.omdbapi.com/) para permitir a
busca de filmes. O projeto utiliza tecnologias modernas como Kotlin, Retrofit, Dagger e Arquitetura
MVVM, com foco em clean architecture, boas práticas e uma interface de usuário intuitiva.

---

## ✨ **Funcionalidades**

- Busca de filmes por título usando a OMDB API.
- Exibição dos resultados retornados pela api, como: nome do filme, ano de lançamento e pôster.
- Exibição de informações detalhadas dos filmes, ao clicar em uma opção na listagem, como:
  descrição, premiações e diretores.

---

## 🏗️ **Arquitetura**

O projeto segue o padrão **MVVM (Model-View-ViewModel)** para manter a separação de
responsabilidades e facilitar a testabilidade.

### Componentes principais:

- **ViewModel:** Responsável por gerenciar os dados da interface e a lógica de negócios.
- **Repository:** Interage com a API e gerencia os dados.
- **Retrofit:** Realiza as chamadas de rede.
- **Dagger:** Gerencia a injeção de dependências.

---

## 🛠️ **Tecnologias Utilizadas**

- **Kotlin:** Linguagem de desenvolvimento.
- **Retrofit:** Para comunicação com APIs RESTful.
- **Dagger:** Para injeção de dependências.
- **Coroutines:** Para chamadas assíncronas e manipulação de fluxo de dados.
- **XML:** Para criação de layouts.
- **Jetpack Components:**
    - ViewModel
    - LiveData
    - RecyclerView
- **Glide:** Para carregamento de imagens.
- **Navigation:** Para navegação entre as telas.
- **Material Design:** Para uma interface moderna e consistente.
- **Mockk:** Para criação de objetos simulados em testes de unidade.
- **JUnit:** Framework para testes de unidade e execução de casos de teste.

---

## 🚀 **Instalação e Configuração**

### Pré-requisitos:

- Android Studio.
- Gradle configurado corretamente.
- Uma chave de API da [OMDB API](https://www.omdbapi.com/apikey.aspx).

### Passo a passo:

1. Clone o repositório:

```bash
git clone https://github.com/cintyagomes/omdbapi.git
```

2. Abra o projeto no Android Studio.

3. Sincronize o projeto com o Gradle.

4. Execute o aplicativo no emulador ou dispositivo físico.

---

## 🎬 **Mídia**

https://github.com/user-attachments/assets/83020715-de38-4f55-966f-b727324e6dd1

---

## 🧑‍💻 **Contribuição**

Contribuições são bem-vindas! Siga os passos abaixo para contribuir:

1. Faça um fork do repositório.

2. Crie uma branch para sua feature:

```bash
git checkout -b feature/nome-da-feature
```

3. Faça commit das suas alterações:

```bash
git commit -m "feat: Adds new feature"
```

4. Envie suas alterações:

```bash
git push origin added/nome-da-feature
```

5. Abra um Pull Request.
