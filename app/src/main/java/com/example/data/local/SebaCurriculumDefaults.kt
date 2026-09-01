package com.example.data.local

object SebaCurriculumDefaults {

    val DEFAULT_SUBJECTS = listOf(
        SebaSubjectEntity(
            id = "mathematics",
            code = "C1",
            titleEnglish = "Mathematics",
            titleAssamese = "গণিত",
            totalMarks = 100,
            theoryMarks = 90,
            internalAssessmentMarks = 10,
            passMarks = 30,
            iconKey = "calculator",
            colorHex = "#1E3A8A",
            displayOrder = 1
        ),
        SebaSubjectEntity(
            id = "science",
            code = "C2",
            titleEnglish = "General Science",
            titleAssamese = "সাধাৰণ বিজ্ঞান",
            totalMarks = 100,
            theoryMarks = 90,
            internalAssessmentMarks = 10,
            passMarks = 30,
            iconKey = "flask",
            colorHex = "#0D9488",
            displayOrder = 2
        ),
        SebaSubjectEntity(
            id = "social_science",
            code = "C3",
            titleEnglish = "Social Science",
            titleAssamese = "সমাজ বিজ্ঞান",
            totalMarks = 100,
            theoryMarks = 90,
            internalAssessmentMarks = 10,
            passMarks = 30,
            iconKey = "public",
            colorHex = "#7C3AED",
            displayOrder = 3
        ),
        SebaSubjectEntity(
            id = "english",
            code = "C4",
            titleEnglish = "English",
            titleAssamese = "ইংৰাজী",
            totalMarks = 100,
            theoryMarks = 90,
            internalAssessmentMarks = 10,
            passMarks = 30,
            iconKey = "menu_book",
            colorHex = "#B45309",
            displayOrder = 4
        ),
        SebaSubjectEntity(
            id = "assamese",
            code = "IL1",
            titleEnglish = "Assamese (MIL)",
            titleAssamese = "অসমীয়া (মাতৃভাষা)",
            totalMarks = 100,
            theoryMarks = 90,
            internalAssessmentMarks = 10,
            passMarks = 30,
            iconKey = "auto_stories",
            colorHex = "#BE123C",
            displayOrder = 5
        )
    )

