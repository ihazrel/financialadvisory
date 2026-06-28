package chatbot;

import dao.ChatHistoryDAO;
import dao.ChatSessionDAO;
import dao.FinancialContextDAO;
import gemini.GeminiService;
import helper.SessionHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ChatHistoryModel;
import model.ChatSessionModel;
import model.FinancialContextModel;
import model.UserModel;
import util.ErrorUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet("/sendPrompt")
public class sendPrompt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int MAX_PROMPT_LENGTH = 1200;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = normalizeRole(request.getParameter("role"));
        response.sendRedirect("aiadvisory.jsp?role=" + encode(role));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserModel user = SessionHelper.getCurrentUser(request);
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = getStringParameter(request, "action");
        if (!"send".equalsIgnoreCase(action)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    ErrorUtil.format("sendPrompt.java", "doPost", "Invalid chat action"));
            return;
        }

        String role = normalizeRole(request.getParameter("role"));
        String prompt = getStringParameter(request, "prompt");

        if (prompt.isEmpty()) {
            redirectToChat(response, role, null, "Please enter a question before sending.");
            return;
        }

        if (prompt.length() > MAX_PROMPT_LENGTH) {
            prompt = prompt.substring(0, MAX_PROMPT_LENGTH);
        }

        ChatSessionDAO chatSessionDAO = new ChatSessionDAO();
        ChatHistoryDAO chatHistoryDAO = new ChatHistoryDAO();
        Integer chatId = getIntegerParameter(request, "chatId");
        ChatSessionModel chatSession = null;

        if (chatId != null) {
            chatSession = chatSessionDAO.getSessionByIdAndUserId(chatId, user.getUserId());
            if (chatSession == null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        ErrorUtil.format("sendPrompt.java", "doPost", "Chat session does not belong to current user"));
                return;
            }
        } else {
            chatId = chatSessionDAO.createSession(user.getUserId(), buildSessionName(prompt));
            if (chatId == null) {
                redirectToChat(response, role, null, "Could not create chat session. Please confirm the chat database tables exist.");
                return;
            }
        }

        ArrayList<ChatHistoryModel> recentMessages = chatHistoryDAO.getRecentMessages(chatId, 8);
        FinancialContextModel financialContext = new FinancialContextDAO().getFinancialContext(user);
        String geminiPrompt = buildGeminiPrompt(prompt, financialContext, recentMessages);

        boolean userMessageSaved = chatHistoryDAO.addMessage(chatId, true, prompt);
        String assistantResponse = new GeminiService().generateAdvisory(geminiPrompt);
        boolean responseSaved = chatHistoryDAO.addMessage(chatId, false, assistantResponse);
        chatSessionDAO.touchSession(chatId);

        if (!userMessageSaved || !responseSaved) {
            redirectToChat(response, role, chatId, "Message was generated, but one or more chat records could not be saved.");
            return;
        }

        redirectToChat(response, role, chatId, null);
    }

    private String buildGeminiPrompt(String userPrompt,
                                     FinancialContextModel financialContext,
                                     ArrayList<ChatHistoryModel> recentMessages) {
        StringBuilder builder = new StringBuilder();
        builder.append("You are an AI financial advisory assistant for an internal financial advisory system.\n");
        builder.append("Use only the provided financial data context. Do not invent database records.\n");
        builder.append("Give practical, concise recommendations with reasons and risks.\n");
        builder.append("Do not produce SQL. Do not request or reveal secrets.\n\n");

        builder.append("Financial context:\n");
        builder.append(financialContext.toPromptText()).append('\n');

        builder.append("Recent chat history:\n");
        if (recentMessages == null || recentMessages.isEmpty()) {
            builder.append("- None\n");
        } else {
            for (ChatHistoryModel message : recentMessages) {
                builder.append(message.isPromptOrResponse() ? "User: " : "Assistant: ");
                builder.append(limitForPrompt(message.getContent(), 500)).append('\n');
            }
        }

        builder.append("\nCurrent user question:\n");
        builder.append(userPrompt);

        return builder.toString();
    }

    private String buildSessionName(String prompt) {
        String normalized = prompt.replaceAll("\\s+", " ").trim();
        if (normalized.length() > 60) {
            return normalized.substring(0, 60);
        }
        return normalized.isEmpty() ? "New advisory chat" : normalized;
    }

    private String limitForPrompt(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }

    private void redirectToChat(HttpServletResponse response, String role, Integer chatId, String error) throws IOException {
        StringBuilder url = new StringBuilder("aiadvisory.jsp?role=").append(encode(role));
        if (chatId != null) {
            url.append("&chatId=").append(chatId);
        }
        if (error != null && !error.trim().isEmpty()) {
            url.append("&chatError=").append(encode(error));
        }
        response.sendRedirect(url.toString());
    }

    private Integer getIntegerParameter(HttpServletRequest request, String key) {
        String value = getStringParameter(request, key);
        if (value.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getStringParameter(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        return value == null ? "" : value.trim();
    }

    private String normalizeRole(String role) {
        if ("departmentmanager".equals(role) || "financialmanager".equals(role) || "staff".equals(role)) {
            return role;
        }
        return "staff";
    }

    private String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }
}
