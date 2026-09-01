package com.example.data.repository

import com.example.data.model.EnglishGrammarResponse
import com.example.data.model.EvaluateResponse
import com.example.data.model.GrammarExample
import com.example.data.model.MistakeAnalysisResponse
import com.example.data.model.MistakePatternItem
import com.example.data.model.MockPaperGroup
import com.example.data.model.MockPaperResponse
import com.example.data.model.MockTestResponse
import com.example.data.model.MockTestSection
import com.example.data.model.QuestionItem
import com.example.data.model.QuizResponse
import com.example.data.model.RubricItem

object OfflineSebaBank {

    fun getFallbackQuiz(subject: String, chapter: String): QuizResponse {
        val questions = when {
            subject.contains("Math", ignoreCase = true) -> getMathQuestions(chapter)
            subject.contains("Science", ignoreCase = true) -> getScienceQuestions(chapter)
            subject.contains("Social", ignoreCase = true) -> getSocialScienceQuestions(chapter)
            subject.contains("Assamese", ignoreCase = true) -> getAssameseQuestions(chapter)
            else -> getEnglishQuestions(chapter)
        }

        return QuizResponse(
            mode = "quiz",
            subject = subject,
            chapter = chapter,
            questions = questions,
            weak_topic_focus_applied = true
        )
    }

    private fun getMathQuestions(chapter: String): List<QuestionItem> = listOf(
        QuestionItem(
            id = "math_q1",
            type = "MCQ",
            marks = 1,
            question_assamese = "তলৰ কোনটো এটা অপৰিমেয় সংখ্যা (Irrational Number)?",
            question_english_terms = listOf("Irrational Number", "Rational Number"),
            options = listOf("A) √4", "B) √9", "C) √7", "D) 2/3"),
            correct_answer = "C) √7",
            solution_steps = listOf(
                "√4 = 2 (পৰিমেয়)",
                "√9 = 3 (পৰিমেয়)",
                "7 এটা মৌলিক সংখ্যা আৰু ই কোনো পূৰ্ণবৰ্গ নহয়, গতিকে √7 এটা অপৰিমেয় সংখ্যা (Irrational Number)।"
            ),
            concept_tag = "Real Numbers - Irrationality",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA পাঠ্যপুথি অধ্যায় ১: বাস্তৱ সংখ্যা অনুসৰি।"
        ),
        QuestionItem(
            id = "math_q2",
            type = "MCQ",
            marks = 1,
            question_assamese = "দ্বিঘাত সমীকৰণ ax² + bx + c = 0 ৰ ভেদ নিৰূপক (Discriminant, D) কি হ'লে মূল দুটা বাস্তৱ আৰু সমান হ'ব?",
            question_english_terms = listOf("Discriminant", "Quadratic Equation", "Real and Equal Roots"),
            options = listOf("A) D > 0", "B) D = 0", "C) D < 0", "D) D ≤ 0"),
            correct_answer = "B) D = 0",
            solution_steps = listOf(
                "দ্বিঘাত সমীকৰণৰ ভেদ নিৰূপক D = b² - 4ac",
                "যদি D > 0 হয়, মূল দুটা বাস্তৱ আৰু পৃথক হ'ব।",
                "যদি D = 0 হয়, মূল দুটা বাস্তৱ আৰু সমান (Real and Equal) হ'ব।",
                "যদি D < 0 হয়, কোনো বাস্তৱ মূল নাথাকে।"
            ),
            concept_tag = "Quadratic Equations - Nature of Roots",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "NCERT/SEBA অধ্যায় ৪ অনুসৰি।"
        ),
        QuestionItem(
            id = "math_q3",
            type = "short_answer",
            marks = 2,
            question_assamese = "সমান্তৰ প্ৰগতি (Arithmetic Progression) 3, 8, 13, 18,... ৰ ১০ম পদটো নিৰ্ণয় কৰা।",
            question_english_terms = listOf("Arithmetic Progression", "nth term", "Common Difference"),
            options = emptyList(),
            correct_answer = "48",
            solution_steps = listOf(
                "প্ৰথম পদ (a) = 3",
                "সাধাৰণ অন্তৰ (d) = 8 - 3 = 5",
                "n-তম পদৰ সূত্ৰ: an = a + (n - 1)d",
                "১০ম পদ a₁₀ = 3 + (10 - 1) × 5 = 3 + 9 × 5 = 3 + 45 = 48"
            ),
            concept_tag = "Arithmetic Progression - nth Term",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA গণিত অধ্যায় ৫।"
        ),
        QuestionItem(
            id = "math_q4",
            type = "true_false",
            marks = 1,
            question_assamese = "সকলো সমবাহু ত্ৰিভুজ (Equilateral Triangles) পৰস্পৰ সদৃশ (Similar)।",
            question_english_terms = listOf("Equilateral Triangles", "Similar Triangles"),
            options = listOf("সত্য (True)", "মিছা (False)"),
            correct_answer = "সত্য (True)",
            solution_steps = listOf(
                "সকলো সমবাহু ত্ৰিভুজৰ প্ৰতিটো কোণ 60°।",
                "AAA সদৃশতা স্বীকাৰ্য্য অনুসৰি কোণবোৰ সমান হোৱাৰ বাবে সকলো সমবাহু ত্ৰিভুজ সদৃশ।"
            ),
            concept_tag = "Triangles - Similarity",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA পাঠ্যপুথি জ্যামিতি।"
        ),
        QuestionItem(
            id = "math_q5",
            type = "MCQ",
            marks = 1,
            question_assamese = "sin² 30° + cos² 30° ৰ মান কিমান হ'ব?",
            question_english_terms = listOf("Trigonometric Identity", "Trigonometry"),
            options = listOf("A) 0", "B) 1/2", "C) 1", "D) √3/2"),
            correct_answer = "C) 1",
            solution_steps = listOf(
                "ত্ৰিকোণমিতিক অভেদ: sin² θ + cos² θ = 1 (সকলো কোণ θ ৰ বাবে)",
                "sin 30° = 1/2 ⇒ sin² 30° = 1/4",
                "cos 30° = √3/2 ⇒ cos² 30° = 3/4",
                "1/4 + 3/4 = 4/4 = 1"
            ),
            concept_tag = "Trigonometry - Identities",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA ত্ৰিকোণমিতি অধ্যায় ৮।"
        )
    )

