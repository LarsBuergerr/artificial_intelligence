package kalah;

import java.util.LinkedList;
import java.util.List;

public class Heuristic {
    public static int count = 0;

    public static int MaxAction(KalahBoard current_state, int depth){
        if (current_state.isFinished()){
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MIN_VALUE;
        KalahBoard best_action = null;

        for (var action: heuristic(current_state, 1)){
            count++;
            int v1;
            if (action.isBonus()){
                v1 = Integer.max(v, MaxValue(action, Integer.MIN_VALUE, Integer.MAX_VALUE, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v1 = Integer.max(v, MinValue(action, Integer.MIN_VALUE, Integer.MAX_VALUE, depth -1));
            }

            if (v1 > v){
                v = v1;
                best_action = action;
            }
        }
        System.out.printf("\nHeuristic hat %d Zustände betrachtet\n", count);
        return best_action.getLastPlay();
    }

    private static int MinValue(KalahBoard current_state, int alpha, int beta, int depth){
        if (current_state.isFinished() || depth == 0){
            // TODO I changed that we calculate the same thing for both players. Since A wants to maximize that value and B wants to minimize it. Is that correct ???
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MAX_VALUE;

        for (var action: heuristic(current_state, 2)){
            count++;
            if (action.isBonus()){
                v = Integer.min(v, MinValue(action, alpha, beta, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v = Integer.min(v, MaxValue(action, alpha, beta, depth -1));
            }
            if (v <= alpha) {   // Alpha Cutoff
                return v;
            }
            beta = Integer.min(beta, v);
        }
        return v;
    }

    private static int MaxValue(KalahBoard current_state, int alpha, int beta, int depth){
        if (current_state.isFinished() || depth == 0){
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MIN_VALUE;

        for (var action: heuristic(current_state, 1)){
            count++;
            if (action.isBonus()){
                v = Integer.max(v, MaxValue(action, alpha, beta, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v = Integer.max(v, MinValue(action, alpha, beta, depth -1));
            }
            if (v >= beta){ // Beta Cutoff
                return v;
            }
            alpha = Integer.max(alpha, v);
        }
        return v;
    }

    private static List<KalahBoard> heuristic(KalahBoard current_state, int player_number){
        List<KalahBoard> possible_actions = current_state.possibleActions();
        List<KalahBoard> sorted_actions = new LinkedList<>();

        while (possible_actions.size() > 0) {
            KalahBoard current_best = possible_actions.get(0);
            int best_score = evaluate_action(current_best);

            for (var action: possible_actions){
                if(player_number == 1){
                    int score = evaluate_action(action);
                    if (score > best_score){
                        best_score = score;
                        current_best = action;
                    }
                } else if(player_number == 2){
                    int score = evaluate_action(action);
                    if (score < best_score){
                        best_score = score;
                        current_best = action;
                    }
                }else{
                    throw new IllegalArgumentException();
                }
            }
            sorted_actions.add(current_best);
            possible_actions.remove(current_best);
        }
        return sorted_actions;
    }

    private static int evaluate_action(KalahBoard action){
        return action.getAKalah() - action.getBKalah() + (action.isBonus() ? 2 : 0);
    }
}
