# Complexidade e corretude do algoritmo

Este documento apresenta uma análise detalhada da corretude do algoritmo orderByMinCost, implementado para a atribuição otimizada de ordens a funcionários com base no tempo mínimo de deslocamento. O algoritmo é parte de um sistema mais amplo de gerenciamento de tarefas e recursos, visando maximizar a eficiência operacional mantendo as restrições de jornada de trabalho dos funcionários. Esta análise abrange a lógica do algoritmo, sua invariante de laço, corretude, e considerações sobre sua terminação e eficiência.

## Descrição do Algoritmo
O algoritmo `orderByMinCost` é projetado para organizar uma lista de funcionários (`ArrayList<Funcionario>`) e ordens (`ArrayList<Ordem>`) de forma a minimizar o custo, considerado aqui como o tempo de deslocamento entre funcionários e ordens. O processo é detalhado a seguir:

1.  **Inicialização:** O algoritmo começa com a lista de ordens não vazia e uma lista de funcionários. Cada funcionário tem atributos como `id`, `ordens` (lista de ordens já atribuídas), `deslocamento` (tempo total de deslocamento acumulado), `jornada_de_trabalho` (tempo total de trabalho acumulado) e `periodo` (período de trabalho, manhã ou tarde).
    
2.  **Processo de Seleção:**
    
    *   Em cada iteração do laço `while`, o algoritmo procura pela ordem que pode ser cumprida pelo funcionário com o menor tempo de deslocamento adicional, respeitando os limites de deslocamento e jornada de trabalho.
    *   A seleção considera a última ordem cumprida por cada funcionário (para calcular o deslocamento) e verifica se a nova ordem se encaixa no período de trabalho do funcionário.
3.  **Atribuição e Atualização de Estado:**
    
    *   Uma vez encontrada a combinação ótima de funcionário e ordem, a ordem é atribuída ao funcionário, e a ordem é removida da lista de ordens pendentes.
    *   Os atributos de deslocamento e jornada de trabalho do funcionário são atualizados.
4.  **Alteração de Período e Condição de Saída:**
    
    *   Se não for possível encontrar um funcionário para uma ordem, o algoritmo tenta mudar o período de trabalho dos funcionários para "tarde", se possível.
    *   O algoritmo termina quando todas as ordens são atribuídas ou quando não é possível atribuir as ordens restantes devido às restrições de tempo.

Este algoritmo representa uma abordagem de otimização heurística, procurando fazer a melhor escolha local em cada passo, dentro das restrições definidas. 


## Pré-condições

Antes da execução do algoritmo `orderByMinCost`, as seguintes pré-condições devem ser satisfeitas:

1.  **Lista de Funcionários (`ArrayList<Funcionario>`):**
    
    *   Deve ser uma lista válida de objetos `Funcionario`.
    *   Cada `Funcionario` deve ter atributos iniciais definidos, incluindo `id`, `ordens` (que pode ser inicialmente vazia), `deslocamento`, `jornada_de_trabalho`, e `periodo`.
2.  **Lista de Ordens (`ArrayList<Ordem>`):**
    
    *   Deve ser uma lista não vazia de objetos `Ordem`.
    *   Cada `Ordem` deve ter um `id` único e um atributo `periodo` que especifica quando a ordem deve ser realizada.
3.  **Função de Tempo (`GetMatrizTempo.getTempoEntrePontos`):**
    
    *   Deve existir uma função confiável que calcule o tempo de deslocamento entre dois pontos, identificados pelos `id` dos funcionários e das ordens.
4.  **Limites de Tempo:**
    
    *   Os limites de deslocamento e jornada de trabalho para cada funcionário devem estar claramente definidos e acessíveis no contexto do algoritmo.

Essas pré-condições garantem que os dados iniciais necessários para a execução do algoritmo estejam corretos e completos.

## Pós-condições

Após a execução do algoritmo `orderByMinCost`, as seguintes pós-condições devem ser observadas:

1.  **Atribuição Completa ou Máxima de Ordens:**
    
    *   Todas as ordens possíveis devem ser atribuídas a funcionários, respeitando os limites de deslocamento e jornada de trabalho.
    *   Se algumas ordens não puderem ser atribuídas, isso deve ser devido exclusivamente a restrições de tempo e não a falhas no algoritmo.