    private fun getScienceQuestions(chapter: String): List<QuestionItem> = listOf(
        QuestionItem(
            id = "sci_q1",
            type = "MCQ",
            marks = 1,
            question_assamese = "চুনাপানীৰ (Lime water) মাজেৰে কাৰ্বন ডাই-অক্সাইড (CO₂) গেছ যাবলৈ দিলে চুনাপানী কি কাৰণে গাখীৰৰ দৰে বগা হয়?",
            question_english_terms = listOf("Lime water", "Carbon dioxide", "Calcium Carbonate", "Precipitate"),
            options = listOf(
                "A) কেলছিয়াম অক্সাইড (CaO) গঠনৰ বাবে",
                "B) অদ্ৰৱণীয় কেলছিয়াম কাৰ্বনেট (CaCO₃) গঠনৰ বাবে",
                "C) কেলছিয়াম হাইড্ৰক্সাইড গঠনৰ বাবে",
                "D) হাইড্ৰজেন গেছ নিৰ্গমনৰ বাবে"
            ),
            correct_answer = "B) অদ্ৰৱণীয় কেলছিয়াম কাৰ্বনেট (CaCO₃) গঠনৰ বাবে",
            solution_steps = listOf(
                "ৰাসায়নিক সমীকৰণ: Ca(OH)₂(aq) + CO₂(g) → CaCO₃(s)↓ + H₂O(l)",
                "CaCO₃ (কেলছিয়াম কাৰ্বনেট) অদ্ৰৱণীয় হোৱাৰ বাবে ই বগা অধঃক্ষেপ (white precipitate) সৃষ্টি কৰে।"
            ),
            concept_tag = "Chemical Reactions - Precipitation",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA বিজ্ঞান অধ্যায় ১।"
        ),
        QuestionItem(
            id = "sci_q2",
            type = "MCQ",
            marks = 1,
            question_assamese = "বৃক্কৰ (Kidney) গঠনমূলক আৰু কাৰ্য্যকৰী এককক কি বুলি কোৱা হয়?",
            question_english_terms = listOf("Kidney", "Nephron", "Neuron", "Life Processes"),
            options = listOf("A) নিউৰণ (Neuron)", "B) নেফ্ৰন (Nephron)", "C) এলভিঅ'লাই (Alveoli)", "D) ভিলাই (Villi)"),
            correct_answer = "B) নেফ্ৰন (Nephron)",
            solution_steps = listOf(
                "বৃক্কৰ ক্ষুদ্ৰ পৰিস্ৰাৱক একক হ'ল নেফ্ৰন (Nephron)।",
                "নিউৰণ হ'ল স্নায়ুতন্ত্ৰৰ একক।"
            ),
            concept_tag = "Life Processes - Excretion",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA বিজ্ঞান অধ্যায় ৬: জীৱন প্ৰক্ৰিয়া।"
        ),
        QuestionItem(
            id = "sci_q3",
            type = "MCQ",
            marks = 1,
            question_assamese = "বিদ্যুৎ প্ৰবাহৰ SI একক কি?",
            question_english_terms = listOf("Electric Current", "SI Unit", "Ampere", "Volt"),
            options = listOf("A) ভল্ট (Volt)", "B) ওম (Ohm)", "C) এম্পিয়াৰ (Ampere)", "D) ৱাট (Watt)"),
            correct_answer = "C) এম্পিয়াৰ (Ampere)",
            solution_steps = listOf(
                "বিদ্যুৎ প্ৰবাহ (I) = Q/t (আধান/সময়)",
                "ইয়াৰ SI একক হ'ল এম্পিয়াৰ (Ampere, A)।"
            ),
            concept_tag = "Electricity - Current & Units",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA বিজ্ঞান অধ্যায় ১২: বিদ্যুৎ।"
        ),
        QuestionItem(
            id = "sci_q4",
            type = "short_answer",
            marks = 2,
            question_assamese = "প্ৰভাৱী চৰিত্ৰ (Dominant trait) আৰু অপ্ৰভাৱী চৰিত্ৰৰ (Recessive trait) মূল পাৰ্থক্য কি?",
            question_english_terms = listOf("Dominant trait", "Recessive trait", "Heredity"),
            options = emptyList(),
            correct_answer = "প্ৰভাৱী চৰিত্ৰই F₁ জনুত নিজকে প্ৰকাশ কৰিব পাৰে, কিন্তু অপ্ৰভাৱী চৰিত্ৰই প্ৰভাৱী কাৰকৰ উপস্থিতিত নিজকে প্ৰকাশ কৰিব নোৱাৰে।",
            solution_steps = listOf(
                "Dominant trait: প্ৰথম অপত্য জনুত (F₁) প্ৰকাশ পোৱা বৈশিষ্ট্য (যেনে: ওখ গছ 'T')।",
                "Recessive trait: F₁ জনুত সুপ্ত হৈ থকা আৰু কেৱল সমযুগ্মজী অৱস্থাতহে (যেনে: 'tt') প্ৰকাশ পোৱা বৈশিষ্ট্য।"
            ),
            concept_tag = "Heredity - Mendelian Genetics",
            difficulty = "topper_level",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA বিজ্ঞান অধ্যায় ৯: বংশগতি।"
        )
    )

