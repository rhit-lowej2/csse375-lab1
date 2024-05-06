package mainApp;

public class FitnessMethodFactory {
    public FitnessMethod getFitnessMethod(String method) {
        return switch (method) {
            case "Smiley" -> new StringCompareFitnessMethod(FitnessMethod.SMILEY);
            case "Slime" -> new StringCompareFitnessMethod(FitnessMethod.SLIME);
            case "All Green", "twenty 1s" -> new StringCompareFitnessMethod(FitnessMethod.GREEN);
            case "Jack" -> new JackFitnessMethod();
            case "Clock" -> new ClockFitnessMethod();
            case "All" -> new AllFitnessMethod();
            case "miniJack" -> new MiniJackFitnessMethod();
            default -> null;
        };
    }
}