2.  **Atualização de Estado dos Funcionários:**
    
    *   Cada `Funcionario` na lista deve ter seu `deslocamento` e `jornada_de_trabalho` atualizados para refletir as ordens atribuídas.
    *   O atributo `periodo` de cada funcionário deve ser atualizado corretamente, se necessário.
3.  **Integridade dos Dados:**
    
    *   A integridade da lista de funcionários e ordens deve ser mantida, sem perda ou corrupção de dados.
4.  **Otimização de Tempo de Deslocamento:**
    
    *   Dentro das restrições estabelecidas, o algoritmo deve buscar minimizar o tempo total de deslocamento dos funcionários.

## Análise do melhor e pior caso
Nesse tópico aborda-se a explicação e a justificativa do melhor e pior caso.

### Melhor caso
O melhor caso, do ponto de vista de complexidade, ocorre quando o algoritmo não precisa percorrer todas as ordens, isso acontece quando os funcionários não são capazes de atender as ordens, ou seja, considerando que todos os funcionários respeitem as restrições é impossível eles atenderem as demandas. Pois dessa maneira o laço principal do algoritmo será quebrado antes de passar por todas as ordens.

no código:
caso os funcionários não consigam atender todas as ordens, para uma determinada ordem não terá um funcionário escolhido para atendê-la, dessa maneira não entraremos nesse condicional(if), pois o funcionarioEscolhido será null

```java
    if (funcionarioEscolhido != null) {
        funcionarioEscolhido.addOrdem(ordemEscolhida);;
        ordens.remove(ordemEscolhida);
        funcionarioEscolhido.setDeslocamento(funcionarioEscolhido.getDeslocamento() + menorTempo);
        funcionarioEscolhido.setJornada_de_trabalho(funcionarioEscolhido.getJornada_de_trabalho() + menorTempo + 7200);
        if (funcionarioEscolhido.getJornada_de_trabalho() > 14400) {
            funcionarioEscolhido.setPeriodo("tarde");
        }
    }
```

Dessa maneira, entraremos no condicional else
que tentará alocar os funcionários para o período da tarde e tenta novamente alocar algum funcionário para as ordens restantes, caso isso não seja possível, flag será False então entrará no condicional if (!flag), que quebrará o laço while (!ordens.isEmpty()), justificativa: assim o algoritmo irá realizar menos operações, portanto a complexidade será reduzida.
```java
else {
    boolean flag = false;
    for (Funcionario funcionario :funcionarios) {
        if (funcionario.getJornada_de_trabalho() < 14400) {
            funcionario.setPeriodo("tarde");
            flag = true;
        }
    }
    if (!flag) {
        break;
    }
```

### Complexidade do melhor caso

F = número de funcionários
O = número de ordens

Dentro do laço while (!ordens.isEmpty()), estamos lidando com um cenário onde o número de funcionários é insuficiente para atender todas as demandas disponíveis. O algoritmo está projetado para atribuir ordens aos funcionários, mas o loop não percorre todas as ordens necessariamente, pois ele para quando todos os funcionários têm suas ordens atribuídas, considerando as restrições do problema.

A cada iteração do while, o algoritmo tenta atribuir uma ordem a um funcionário, e isso ocorre até que todos os funcionários tenham suas ordens atribuídas ou até que não haja mais ordens disponíveis. Dentro desse loop, temos um segundo loop, o for (Funcionario funcionario : funcionarios), que percorre cada funcionário. Cada funcionário, por sua vez, tem um loop interno for (Ordem ordem : ordens) que verifica cada ordem disponível.

O algoritmo irá parar quando todos os funcionários tiverem suas ordens atribuídas, mesmo que ainda existam ordens não atribuídas. Portanto, o número total de iterações do while será limitado pela quantidade de funcionários. Este cenário reflete uma situação em que o algoritmo não precisa percorrer todas as ordens disponíveis, mas apenas o número de funcionários existentes. Dessa forma, a complexidade do OrderByMinCost será:

