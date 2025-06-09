package org.projects.backend.service.AlgorithmPlus;

public interface AIApiService {
    String updateChapterDegree();
    String getWrongQuestionsByQuestionIdThroughAI(String questionId);
    String getChartOfGrowingCapacityThroughAI();
    String getChartOfMasteryDegreeThroughAI();
}
