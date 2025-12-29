package com.example.homeway.AIService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;
    public AIService(@Value("${openai.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    private String askChat(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", prompt);
        messages.add(userMsg);

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, requestEntity, Map.class);

        Map responseBody = responseEntity.getBody();
        if (responseBody == null) {
            return "AI did not return a response.";
        }

        List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
        if (choices == null || choices.isEmpty()) {
            return "AI returned no choices.";
        }

        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        if (message == null) {
            return "AI returned an empty message.";
        }

        Object content = message.get("content");
        return content != null ? content.toString() : "AI returned no content.";
    }

    //template
    public String methodName(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }

    //Osama
    public String customerRequestCostEstimation(String input) {
        String prompt = """
                You are a home services pricing assistant.
                Read the customer's request description and FIRST classify it as ONE of:
                (MOVING / INSPECTION / MAINTENANCE / REDESIGN).
                Then provide a cost estimate ONLY for that service type.

                Be clear, not too long (6–10 sentences total).
                Use Saudi Riyal (SAR). If location isn't provided, say "location affects price".

                Output format:
                - Service type detected:
                - Estimated total cost range (SAR):
                - Main factors affecting price (5 bullets)
                - Tips to reduce cost (3 bullets)
                - Questions to confirm for better estimate (3 short questions)

                Customer request description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String customerReportSummary(String input) {
        String prompt = """
                You are a home inspection report explainer for customers (non-technical).
                Explain the report in simple language.

                Output rules:
                - Keep it short: 8–12 lines
                - Use headings exactly:
                  1) Summary
                  2) Critical issues
                  3) Non-critical issues
                  4) Terms explained
                - "Critical issues" should list only items that affect safety/structure/major cost.
                - "Terms explained" should explain up to 4 technical terms simply.

                Report text:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String workerIssueDiagnosis(String input) {
        String prompt = """
                You are a field technician assistant.
                Given symptoms/issue description, suggest possible causes and what to check first.
                Be practical and step-by-step. Keep it short (5–8 sections max).

                Output format:
                1) Most likely causes (top 3)
                2) What to check first (step-by-step checklist)
                3) Common mistakes to avoid (3 bullets)
                4) When to escalate / call specialist (2 bullets)
                5) Safety warning (1 sentence)

                Symptoms / issue description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String movingCompanyMovingEstimation(String input) {
        String prompt = """
                You are a moving operations planner.
                Based on the move description, estimate ONLY:
                - crew size
                - truck size
                - packing materials recommendations
                - handling notes (fragile/heavy items)
                DO NOT mention time or duration.

                Output format:
                1) Crew size recommendation + why
                2) Truck size recommendation (e.g., small van / 3-ton / 5-ton / 10-ton) + why
                3) Packing materials list (bullets)
                4) Special handling notes (bullets)
                5) Missing details to confirm (3 questions)

                Moving request description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String workerJobTimeEstimation(String input) {
        String prompt = """
                You are a job duration estimator for field work.
                Estimate the time needed to complete the described job.

                Output format:
                1) Base duration estimate (range)
                2) Extra buffer time (range) and why
                3) Complexity level (Low/Medium/High) with 1 sentence
                4) Key steps that consume time (bullets)
                5) What could cause delays (3 bullets)

                Job description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String maintenanceCompanyMaintenancePlan(String input) {
        String prompt = """
                You are a maintenance planner.
                Create a clear maintenance plan based on the request description.
                The plan should be actionable for a worker team.

                Output format:
                1) Objective (1 sentence)
                2) Step-by-step plan (6–10 steps)
                3) Tools/materials needed (bullets)
                4) Risks & precautions (bullets)
                5) Quality checklist before marking complete (bullets)

                Request description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String redesignCompanyRedesignScope(String input) {
        String prompt = """
                You are an interior/home redesign scope assistant.
                Based on the customer's goals, propose a redesign scope and style suggestions.
                Keep it practical and structured.

                Output format:
                1) Design goals understood (2–4 bullets)
                2) Proposed scope (bullets: areas/rooms/features)
                3) Style directions (3 options with short description each)
                4) Materials/finishes ideas (bullets)
                5) Questions to confirm (3 questions)

                Customer goals / redesign request description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    //turki
    public String customerServicesTimeEstimation(String input) {
        String prompt = """
                         You are a home-services timeline estimation assistant for a property platform.
                         The user will provide a description of an issue in a home/property.
                
                         Your job:
                         - Estimate how long an INSPECTION usually takes
                         - Estimate how long MOVING usually takes
                         - Estimate how long MAINTENANCE usually takes
                         - Estimate how long a REDESIGN might take
                
                         Rules:
                         - Be realistic and conservative.
                         - Use time ranges (e.g., "2–4 hours", "1–3 days", "1–2 weeks").
                         - If details are missing, state assumptions briefly.
                         - Keep the answer short: 5–8 sentences maximum.
                         - Do NOT mention pricing or cost.
                         - Do NOT mention policy or system text.
                
                         Input description:
                         %s
                
                         Output format:
                         1) Inspection: ...
                         2) Maintenance: ...
                         3) Maintenance: ...
                         4) Redesign: ...
                         Assumptions: ...
                """.formatted(input);

        return askChat(prompt);
    }

    public String customerReviewWritingAssist(String notes, String tone) {

        // Normalize tone so the prompt stays stable even if user sends weird values
        String safeTone = (tone == null) ? "neutral" : tone.trim().toLowerCase();
        if (!safeTone.equals("polite") && !safeTone.equals("strict") && !safeTone.equals("neutral")) {
            safeTone = "neutral";
        }

        String prompt = """
                You are a customer review writing assistant for a home services platform (inspection, maintenance, moving, redesign).
                
                Task:
                - Convert the customer's rough notes into a clear, fair, and realistic review.
                - Keep it short: 5 to 8 sentences total.
                - The tone MUST be: %s
                
                Rules:
                - Do NOT invent details that are not in the notes.
                - If the notes are too vague, write a neutral review and mention "details were limited".
                - Avoid insults, hate, or unsafe content. Keep it professional.
                
                Output format:
                Title: <short title>
                Review: <5-8 sentences>
                RatingSuggestion: <1-5 number> (estimate based only on the notes)
                
                Customer notes:
                %s
                """.formatted(safeTone, notes);

        return askChat(prompt);
    }


    public String workerRepairChecklist(String input) {

        String prompt = """
            You are a professional maintenance and repair assistant.

            A worker will provide a short description of a repair issue.
            Your task is to generate a clear, practical repair checklist that a field worker can follow.

            Input issue description:
            %s

            Respond with:
            1. A short overview of the issue (1 sentence)
            2. Step-by-step repair checklist (numbered steps)
            3. Required tools and materials (bullet points)
            4. Safety precautions (if applicable)

            Rules:
            - Be clear and practical
            - Do not assume advanced expertise
            - Keep the response between 6–10 short steps
            - Do not mention prices or costs
            - Do not mention AI or disclaimers
            """.formatted(input);

        return askChat(prompt);
    }

    public String workerSafetyRequirements(String input) {

        String prompt = """
            You are a professional safety compliance assistant for maintenance, inspection, moving, and redesign work.

            Based on the task description below, provide:
            - Key safety precautions
            - Required personal protective equipment (PPE)
            - Common safety risks
            - General compliance and best-practice tips used in the industry

            Keep the response clear, practical, and concise (5–8 bullet points).

            Task description:
            %s
            """.formatted(input);

        return askChat(prompt);
    }

    public String companyServiceEstimationCost(String input) {
        String prompt = """
                You are a cost estimation assistant for home services (inspection, maintenance, moving, redesign).
                Based ONLY on the user's description, provide a realistic estimated cost breakdown for the COMPANY.
                
                Rules:
                - Use Saudi Riyal (SAR).
                - If details are missing, state assumptions briefly.
                - Give a RANGE (min-max) not a single number.
                - Keep it concise (8-12 lines).
                - Do NOT promise exact pricing. Mention it depends on site assessment.
                
                Output format:
                1) Quick summary (1-2 lines)
                2) Estimated total range (SAR)
                3) Breakdown (labor, materials, transport, equipment, overhead) with ranges
                4) Price drivers: what increases price (3 bullets)
                5) Price reducers: what decreases price (3 bullets)
                6) Questions to confirm (max 3)
                
                Description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    public String maintenanceCompanySparePartsCosts(String input) {
        String prompt = """
                You are a maintenance cost estimator.
                Based ONLY on the issue description, suggest likely spare parts and a realistic cost range.
                Keep it short and practical (5–10 bullet points max). If info is missing, state assumptions.

                Output format:
                1) Quick diagnosis guess (1 sentence)
                2) Likely spare parts list (part + why)
                3) Estimated parts cost range (low–high)
                4) What could increase/decrease parts cost (3 bullets)
                5) Safety note (1 sentence)

                Issue description:
                %s
                """.formatted(input);

        return askChat(prompt);
    }

    //Leen
    public String CustomerAskAIWhatServiceDoesTheIssueFits(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }

    public String CustomerIsFixOrDesignCheaper(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }

    public String WorkerReportCreationAssistent(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }

    public String CompanyInspectionPlanningAssistent(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }


    public String MovingCompanyTimeAdvice(String CityAndTime) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(CityAndTime);

        return askChat(prompt);
    }

    public String MaintenanceFixOrReplace(String input) {
        String prompt = """
                You are an ... assistant.
                Explain in clear, simple steps how to ...  and list some simple resources, be short 5-8 sentences:

                input: %s

                Mention:
                - 1
                - 2
                - 3
                - 4
                
                """.formatted(input);

        return askChat(prompt);
    }
}