$$ F \cdot F \cdot O  $$


Além disso, temos as funções otimização e destroi no seguinte codigo:
```java
public static ArrayList<Ordem> destroi(ArrayList<Funcionario> funcionarios, Funcionario funcionarioTarget) {
        if (funcionarioTarget.getOrdens().isEmpty()) {
            return new ArrayList<Ordem>();
        }

        ArrayList<Ordem> ordens = new ArrayList<>();

        ArrayList<Double> limites = limites(funcionarioTarget);

        for (Funcionario funcionario : funcionarios) {
            boolean estaDentro = false;
            if (funcionario.getLatitude() >= limites.get(0) &&
                    funcionario.getLatitude() <= limites.get(1) &&
                    funcionario.getLongitude() >= limites.get(2) &&
                    funcionario.getLongitude() <= limites.get(3)) {
                        estaDentro = true;
                        System.out.println("funcionario");
                        System.out.println(funcionario.getLatitude()+" "+ funcionario.getLongitude());
            }
            for (Ordem ordem : funcionario.getOrdens()) {
                if (estaDentro) break;
                if (ordem.getLatitude() >= limites.get(0) &&
                    ordem.getLatitude() <= limites.get(1) &&
                    ordem.getLongitude() >= limites.get(2) &&
                    ordem.getLongitude() <= limites.get(3)) {
                        estaDentro = true;
                        System.out.println("ordem");
                        System.out.println(funcionario.getLatitude()+" "+ funcionario.getLongitude());
                
                }
            }

            if (estaDentro) {
                funcionario.setPeriodo("manha");
                funcionario.setDeslocamento(0);
                funcionario.setJornada_de_trabalho(0);
                if (!funcionario.getOrdens().isEmpty()) {
                    for (Ordem ordem : funcionario.getOrdens()) {
                        ordens.add(ordem);
                    }
                    funcionario.setOrdens(new ArrayList<Ordem>());
                }
            }
        }

        return ordens;
    }
```
Dentro da função destroi, percorremos todos os funcionários e suas respectivas ordens(No máximo 3 para respeitar as restrições), logo, a complexidade da função destroi é de:

$$destroi = 3\cdot F $$

Levando em consideração a função ORDERBYMINCOST, a complexidade da função destroi se torna irrelevante, haja vista que F^2 se torna predominante:

$$ (F^2 \cdot O) + 3\cdot F$$

Enquanto isso, temos a função otmização:

```java
public static ArrayList<Funcionario> otimizacao(ArrayList<Funcionario> funcionarios) {
        boolean otimizado = false;
        ArrayList<Ordem> ordens = new ArrayList<Ordem>();

        while (!otimizado) {
            otimizado = true;
            double funcaoObjetivo = funcaoObjetivo(funcionarios);
            ordenaFuncMaiorDeslocamento(funcionarios);

            ArrayList<Funcionario> funcionariosCopia = new ArrayList<Funcionario>();

            for (int i = 0; i < funcionarios.size(); i++) {
                
                funcionariosCopia = new ArrayList<Funcionario>();
                for (int j = 0; j < funcionarios.size(); j++) {
                    Funcionario funcionario = funcionarios.get(j);
                    funcionariosCopia.add(funcionario.clone());
                    funcionariosCopia.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        funcionariosCopia.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }

                ordens = destroi(funcionariosCopia, funcionariosCopia.get(i));
                orderByMinCost(funcionariosCopia, ordens);
                double newFuncaoObjetivo = funcaoObjetivo(funcionariosCopia);
                if (newFuncaoObjetivo < funcaoObjetivo && newFuncaoObjetivo != 0) {
                    otimizado = false;
                    // otimizado = true;
                    funcionarios = funcionariosCopia;
                    funcaoObjetivo = newFuncaoObjetivo;
                    //break;
                } 
            }
        }

        return funcionarios;
    }
```

