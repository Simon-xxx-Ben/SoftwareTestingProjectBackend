package org.projects.backend.service.AlgorithmPlus;

public interface AIApiService {
    boolean updateChapterDegree();
    String getWrongQuestionsByQuestionIdThroughAI(Integer questionId);
}
