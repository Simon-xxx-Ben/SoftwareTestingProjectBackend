package org.projects.backend.service.AlgorithmPlus;

public interface AIApiService {
    String updateChapterDegree();
    String getWrongQuestionsByQuestionIdThroughAI(Integer questionId);
    String getChartOfGrowingCapacityThroughAI();
    String getChartOfMasteryDegreeThroughAI();
}
