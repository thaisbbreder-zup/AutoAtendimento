## AutoAtendimento 🍔🥤

O AutoAtendimento é um sistema de pedidos para um fast food, desenvolvido em Java. 🚀

## Descrição 📝

O sistema permite que os clientes façam pedidos de lanches e bebidas através de um aplicativo de autoatendimento. Os pedidos são enviados ao sistema e podem ser visualizados pelos funcionários do fast food para preparação e entrega.

## Requisitos 🛠️

- Java 11 ou superior ☕
- PostgreSQL (ou outro banco de dados compatível) 🐘
- Maven 📦

## Configuração do banco de dados 🗄️

Antes de executar o sistema, é necessário configurar o banco de dados PostgreSQL e criar as tabelas necessárias. A configuração do banco de dados pode ser feita no arquivo `application.properties` localizado em `src/main/resources`.

## Funcionalidades 🎯
#### 1. Lanche 🍔 
Permite que o cliente escolha um lanche dentre as opções disponíveis no menu. O cliente pode selecionar o lanche desejado e a quantidade desejada para cada item.
#### 2. Bebida 🥤
Permite que o cliente escolha uma bebida dentre as opções disponíveis no menu. O cliente pode selecionar a bebida desejada e a quantidade desejada para cada item.

#### 3. Visualizar o pedido 👀
Permite que o cliente visualize o pedido atual com todos os itens selecionados. O cliente pode ver os lanches e bebidas selecionados, bem como a quantidade de cada item.

#### 4. Editar a quantidade do pedido ✏️
Permite que o cliente edite a quantidade de um item específico no pedido atual. Se o cliente desejar alterar a quantidade de um lanche ou bebida, ele pode fazer isso através dessa funcionalidade.

#### 5. Remover item do pedido ❌
Permite que o cliente remova um item específico do pedido atual. Se o cliente não quiser mais um lanche ou bebida no pedido, ele pode removê-lo usando essa funcionalidade.

#### 6. Finalizar pedido e pagamento 💳
Permite que o cliente finalize o pedido e proceda ao pagamento. Nesta etapa, o cliente deve confirmar todos os itens selecionados e a quantidade desejada. Após a confirmação, o sistema registrará o pedido e estará pronto para a preparação e entrega.

#### Para voltar ao MENU, digite qualquer número 🔙
Em qualquer ponto do processo de pedido, o cliente pode digitar qualquer número para retornar ao menu principal e selecionar outra opção. Isso permite que o cliente navegue pelo aplicativo de autoatendimento com facilidade e faça escolhas diferentes a qualquer momento.


## Testes Unitários 🧪

Estão sendo realizados testes unitários para garantir a qualidade do código e a funcionalidade correta das diferentes partes do sistema. Os testes verificam o correto funcionamento das operações de cadastro de clientes, realização de pedidos e acompanhamento de status do pedido, entre outras funcionalidades.

 
