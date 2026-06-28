package dao;

import connection.DBConnection;
import model.ChatHistoryModel;
import util.ErrorUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatHistoryDAO {

    public boolean addMessage(Integer chatId, boolean isPrompt, String content) {
        String sql = "INSERT INTO chathistory (chatid, promptorresponse, content, datecreated) VALUES (?, ?, ?, SYSTIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, isPrompt ? 1 : 0);
            pstmt.setString(3, content);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorUtil.log("ChatHistoryDAO.java", "addMessage", e);
            return addMessageWithoutDateCreated(chatId, isPrompt, content);
        }
    }

    private boolean addMessageWithoutDateCreated(Integer chatId, boolean isPrompt, String content) {
        String sql = "INSERT INTO chathistory (chatid, promptorresponse, content) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, isPrompt ? 1 : 0);
            pstmt.setString(3, content);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorUtil.log("ChatHistoryDAO.java", "addMessageWithoutDateCreated", e);
        }

        return false;
    }

    public ArrayList<ChatHistoryModel> getMessagesByChatId(Integer chatId) {
        ArrayList<ChatHistoryModel> messages = new ArrayList<>();
        String sql = """
                SELECT chathistoryid, chatid, promptorresponse, content, datecreated
                FROM chathistory
                WHERE chatid = ?
                ORDER BY NVL(datecreated, TO_TIMESTAMP('1970-01-01', 'YYYY-MM-DD')) ASC, chathistoryid ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapMessage(rs));
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatHistoryDAO.java", "getMessagesByChatId", e);
            return getMessagesByChatIdWithoutDateCreated(chatId);
        }

        return messages;
    }

    private ArrayList<ChatHistoryModel> getMessagesByChatIdWithoutDateCreated(Integer chatId) {
        ArrayList<ChatHistoryModel> messages = new ArrayList<>();
        String sql = """
                SELECT chathistoryid, chatid, promptorresponse, content
                FROM chathistory
                WHERE chatid = ?
                ORDER BY chathistoryid ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapMessageWithoutDateCreated(rs));
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatHistoryDAO.java", "getMessagesByChatIdWithoutDateCreated", e);
        }

        return messages;
    }

    public ArrayList<ChatHistoryModel> getRecentMessages(Integer chatId, int limit) {
        ArrayList<ChatHistoryModel> messages = new ArrayList<>();
        String sql = """
                SELECT *
                FROM (
                    SELECT chathistoryid, chatid, promptorresponse, content, datecreated
                    FROM chathistory
                    WHERE chatid = ?
                    ORDER BY NVL(datecreated, TO_TIMESTAMP('1970-01-01', 'YYYY-MM-DD')) DESC, chathistoryid DESC
                )
                WHERE ROWNUM <= ?
                ORDER BY chathistoryid ASC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(mapMessage(rs));
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatHistoryDAO.java", "getRecentMessages", e);
        }

        return messages;
    }

    private ChatHistoryModel mapMessage(ResultSet rs) throws SQLException {
        return new ChatHistoryModel(
                rs.getInt("chathistoryid"),
                rs.getInt("chatid"),
                rs.getInt("promptorresponse") == 1,
                rs.getString("content"),
                toDate(rs.getTimestamp("datecreated"))
        );
    }

    private ChatHistoryModel mapMessageWithoutDateCreated(ResultSet rs) throws SQLException {
        return new ChatHistoryModel(
                rs.getInt("chathistoryid"),
                rs.getInt("chatid"),
                rs.getInt("promptorresponse") == 1,
                rs.getString("content"),
                null
        );
    }

    private java.util.Date toDate(Timestamp timestamp) {
        return timestamp == null ? null : new java.util.Date(timestamp.getTime());
    }
}
