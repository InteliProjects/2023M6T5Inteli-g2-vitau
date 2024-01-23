Heuristic construct and improve


Greedy:
  Choose the customer that increases the cost by the least
  Insert in the position that increases the cost by the least

Regret:
  Regret = Cost(2 best route) - Cost(best route)
  K regret
  Insert the customer with maximun regret

Local Search:
  K-opt
  Or-opt
  • Consider chains of length k
  • ktakes value 1.. n / 2
  • Remove the chain from its current position
  • Consider placing in each other possible position
  • in forward orientation
  • and reverse orientation
  • Very effective
  Meta Heuristics to move out from local minima

Large Neighbourhood Search
- Destroy part of the solution
- Remove visits from the solution
• Re-create solution
- Use favourite construct method to re-insert customers
• If the solution is better, keep it
• Repeat
Destroy part of the solution (Select method)
Examples
  • Choose longest (worst) arc in solution
  - Remove visits at each end
  - Remove nearby visits
  • Actually, choose rth worst
  • r= n* (uniform(0,1))y
  • y~ 6
  - 0.56 = 0.016
  - 0.96 = 0.531
Re-create solution
• Use your favourite insert method
• Better still, use a portfolio
- Ropke: Select amongst
- Minimum Insert Cost
- Regret
- 3-regret
- 4-regret