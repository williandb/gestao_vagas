# Gestão de Vagas - Fluxo das Informações

Este documento detalha o fluxo das informações e as interações entre as classes do projeto Gestão de Vagas, com foco nos endpoints de vagas da empresa (`/company/job/`).

## Visão Geral
O projeto utiliza arquitetura em camadas, separando responsabilidades entre controllers, DTOs, entidades, use cases, repositórios e segurança. O fluxo de cada requisição é protegido por autenticação JWT e validação de permissões.

## Endpoints do JobController
- `POST /company/job/` - Cadastro de vaga
- `GET /company/job/` - Listagem de vagas da empresa

## Fluxo da Requisição - Cadastro de Vaga (POST /company/job/)
1. **Requisição HTTP**: O cliente envia uma requisição POST para `/company/job/` com o corpo contendo os dados da vaga (CreateJobDTO) e o header `Authorization` com o token JWT.
2. **SecurityFilter**: O filtro intercepta a requisição, valida o token JWT, extrai o `company_id` e define como atributo na requisição. Se o token for inválido, retorna 401.
3. **SecurityConfig**: Garante que o endpoint exige autenticação e que o filtro é aplicado antes dos controllers.
4. **Controller (JobController)**: O método `create` recebe o DTO, recupera o `company_id` da requisição e constrói um `JobEntity` associando à empresa autenticada.
5. **DTO (CreateJobDTO)**: Representa os dados recebidos do cliente para cadastro da vaga.
6. **Entity (JobEntity)**: Representa a vaga no domínio, incluindo o vínculo com a empresa (`companyId`).
7. **UseCase (CreateJobUseCase)**: Contém a lógica de negócio para persistir a vaga, validando regras e chamando o repositório.
8. **Repository**: Realiza a persistência da vaga no banco de dados.
9. **Resposta**: O controller retorna o resultado da operação (vaga criada ou erro).

## Fluxo da Requisição - Listagem de Vagas (GET /company/job/)
1. **Requisição HTTP**: O cliente envia uma requisição GET para `/company/job/` com o header `Authorization` contendo o token JWT.
2. **SecurityFilter**: Valida o token, extrai o `company_id` e define como atributo na requisição.
3. **SecurityConfig**: Garante que o endpoint exige autenticação.
4. **Controller (JobController)**: O método `listByCompany` recupera o `company_id` e chama o use case para buscar as vagas da empresa.
5. **UseCase (ListAllJobsByCompanyUseCase)**: Realiza a busca das vagas associadas ao `companyId` informado.
6. **Repository**: Consulta o banco de dados pelas vagas da empresa.
7. **Resposta**: O controller retorna a lista de vagas ou erro.

## Interações Entre Classes
- **SecurityFilter**: Intercepta todas as requisições protegidas, valida o JWT, define atributos de autenticação na requisição.
- **SecurityConfig**: Configura as regras de segurança, define endpoints públicos e protegidos, registra o filtro.
- **Controller**: Recebe requisições, valida dados, recupera informações de autenticação, chama os use cases.
- **DTO**: Utilizado para transferência de dados entre cliente e backend.
- **Entity**: Representa os dados persistidos e manipulados no domínio.
- **UseCase**: Centraliza a lógica de negócio, orquestrando entidades e repositórios.
- **Repository**: Interface com o banco de dados para persistência e consulta.

## Autenticação e Autorização
- O token JWT é obrigatório para acessar endpoints protegidos.
- O filtro de segurança garante que apenas usuários autenticados e autorizados (com papel COMPANY) possam acessar os endpoints de vagas.
- O atributo `company_id` é utilizado para garantir que operações sejam realizadas apenas para a empresa autenticada.

## Tratamento de Erros
- Erros de autenticação retornam 401.
- Erros de validação ou negócio retornam 400 com mensagem detalhada.

---

Este fluxo garante segurança, integridade dos dados e separação clara de responsabilidades entre as camadas do sistema.