Na função de otimização, temos a estrutura while(!otimizado), onde dentro dele é possivel encontrar um laço for ```for (int i = 0; i < funcionarios.size(); i++)``` que percorremos o tamanho do array de funcionários, em seguida, encontramos outro laço por funcionarios e chamamos a função ```orderByMinCost(funcionariosCopia, ordens);```. Dessa forma, levando em consideração que para todos os testes feitos o laço ```while(!otimizado)``` foi percorrido entre 1 e 4 vezes, porém vamos considerar como 1, por ser o melhor caso, ou seja, ele precisa ser melhorado, apenas, 1 vez no melhor dos casos. Com isso, teremos a seguinte complexidade:

$$1((F \cdot F \cdot O )\cdot F + 3F^2)$$
$$((F^3 \cdot O ) + 3F^2)$$

Por fim, a complexidade total será da seguinte forma:

orderByMinCost + Otimização
$$ (F \cdot F \cdot O) + ((F^3 \cdot O ) + 3F^2)$$

Porém, devemos considerar, apenas, o predominante. No caso seria:

$$ F^2 \cdot O + F^3 \cdot O + 3F^2  $$

$$ (F^2 \cdot O + 3F^2) + F^3 \cdot O $$

$$ F^2(O + 3) + F^3 \cdot O $$

$$ F^3 \cdot O $$

Em suma:

A Notação O() será 

$$ F^3 \cdot O $$

--- 

### Pior caso
O pior caso, do ponto de vista de complexidade, ocorre quando o algoritmo precisa percorrer todas as ordens, isso acontece quando os funcionários disponíveis são capazes de atender as ordens, ou seja, considerando que todos os funcionários respeitem as restrições eles conseguiram atender as demandas. Pois, dessa maneira, o laço principal do algoritmo irá continuar até que todas as ordens sejam atendidas.

no código:
``` java
while (!ordens.isEmpty()) {
            double menorTempo = Integer.MAX_VALUE;
            Funcionario funcionarioEscolhido = null;
            Ordem ordemEscolhida = null;
            for (Funcionario funcionario : funcionarios) {
                int id = funcionario.getId();
                if (!funcionario.getOrdens().isEmpty()) {
                    id = funcionario.getOrdens().get(funcionario.getOrdens().size() - 1).getId();
                }
                for (Ordem ordem : ordens) {
                    double tempoAtual = GetMatrizTempo.getTempoEntrePontos(id, ordem.getId());
                    if (tempoAtual < menorTempo) {
                        if (funcionario.getDeslocamento() + tempoAtual <= 14400
                            && funcionario.getJornada_de_trabalho() + tempoAtual + 7200 <= 28800
                            && funcionario.getPeriodo().equals(ordem.getPeriodo())) {
                            
                            menorTempo = tempoAtual;
                            funcionarioEscolhido = funcionario;
                            ordemEscolhida = ordem;
                        }
                    }
                }
            }

            if (funcionarioEscolhido != null) {
                funcionarioEscolhido.addOrdem(ordemEscolhida);;
                ordens.remove(ordemEscolhida);
                funcionarioEscolhido.setDeslocamento(funcionarioEscolhido.getDeslocamento() + menorTempo);
                funcionarioEscolhido.setJornada_de_trabalho(funcionarioEscolhido.getJornada_de_trabalho() + menorTempo + 7200);
                if (funcionarioEscolhido.getJornada_de_trabalho() > 14400) {
                    funcionarioEscolhido.setPeriodo("tarde");
                }
            } else {
                boolean flag = false;
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getJornada_de_trabalho() < 14400) {
                        funcionario.setPeriodo("tarde");
                        flag = true;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            
        }
        
        return funcionarios;
    }
```
justificativa de pior caso:
o laço while irá ser continuado até que todas as ordens sejam atendidas, dessa maneira caso eu tenha funcionários suficientes para atender as ordens, esse laço irá "rodar" para todas as ordens, diferente do que acontece no melhor caso, onde ele irá sair do laço quando não tiver mais funcionário restante. Assim o número de operações realizadas será maximizado.

### Complexidade Pior caso
F = número de funcionários
O = número de ordens

