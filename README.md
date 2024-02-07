# Stateless Auth Sample

## Descrição

Este projeto é um exemplo de autenticação stateless desenvolvido em Java 17 usando Spring Boot, criado para fins de estudo e compartilhamento de conhecimento sobre implementação de sistemas de autenticação sem manutenção de estado no servidor.

## Pré-requisitos

Para executar este projeto, você precisará ter instalado:
- Docker
- Docker Compose
- Java 17
- Python 3

## Configuração

Para configurar o projeto em sua máquina local, siga os passos abaixo:

1. Clone o repositório para sua máquina local usando:
git clone https://github.com/LeonardolRibeiro/stateless-auth-sample.git

2. Navegue até o diretório do projeto:
cd stateless-auth-sample


## Execução

### ⚠️ Ponto de Atenção

**A execução do arquivo `build.py` remove todos os containers Docker da sua máquina.** Garanta que isto não afetará outros containers em execução.

Para executar o projeto:
python build.py

## Uso

Após a execução, teste a autenticação e o acesso aos endpoints protegidos com os seguintes comandos `curl`:

### Autenticar usuário
curl -X POST http://localhost:8080/api/auth/login
-H "Content-Type: application/json"
-d '{"username": "user.test", "password": "123456"}'

### Acessar recurso protegido
curl -X GET http://localhost:8080/api/protected/resource
-H "Authorization: Bearer <TOKEN_AQUI>"

curl -X GET http://localhost:8080/api/protected/resource
-H "Authorization: Bearer <TOKEN_AQUI>"


Substitua `<TOKEN_AQUI>` pelo token de acesso recebido após a autenticação.

## Contribuindo

Contribuições são bem-vindas! Para contribuir, abra uma issue ou faça um pull request.