    private fun getSocialScienceQuestions(chapter: String): List<QuestionItem> = listOf(
        QuestionItem(
            id = "soc_q1",
            type = "MCQ",
            marks = 1,
            question_assamese = "বংগ বিভাজন (Partition of Bengal) কোন চনত আৰু কাৰ কাৰ্য্যকালত কাৰ্যকৰী কৰা হৈছিল?",
            question_english_terms = listOf("Partition of Bengal", "Lord Curzon", "Swadeshi Movement"),
            options = listOf(
                "A) ১৯০৫ চনত লৰ্ড কাৰ্জনৰ দ্বাৰা",
                "B) ১৯১১ চনত লৰ্ড হাৰ্ডিঞ্জৰ দ্বাৰা",
                "C) ১৮৫৭ চনত লৰ্ড ডেলহাউচিৰ দ্বাৰা",
                "D) ১৯৪৭ চনত লৰ্ড মাউণ্টবেটেনৰ দ্বাৰা"
            ),
            correct_answer = "A) ১৯০৫ চনত লৰ্ড কাৰ্জনৰ দ্বাৰা",
            solution_steps = listOf(
                "১৯০৫ চনৰ ১৬ অক্টোবৰত ভাইচৰয় লৰ্ড কাৰ্জনে বংগ বিভাজন কাৰ্যকৰী কৰে।",
                "ইয়াৰ প্ৰতিবাদতেই সমগ্ৰ দেশত স্বদেশী আন্দোলন গঢ়ি উঠিছিল।"
            ),
            concept_tag = "History - Partition of Bengal",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA সমাজ বিজ্ঞান ইতিহাস খণ্ড অধ্যায় ১।"
        ),
        QuestionItem(
            id = "soc_q2",
            type = "MCQ",
            marks = 1,
            question_assamese = "অসমৰ কৃষক বিদ্ৰোহৰ ভিতৰত 'পথৰুঘাটৰ ৰণ' (Patharughat Battle) কোন চনত সংঘটিত হৈছিল?",
            question_english_terms = listOf("Patharughat Battle", "Peasant Revolt", "Assam History"),
            options = listOf("A) ১৮৬১ চনত", "B) ১৮৯৪ চনত", "C) ১৮৯৩ চনত", "D) ১৯২১ চনত"),
            correct_answer = "B) ১৮৯৪ চনত",
            solution_steps = listOf(
                "১৮৯৪ চনৰ ২৮ জানুৱাৰীত দৰং জিলাৰ পথৰুঘাটত ব্ৰিটিছৰ বৰ্ধিত খাজনাৰ বিৰুদ্ধে কৃষকসকলে বিদ্ৰোহ কৰে য'ত বহু কৃষক ছহিদ হয়।"
            ),
            concept_tag = "History - Assam Peasant Revolts",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA সমাজ বিজ্ঞান ইতিহাস অধ্যায় ৩।"
        )
    )

