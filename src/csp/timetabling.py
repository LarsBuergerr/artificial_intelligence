import constraint

problem = constraint.Problem()

# Werte:
room = [1, 2, 3, 4]

# Variablen:
teacher = ["Maier", "Huber", "Müller", "Schmid"]
subject = ["Deutsch", "Mathe", "Englisch", "Physik"]

problem.addVariables(teacher, room)
problem.addVariables(subject, room)

# Constraints:
problem.addConstraint(constraint.AllDifferentConstraint(), teacher)
problem.addConstraint(constraint.AllDifferentConstraint(), subject)

problem.addConstraint(lambda a: a != 4, ["Maier"])
problem.addConstraint(lambda a, b: a == b, ("Müller", "Deutsch"))
problem.addConstraint(lambda a, b: abs(a - b) > 1, ("Schmid", "Müller"))
problem.addConstraint(lambda a, b: a == b, ("Huber", "Mathe"))
problem.addConstraint(lambda a: a == 4, ["Physik"])
problem.addConstraint(lambda a, b: a != 1 and b != 1, ("Deutsch", "Englisch"))

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
