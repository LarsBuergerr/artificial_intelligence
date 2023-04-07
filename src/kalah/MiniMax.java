package kalah;

public class MiniMax {

    public static int MaxAction(KalahBoard current_state, int depth){
        if (current_state.isFinished()){
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MIN_VALUE;
        KalahBoard best_action = null;

        for (var action: current_state.possibleActions()){
            int v1;
            if (action.isBonus()){
                v1 = Integer.max(v, MaxValue(action, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v1 = Integer.max(v, MinValue(action, depth -1));
            }

            if (v1 > v){
                v = v1;
                best_action = action;
            }
        }
        return best_action.getLastPlay();
    }

    private static int MinValue(KalahBoard current_state, int depth){
        if (current_state.isFinished() || depth == 0){
            return current_state.getBKalah() - current_state.getAKalah();
        }

        int v = Integer.MAX_VALUE;

        for (var action: current_state.possibleActions()){
            if (action.isBonus()){
                v = Integer.min(v, MinValue(action, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v = Integer.min(v, MaxValue(action, depth -1));
            }
        }
        return v;
    }

    private static int MaxValue(KalahBoard current_state, int depth){
        if (current_state.isFinished() || depth == 0){
            return current_state.getAKalah() - current_state.getBKalah();
        }

        int v = Integer.MIN_VALUE;

        for (var action: current_state.possibleActions()){
            if (action.isBonus()){
                v = Integer.max(v, MaxValue(action, depth)); // since it is technically the same move we don't decrement the depth here
            }else{
                v = Integer.max(v, MinValue(action, depth -1));
            }
        }
        return v;
    }
}