    private fun getAssameseQuestions(chapter: String): List<QuestionItem> = listOf(
        QuestionItem(
            id = "asm_q1",
            type = "MCQ",
            marks = 1,
            question_assamese = "'মোৰ মাতৃমুখ দৰ্শন' কবিতাটোৰ কবি কোন?",
            question_english_terms = listOf("Assamese Literature", "Poetry"),
            options = listOf(
                "A) লক্ষ্মীনাথ বেজবৰুৱা",
                "B) হেমচন্দ্ৰ গোস্বামী",
                "C) চন্দ্ৰকুমাৰ আগৰৱালা",
                "D) আনন্দ চন্দ্ৰ আগৰৱালা"
            ),
            correct_answer = "A) লক্ষ্মীনাথ বেজবৰুৱা",
            solution_steps = listOf(
                "ৰসৰাজ লক্ষ্মীনাথ বেজবৰুৱাই 'মোৰ মাতৃমুখ দৰ্শন' কবিতাটি ৰচনা কৰিছিল, য'ত মাতৃভূমি অসমৰ প্ৰতি অপৰিসীম শ্ৰদ্ধা প্ৰকাশ পাইছে।"
            ),
            concept_tag = "Assamese Literature - Poetry",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA অসমীয়া (MIL) পাঠ্যপুথি।"
        ),
        QuestionItem(
            id = "asm_q2",
            type = "MCQ",
            marks = 1,
            question_assamese = "'গাভৰু কালত লোকলৈ আশা, যেনিবা বোঁৱতী সুঁতিৰ বালি' — তলৰ কোনটো জতুৱা ঠাঁচৰ অৰ্থ 'অভাৱৰ সময়ত উপযাচি সহায় কৰা' বুজায়?",
            question_english_terms = listOf("Idioms", "Assamese Grammar"),
            options = listOf("A) উৰহীৰ গছৰ ওৰ", "B) আকালৰ বাঘী", "C) আঙুলি কাটি বুৰা কৰা", "D) কাঁচিজোন দেখা"),
            correct_answer = "B) আকালৰ বাঘী",
            solution_steps = listOf(
                "'আকালৰ বাঘী' জতুৱা ঠাঁচটোৱে টান বা বিপদৰ সময়ত সহায়কাৰী হিচাপে উপকাৰ সাধন কৰা বুজায়।"
            ),
            concept_tag = "Assamese Grammar - Idioms",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA অসমীয়া ব্যাকৰণ।"
        )
    )

