class StudentGrade {
    private String studentId;
    private String name;
    private double assignmentScore; // 平時 50%
    private double midtermScore;    // 期中 20%
    private double finalExamScore;  // 期末 20%
    private double attendanceScore; // 出席 10%

    public StudentGrade(String studentId, String name, double assignmentScore, double midtermScore, double finalExamScore, double attendanceScore) {
        this.studentId = studentId;
        this.name = name;
        this.assignmentScore = validateScore(assignmentScore);
        this.midtermScore = validateScore(midtermScore);
        this.finalExamScore = validateScore(finalExamScore);
        this.attendanceScore = validateScore(attendanceScore);
    }

    private double validateScore(double score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return score;
    }

    public double calculateFinalScore() {
        return (assignmentScore * 0.50) + (midtermScore * 0.20) + (finalExamScore * 0.20) + (attendanceScore * 0.10);
    }

    public String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public boolean isFailed() {
        return calculateFinalScore() < 60;
    }

    public String getName() { return name; }
    public String getStudentId() { return studentId; }

    @Override
    public String toString() {
        return String.format("學號: %s | 姓名: %-4s | 平時: %5.1f | 期中: %5.1f | 期末: %5.1f | 出席: %5.1f | 總分: %5.1f | 等第: %s",
                studentId, name, assignmentScore, midtermScore, finalExamScore, attendanceScore, calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        StudentGrade[] grades = new StudentGrade[]{
            new StudentGrade("S01", "林小華", 85, 90, 88, 100),
            new StudentGrade("S02", "陳大同", 45, 50, 55, 60),
            new StudentGrade("S03", "黃美玲", 95, 92, 96, 90),
            new StudentGrade("S04", "王志明", 30, 40, 35, 50),
            new StudentGrade("S05", "李雅婷", 70, 75, 80, 85)
        };

        System.out.println("=== 班級學生成績列表 ===");
        double totalSum = 0;
        StudentGrade highestStudent = grades[0];

        for (StudentGrade sg : grades) {
            System.out.println(sg);
            double score = sg.calculateFinalScore();
            totalSum += score;
            if (score > highestStudent.calculateFinalScore()) {
                highestStudent = sg;
            }
        }

        double average = totalSum / grades.length;

        System.out.println("\n=== 班級統計報告 ===");
        System.out.printf("全班平均分數：%.2f 分\n", average);
        System.out.printf("最高分學生：%s (%s) - %.1f 分\n", highestStudent.getName(), highestStudent.getStudentId(), highestStudent.calculateFinalScore());

        System.out.println("\n=== 不及格名單 (總分 < 60) ===");
        boolean hasFailed = false;
        for (StudentGrade sg : grades) {
            if (sg.isFailed()) {
                System.out.printf("- %s (%s) : %.1f 分 (等第: %s)\n", sg.getName(), sg.getStudentId(), sg.calculateFinalScore(), sg.getLevel());
                hasFailed = true;
            }
        }
        if (!hasFailed) {
            System.out.println("無");
        }
    }
}