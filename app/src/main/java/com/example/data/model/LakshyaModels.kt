package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionItem(
    @Json(name = "id") val id: String = "",
    @Json(name = "type") val type: String = "MCQ", // MCQ | short_answer | one_word | true_false
    @Json(name = "marks") val marks: Int = 1,
    @Json(name = "question_assamese") val question_assamese: String = "",
    @Json(name = "question_english_terms") val question_english_terms: List<String> = emptyList(),
    @Json(name = "options") val options: List<String> = emptyList(),
    @Json(name = "correct_answer") val correct_answer: String = "",
    @Json(name = "solution_steps") val solution_steps: List<String> = emptyList(),
    @Json(name = "concept_tag") val concept_tag: String = "",
    @Json(name = "difficulty") val difficulty: String = "medium", // easy | medium | hard | topper_level
    @Json(name = "grounded_in") val grounded_in: String = "general_knowledge", // source_material | video_transcript | general_knowledge
    @Json(name = "confidence_flag") val confidence_flag: String = "HIGH", // HIGH | MEDIUM | LOW
    @Json(name = "verify_note") val verify_note: String = "",
    @Json(name = "video_title") val video_title: String? = null,
    @Json(name = "timestamp_reference") val timestamp_reference: String? = null,
    @Json(name = "or_question_assamese") val or_question_assamese: String? = null
)

@JsonClass(generateAdapter = true)
data class QuizResponse(
    @Json(name = "mode") val mode: String = "quiz",
    @Json(name = "subject") val subject: String = "",
    @Json(name = "chapter") val chapter: String = "",
    @Json(name = "questions") val questions: List<QuestionItem> = emptyList(),
    @Json(name = "weak_topic_focus_applied") val weak_topic_focus_applied: Boolean = false
)

@JsonClass(generateAdapter = true)
data class MockTestSection(
    @Json(name = "section_name") val section_name: String = "",
    @Json(name = "marks_each") val marks_each: Int = 1,
    @Json(name = "questions") val questions: List<QuestionItem> = emptyList()
)

@JsonClass(generateAdapter = true)
data class MockTestResponse(
    @Json(name = "mode") val mode: String = "mock_test",
    @Json(name = "subject") val subject: String = "",
    @Json(name = "total_marks") val total_marks: Int = 50,
    @Json(name = "suggested_time_minutes") val suggested_time_minutes: Int = 90,
    @Json(name = "sections") val sections: List<MockTestSection> = emptyList(),
    @Json(name = "marking_scheme_note") val marking_scheme_note: String = ""
)

@JsonClass(generateAdapter = true)
data class MockPaperGroup(
    @Json(name = "group_name") val group_name: String = "",
    @Json(name = "instructions_assamese") val instructions_assamese: String = "",
    @Json(name = "questions") val questions: List<QuestionItem> = emptyList()
)

@JsonClass(generateAdapter = true)
data class MockPaperResponse(
    @Json(name = "mode") val mode: String = "mock_paper",
    @Json(name = "subject") val subject: String = "",
    @Json(name = "full_marks") val full_marks: Int = 90,
    @Json(name = "time_hours") val time_hours: Double = 3.0,
    @Json(name = "pattern_confidence") val pattern_confidence: String = "HIGH",
    @Json(name = "pattern_note") val pattern_note: String = "",
    @Json(name = "groups") val groups: List<MockPaperGroup> = emptyList()
)

@JsonClass(generateAdapter = true)
data class RubricItem(
    @Json(name = "criterion") val criterion: String = "",
    @Json(name = "marks") val marks: Double = 0.0,
    @Json(name = "feedback_assamese") val feedback_assamese: String = ""
)

