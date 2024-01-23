Nodo atual:
- Long
- lat
- is_func
- id

Funcionário:
- Long
- lat
- id
- lista de ordens
- deslocamento_atual(func)
- jornada de trabalho <= 8

Ordem:
- Long
- lat
- id
- id_funcionário
- período(ordem)

# restricões:
Todos comecam no mesmo horário
Para cada atendimento, somo 2 horas + deslocamento
Se deslocamento rstante + deslocamento <= 4 horas, atendo
Se deslocamento restante + 2 * demandas atendidas >= 4 horas, atendo de tarde
Se deslocamento restante + 2 * demandas atendidas + próxima demanda + deslocamento<= 8 horas, posso atender