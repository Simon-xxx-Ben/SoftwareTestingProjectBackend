package org.projects.backend.service.AlgorithmPro;

import java.util.List;
import java.util.Map;

public interface AdaptivePracticeService {
    boolean judgeAnswer(Integer id, String answer);
    Object getNextPractice(Map<String, String> data, List<Integer> practicedQuestionsIdList);
}
