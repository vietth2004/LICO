package data.clonedProject;

import core.dataStructure.MarkedPath;

public class Abc {
    public static double intPow(double number, int index) {
        MarkedPath.markOneStatement("double result=1;\n", false, false);
        double result = 1;
        if (((index == 0) && MarkedPath.markOneStatement("index == 0", true, false)) || MarkedPath.markOneStatement("index == 0", false, true)) {
            MarkedPath.markOneStatement("return 1;\n", false, false);
            return 1;
        } else {
            if (((index < 0) && MarkedPath.markOneStatement("index < 0", true, false)) || MarkedPath.markOneStatement("index < 0", false, true)) {
                {
                    MarkedPath.markOneStatement("int i=0", false, false);
                    for (int i = 0; ((i < -index) && MarkedPath.markOneStatement("i < -index", true, false)) || MarkedPath.markOneStatement("i < -index", false, true); MarkedPath.markOneStatement("i++", false, false),
                            i++) {
                        {
                            MarkedPath.markOneStatement("result*=number;\n", false, false);
                            result *= number;
                        }
                    }
                    MarkedPath.markOneStatement("return 1 / result;\n", false, false);
                    return 1 / result;
                }
            } else {
                {
                    MarkedPath.markOneStatement("int i=0", false, false);
                    for (int i = 0; ((i < index) && MarkedPath.markOneStatement("i < index", true, false)) || MarkedPath.markOneStatement("i < index", false, true); MarkedPath.markOneStatement("i++", false, false),
                            i++) {
                        {
                            MarkedPath.markOneStatement("result*=number;\n", false, false);
                            result *= number;
                        }
                    }
                    MarkedPath.markOneStatement("return result;\n", false, false);
                    return result;
                }
            }
        }
    }

}