@JsonClass(generateAdapter = true)
data class EvaluateResponse(
    @Json(name = "mode") val mode: String = "evaluate",
    @Json(name = "question_id") val question_id: String = "",
    @Json(name = "marks_awarded") val marks_awarded: Double = 0.0,
    @Json(name = "marks_total") val marks_total: Double = 0.0,
    @Json(name = "rubric_breakdown") val rubric_breakdown: List<RubricItem> = emptyList(),
    @Json(name = "mistake_pattern_tag") val mistake_pattern_tag: String = "",
    @Json(name = "improvement_tip_assamese") val improvement_tip_assamese: String = ""
)

@JsonClass(generateAdapter = true)
data class MistakePatternItem(
    @Json(name = "pattern") val pattern: String = "",
    @Json(name = "frequency") val frequency: Int = 1,
    @Json(name = "affected_topics") val affected_topics: List<String> = emptyList(),
    @Json(name = "recommended_action_assamese") val recommended_action_assamese: String = ""
)

@JsonClass(generateAdapter = true)
data class MistakeAnalysisResponse(
    @Json(name = "mode") val mode: String = "mistake_analysis",
    @Json(name = "recurring_patterns") val recurring_patterns: List<MistakePatternItem> = emptyList(),
    @Json(name = "next_spaced_repetition_topics") val next_spaced_repetition_topics: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class GrammarExample(
    @Json(name = "sentence") val sentence: String = "",
    @Json(name = "correction_or_transformation") val correction_or_transformation: String = ""
)

@JsonClass(generateAdapter = true)
data class EnglishGrammarResponse(
    @Json(name = "mode") val mode: String = "english_grammar",
    @Json(name = "grammar_topic") val grammar_topic: String = "",
    @Json(name = "rule_explanation_english") val rule_explanation_english: String = "",
    @Json(name = "examples") val examples: List<GrammarExample> = emptyList(),
    @Json(name = "practice_questions") val practice_questions: List<QuestionItem> = emptyList()
)

@JsonClass(generateAdapter = true)
data class InsufficientDataResponse(
    @Json(name = "mode") val mode: String = "insufficient_data",
    @Json(name = "reason") val reason: String = "",
    @Json(name = "what_is_needed") val what_is_needed: List<String> = emptyList()
)

enum class SebaSubject(
    val id: String,
    val titleEnglish: String,
    val titleAssamese: String,
    val chapters: List<Pair<String, String>> // English, Assamese
) {
    MATHEMATICS(
        id = "Mathematics",
        titleEnglish = "Mathematics",
        titleAssamese = "গণিত",
        chapters = listOf(
            "Real Numbers" to "বাস্তৱ সংখ্যা (Real Numbers)",
            "Polynomials" to "বহুপদ (Polynomials)",
            "Pair of Linear Equations in Two Variables" to "দুটা চলকত ৰৈখিক সমীকৰণৰ যোৰ (Linear Equations)",
            "Quadratic Equations" to "দ্বিঘাত সমীকৰণ (Quadratic Equations)",
            "Arithmetic Progressions" to "সমান্তৰ প্ৰগতি (Arithmetic Progressions)",
            "Triangles" to "ত্ৰিভুজ (Triangles)",
            "Coordinate Geometry" to "স্থানাংক জ্যামিতি (Coordinate Geometry)",
            "Introduction to Trigonometry" to "ত্ৰিকোণমিতিৰ পৰিচয় (Trigonometry)",
            "Some Applications of Trigonometry" to "ত্ৰিকোণমিতিৰ কিছুমান প্ৰয়োগ (Heights & Distances)",
            "Circles" to "বৃত্ত (Circles)",
            "Constructions" to "অঙ্কন (Constructions)",
            "Areas Related to Circles" to "বৃত্ত সম্পৰ্কীয় কালি (Areas Related to Circles)",
            "Surface Areas and Volumes" to "পৃষ্ঠকালি আৰু আয়তন (Surface Areas & Volumes)",
            "Statistics" to "পৰিসংখ্যা (Statistics)",
            "Probability" to "সম্ভাৱিতা (Probability)"
        )
    ),
    SCIENCE(
        id = "Science",
        titleEnglish = "General Science",
        titleAssamese = "সাধাৰণ বিজ্ঞান",
        chapters = listOf(
            "Chemical Reactions and Equations" to "ৰাসায়নিক বিক্ৰিয়া আৰু সমীকৰণ (Chemical Reactions)",
            "Acids, Bases and Salts" to "এছিড, ক্ষাৰক আৰু লৱণ (Acids, Bases & Salts)",
            "Metals and Non-metals" to "ধাতু আৰু অধাতু (Metals & Non-metals)",
            "Carbon and its Compounds" to "কাৰ্বন আৰু তাৰ যৌগ (Carbon Compounds)",
            "Periodic Classification of Elements" to "মৌলৰ পৰ্যাবৃত্ত শ্ৰেণীবিভাজন (Periodic Table)",
            "Life Processes" to "জীৱন প্ৰক্ৰিয়া (Life Processes)",
            "Control and Coordination" to "নিয়ন্ত্ৰণ আৰু সমন্বয় (Control & Coordination)",
            "How do Organisms Reproduce" to "জীৱই কেনেকৈ বংশবৃদ্ধি কৰে (Reproduction)",
            "Heredity and Evolution" to "বংশগতি আৰু ক্ৰমবিকাশ (Heredity & Evolution)",
            "Light - Reflection and Refraction" to "পোহৰ-প্ৰতিফলন আৰু প্ৰতিসৰণ (Light)",
            "The Human Eye and Colourful World" to "মানুহৰ চকু আৰু বৰ্ণময় পৃথিৱী (Human Eye)",
            "Electricity" to "বিদ্যুৎ (Electricity)",
            "Magnetic Effects of Electric Current" to "বিদ্যুৎ প্ৰবাহৰ চুম্বকীয় ক্ৰিয়া (Magnetic Effects)",
            "Sources of Energy" to "শক্তিৰ উৎসসমূহ (Sources of Energy)",
            "Our Environment" to "আমাৰ পৰিৱেশ (Our Environment)",
            "Management of Natural Resources" to "প্ৰাকৃতিক সম্পদৰ ব্যৱস্থাপনা (Resource Management)"
        )
    ),
    SOCIAL_SCIENCE(
        id = "Social Science",
        titleEnglish = "Social Science",
        titleAssamese = "সমাজ বিজ্ঞান",
        chapters = listOf(
            "Advent of the Europeans in India" to "ভাৰতবৰ্ষলৈ ইউৰোপীয়সকলৰ আগমন (History)",
            "Partition of Bengal and Swadeshi Movement" to "বঙ্গ ভংগ আৰু স্বদেশী আন্দোলন (History)",
            "Anti-British Rising and Peasant Revolts in Assam" to "অসমত বৃটিছ বিৰোধী জাগৰণ আৰু কৃষক বিদ্ৰোহ (History)",
            "Indian Freedom Movement and National Awakening in Assam" to "ভাৰতৰ স্বাধীনতা আন্দোলন আৰু অসমত জাতীয় জাগৰণ (History)",
            "Cultural Heritage of India and North East" to "ভাৰত আৰু উত্তৰ-পূৰ্বাঞ্চলৰ সাংস্কৃতিক ঐতিহ্য (History)",
            "Economic Geography: Subject Matter and Resource" to "অৰ্থনৈতিক ভূগোল: বিষয়বস্তু আৰু সম্পদ (Geography)",
            "Environment and Environmental Problems" to "পৰিৱেশ আৰু পৰিৱেশৰ সমস্যা (Geography)",
            "Geography of India" to "ভাৰতবৰ্ষৰ ভূগোল (Geography)",
            "Geography of Assam" to "অসমৰ ভূগোল (Geography)",
            "Indian Democracy" to "ভাৰতীয় গণতন্ত্ৰ (Pol Science)",
            "International Organizations" to "আন্তৰ্জাতিক সংস্থা (Pol Science)",
            "Money and Banking" to "টকা আৰু বেংক ব্যৱস্থা (Economics)",
            "Economic Development" to "অৰ্থনৈতিক উন্নয়ন (Economics)"
        )
    ),
    ENGLISH(
        id = "English",
        titleEnglish = "English",
        titleAssamese = "ইংৰাজী",
        chapters = listOf(
            "A Letter to God" to "A Letter to God (Prose)",
            "Nelson Mandela: Long Walk to Freedom" to "Nelson Mandela (Prose)",
            "Coorg & Tea from Assam" to "Glimpses of India: Coorg & Assam",
            "Madam Rides the Bus" to "Madam Rides the Bus (Prose)",
            "The Midnight Visitor & A Question of Trust" to "Footprints without Feet",
            "The Hack Driver & Bholi" to "Footprints Supplementary",
            "Dust of Snow & Fire and Ice" to "Poetry (Robert Frost)",
            "A Tiger in the Zoo & Animals" to "Poetry Module",
            "SEBA English Grammar Masterclass" to "SEBA Comprehensive Grammar"
        )
    ),
    ASSAMESE(
        id = "Assamese",
        titleEnglish = "Assamese (MIL)",
        titleAssamese = "অসমীয়া (মাতৃভাষা)",
        chapters = listOf(
            "বৰগীত (Borgeet)" to "শ্ৰীমন্ত শংকৰদেৱৰ বৰগীত",
            "ছাত্ৰজীৱন আৰু সমাজসেৱা" to "ছাত্ৰজীৱন আৰু সমাজসেৱা (গদ্য)",
            "ভাৰতীয় সংস্কৃতি" to "ভাৰতীয় সংস্কৃতি (গদ্য)",
            "অসমৰ জনগোষ্ঠীৰ গাঁথনি" to "অসমৰ জনগোষ্ঠীৰ গাঁথনি আৰু সংস্কৃতি",
            "মোৰ মাতৃমুখ দৰ্শন" to "মোৰ মাতৃমুখ দৰ্শন (লক্ষ্মীনাথ বেজবৰুৱা)",
            "প্ৰিয়তমৰ চিঠি" to "প্ৰিয়তমৰ চিঠি (হেমচন্দ্ৰ গোস্বামী)",
            "জিকিৰ" to "জিকিৰ (আজান ফকীৰ)",
            "অসমীয়া ব্যাকৰণ: সন্ধি আৰু সমাস" to "সন্ধি আৰু সমাস (Grammar)",
            "অসমীয়া ব্যাকৰণ: ণত্ব-ষত্ব বিধি আৰু বাক্য পৰিবৰ্তন" to "ণত্ব-ষত্ব বিধি আৰু বাক্য পৰিবৰ্তন",
            "জতুৱা ঠাঁচ আৰু খণ্ডবাক্য" to "জতুৱা ঠাঁচ আৰু খণ্ডবাক্য"
        )
    )
}

val ENGLISH_GRAMMAR_TOPICS = listOf(
    "Tenses" to "Tenses & Correct Tense Form",
    "Voice (Active/Passive)" to "Active & Passive Voice Transformation",
    "Narration (Direct/Indirect)" to "Direct and Indirect Speech",
    "Articles & Determiners" to "Articles (A/An/The) and Determiners",
    "Prepositions" to "Appropriate Prepositions",
    "Subject-Verb Agreement" to "Subject-Verb Concord",
    "Modals" to "Modal Auxiliaries (Can, Could, May, Must, Should)",
    "Clauses" to "Relative, Noun & Adverbial Clauses",
    "Sentence Transformation" to "Simple, Complex & Compound Transformations",
    "Synonyms & Antonyms" to "Vocabulary & Word Power",
    "Idioms & Phrasal Verbs" to "Idioms, Phrases & Phrasal Verbs"
)