    private fun getEnglishQuestions(chapter: String): List<QuestionItem> = listOf(
        QuestionItem(
            id = "eng_q1",
            type = "MCQ",
            marks = 1,
            question_assamese = "Lencho-এ ঈশ্বৰলৈ চিঠিখন কিমান টকা (Pesos) সাহায্য বিচাৰি লিখিছিল?",
            question_english_terms = listOf("A Letter to God", "Lencho", "Pesos", "Postmaster"),
            options = listOf("A) 50 Pesos", "B) 70 Pesos", "C) 100 Pesos", "D) 1000 Pesos"),
            correct_answer = "C) 100 Pesos",
            solution_steps = listOf(
                "Lencho requested 100 pesos from God to sow his field again and survive until the next crop.",
                "The postmaster was only able to collect and send 70 pesos."
            ),
            concept_tag = "First Flight - A Letter to God",
            difficulty = "easy",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA English Class 10 Prose Unit 1."
        ),
        QuestionItem(
            id = "eng_q2",
            type = "MCQ",
            marks = 1,
            question_assamese = "Change the voice: 'The teacher praised the boy.'",
            question_english_terms = listOf("Voice Change", "Active to Passive", "Past Simple"),
            options = listOf(
                "A) The boy is praised by the teacher.",
                "B) The boy was praised by the teacher.",
                "C) The boy had been praised by the teacher.",
                "D) The boy was being praised by the teacher."
            ),
            correct_answer = "B) The boy was praised by the teacher.",
            solution_steps = listOf(
                "Active: Subject (The teacher) + Verb in Past Simple (praised) + Object (the boy)",
                "Passive Rule: Object + was/were + V3 (praised) + by + Subject",
                "Correct Passive: 'The boy was praised by the teacher.'"
            ),
            concept_tag = "English Grammar - Voice Change",
            difficulty = "medium",
            grounded_in = "general_knowledge",
            confidence_flag = "HIGH",
            verify_note = "SEBA Grammar Syllabus: Active/Passive Voice."
        )
    )

    fun getFallbackGrammar(topic: String): EnglishGrammarResponse {
        val topicNormalized = topic.ifBlank { "Tenses" }
        return EnglishGrammarResponse(
            mode = "english_grammar",
            grammar_topic = topicNormalized,
            rule_explanation_english = "In SEBA Class 10 Board Examinations, questions on $topicNormalized test fundamental accuracy, tense consistency, and structural transformation. Master the core rules, observe the standard transformations, and solve the targeted practice set below.",
            examples = listOf(
                GrammarExample(
                    sentence = "Direct: He said to me, 'I am reading a novel now.'",
                    correction_or_transformation = "Indirect: He told me that he was reading a novel then."
                ),
                GrammarExample(
                    sentence = "Tense Error: If it will rain, we shall cancel the match.",
                    correction_or_transformation = "Correct: If it rains, we shall cancel the match. (Conditional Type 1)"
                ),
                GrammarExample(
                    sentence = "Active: Who wrote this letter?",
                    correction_or_transformation = "Passive: By whom was this letter written?"
                )
            ),
            practice_questions = listOf(
                QuestionItem(
                    id = "gram_p1",
                    type = "MCQ",
                    marks = 1,
                    question_assamese = "Correct tense: 'He (live) in Guwahati since 2015.'",
                    question_english_terms = listOf("Present Perfect Continuous", "Since/For"),
                    options = listOf(
                        "A) is living",
                        "B) has been living",
                        "C) lived",
                        "D) had lived"
                    ),
                    correct_answer = "B) has been living",
                    solution_steps = listOf(
                        "Action started in the past (2015) and is still continuing ('since 2015').",
                        "We use Present Perfect Continuous: Subject + has/have been + V-ing.",
                        "Hence: 'has been living'."
                    ),
                    concept_tag = "Grammar - Correct Tense",
                    difficulty = "medium",
                    grounded_in = "general_knowledge",
                    confidence_flag = "HIGH",
                    verify_note = "SEBA English Question Paper Standard Grammar Question."
                ),
                QuestionItem(
                    id = "gram_p2",
                    type = "MCQ",
                    marks = 1,
                    question_assamese = "Appropriate Preposition: 'He is junior ___ me in service.'",
                    question_english_terms = listOf("Preposition", "Junior/Senior"),
                    options = listOf("A) than", "B) to", "C) with", "D) from"),
                    correct_answer = "B) to",
                    solution_steps = listOf(
                        "Comparative adjectives ending in -ior (junior, senior, superior, inferior, prior) take the preposition 'to', not 'than'.",
                        "Correct: 'He is junior to me.'"
                    ),
                    concept_tag = "Grammar - Prepositions",
                    difficulty = "easy",
                    grounded_in = "general_knowledge",
                    confidence_flag = "HIGH",
                    verify_note = "SEBA English Question Pattern."
                ),
                QuestionItem(
                    id = "gram_p3",
                    type = "MCQ",
                    marks = 1,
                    question_assamese = "Subject-Verb Concord: 'Neither the teacher nor the students ___ present.'",
                    question_english_terms = listOf("Subject-Verb Concord", "Neither...nor"),
                    options = listOf("A) was", "B) were", "C) is", "D) has"),
                    correct_answer = "B) were",
                    solution_steps = listOf(
                        "When subjects are connected by 'neither...nor', the verb agrees with the closer subject.",
                        "Here 'the students' (plural) is closer to the verb, so plural verb 'were' is used."
                    ),
                    concept_tag = "Grammar - Concord",
                    difficulty = "medium",
                    grounded_in = "general_knowledge",
                    confidence_flag = "HIGH",
                    verify_note = "SEBA Class 10 Grammar Rule."
                )
            )
        )
    }