Dentro do laço while (!ordens.isEmpty()) temos um p.a com razão -1, pois a cada iteração alocaremos a ordem ao funcionário e iremos removê-la, então no outro laço ```for (Ordem ordem : ordens)``` percorrerá 1 ordem a menos, pois a cada iteração no laço while remove-se uma ordem. Além disso temos o laço ```for (Funcionario funcionario : funcionarios)``` anterior ao laço de ordens, portanto a complexidade total do laço while (!ordens.isEmpty()) acima será:
$$P.A = FO + F(O-1) + F(O-2) + ... +F\cdot 1 $$
SOMA DA P.A
$$S =\frac{(FO+F)\cdot O}{2}$$
$$S = \frac{(FO^2+FO)}{2} $$

Além disso ainda dentro do laço while temos o laço ```for (Funcionario funcionario : funcionarios)```, portanto a soma da p.a será multiplicada por Funcionários no pior caso, logo para a complexidade de ORDERBYMINCOST obtém-se:
$$\frac{(FO^2+FO)}{2} \cdot F$$

Ademais, temos as funções destroi e otimização
```java
public static ArrayList<Ordem> destroi(ArrayList<Funcionario> funcionarios, Funcionario funcionarioTarget) {
        if (funcionarioTarget.getOrdens().isEmpty()) {
            return new ArrayList<Ordem>();
        }

        ArrayList<Ordem> ordens = new ArrayList<>();

        ArrayList<Double> limites = limites(funcionarioTarget);

        for (Funcionario funcionario : funcionarios) {
            boolean estaDentro = false;
            if (funcionario.getLatitude() >= limites.get(0) &&
                    funcionario.getLatitude() <= limites.get(1) &&
                    funcionario.getLongitude() >= limites.get(2) &&
                    funcionario.getLongitude() <= limites.get(3)) {
                        estaDentro = true;
                        System.out.println("funcionario");
                        System.out.println(funcionario.getLatitude()+" "+ funcionario.getLongitude());
            }
            for (Ordem ordem : funcionario.getOrdens()) {
                if (estaDentro) break;
                if (ordem.getLatitude() >= limites.get(0) &&
                    ordem.getLatitude() <= limites.get(1) &&
                    ordem.getLongitude() >= limites.get(2) &&
                    ordem.getLongitude() <= limites.get(3)) {
                        estaDentro = true;
                        System.out.println("ordem");
                        System.out.println(funcionario.getLatitude()+" "+ funcionario.getLongitude());
                
                }
            }

            if (estaDentro) {
                funcionario.setPeriodo("manha");
                funcionario.setDeslocamento(0);
                funcionario.setJornada_de_trabalho(0);
                if (!funcionario.getOrdens().isEmpty()) {
                    for (Ordem ordem : funcionario.getOrdens()) {
                        ordens.add(ordem);
                    }
                    funcionario.setOrdens(new ArrayList<Ordem>());
                }
            }
        }

        return ordens;
    }
```
Na função destrói temos um laço pelos funcionários e dentro dele um laço pelas ordens daquele funcionário(que são no máximo 3 ordens para cada funcionário), portanto a complexidade de destrói é:
$$destroi = 3\cdot F $$

considerando a complexidade de ORDERBYMINCOST, a complexidade de destroi torna-se irrelevante, pois obtém-se:
$$\frac{(FO^2+FO)}{2} \cdot F + 3\cdot F$$

```java
public static ArrayList<Funcionario> otimizacao(ArrayList<Funcionario> funcionarios) {
        boolean otimizado = false;
        ArrayList<Ordem> ordens = new ArrayList<Ordem>();

        while (!otimizado) {
            otimizado = true;
            double funcaoObjetivo = funcaoObjetivo(funcionarios);
            ordenaFuncMaiorDeslocamento(funcionarios);

            ArrayList<Funcionario> funcionariosCopia = new ArrayList<Funcionario>();

            for (int i = 0; i < funcionarios.size(); i++) {
                
                funcionariosCopia = new ArrayList<Funcionario>();
                for (int j = 0; j < funcionarios.size(); j++) {
                    Funcionario funcionario = funcionarios.get(j);
                    funcionariosCopia.add(funcionario.clone());
                    funcionariosCopia.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        funcionariosCopia.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }

                ordens = destroi(funcionariosCopia, funcionariosCopia.get(i));
                orderByMinCost(funcionariosCopia, ordens);
                double newFuncaoObjetivo = funcaoObjetivo(funcionariosCopia);
                if (newFuncaoObjetivo < funcaoObjetivo && newFuncaoObjetivo != 0) {
                    otimizado = false;
                    // otimizado = true;
                    funcionarios = funcionariosCopia;
                    funcaoObjetivo = newFuncaoObjetivo;
                    //break;
                } 
            }
        }

        return funcionarios;
    }
```