    val DEFAULT_MODULES = listOf(
        // MATHEMATICS MODULES
        StudyModuleEntity(
            id = "math_ch1",
            subjectId = "mathematics",
            chapterNumber = 1,
            titleEnglish = "Real Numbers",
            titleAssamese = "বাস্তৱ সংখ্যা (Real Numbers)",
            subtopicsJson = "[\"Euclid's Division Lemma\", \"Fundamental Theorem of Arithmetic\", \"Irrationality of sqrt(2), sqrt(3)\", \"Decimal Expansions of Rational Numbers\"]",
            keyFormulasOrPointsJson = "[\"HCF(a,b) * LCM(a,b) = a * b\", \"Terminating if denominator in form 2^n * 5^m\"]",
            weightageMarks = 5,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 1
        ),
        StudyModuleEntity(
            id = "math_ch2",
            subjectId = "mathematics",
            chapterNumber = 2,
            titleEnglish = "Polynomials",
            titleAssamese = "বহুপদ (Polynomials)",
            subtopicsJson = "[\"Geometrical Meaning of Zeroes\", \"Relationship between Zeroes and Coefficients\", \"Division Algorithm for Polynomials\"]",
            keyFormulasOrPointsJson = "[\"Sum of zeroes: alpha + beta = -b/a\", \"Product of zeroes: alpha * beta = c/a\"]",
            weightageMarks = 4,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 2
        ),
        StudyModuleEntity(
            id = "math_ch3",
            subjectId = "mathematics",
            chapterNumber = 3,
            titleEnglish = "Pair of Linear Equations in Two Variables",
            titleAssamese = "দুটা চলকত ৰৈখিক সমীকৰণৰ যোৰ (Linear Equations)",
            subtopicsJson = "[\"Graphical Method\", \"Substitution Method\", \"Elimination Method\", \"Cross-Multiplication Method\", \"Equations Reducible to Linear Form\"]",
            keyFormulasOrPointsJson = "[\"Unique solution: a1/a2 != b1/b2\", \"Infinite solutions: a1/a2 = b1/b2 = c1/c2\", \"No solution: a1/a2 = b1/b2 != c1/c2\"]",
            weightageMarks = 7,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 3
        ),
        StudyModuleEntity(
            id = "math_ch4",
            subjectId = "mathematics",
            chapterNumber = 4,
            titleEnglish = "Quadratic Equations",
            titleAssamese = "দ্বিঘাত সমীকৰণ (Quadratic Equations)",
            subtopicsJson = "[\"Standard Form ax^2 + bx + c = 0\", \"Factorisation Method\", \"Completing Square Method\", \"Quadratic Formula\", \"Nature of Roots\"]",
            keyFormulasOrPointsJson = "[\"Discriminant D = b^2 - 4ac\", \"D > 0: Two distinct real roots\", \"D = 0: Two equal real roots\", \"D < 0: No real roots\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 4
        ),
        StudyModuleEntity(
            id = "math_ch5",
            subjectId = "mathematics",
            chapterNumber = 5,
            titleEnglish = "Arithmetic Progressions",
            titleAssamese = "সমান্তৰ প্ৰগতি (Arithmetic Progressions)",
            subtopicsJson = "[\"nth Term of an AP\", \"Sum of First n Terms of an AP\", \"Word Problems on AP\"]",
            keyFormulasOrPointsJson = "[\"an = a + (n - 1)d\", \"Sn = n/2 * [2a + (n - 1)d] = n/2 * (a + l)\"]",
            weightageMarks = 6,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 5
        ),
        StudyModuleEntity(
            id = "math_ch6",
            subjectId = "mathematics",
            chapterNumber = 6,
            titleEnglish = "Triangles",
            titleAssamese = "ত্ৰিভুজ (Triangles - Geometry)",
            subtopicsJson = "[\"Basic Proportionality Theorem (Thales)\", \"Converse of BPT\", \"Criteria for Similarity (AAA, SSS, SAS)\", \"Areas of Similar Triangles\", \"Pythagoras Theorem & Converse\"]",
            keyFormulasOrPointsJson = "[\"Ratio of areas = (ratio of corresponding sides)^2\", \"Pythagoras: AC^2 = AB^2 + BC^2\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 6
        ),
        StudyModuleEntity(
            id = "math_ch7",
            subjectId = "mathematics",
            chapterNumber = 7,
            titleEnglish = "Coordinate Geometry",
            titleAssamese = "স্থানাংক জ্যামিতি (Coordinate Geometry)",
            subtopicsJson = "[\"Distance Formula\", \"Section Formula\", \"Midpoint Formula\", \"Area of Triangle\"]",
            keyFormulasOrPointsJson = "[\"d = sqrt((x2 - x1)^2 + (y2 - y1)^2)\", \"Section: ((m1*x2 + m2*x1)/(m1+m2), (m1*y2 + m2*y1)/(m1+m2))\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 7
        ),
        StudyModuleEntity(
            id = "math_ch8",
            subjectId = "mathematics",
            chapterNumber = 8,
            titleEnglish = "Introduction to Trigonometry",
            titleAssamese = "ত্ৰিকোণমিতিৰ পৰিচয় (Trigonometry)",
            subtopicsJson = "[\"Trigonometric Ratios\", \"Values for 0, 30, 45, 60, 90 degrees\", \"Trigonometric Ratios of Complementary Angles\", \"Trigonometric Identities\"]",
            keyFormulasOrPointsJson = "[\"sin^2(theta) + cos^2(theta) = 1\", \"1 + tan^2(theta) = sec^2(theta)\", \"1 + cot^2(theta) = cosec^2(theta)\"]",
            weightageMarks = 7,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 8
        ),
        StudyModuleEntity(
            id = "math_ch9",
            subjectId = "mathematics",
            chapterNumber = 9,
            titleEnglish = "Some Applications of Trigonometry",
            titleAssamese = "ত্ৰিকোণমিতিৰ কিছুমান প্ৰয়োগ (Heights & Distances)",
            subtopicsJson = "[\"Angle of Elevation\", \"Angle of Depression\", \"Multi-step Height and Distance Problems\"]",
            keyFormulasOrPointsJson = "[\"tan(theta) = Opposite / Adjacent\", \"sin(theta) = Opposite / Hypotenuse\"]",
            weightageMarks = 5,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 9
        ),
        StudyModuleEntity(
            id = "math_ch10",
            subjectId = "mathematics",
            chapterNumber = 10,
            titleEnglish = "Circles",
            titleAssamese = "বৃত্ত (Circles - Geometry)",
            subtopicsJson = "[\"Tangent to a Circle\", \"Number of Tangents from a Point to a Circle\", \"Theorems on Tangent Lengths\"]",
            keyFormulasOrPointsJson = "[\"Tangent is perpendicular to radius at point of contact\", \"Lengths of tangents drawn from external point are equal\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 10
        ),
        StudyModuleEntity(
            id = "math_ch11",
            subjectId = "mathematics",
            chapterNumber = 11,
            titleEnglish = "Constructions",
            titleAssamese = "অঙ্কন (Constructions)",
            subtopicsJson = "[\"Division of a Line Segment internally\", \"Construction of Tangents to a Circle\"]",
            keyFormulasOrPointsJson = "[\"Use compass and ruler only\", \"Write steps of construction clearly\"]",
            weightageMarks = 4,
            difficultyLevel = "EASY",
            isHighYield = false,
            orderIndex = 11
        ),
        StudyModuleEntity(
            id = "math_ch12",
            subjectId = "mathematics",
            chapterNumber = 12,
            titleEnglish = "Areas Related to Circles",
            titleAssamese = "বৃত্ত সম্পৰ্কীয় কালি (Areas Related to Circles)",
            subtopicsJson = "[\"Perimeter and Area of a Circle\", \"Area of Sector of Angle theta\", \"Area of Segment of a Circle\"]",
            keyFormulasOrPointsJson = "[\"Area of sector = (theta/360) * pi * r^2\", \"Length of arc = (theta/360) * 2 * pi * r\"]",
            weightageMarks = 4,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 12
        ),
        StudyModuleEntity(
            id = "math_ch13",
            subjectId = "mathematics",
            chapterNumber = 13,
            titleEnglish = "Surface Areas and Volumes",
            titleAssamese = "পৃষ্ঠকালি আৰু আয়তন (Surface Areas & Volumes)",
            subtopicsJson = "[\"Surface Area of Combination of Solids\", \"Volume of Combination of Solids\", \"Conversion of Solid from One Shape to Another\", \"Frustum of a Cone\"]",
            keyFormulasOrPointsJson = "[\"Cylinder V = pi*r^2*h\", \"Cone V = 1/3*pi*r^2*h\", \"Sphere V = 4/3*pi*r^3\"]",
            weightageMarks = 6,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 13
        ),
        StudyModuleEntity(
            id = "math_ch14",
            subjectId = "mathematics",
            chapterNumber = 14,
            titleEnglish = "Statistics",
            titleAssamese = "পৰিসংখ্যা (Statistics)",
            subtopicsJson = "[\"Mean of Grouped Data (Direct, Assumed Mean, Step Deviation)\", \"Mode of Grouped Data\", \"Median of Grouped Data\", \"Graphical Representation of Cumulative Frequency\"]",
            keyFormulasOrPointsJson = "[\"Mode = l + ((f1 - f0)/(2f1 - f0 - f2)) * h\", \"3 Median = Mode + 2 Mean\"]",
            weightageMarks = 7,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 14
        ),
        StudyModuleEntity(
            id = "math_ch15",
            subjectId = "mathematics",
            chapterNumber = 15,
            titleEnglish = "Probability",
            titleAssamese = "সম্ভাৱিতা (Probability)",
            subtopicsJson = "[\"Classical Definition of Probability\", \"Elementary and Compound Events\", \"Complementary Events\"]",
            keyFormulasOrPointsJson = "[\"P(E) = Number of favorable outcomes / Total outcomes\", \"P(E) + P(not E) = 1\"]",
            weightageMarks = 4,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 15
        ),

        // GENERAL SCIENCE MODULES
        StudyModuleEntity(
            id = "sci_ch1",
            subjectId = "science",
            chapterNumber = 1,
            titleEnglish = "Chemical Reactions and Equations",
            titleAssamese = "ৰাসায়নিক বিক্ৰিয়া আৰু সমীকৰণ (Chemical Reactions)",
            subtopicsJson = "[\"Balanced Chemical Equations\", \"Types: Combination, Decomposition, Displacement, Double Displacement, Redox\", \"Corrosion & Rancidity\"]",
            keyFormulasOrPointsJson = "[\"2Mg + O2 -> 2MgO (white powder)\", \"Zn + CuSO4 -> ZnSO4 + Cu (Zinc more reactive)\", \"Exothermic vs Endothermic\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 1
        ),
        StudyModuleEntity(
            id = "sci_ch2",
            subjectId = "science",
            chapterNumber = 2,
            titleEnglish = "Acids, Bases and Salts",
            titleAssamese = "এছিড, ক্ষাৰক আৰু লৱণ (Acids, Bases & Salts)",
            subtopicsJson = "[\"Chemical Properties of Acids & Bases\", \"pH Scale & Importance in Everyday Life\", \"Common Salts: Bleaching Powder, Baking Soda, Washing Soda, Plaster of Paris\"]",
            keyFormulasOrPointsJson = "[\"Plaster of Paris: CaSO4 . 1/2 H2O\", \"Baking Soda: NaHCO3\", \"Bleaching powder: CaOCl2\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 2
        ),
        StudyModuleEntity(
            id = "sci_ch3",
            subjectId = "science",
            chapterNumber = 3,
            titleEnglish = "Metals and Non-metals",
            titleAssamese = "ধাতু আৰু অধাতু (Metals & Non-metals)",
            subtopicsJson = "[\"Physical & Chemical Properties of Metals/Non-metals\", \"Reactivity Series\", \"Ionic Compounds formation & properties\", \"Metallurgy & Extraction of Metals\", \"Corrosion & Prevention\"]",
            keyFormulasOrPointsJson = "[\"Reactivity: K > Na > Ca > Mg > Al > Zn > Fe > Pb > [H] > Cu > Hg > Ag > Au\", \"Amphoteric oxides: Al2O3, ZnO\"]",
            weightageMarks = 7,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 3
        ),
        StudyModuleEntity(
            id = "sci_ch4",
            subjectId = "science",
            chapterNumber = 4,
            titleEnglish = "Carbon and its Compounds",
            titleAssamese = "কাৰ্বন আৰু তাৰ যৌগ (Carbon Compounds)",
            subtopicsJson = "[\"Covalent Bonding in Carbon\", \"Versatile Nature: Catenation & Tetravalency\", \"Homologous Series\", \"Functional Groups\", \"Chemical Properties of Carbon Compounds\", \"Ethanol and Ethanoic Acid\", \"Soaps & Detergents\"]",
            keyFormulasOrPointsJson = "[\"Saturated (alkanes CnH2n+2) vs Unsaturated (alkenes CnH2n, alkynes CnH2n-2)\", \"Esterification: Acid + Alcohol -> Ester + H2O\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 4
        ),
        StudyModuleEntity(
            id = "sci_ch5",
            subjectId = "science",
            chapterNumber = 5,
            titleEnglish = "Periodic Classification of Elements",
            titleAssamese = "মৌলৰ পৰ্যাবৃত্ত শ্ৰেণীবিভাজন (Periodic Table)",
            subtopicsJson = "[\"Dobereiner's Triads\", \"Newlands' Law of Octaves\", \"Mendeleev's Periodic Table & Anomalies\", \"Modern Periodic Table & Trends: Valency, Atomic Size, Metallic Character\"]",
            keyFormulasOrPointsJson = "[\"Modern Periodic Law: Properties are periodic function of atomic numbers\", \"Atomic radius decreases across period, increases down group\"]",
            weightageMarks = 4,
            difficultyLevel = "MEDIUM",
            isHighYield = false,
            orderIndex = 5
        ),
        StudyModuleEntity(
            id = "sci_ch6",
            subjectId = "science",
            chapterNumber = 6,
            titleEnglish = "Life Processes",
            titleAssamese = "জীৱন প্ৰক্ৰিয়া (Life Processes - Biology)",
            subtopicsJson = "[\"Autotrophic & Heterotrophic Nutrition\", \"Human Alimentary Canal\", \"Respiration: Aerobic vs Anaerobic\", \"Human Respiratory System\", \"Transportation in Humans (Heart) and Plants (Xylem/Phloem)\", \"Excretion in Humans (Nephron) and Plants\"]",
            keyFormulasOrPointsJson = "[\"Photosynthesis: 6CO2 + 12H2O -> C6H12O6 + 6O2 + 6H2O\", \"Nephron filtration unit of kidney\", \"Double circulation in human heart\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 6
        ),
        StudyModuleEntity(
            id = "sci_ch7",
            subjectId = "science",
            chapterNumber = 7,
            titleEnglish = "Control and Coordination",
            titleAssamese = "নিয়ন্ত্ৰণ আৰু সমন্বয় (Control & Coordination)",
            subtopicsJson = "[\"Nervous System & Neuron structure\", \"Reflex Arc\", \"Human Brain (Forebrain, Midbrain, Hindbrain)\", \"Plant Hormones (Auxin, Gibberellin, Cytokinin, ABA)\", \"Endocrine System & Hormones in Animals\"]",
            keyFormulasOrPointsJson = "[\"Neuron: Dendrite -> Cyton -> Axon -> Synapse\", \"Insulin from pancreas regulates blood sugar\"]",
            weightageMarks = 5,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 7
        ),
        StudyModuleEntity(
            id = "sci_ch8",
            subjectId = "science",
            chapterNumber = 8,
            titleEnglish = "How do Organisms Reproduce",
            titleAssamese = "জীৱই কেনেকৈ বংশবৃদ্ধি কৰে (Reproduction)",
            subtopicsJson = "[\"Asexual Modes: Binary Fission, Budding, Spore formation, Vegetative Propagation\", \"Sexual Reproduction in Flowering Plants\", \"Human Male & Female Reproductive Systems\", \"Reproductive Health & Contraception\"]",
            keyFormulasOrPointsJson = "[\"Double fertilisation in angiosperms\", \"Placenta provides nutrition to developing embryo\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 8
        ),
        StudyModuleEntity(
            id = "sci_ch9",
            subjectId = "science",
            chapterNumber = 9,
            titleEnglish = "Heredity and Evolution",
            titleAssamese = "বংশগতি আৰু ক্ৰমবিকাশ (Heredity & Evolution)",
            subtopicsJson = "[\"Mendel's Experiments (Monohybrid 3:1 & Dihybrid 9:3:3:1)\", \"Sex Determination in Humans (XX / XY)\", \"Evolution & Speciation\", \"Homologous and Analogous Organs\", \"Fossils\"]",
            keyFormulasOrPointsJson = "[\"Father determines sex of child: Sperm brings X or Y\", \"Homologous: Same structure, different function (divergent evolution)\"]",
            weightageMarks = 5,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 9
        ),
        StudyModuleEntity(
            id = "sci_ch10",
            subjectId = "science",
            chapterNumber = 10,
            titleEnglish = "Light - Reflection and Refraction",
            titleAssamese = "পোহৰ-প্ৰতিফলন আৰু প্ৰতিসৰণ (Light - Physics)",
            subtopicsJson = "[\"Spherical Mirrors (Concave/Convex) & Ray Diagrams\", \"Mirror Formula and Magnification\", \"Refraction through Glass Slab & Snell's Law\", \"Refractive Index\", \"Lenses & Lens Formula\", \"Power of Lens\"]",
            keyFormulasOrPointsJson = "[\"Mirror formula: 1/f = 1/v + 1/u\", \"Lens formula: 1/f = 1/v - 1/u\", \"Power P = 1/f(m) Dioptre\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 10
        ),
        StudyModuleEntity(
            id = "sci_ch11",
            subjectId = "science",
            chapterNumber = 11,
            titleEnglish = "The Human Eye and Colourful World",
            titleAssamese = "মানুহৰ চকু আৰু বৰ্ণময় পৃথিৱী (Human Eye)",
            subtopicsJson = "[\"Structure of Eye & Power of Accommodation\", \"Defects of Vision: Myopia, Hypermetropia, Presbyopia & Corrections\", \"Refraction through Prism\", \"Dispersion of White Light\", \"Atmospheric Refraction & Twinkling of Stars\", \"Scattering of Light & Tyndall Effect\"]",
            keyFormulasOrPointsJson = "[\"Myopia corrected by concave lens\", \"Hypermetropia corrected by convex lens\", \"Blue sky due to Rayleigh scattering (proportional to 1/lambda^4)\"]",
            weightageMarks = 5,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 11
        ),
        StudyModuleEntity(
            id = "sci_ch12",
            subjectId = "science",
            chapterNumber = 12,
            titleEnglish = "Electricity",
            titleAssamese = "বিদ্যুৎ (Electricity - Physics)",
            subtopicsJson = "[\"Electric Current & Potential Difference\", \"Ohm's Law & Resistance Factors\", \"Resistivity\", \"Series & Parallel Combination of Resistors\", \"Joule's Law of Heating\", \"Electric Power\"]",
            keyFormulasOrPointsJson = "[\"V = IR\", \"Series: Rs = R1 + R2 + ...\", \"Parallel: 1/Rp = 1/R1 + 1/R2 + ...\", \"H = I^2*R*t\", \"P = VI = I^2*R = V^2/R\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 12
        ),
        StudyModuleEntity(
            id = "sci_ch13",
            subjectId = "science",
            chapterNumber = 13,
            titleEnglish = "Magnetic Effects of Electric Current",
            titleAssamese = "বিদ্যুৎ প্ৰবাহৰ চুম্বকীয় ক্ৰিয়া (Magnetic Effects)",
            subtopicsJson = "[\"Magnetic Field Lines & Properties\", \"Right-Hand Thumb Rule\", \"Magnetic Field due to Circular Loop and Solenoid\", \"Fleming's Left-Hand Rule & Electric Motor\", \"Electromagnetic Induction & Fleming's Right-Hand Rule\", \"Domestic Electric Circuits\"]",
            keyFormulasOrPointsJson = "[\"Fleming's Left Hand: Thumb=Force, Forefinger=Field, Center=Current\", \"Earth wire protects from electric shocks\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 13
        ),
        StudyModuleEntity(
            id = "sci_ch14",
            subjectId = "science",
            chapterNumber = 14,
            titleEnglish = "Sources of Energy",
            titleAssamese = "শক্তিৰ উৎসসমূহ (Sources of Energy)",
            subtopicsJson = "[\"Conventional Sources: Fossil Fuels, Thermal, Hydro, Biomass, Wind\", \"Non-conventional: Solar, Sea, Geothermal, Nuclear\", \"Environmental Consequences\"]",
            keyFormulasOrPointsJson = "[\"Solar cell converts light to electrical energy\", \"Nuclear fission of U-235 releases massive energy\"]",
            weightageMarks = 4,
            difficultyLevel = "EASY",
            isHighYield = false,
            orderIndex = 14
        ),
        StudyModuleEntity(
            id = "sci_ch15",
            subjectId = "science",
            chapterNumber = 15,
            titleEnglish = "Our Environment",
            titleAssamese = "আমাৰ পৰিৱেশ (Our Environment)",
            subtopicsJson = "[\"Ecosystem & Components (Biotic/Abiotic)\", \"Food Chains and Food Webs\", \"10% Law of Energy Transfer\", \"Ozone Depletion & CFCs\", \"Waste Management: Biodegradable vs Non-biodegradable\"]",
            keyFormulasOrPointsJson = "[\"10% law: Only 10% energy transferred to next trophic level\", \"Ozone O3 protects from harmful UV rays\"]",
            weightageMarks = 4,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 15
        ),
        StudyModuleEntity(
            id = "sci_ch16",
            subjectId = "science",
            chapterNumber = 16,
            titleEnglish = "Management of Natural Resources",
            titleAssamese = "প্ৰাকৃতিক সম্পদৰ ব্যৱস্থাপনা (Resource Management)",
            subtopicsJson = "[\"5 R's to save environment\", \"Forests and Wildlife Stakeholders\", \"Water for all & Dams vs Rainwater Harvesting\", \"Coal and Petroleum Conservation\"]",
            keyFormulasOrPointsJson = "[\"5 R's: Refuse, Reduce, Reuse, Repurpose, Recycle\", \"Chipko Movement protected Himalayan forests\"]",
            weightageMarks = 3,
            difficultyLevel = "EASY",
            isHighYield = false,
            orderIndex = 16
        ),

        // SOCIAL SCIENCE MODULES
        StudyModuleEntity(
            id = "soc_ch1",
            subjectId = "social_science",
            chapterNumber = 1,
            titleEnglish = "Advent of the Europeans in India",
            titleAssamese = "ভাৰতবৰ্ষলৈ ইউৰোপীয়সকলৰ আগমন (History)",
            subtopicsJson = "[\"Vasco da Gama 1498\", \"Establishment of English East India Company\", \"Battle of Plassey 1757 & Buxar 1764\", \"Administrative Expansion\"]",
            keyFormulasOrPointsJson = "[\"Vasco da Gama reached Calicut in 1498\", \"Robert Clive established British power after Plassey\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 1
        ),
        StudyModuleEntity(
            id = "soc_ch2",
            subjectId = "social_science",
            chapterNumber = 2,
            titleEnglish = "Partition of Bengal and Swadeshi Movement",
            titleAssamese = "বঙ্গ ভংগ আৰু স্বদেশী আন্দোলন (History)",
            subtopicsJson = "[\"Lord Curzon & 1905 Partition\", \"Swadeshi and Boycott Movement\", \"National Education Movement\", \"Impact on Assam\"]",
            keyFormulasOrPointsJson = "[\"Partition announced on 16 Oct 1905\", \"Vande Mataram became national song of resistance\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 2
        ),
        StudyModuleEntity(
            id = "soc_ch3",
            subjectId = "social_science",
            chapterNumber = 3,
            titleEnglish = "Anti-British Rising and Peasant Revolts in Assam",
            titleAssamese = "অসমত বৃটিছ বিৰোধী জাগৰণ আৰু কৃষক বিদ্ৰোহ (History)",
            subtopicsJson = "[\"Gomdhar Konwar & Piyoli Phukan\", \"Maniram Dewan & 1857 Revolt in Assam\", \"Phulaguri Dhewa 1861\", \"Patharughat Peasant Revolt 1894\"]",
            keyFormulasOrPointsJson = "[\"Phulaguri Dhewa (1861) was first peasant revolt against opium ban/tax\", \"Patharughat (1894) known as Jallianwala Bagh of Assam\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 3
        ),
        StudyModuleEntity(
            id = "soc_ch4",
            subjectId = "social_science",
            chapterNumber = 4,
            titleEnglish = "Indian Freedom Movement and National Awakening in Assam",
            titleAssamese = "ভাৰতৰ স্বাধীনতা আন্দোলন আৰু অসমত জাতীয় জাগৰণ (History)",
            subtopicsJson = "[\"Assam Association 1903 & Assam Pradesh Congress Committee 1921\", \"Non-Cooperation Movement in Assam\", \"Civil Disobedience & Cunningham Circular\", \"Quit India Movement 1942: Kushal Konwar, Kanaklata Barua, Bhogeswari Phukanani\"]",
            keyFormulasOrPointsJson = "[\"Kanaklata Barua attained martyrdom at Gohpur on 20 Sep 1942\", \"Kushal Konwar hanged in Sarupathar derailment case\"]",
            weightageMarks = 8,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 4
        ),
        StudyModuleEntity(
            id = "soc_ch5",
            subjectId = "social_science",
            chapterNumber = 5,
            titleEnglish = "Geography of Assam",
            titleAssamese = "অসমৰ ভূগোল (Geography)",
            subtopicsJson = "[\"Physiography: Brahmaputra Valley, Barak Valley, Hill Regions\", \"Drainage System & River Island Majuli\", \"Climate & Monsoons\", \"Biodiversity & National Parks (Kaziranga, Manas)\", \"Agriculture (Tea, Rice, Jute) & Mineral Resources (Petroleum at Digboi, Coal)\"]",
            keyFormulasOrPointsJson = "[\"Digboi is Asia's oldest oil refinery (1901)\", \"Majuli is world's largest river island in Brahmaputra\"]",
            weightageMarks = 8,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 5
        ),
        StudyModuleEntity(
            id = "soc_ch6",
            subjectId = "social_science",
            chapterNumber = 6,
            titleEnglish = "Indian Democracy and International Organizations",
            titleAssamese = "ভাৰতীয় গণতন্ত্ৰ আৰু আন্তৰ্জাতিক সংস্থা (Pol Science)",
            subtopicsJson = "[\"Preamble & Fundamental Rights/Duties\", \"Parliamentary System of Governance\", \"United Nations (UN) Structure: General Assembly, Security Council\", \"Human Rights & World Peace\"]",
            keyFormulasOrPointsJson = "[\"Preamble declares India Sovereign, Socialist, Secular, Democratic Republic\", \"UN formed on 24 October 1945 with 5 permanent members in UNSC\"]",
            weightageMarks = 7,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 6
        ),
        StudyModuleEntity(
            id = "soc_ch7",
            subjectId = "social_science",
            chapterNumber = 7,
            titleEnglish = "Money and Banking & Economic Development",
            titleAssamese = "টকা আৰু বেংক ব্যৱস্থা আৰু অৰ্থনৈতিক উন্নয়ন (Economics)",
            subtopicsJson = "[\"Evolution of Money & Functions\", \"Commercial Banks & RBI\", \"NITI Aayog & Economic Planning\", \"Human Development Index (HDI) & Sustainable Development\"]",
            keyFormulasOrPointsJson = "[\"RBI established in 1935 is central bank of India\", \"NITI Aayog replaced Planning Commission in 2015\"]",
            weightageMarks = 7,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 7
        ),

        // ENGLISH MODULES
        StudyModuleEntity(
            id = "eng_ch1",
            subjectId = "english",
            chapterNumber = 1,
            titleEnglish = "A Letter to God",
            titleAssamese = "A Letter to God (Prose - Lencho)",
            subtopicsJson = "[\"Lencho's Faith in God\", \"Hailstorm Destruction of Corn Field\", \"Postmaster's Kindness\", \"Irony of Bunch of Crooks\"]",
            keyFormulasOrPointsJson = "[\"Author: G.L. Fuentes\", \"Lencho asked for 100 pesos, received 70 pesos\"]",
            weightageMarks = 5,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 1
        ),
        StudyModuleEntity(
            id = "eng_ch2",
            subjectId = "english",
            chapterNumber = 2,
            titleEnglish = "Nelson Mandela: Long Walk to Freedom",
            titleAssamese = "Nelson Mandela (Prose - Apartheid)",
            subtopicsJson = "[\"Inauguration at Union Buildings amphitheatre\", \"Twin Obligations\", \"Courage and Definition of Freedom\"]",
            keyFormulasOrPointsJson = "[\"Inauguration date: 10th May 1994\", \"'Courage is not absence of fear, but triumph over it'\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 2
        ),
        StudyModuleEntity(
            id = "eng_ch3",
            subjectId = "english",
            chapterNumber = 3,
            titleEnglish = "Coorg & Tea from Assam",
            titleAssamese = "Glimpses of India: Coorg & Tea from Assam",
            subtopicsJson = "[\"Coorg coffee estates and martial men\", \"Pranjol & Rajvir train journey\", \"Legends of Tea: Chinese Emperor & Bodhidharma\", \"Dhekiabari Tea Estate\"]",
            keyFormulasOrPointsJson = "[\"Author: Arup Kumar Dutta (Tea from Assam), Lokesh Abrol (Coorg)\", \"Sprouting period: May to July yielding best tea\"]",
            weightageMarks = 6,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 3
        ),
        StudyModuleEntity(
            id = "eng_ch4",
            subjectId = "english",
            chapterNumber = 4,
            titleEnglish = "Madam Rides the Bus",
            titleAssamese = "Madam Rides the Bus (Prose - Valli)",
            subtopicsJson = "[\"Valli's 8-year curiosity and planning\", \"Bus fare 30 paise each way\", \"Encounter with dead cow on return journey\"]",
            keyFormulasOrPointsJson = "[\"Author: Vallikkannan\", \"Saved sixty paise by resisting peppermint and merry-go-round\"]",
            weightageMarks = 5,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 4
        ),
        StudyModuleEntity(
            id = "eng_ch5",
            subjectId = "english",
            chapterNumber = 5,
            titleEnglish = "SEBA English Grammar High-Yield Masterclass",
            titleAssamese = "SEBA Grammar: Tenses, Voice, Narration, Prepositions",
            subtopicsJson = "[\"Correct Tense Forms\", \"Voice Change (Active/Passive)\", \"Direct & Indirect Narration\", \"Appropriate Prepositions\", \"Subject-Verb Concord\", \"Determiners & Synthesis\"]",
            keyFormulasOrPointsJson = "[\"Since/For + Time period -> Present Perfect Continuous\", \"Universal Truth remains unchanged in Indirect Speech\"]",
            weightageMarks = 20,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 5
        ),

        // ASSAMESE (MIL) MODULES
        StudyModuleEntity(
            id = "asm_ch1",
            subjectId = "assamese",
            chapterNumber = 1,
            titleEnglish = "Borgeet (শ্ৰীমন্ত শংকৰদেৱ)",
            titleAssamese = "বৰগীত: তেজৰে কমলাপতি (শ্ৰীমন্ত শংকৰদেৱ)",
            subtopicsJson = "[\"যশোদাৰ কৃষ্ণ বন্দনা আৰু পুৱাৰ বৰ্ণনা\", \"ব্ৰজাৱলী ভাষাৰ বৈশিষ্ট্য\", \"আধ্যাত্মিক তাৎপৰ্য\"]",
            keyFormulasOrPointsJson = "[\"ৰচক: মহাপুৰুষ শ্ৰীমন্ত শংকৰদেৱ\", \"ৰাগ: বিভাস, তাল: একতাল\"]",
            weightageMarks = 5,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 1
        ),
        StudyModuleEntity(
            id = "asm_ch2",
            subjectId = "assamese",
            chapterNumber = 2,
            titleEnglish = "ছাত্ৰজীৱন আৰু সমাজসেৱা",
            titleAssamese = "ছাত্ৰজীৱন আৰু সমাজসেৱা (গদ্য)",
            subtopicsJson = "[\"ছাত্ৰসমাজৰ কৰ্তব্য আৰু দায়িত্ব\", \"সমাজ সংস্কাৰ আৰু দেশগঠনত ভূমিকা\"]",
            keyFormulasOrPointsJson = "[\"ৰচক: ডিম্বেশ্বৰ নেওগ\", \"'ছাত্ৰ অৱস্থা জীৱন গঠনৰ প্ৰস্তুতি পৰ্ব'\"]",
            weightageMarks = 5,
            difficultyLevel = "EASY",
            isHighYield = true,
            orderIndex = 2
        ),
        StudyModuleEntity(
            id = "asm_ch3",
            subjectId = "assamese",
            chapterNumber = 3,
            titleEnglish = "মোৰ মাতৃমুখ দৰ্শন",
            titleAssamese = "মোৰ মাতৃমুখ দৰ্শন (লক্ষ্মীনাথ বেজবৰুৱা)",
            subtopicsJson = "[\"দীৰ্ঘদিনৰ মূৰত অসম ভ্ৰমণৰ অনুভূতি\", \"মাতৃভাষা আৰু জন্মভূমিৰ প্ৰতি আকুলতা\"]",
            keyFormulasOrPointsJson = "[\"ৰচক: ৰসৰাজ লক্ষ্মীনাথ বেজবৰুৱা\", \"প্ৰকাশ পোৱা গ্ৰন্থ: 'মোৰ জীৱন সোঁৱৰণ'\"]",
            weightageMarks = 6,
            difficultyLevel = "MEDIUM",
            isHighYield = true,
            orderIndex = 3
        ),
        StudyModuleEntity(
            id = "asm_ch4",
            subjectId = "assamese",
            chapterNumber = 4,
            titleEnglish = "অসমীয়া ব্যাকৰণ আৰু ৰচনা",
            titleAssamese = "সন্ধি, সমাস, ণত্ব-ষত্ব বিধি, জতুৱা ঠাঁচ",
            subtopicsJson = "[\"স্বৰসন্ধি আৰু ব্যঞ্জনসন্ধি\", \"সমাসৰ ৬ প্ৰকাৰ\", \"ণত্ব-ষত্ব বিধিৰ নিয়ম\", \"জতুৱা ঠাঁচ আৰু খণ্ডবাক্যৰে বাক্য ৰচনা\"]",
            keyFormulasOrPointsJson = "[\"ঋ, ৰ, ষ ৰ পাছত দন্ত্য 'ন' মূৰ্ধন্য 'ণ' হয়\", \"উপপদ তৎপুৰুষ, দ্বন্দ্ব, বহুব্ৰীহি সমাসৰ নিয়ম\"]",
            weightageMarks = 15,
            difficultyLevel = "HARD",
            isHighYield = true,
            orderIndex = 4
        )
    )

