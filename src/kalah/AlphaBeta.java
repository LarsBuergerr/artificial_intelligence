package kalah;

public class AlphaBeta {

    public static int count = 0;

    public static int MaxAction(KalahBoard current_state, int depth){
        if (current_state.isFinished()){
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MIN_VALUE;
        KalahBoard best_action = null;

        for (var action: current_state.possibleActions()){
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
        System.out.printf("\nAlphaBeta hat %d Zustände betrachtet\n", count);
        return best_action.getLastPlay();
    }

    private static int MinValue(KalahBoard current_state, int alpha, int beta, int depth){
        if (current_state.isFinished() || depth == 0){
            // TODO I changed that we calculate the same thing for both players. Since A wants to maximize that value and B wants to minimize it. Is that correct ???
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MAX_VALUE;

        for (var action: current_state.possibleActions()){
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

        for (var action: current_state.possibleActions()){
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
}
