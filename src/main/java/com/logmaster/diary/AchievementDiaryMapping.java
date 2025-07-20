package com.logmaster.diary;

import java.util.HashMap;
import java.util.Map;

/**
 * Achievement diary mapping from old varbit/value format to new area/tier format
 * Auto-generated from JSON files
 */
public class AchievementDiaryMapping {
    
    public static class DiaryInfo {
        public final int varbit;
        public final int value;
        public final String region;
        public final String difficulty;
        
        public DiaryInfo(int varbit, int value, String region, String difficulty) {
            this.varbit = varbit;
            this.value = value;
            this.region = region;
            this.difficulty = difficulty;
        }
    }
    
    private static final Map<String, DiaryInfo> DIARY_MAPPING = new HashMap<>();
    
    static {
        DIARY_MAPPING.put("ardougne_easy", new DiaryInfo(4458, 1, "ardougne", "easy"));
        DIARY_MAPPING.put("ardougne_medium", new DiaryInfo(4459, 1, "ardougne", "medium"));
        DIARY_MAPPING.put("ardougne_hard", new DiaryInfo(4460, 1, "ardougne", "hard"));
        DIARY_MAPPING.put("ardougne_elite", new DiaryInfo(4461, 1, "ardougne", "elite"));
        DIARY_MAPPING.put("desert_easy", new DiaryInfo(4483, 1, "desert", "easy"));
        DIARY_MAPPING.put("desert_medium", new DiaryInfo(4484, 1, "desert", "medium"));
        DIARY_MAPPING.put("desert_hard", new DiaryInfo(4485, 1, "desert", "hard"));
        DIARY_MAPPING.put("desert_elite", new DiaryInfo(4486, 1, "desert", "elite"));
        DIARY_MAPPING.put("falador_easy", new DiaryInfo(4462, 1, "falador", "easy"));
        DIARY_MAPPING.put("falador_medium", new DiaryInfo(4463, 1, "falador", "medium"));
        DIARY_MAPPING.put("falador_hard", new DiaryInfo(4464, 1, "falador", "hard"));
        DIARY_MAPPING.put("falador_elite", new DiaryInfo(4465, 1, "falador", "elite"));
        DIARY_MAPPING.put("fremennik_easy", new DiaryInfo(4491, 1, "fremennik", "easy"));
        DIARY_MAPPING.put("fremennik_medium", new DiaryInfo(4492, 1, "fremennik", "medium"));
        DIARY_MAPPING.put("fremennik_hard", new DiaryInfo(4493, 1, "fremennik", "hard"));
        DIARY_MAPPING.put("fremennik_elite", new DiaryInfo(4494, 1, "fremennik", "elite"));
        DIARY_MAPPING.put("kandarin_easy", new DiaryInfo(4475, 1, "kandarin", "easy"));
        DIARY_MAPPING.put("kandarin_medium", new DiaryInfo(4476, 1, "kandarin", "medium"));
        DIARY_MAPPING.put("kandarin_hard", new DiaryInfo(4477, 1, "kandarin", "hard"));
        DIARY_MAPPING.put("kandarin_elite", new DiaryInfo(4478, 1, "kandarin", "elite"));
        DIARY_MAPPING.put("karamja_easy", new DiaryInfo(3578, 2, "karamja", "easy"));
        DIARY_MAPPING.put("karamja_medium", new DiaryInfo(3599, 2, "karamja", "medium"));
        DIARY_MAPPING.put("karamja_hard", new DiaryInfo(3611, 2, "karamja", "hard"));
        DIARY_MAPPING.put("karamja_elite", new DiaryInfo(4566, 1, "karamja", "elite"));
        DIARY_MAPPING.put("kourend & kebos_easy", new DiaryInfo(7925, 1, "kourend & kebos", "easy"));
        DIARY_MAPPING.put("kourend & kebos_medium", new DiaryInfo(7926, 1, "kourend & kebos", "medium"));
        DIARY_MAPPING.put("kourend & kebos_hard", new DiaryInfo(7927, 1, "kourend & kebos", "hard"));
        DIARY_MAPPING.put("kourend & kebos_elite", new DiaryInfo(7928, 1, "kourend & kebos", "elite"));
        DIARY_MAPPING.put("lumbridge & draynor_easy", new DiaryInfo(4495, 1, "lumbridge & draynor", "easy"));
        DIARY_MAPPING.put("lumbridge & draynor_medium", new DiaryInfo(4496, 1, "lumbridge & draynor", "medium"));
        DIARY_MAPPING.put("lumbridge & draynor_hard", new DiaryInfo(4497, 1, "lumbridge & draynor", "hard"));
        DIARY_MAPPING.put("lumbridge & draynor_elite", new DiaryInfo(4498, 1, "lumbridge & draynor", "elite"));
        DIARY_MAPPING.put("morytania_easy", new DiaryInfo(4487, 1, "morytania", "easy"));
        DIARY_MAPPING.put("morytania_medium", new DiaryInfo(4488, 1, "morytania", "medium"));
        DIARY_MAPPING.put("morytania_hard", new DiaryInfo(4489, 1, "morytania", "hard"));
        DIARY_MAPPING.put("morytania_elite", new DiaryInfo(4490, 1, "morytania", "elite"));
        DIARY_MAPPING.put("varrock_easy", new DiaryInfo(4479, 1, "varrock", "easy"));
        DIARY_MAPPING.put("varrock_medium", new DiaryInfo(4480, 1, "varrock", "medium"));
        DIARY_MAPPING.put("varrock_hard", new DiaryInfo(4481, 1, "varrock", "hard"));
        DIARY_MAPPING.put("varrock_elite", new DiaryInfo(4482, 1, "varrock", "elite"));
        DIARY_MAPPING.put("western provinces_easy", new DiaryInfo(4471, 1, "western provinces", "easy"));
        DIARY_MAPPING.put("western provinces_medium", new DiaryInfo(4472, 1, "western provinces", "medium"));
        DIARY_MAPPING.put("western provinces_hard", new DiaryInfo(4473, 1, "western provinces", "hard"));
        DIARY_MAPPING.put("western provinces_elite", new DiaryInfo(4474, 1, "western provinces", "elite"));
        DIARY_MAPPING.put("wilderness_easy", new DiaryInfo(4466, 1, "wilderness", "easy"));
        DIARY_MAPPING.put("wilderness_medium", new DiaryInfo(4467, 1, "wilderness", "medium"));
        DIARY_MAPPING.put("wilderness_hard", new DiaryInfo(4468, 1, "wilderness", "hard"));
        DIARY_MAPPING.put("wilderness_elite", new DiaryInfo(4469, 1, "wilderness", "elite"));
    }

    public static DiaryInfo getDiaryInfo(String area, String tier) {
        return DIARY_MAPPING.get(area + "_" + tier);
    }
}