# Busca Correios

Ese projeto trata-se de um programa que salva uma lista de CEPs extraídas dos correios em um banco SQL e permite a consulta desses CEPs via API REST.

No momento por uma questão de desempenho estão presentes apenas os CEPs do estado de Minas Gerais que totalizam quase 80 mil linhas no banco de dados porém
a intenção é ir adicionando todos por estado até finalmente mapear todos e permitir a busca de qualquer CEP do país, totalizando aproximadamente 890 mil linhas no banco de dados

## Tecnologias utilizadas
- Java 17
- Spring Boot 3
- Lombok
- MySQL
- Spring Data
- JPA
- Maven

## Adições futuras
- [ ] Corrigir formatação dos dados recebidos
- [ ] Tratamento da requisição com formato do CEP
- [ ] Opção para calcular o frete entre duas localidades
- [ ] Testes unitários com JUnit
- [ ] Build com Docker compose

## Como usar
Por enquanto o código apenas funciona em um ambiente de desenvolvimento. Caso o seu ambiente não atualize as dependências do Maven, insira no projeto utilizando este comando:
```
mvn clean install
```
Inicialize a aplicação Spring e aguarde até a aplicação subir a API, o que leva aproximadamente 1 minuto. Além do terminal você pode conferir o status através do endereço
`http://localhost:8080/status`. Ao tentar realizar uma busca durante o setup um erro 503 será retornado informando que o setup ainda está rodando.

![image](https://user-images.githubusercontent.com/102702376/231016083-7b5369d7-2115-41e1-b3e6-7b216141f50e.png)
<div align="center"><sup align="center">Foi utilizado o Insomnia para teste da API, porém neste projeto pode ser realizado pelo browser</sup></div>

Assim que concluído, o site retorna este resultado:

![image](https://user-images.githubusercontent.com/102702376/231016793-d8823f11-c068-4395-a9e5-f92b7a026934.png)

Para realizar uma busca, basta acessar `http://localhost:8080/cep/numerocep`, trocando "numerocep" pelo número do CEP desejado, lembrando que atualmente estão disponíveis apenas CEPs do estado de Minas Gerais

![image](https://user-images.githubusercontent.com/102702376/231017192-5a7f5913-8874-471a-a9ae-add7a91ea6f9.png)
