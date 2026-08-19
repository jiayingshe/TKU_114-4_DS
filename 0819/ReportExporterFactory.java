import java.util.Arrays;

interface ReportExporter {
    String export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title,").append(title != null ? title : "").append("\nData,");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                sb.append(values[i]).append(i == values.length - 1 ? "" : ",");
            }
        }
        return sb.toString();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String valStr = (values == null) ? "[]" : Arrays.toString(values);
        return String.format("{\n  \"title\": \"%s\",\n  \"values\": %s\n}", title != null ? title : "", valStr);
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 報表: ").append(title != null ? title : "無標題").append(" ===\n數值列表: ");
        if (values != null && values.length > 0) {
            for (int val : values) {
                sb.append("[").append(val).append("] ");
            }
        } else {
            sb.append("無資料");
        }
        return sb.toString();
    }
}

public class ReportExporterFactory {
    public static ReportExporter createExporter(String format) {
        if (format == null) return new TextExporter();
        switch (format.toLowerCase()) {
            case "csv": return new CsvExporter();
            case "json": return new JsonExporter();
            case "text":
            default: return new TextExporter();
        }
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        if (exporter == null) {
            System.out.println("匯出失敗：Exporter 不能為 null");
            return;
        }
        System.out.println(exporter.export(title, values));
    }

    public static void main(String[] args) {
        int[] data = new int[]{10, 20, 30, 40};

        System.out.println("=== 測試 1：CSV 格式 ===");
        ReportExporter csvExporter = createExporter("csv");
        exportReport(csvExporter, "月度銷售", data);

        System.out.println("\n=== 測試 2：JSON 格式 ===");
        ReportExporter jsonExporter = createExporter("json");
        exportReport(jsonExporter, "月度銷售", data);

        System.out.println("\n=== 測試 3：不支援或預設 Text 格式 ===");
        ReportExporter textExporter = createExporter("xml"); // 不支援回傳 TextExporter
        exportReport(textExporter, "月度銷售", data);

        System.out.println("\n=== 測試 4：values 為 null 測試 ===");
        exportReport(csvExporter, "空報表", null);
    }
}