# Trabalho LBD -  Sistema de Biblioteca

## Tabela de Conteúdo

1. [Descrição](#descrição)
2. [Tecnologias](#tecnologias)
3. [Funcionalidades](#funcionalidades)
4. [Processo de execução](#execução)



## Descrição <a name="descrição"> </a>

  Nosso projeto tem como objetivo permitir o gerenciamento de uma aplicação web bibliotecária, onde por meio de interações rápidas e simples o usuário pode gerenciar os principais elementos necessários para o seu negócio. Optamos por implementar o backend em Spring Boot pela sua praticidade e agilidade de produção, assim como o React js para o frontend pelo mesmo motivo.



## Tecnologias utilizadas <a name="tecnologias"> </a>

### Spring Boot
- **JPA (Java Persistence API) com QueryDSL:** Para interação com o banco de dados e consulta de dados de forma eficiente.
- **Flyway:** Utilizado para controle de versionamento do banco de dados.
- **Spring Web:** Para criar aplicativos web usando o Spring MVC.
- **Lombok:** Biblioteca Java que ajuda a reduzir o código boilerplate.
- **Swagger:** Ferramenta para documentação de APIs. A documentação pode ser acessada em [[http://localhost:3001/swagger-ui/index.html#/](http://localhost:3001/swagger-ui/index.html#/)http://localhost:3001/swagger-ui/index.html#/]


### Banco de Dados PostgreSQL
- Optamos pelo PostgreSQL devido a:
  - **Padrão de Mercado:** Ampla adoção na indústria de TI.
  - **Abundância de Conteúdo Disponível:** Facilidade em encontrar recursos educacionais e suporte online.
  - **Conhecimento Prévio da Equipe:** Membros da equipe já possuem experiência com PostgreSQL.

### Docker
- Utilizamos Docker pelos seguintes motivos:
  - **Facilidade com Docker Compose:** Permite a execução rápida das aplicações por meio do arquivo `docker-compose.yml`.


 ## Funcionalidades <a name="funcionalidades"> </a>

 ### **Gestão(criar, atualizar, buscar e deletar)**

- **Gestão de Leitores**
  - Registre os leitores(clientes da biblioteca) para manutenção das outras funcionalidades.

- **Gestão de Livros**
  - Registre os livros disponíveis.

- **Gestão de Autores**
  - Registre os autores dos livros do sistema.

- **Gestão de Aluguel**
  - Registre os aluguéis de livros para o gerenciamento de negócio do sistema.


## Execução <a name="execução"> </a>

- Para execução do sistema o usuário deve subir um container do docker utilizando o comando /docker-compose up, informações sobre a configuração do pgadmin estão no arquivo docker-compose.yml
- Depois de se conectar com o banco, o usuário deve rodar a aplicação a partir do comando SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
- Por fim, o usuário deve abrir o diretório do frontend, instalar as dependências do react através do comando /npm install e rodar a aplicação através do comando /npm start
