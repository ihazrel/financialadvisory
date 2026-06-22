package model;

public class ChatHistoryModel {

    private Integer chatHistoryId;
    private Integer chatId;
    private boolean promptOrResponse;
    private String content;

    public ChatHistoryModel() {}

    public ChatHistoryModel(Integer chatHistoryId,
                            Integer chatId,
                            boolean promptOrResponse,
                            String content) {

        this.chatHistoryId = chatHistoryId;
        this.chatId = chatId;
        this.promptOrResponse = promptOrResponse;
        this.content = content;
    }

    public Integer getChatHistoryId() {
        return chatHistoryId;
    }

    public void setChatHistoryId(Integer chatHistoryId) {
        this.chatHistoryId = chatHistoryId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public boolean isPromptOrResponse() {
        return promptOrResponse;
    }

    public void setPromptOrResponse(boolean promptOrResponse) {
        this.promptOrResponse = promptOrResponse;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}