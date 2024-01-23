<table>
<tr>
<td>
<a href= "https://vtal.com"><img src="https://upload.wikimedia.org/wikipedia/commons/0/09/Vtal_logo_2022.png" alt="V.tal" border="0" width="60%"></a>
</td>
<td><a href= "https://www.inteli.edu.br/"><img src="./inteli-logo.png" alt="Inteli - Instituto de Tecnologia e Liderança" border="0" width="50%"></a>
</td>
</tr>
</table>

# Introdução

Este é um dos repositórios do projeto de alunos do Inteli em parceria com a V.tal no 2º semestre de 2023. Este projeto está sendo desenvolvido por alunos do Módulo 6 do curso de Ciência da Computação.

# Projeto: *Algoritmo de otimização para alocação e distribuição de equipes de técnicos*

# Grupo: *.Vitau.*

# Integrantes:

* [Arthur Tsukamoto Oliveira](https://www.linkedin.com/in/arthur-tsukamoto/)
* [Celine Pereira de Souza](https://www.linkedin.com/in/celine-souza-1a38aa225/)
* [Fábio Piemonte Lopes](https://www.linkedin.com/in/fabio-piemonte-823a65211/)
* [Marcelo Maia Fernandes Filho](https://www.linkedin.com/in/marcelo-maia-b90b03231/)
* [Samuel Lucas de Almeida](https://www.linkedin.com/in/samuel-lucas-de-almeida-241a77210/)
* [Yago Phellipe Matos Lopes](https://www.linkedin.com/in/yago-phellipe/)

# Descrição

*Este projeto tem como objetivo o desenvolvimento de um algoritmo de otimização que ajude a V.tal na distribuição e otimização dos técnicos. Com essa ferramenta à disposição, os coordenadores poderão organizar e distribuir o trabalho de forma mais assertiva, buscando uma potencial otimização de custos, bem como potencial melhora nos indicadores de nível de serviços (SLA ́s).*

*O projeto Vitau é uma plataforma web projetada para simplificar o cálculo das rotas diárias para alocar técnicos. Nesta plataforma, os usuários podem visualizar facilmente as rotas calculadas em um mapa, exportar um arquivo CSV contendo as rotas de cada técnico e realizar o cálculo preciso das rotas.*


# Configuração de desenvolvimento

# Guia para Rodar um Backend Local Spring Boot

Este guia explica como configurar e executar um projeto Spring Boot em sua máquina local.

## Pré-requisitos

- Java JDK 8 ou superior instalado
- Maven instalado (opcional, se o projeto tiver um wrapper Maven)
- IDE de sua escolha (IntelliJ IDEA, Eclipse, VSCode, etc)

## Passos

### 1. Clonar o Repositório

Primeiro, clone o repositório do seu projeto Spring Boot:

```bash
git clone [URL_DO_REPOSITORIO]
cd [NOME_DO_PROJETO]
```

### 2. Construir o Projeto

Com Maven:

```bash
mvn clean install
```

Ou, se o projeto tiver um wrapper Maven:

```bash
./mvnw clean install
```

### 3. Executar o Projeto

Você pode executar o projeto de várias maneiras:

#### Via Linha de Comando

```bash
mvn clean install
mvn spring-boot:run
```

#### Via IDE

Abra o projeto na sua IDE e execute a classe principal que contém o método `main`.

### 4. Verificar

Após iniciar o servidor, você pode verificar se está funcionando acessando:

```
http://localhost:8080
```

Substitua `8080` pela porta configurada no seu projeto, se diferente.

### Executar Front-end

Para executar o front-end, basta ir no terminal da aplicação e colocar os seguintes comandos:

```
npm i // para instalar as dependências necessárias
```

```bash
npm run dev
# ou
yarn dev
# ou
pnpm dev
# ou
bun dev
```

### Executar API OSMR

Para executar a API OSMR, devemos ir no terminal da aplicação e acessar o diretório chamado:

```
api_osmr_py
```

Utilizar o seguinte comando no terminal

```
python .\osrm.py
```

# Releases

* SPRINT1: *Na Sprint 1, nós buscamos ter uma visão holística do projeto, então nós estudamos o négocio da empresa parceira, nossas personas e buscamos reunir e entender todos os dados que temos disponíveis. Estes estudos estão traduzidos nos seguintes artefatos:*
    - *Entendimento de Negócio.*
    - *Entendimento da Experiência do Usuário.*
    - *Entendimento do contexto do problema: modelagem e representação.*

* SPRINT2: *Na Sprint 2, concentramos nossos esforços em aprimorar a modelagem do problema, com o objetivo de criar uma versão simplificada do algoritmo. Além disso, demos início ao desenvolvimento do artigo científico do projeto. Isso resultou nos seguintes artefatos:*
    - *Artigo - versão inicial.*
    - *Entendimento do contexto do problema.*
    - *Resolução de uma versão simplificada do problema.*
    - *Repositório de código.*

* SPRINT3: *Na Sprint 3, nos concentramos em fazer o front-end, back-end e planejador. Além disso, incluimos trabalhos relacionados no Artigo.*
    - *Artigo - 2a versão.*
    - *Planejador e back-end da aplicação.*
    - *Front-end da aplicação.*
    - *Repositório de código.*

* SPRINT4: *Na Sprint 4, tivemos que fazer a integração do front-end e back-end da aplicação e fazer a complexidade e corretude do algoritmo. Além disso, colocar os resultados do projeto no Artigo.*
    - *Artigo.*
    - *Complexidade e corretude do algoritmo.*
    - *Aplicação.*
    - *Repositório de código.*

* SPRINT5: *Durante a Sprint 5, nós focamos em finalizar e aprimorar o projeto e o Artigo. Além de fazer os testes de usabilidade para garantir uma boa experiência ao usuário.*
    - *Artigo completo.*
    - *Testes de usabilidade.*
    - *Apresentação Final.*

## 📋 Licença/License

<img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/cc.svg?ref=chooser-v1"><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1"><p xmlns:cc="http://creativecommons.org/ns#" xmlns:dct="http://purl.org/dc/terms/">

<a property="dct:title" rel="cc:attributionURL">Grupo</a> by <a rel="cc:attributionURL dct:creator" property="cc:attributionName">Inteli, Arthur Tsukamoto Oliveira, Celine Pereira de Souza, Fábio Piemonte Lopes, Marcelo Maia Fernandes Filho, Samuel Lucas de Almeida, Yago Phellipe Matos Lopes</a> is licensed under <a href="https://creativecommons.org/licenses/by/4.0/?ref=chooser-v1" rel="license noopener noreferrer" style="display:inline-block;">Attribution 4.0 International</a>.</p>
