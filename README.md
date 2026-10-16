# Checkout Strategy API

Uma Simples API educacional em Java puro que simula o fluxo de checkout de um e-commerce,
construída para demonstrar, na prática, três padrões de projeto clássicos do GoF:
**Strategy**, **Singleton** e **Facade**.

O projeto não utiliza frameworks (sem Spring, sem Jakarta EE). O foco é mostrar
como esses padrões se combinam em um cenário realista, usando apenas recursos
da linguagem.

## Sumário

- [Sobre o projeto](#sobre-o-projeto)
- [Padrões de projeto aplicados](#padrões-de-projeto-aplicados)
- [Estrutura do projeto](#estrutura-do-projeto)
- [Como executar](#como-executar)
- [Exemplo de saída](#exemplo-de-saída)
- [Possíveis evoluções](#possíveis-evoluções)
- [Tecnologias](#tecnologias)
- [Autor](#autor)

## Sobre o projeto

O sistema simula o checkout de um pedido com múltiplos itens, permitindo que o
pagamento seja feito por diferentes métodos (Pix, cartão de crédito ou boleto).
Cada etapa do processo: validação do pedido, escolha do método de pagamento e
registro da transação (é resolvida por um padrão de projeto diferente,
mostrando como eles se complementam em código real).

## Padrões de projeto aplicados

### Strategy

Define uma família de algoritmos de pagamento (`CreditCardPaymentStrategy`,
`PixPaymentStrategy`, `BoletoPaymentStrategy`), todos implementando a interface
`PaymentStrategy`. O cliente do sistema escolhe qual estratégia usar em tempo de
execução, sem que o restante do código precise conhecer os detalhes de cada
forma de pagamento. Adicionar um novo método de pagamento significa apenas criar
uma nova classe, sem alterar as já existentes.

Localização: `com.checkoutapi.strategy`

### Singleton

A classe `TransactionLogger` garante que exista, em toda a aplicação, uma única
instância responsável por registrar o histórico de transações. A implementação
usa inicialização preguiçosa com double-checked locking, o que a torna segura
em ambientes concorrentes sem pagar o custo de sincronizar todas as chamadas.

Localização: `com.checkoutapi.singleton`

### Facade

A classe `CheckoutFacade` expõe um único método, `checkout`, que esconde toda a
complexidade do processo: validação do pedido, execução da estratégia de
pagamento escolhida e registro de cada etapa no log. Quem consome a API não
precisa conhecer essas três responsabilidades separadamente, apenas chamar
`checkout(order, strategy)`.

Localização: `com.checkoutapi.facade`

### Como os padrões se conectam

```
Cliente
  |
  v
CheckoutFacade.checkout(order, strategy)
  |
  |--> valida o pedido
  |--> strategy.pay(total)        -> Strategy decide COMO pagar
  |--> TransactionLogger.log(...) -> Singleton registra O QUE aconteceu
  |
  v
PaymentResult
```
## Estrutura do projeto

```
checkout-strategy-api/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── com/
                └── checkoutapi/
                    ├── Main.java
                    ├── exception/
                    │   └── PaymentException.java
                    ├── facade/
                    │   └── CheckoutFacade.java
                    ├── model/
                    │   ├── Item.java
                    │   └── Order.java
                    ├── singleton/
                    │   └── TransactionLogger.java
                    └── strategy/
                        ├── PaymentStrategy.java
                        ├── PaymentResult.java
                        ├── CreditCardPaymentStrategy.java
                        ├── PixPaymentStrategy.java
                        └── BoletoPaymentStrategy.java
```


## Possíveis evoluções

Ideias para quem quiser continuar evoluindo o projeto:

- Adicionar uma camada HTTP simples usando `com.sun.net.httpserver.HttpServer`,
  expondo o checkout como um endpoint REST real sem depender de frameworks.
- Criar testes unitários com JUnit para cada estratégia de pagamento e para a
  fachada.
- Persistir o histórico de transações em arquivo ou banco de dados em vez de
  mantê-lo apenas em memória.
- Aplicar o padrão Factory Method para centralizar a criação das estratégias de
  pagamento a partir de um identificador textual (por exemplo, `"PIX"`,
  `"CARTAO"`, `"BOLETO"`).

## Tecnologias

- Java 25
- Maven (opcional, apenas para build e execução)

## Autor

Desenvolvido por Camilly como projeto de estudo pessoal em padrões de projeto (Design Patterns) em
Java puro.

