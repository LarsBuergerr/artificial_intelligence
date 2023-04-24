import constraint

# Variablen:
neighbours = {
    "Schleswig-Holstein": ["Hamburg", "Niedersachsen", "Mecklenburg-Vorpommern"],
    "Hamburg": ["Schleswig-Holstein", "Niedersachsen"],
    "Niedersachsen": ["Schleswig-Holstein", "Hamburg", "Mecklenburg-Vorpommern", "Brandenburg", "Sachsen-Anhalt", "Thüringen", "Hessen", "Nordrhein-Westfalen", "Bremen"],
    "Bremen": ["Niedersachsen"],
    "Nordrhein-Westfalen": ["Niedersachsen", "Hessen", "Rheinland-Pfalz"],
    "Hessen": ["Nordrhein-Westfalen", "Rheinland-Pfalz", "Baden-Württemberg", "Bayern", "Thüringen", "Niedersachsen"],
    "Rheinland-Pfalz": ["Nordrhein-Westfalen", "Hessen", "Baden-Württemberg", "Saarland"],
    "Saarland": ["Rheinland-Pfalz"],
    "Baden-Württemberg": ["Rheinland-Pfalz", "Hessen", "Bayern"],
    "Bayern": ["Baden-Württemberg", "Hessen", "Thüringen", "Sachsen"],
    "Berlin": ["Brandenburg"],
    "Brandenburg": ["Berlin", "Niedersachsen", "Sachsen-Anhalt", "Sachsen", "Mecklenburg-Vorpommern"],
    "Mecklenburg-Vorpommern": ["Schleswig-Holstein", "Niedersachsen", "Brandenburg"],
    "Sachsen": ["Brandenburg", "Sachsen-Anhalt", "Thüringen", "Bayern"],
    "Sachsen-Anhalt": ["Niedersachsen", "Brandenburg", "Sachsen", "Thüringen"],
    "Thüringen": ["Niedersachsen", "Sachsen-Anhalt", "Sachsen", "Bayern", "Hessen"]
}

# Domain:
colors = ["rot", "grün", "blau", "gelb"]

# CSP-Problem erstellen:
problem = constraint.Problem()

# Variablen hinzufügen:
for key, _ in neighbours.items():
    problem.addVariable(key, colors)

# Constraints hinzufügen:
for state, neighbours in neighbours.items():
    for neighbor in neighbours:
        problem.addConstraint(lambda a, b: a != b, (state, neighbor))

# Lösungssuche:
solutions = problem.getSolutions()

# Ausgaben:
print("Anzahl Lösungen: {}".format(len(solutions)))

print("\nLösung für jede Variable:")
print(solutions[0])

print("\nLösung nach Räumen geordnet:")
sol = {}
for k, v in solutions[0].items():
    if v in sol:
        sol[v].append(k)
    else:
        sol[v] = [k]
print(sol)