Na função otimizacao temos um laço while(!otimizado) e dentro dele temos um laço ```for (int i = 0; i < funcionarios.size(); i++)`, dentro do segundo laço por funcionários temos outro laço por funcionários e chamamos a função orderByMinCost(funcionariosCopia, ordens) novamente. Portanto, considerando que para todos os testes feitos o laço while (!otimizado) foi percorrido entre 1 e 4 vezes até que otimizado seja True, teremos que a complexidade da função otimização será
$$4((orderByMinCost)\cdot F + 3F^2)$$
$$4((\frac{(FO^2+FO)}{2} \cdot F)\cdot F + 3F^2)$$
$$4((\frac{(FO^2+FO)}{2} \cdot F^2) + 3F^2)$$
$$4((\frac{(F^3O^2+F^3O)}{2}) + 3F^2)$$
considerando o pior caso em que otimizado foi percorrido 4 vezes e considerando que a cada iteração pelo for funcionários chama-se orderByMinCost e também o laço de funcionários que percorre as ordens(que será no máximo 3) de cada funcionário.

Logo 
#### complexidade total será:

orderByMinCost + Otimização
$$ \frac{(FO^2+FO)}{2} \cdot F + 4((\frac{(F^3O^2+F^3O)}{2}) + 3F^2)$$

logo:

Notação O() será 
$$O(F^3O \cdot(O+1))$$
considerando que a escala ao cubo é consideravelmente maior do que linear e quadrática, portanto a complexidade será orientada ao trecho do polinômio que está ao cubo.





## Invariante de Laço

A invariante de laço é uma propriedade que se mantém verdadeira antes e depois de cada iteração do laço principal no algoritmo `orderByMinCost`. Para este algoritmo, a invariante pode ser estabelecida da seguinte maneira:

*   **Minimização Local do Tempo de Deslocamento:** Em cada iteração do laço `while`, o algoritmo seleciona a combinação de funcionário e ordem que resulta no menor incremento possível de tempo de deslocamento, respeitando as restrições de jornada de trabalho e deslocamento. Isso significa que, a cada passo, a melhor escolha local é feita para atribuir uma ordem a um funcionário.
    
*   **Manutenção da Viabilidade:** Após cada iteração, o conjunto restante de ordens ainda não atribuídas deve ser viável para atribuição futura, considerando as restrições de tempo dos funcionários restantes. Se a atribuição não for possível, o algoritmo tenta ajustar o período de trabalho dos funcionários para criar novas oportunidades de atribuição.
    

Essa invariante assegura que o algoritmo não apenas procura a otimização local em cada passo, mas também mantém a integridade e viabilidade do processo de atribuição como um todo.

## Análise de Corretude

A corretude do algoritmo `orderByMinCost` pode ser analisada considerando as seguintes vertentes:

1.  **Respeito às Pré-condições e Pós-condições:**
    
    *   O algoritmo começa com as pré-condições validadas e termina satisfazendo todas as pós-condições. Isso inclui a atribuição adequada de ordens e a atualização correta dos estados dos funcionários.
2.  **Manutenção da Invariante de Laço:**
    
    *   Em cada iteração, o algoritmo faz escolhas que mantêm a invariante de laço, garantindo a minimização do tempo de deslocamento dentro das restrições estabelecidas.
3.  **Terminação Garantida:**
    
    *   O algoritmo tem condições claras de terminação: ou todas as ordens são atribuídas ou não é mais possível fazer atribuições adicionais. Isso assegura que o algoritmo não caia em um loop infinito.
4.  **Integridade dos Dados:**
    
    *   Durante sua execução, o algoritmo mantém a integridade dos dados dos funcionários e das ordens, sem causar perda ou corrupção de informações.