    fun createInitialSpacedMetrics(): List<SpacedRepetitionMetricEntity> {
        val now = System.currentTimeMillis()
        return DEFAULT_MODULES.mapIndexed { index, module ->
            // Distribute initial review dates across days for natural spaced repetition demo
            val intervalDays = when (index % 5) {
                0 -> 1
                1 -> 3
                2 -> 7
                3 -> 14
                else -> 0 // brand new
            }
            val status = when {
                intervalDays == 0 -> "NEW"
                intervalDays == 1 -> "DUE_FOR_REVIEW"
                intervalDays >= 14 -> "LEARNING"
                else -> "LEARNING"
            }
            val nextDueDate = if (intervalDays == 0) now else now + (intervalDays * 86400000L)

            SpacedRepetitionMetricEntity(
                id = "sr_${module.id}",
                moduleId = module.id,
                subjectId = module.subjectId,
                repetitionLevel = if (intervalDays > 0) 1 else 0,
                intervalDays = intervalDays,
                easeFactor = 2.5,
                successfulReviews = if (intervalDays > 0) 1 else 0,
                totalReviews = if (intervalDays > 0) 1 else 0,
                lastReviewedAt = if (intervalDays > 0) now - 86400000L else 0L,
                nextReviewDueDate = nextDueDate,
                retentionScore = if (intervalDays > 0) 92.0 else 100.0,
                masteryPercentage = if (intervalDays >= 14) 75.0 else if (intervalDays > 0) 40.0 else 0.0,
                accuracyPercentage = if (intervalDays > 0) 85.0 else 0.0,
                totalQuestionsAttempted = if (intervalDays > 0) 10 else 0,
                totalQuestionsCorrect = if (intervalDays > 0) 8 else 0,
                streakCount = if (intervalDays > 0) 1 else 0,
                lastQualityScore = if (intervalDays > 0) 4 else 0,
                status = status
            )
        }
    }

    fun createInitialSubjectProgress(): List<SubjectProgressMetricEntity> {
        val now = System.currentTimeMillis()
        return DEFAULT_SUBJECTS.map { subject ->
            val subjectModules = DEFAULT_MODULES.filter { it.subjectId == subject.id }
            SubjectProgressMetricEntity(
                subjectId = subject.id,
                totalModules = subjectModules.size,
                masteredModules = 0,
                learningModules = if (subjectModules.isNotEmpty()) 2 else 0,
                dueForReviewCount = if (subjectModules.isNotEmpty()) 1 else 0,
                averageMasteryPercentage = 25.0,
                averageRetentionPercentage = 88.0,
                totalTimeSpentSeconds = 1800L,
                lastActiveTimestamp = now
            )
        }
    }
}
