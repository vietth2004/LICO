public class DummyTel {

    public static double calculateTotalCost(int startTime, int callDuration) {
        // Calculate initial cost of the call with the 0.5đ/minute rate
        double resultCost = 0.5 * callDuration;

        // if the call begins at/after 18p.m or before 8a.m then the cost's reduced by half
        if (startTime >= 18) {
            resultCost = resultCost / 2;
        } else if (startTime < 8) {
            resultCost = resultCost / 2;
        }

        // if the call lasts more than 60 minutes, the cost is reduced by 15% on the current cost
        if (callDuration > 60) {
            resultCost -= (resultCost * 15.0 / 100);
        }

        // 5% tax
        resultCost += (resultCost * 5.0 / 100);

        // final cost is rounded to the nearest hundredth
        return resultCost;
    }
}
