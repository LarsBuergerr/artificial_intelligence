from dataclasses import dataclass
from colorama import Fore, Back, Style
import constraint

big_rectangle_width = 7
big_rectangle_height = 8


@dataclass
class Rectangle:
    width: int
    height: int


def solve(rectangles):
    problem = constraint.Problem()

    # Add variables
    for i, rectangle in enumerate(rectangles):
        if rectangle.width <= big_rectangle_width and rectangle.height <= big_rectangle_height:
            problem.addVariable(f'x_{i}', range(big_rectangle_width + 1))
            problem.addVariable(f'y_{i}', range(big_rectangle_height + 1))
            problem.addVariable(f"o_{i}", [0, 1])
        elif rectangle.height <= big_rectangle_width and rectangle.width <= big_rectangle_height:
            problem.addVariable(f'x_{i}', range(big_rectangle_width + 1))
            problem.addVariable(f'y_{i}', range(big_rectangle_height + 1))
            problem.addVariable(f"o_{i}", [0, 1])

    # Constraints
    for i, rect in enumerate(rectangles):
        problem.addConstraint(
            lambda x, y, o, r=rect: check_bounds(x, y, o, r),
            (f"x_{i}", f"y_{i}", f"o_{i}")
        )

    for i, rect1 in enumerate(rectangles):
        for j, rect2 in enumerate(rectangles):
            if i != j:
                problem.addConstraint(
                    lambda x1, y1, o1, x2, y2, o2, r1=rect1, r2=rect2: check_overlap(x1, y1, o1, x2, y2, o2, r1, r2),
                    (f"x_{i}", f"y_{i}", f"o_{i}", f"x_{j}", f"y_{j}", f"o_{j}"))

    solutions = problem.getSolutions()

    if len(solutions) == 0:
        print("No solution found.")
        return

    if len(solutions) == 0:
        print("No solution found.")
        return

    for sol in solutions:
        big_rectangle = [[" " for _ in range(big_rectangle_width)] for _ in range(big_rectangle_height)]
        vals = [(" 1 ", Fore.RED), (" 2 ", Fore.GREEN), (" 3 ", Fore.YELLOW), (" 4 ", Fore.BLUE), (" 5 ", Fore.CYAN),
                (" 6 ", Fore.BLACK)]
        colors = []

        for i, rect in enumerate(rectangles):
            x = sol[f"x_{i}"]
            y = sol[f"y_{i}"]
            o = sol[f"o_{i}"]
            width = rect.width if o == 0 else rect.height
            height = rect.height if o == 0 else rect.width
            val = vals[i % len(vals)]

            for j in range(x, x + width):
                for k in range(y, y + height):
                    big_rectangle[k][j] = val

        for row in big_rectangle:
            for cell in row:
                print(cell[1] + cell[0], end=" ")
            print()
        print()


def check_bounds(x, y, o, rect: Rectangle):
    width = rect.width if o == 0 else rect.height
    height = rect.height if o == 0 else rect.width

    if x + width > big_rectangle_width:
        return False

    if y + height > big_rectangle_height:
        return False

    return True


def check_overlap(x1: int, y1: int, o1: int, x2: int, y2: int, o2: int, rect1: Rectangle, rect2: Rectangle):
    # print(f"Rect1: {rect1}, Rect2: {rect2},x1: {x1}, y1: {y1}, x2: {x2}, y2: {y2}")
    width1 = rect1.width if o1 == 0 else rect1.height
    height1 = rect1.height if o1 == 0 else rect1.width
    width2 = rect2.width if o2 == 0 else rect2.height
    height2 = rect2.height if o2 == 0 else rect2.width

    rect1_all_coords = []
    for i in range(width1):
        for j in range(height1):
            rect1_all_coords.append((x1 + i, y1 + j))

    rect2_all_coords = []
    for i in range(width2):
        for j in range(height2):
            rect2_all_coords.append((x2 + i, y2 + j))

    return not bool(set(rect1_all_coords) & set(rect2_all_coords))


solve([Rectangle(6, 4), Rectangle(8, 1), Rectangle(4, 1), Rectangle(5, 2), Rectangle(2, 2), Rectangle(3, 2)])