    fun getFallbackMockPaper(subject: String): MockPaperResponse {
        return MockPaperResponse(
            mode = "mock_paper",
            subject = subject,
            full_marks = 90,
            time_hours = 3.0,
            pattern_confidence = "HIGH",
            pattern_note = "SEBA Class 10 official paper structure (Group A: MCQs & 1-mark objectives, Group B: 2-3 marks conceptual, Group C: Long answers with internal OR choices).",
            groups = listOf(
                MockPaperGroup(
                    group_name = "Group A (১ নম্বৰীয়া বহুবিকল্পী আৰু চমু প্ৰশ্ন / 1-Mark Objective)",
                    instructions_assamese = "সকলো প্ৰশ্নৰ উত্তৰ দিয়া বাধ্যতামূলক। প্ৰতিটো প্ৰশ্নৰ মান ১।",
                    questions = listOf(
                        QuestionItem(
                            id = "mp_a1",
                            type = "MCQ",
                            marks = 1,
                            question_assamese = "তলৰ কোনটো এটা প্ৰতিষ্ঠাপিত ৰাসায়নিক সমীকৰণৰ উদাহৰণ?",
                            question_english_terms = listOf("Chemical Equation", "Balanced Equation"),
                            options = listOf(
                                "A) H₂ + O₂ → H₂O",
                                "B) 2H₂ + O₂ → 2H₂O",
                                "C) Mg + O₂ → 2MgO",
                                "D) C + O₂ → CO"
                            ),
                            correct_answer = "B) 2H₂ + O₂ → 2H₂O",
                            solution_steps = listOf("বাওঁফালে 4টা H আৰু 2টা O; সোঁফালে 4টা H আৰু 2টা O থকাৰ বাবে ই সমতুলিত (Balanced)।"),
                            concept_tag = "Group A Objective",
                            difficulty = "easy",
                            grounded_in = "general_knowledge",
                            confidence_flag = "HIGH"
                        ),
                        QuestionItem(
                            id = "mp_a2",
                            type = "one_word",
                            marks = 1,
                            question_assamese = "উদ্ভিদ দেহত পানী আৰু খনিজ লৱণ পৰিবহন কৰা কলাবিধৰ নাম কি?",
                            question_english_terms = listOf("Xylem", "Plant Tissue"),
                            options = emptyList(),
                            correct_answer = "জাইলেম (Xylem)",
                            solution_steps = listOf("জাইলেমে শিপাৰ পৰা পাতলৈ পানী পৰিবহন কৰে।"),
                            concept_tag = "Group A Objective",
                            difficulty = "easy",
                            grounded_in = "general_knowledge",
                            confidence_flag = "HIGH"
                        )
                    )
                ),
                MockPaperGroup(
                    group_name = "Group B (২-৩ নম্বৰীয়া চমু উত্তৰৰ প্ৰশ্ন / Short Answer)",
                    instructions_assamese = "প্ৰয়োজনীয় গণনা আৰু কাৰণ স্পষ্টকৈ উল্লেখ কৰা।",
                    questions = listOf(
                        QuestionItem(
                            id = "mp_b1",
                            type = "short_answer",
                            marks = 2,
                            question_assamese = "অক্সিডেচন (জাৰণ) আৰু ৰিডাকচন (বিজাৰণ)ৰ মাজৰ পাৰ্থক্য লিখা।",
                            question_english_terms = listOf("Oxidation", "Reduction", "Redox Reaction"),
                            options = emptyList(),
                            correct_answer = "জাৰণত অক্সিজেন লাভ বা হাইড্ৰজেন হেৰুওৱা হয়। বিজাৰণত হাইড্ৰজেন লাভ বা অক্সিজেন হেৰুওৱা হয়।",
                            solution_steps = listOf(
                                "জাৰণ (Oxidation): অক্সিজেন যোগ হোৱা বা হাইড্ৰজেন অপসাৰণ।",
                                "বিজাৰণ (Reduction): হাইড্ৰজেন যোগ হোৱা বা অক্সিজেন অপসাৰণ।"
                            ),
                            concept_tag = "Group B Short Answer",
                            difficulty = "medium",
                            grounded_in = "general_knowledge",
                            confidence_flag = "HIGH"
                        )
                    )
                ),
                MockPaperGroup(
                    group_name = "Group C (দীঘলীয়া প্ৰশ্ন আৰু বিকল্প / Long Answer with OR Choice)",
                    instructions_assamese = "তলৰ যিকোনো এটা প্ৰশ্নৰ বহলাই উত্তৰ দিয়া (Internal Choice)।",
                    questions = listOf(
                        QuestionItem(
                            id = "mp_c1",
                            type = "short_answer",
                            marks = 5,
                            question_assamese = "মানুহৰ হৃদপিণ্ডৰ এটা পৰিষ্কাৰ চিহ্নিত চিত্ৰ আঁকি তেজৰ দ্বৈত সংবহন (Double Circulation) পদ্ধতি ব্যাখ্যা কৰা।",
                            question_english_terms = listOf("Human Heart", "Double Circulation", "Systemic & Pulmonary"),
                            options = emptyList(),
                            correct_answer = "হৃদপিণ্ডৰ চাৰিটা কোঠালী (দুটা অলিন্দ আৰু দুটা নিলয়) থাকে। তেজ একেবাৰ শৰীৰত সংবহন হওঁতে হৃদপিণ্ডৰ মাজেৰে দুবাৰ পাৰ হোৱা পদ্ধতিক দ্বৈত সংবহন বোলে।",
                            solution_steps = listOf(
                                "১. হৃদপিণ্ডৰ চতুষ্কোঠালী গঠন চিত্ৰসহ ব্যাখ্যা।",
                                "২. হাওঁফাঁও সংবহন (Pulmonary) আৰু দৈহিক সংবহন (Systemic) বৰ্ণনা।",
                                "৩. অক্সিজেনযুক্ত আৰু অক্সিজেনবিহীন তেজ পৃথক কৰি ৰখাৰ গুৰুত্ব।"
                            ),
                            concept_tag = "Group C Long Answer",
                            difficulty = "topper_level",
                            grounded_in = "general_knowledge",
                            confidence_flag = "HIGH",
                            or_question_assamese = "বিকল্প প্ৰশ্ন (OR): মানুহৰ নেফ্ৰনৰ গঠন আৰু প্ৰস্ৰাৱ গঠনৰ তিনিটা মূল স্তৰ (Ultrafiltration, Reabsorption, Secretion) বৰ্ণনা কৰা।"
                        )
                    )
                )
            )
        )
    }

    fun getFallbackMockTest(subject: String): MockTestResponse {
        return MockTestResponse(
            mode = "mock_test",
            subject = subject,
            total_marks = 30,
            suggested_time_minutes = 60,
            sections = listOf(
                MockTestSection(
                    section_name = "Section A: Multiple Choice Questions (1 Mark Each)",
                    marks_each = 1,
                    questions = getMathQuestions("All").take(5)
                ),
                MockTestSection(
                    section_name = "Section B: Conceptual & Problem Solving (2-3 Marks Each)",
                    marks_each = 2,
                    questions = getScienceQuestions("All").take(3)
                )
            ),
            marking_scheme_note = "SEBA Class 10 Marking Scheme: Correct concept statement = 1 mark, Step calculation = 1 mark, Final answer with SI unit = 1 mark."
        )
    }
}
