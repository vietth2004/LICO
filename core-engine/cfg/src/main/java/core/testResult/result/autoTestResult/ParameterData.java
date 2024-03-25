package core.testResult.result.autoTestResult;

public class ParameterData {
    private String name;
    private String type;
    private String value;

    public ParameterData() {
    }

    public ParameterData(String name, String type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(type + " " + name + " = " + value);

        return result.toString();
    }
}

