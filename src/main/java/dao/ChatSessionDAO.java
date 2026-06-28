package dao;

import connection.DBConnection;
import model.ChatSessionModel;
import util.ErrorUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatSessionDAO {

    public Integer createSession(Integer userId, String name) {
        String sql = "INSERT INTO chatsession (userid, name, datestart, dateupdated) VALUES (?, ?, SYSTIMESTAMP, SYSTIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"chatid"})) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, normalizeName(name));

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "createSession", e);
            return createSessionWithoutDateUpdated(userId, name);
        }

        return null;
    }

    private Integer createSessionWithoutDateUpdated(Integer userId, String name) {
        String sql = "INSERT INTO chatsession (userid, name, datestart) VALUES (?, ?, SYSTIMESTAMP)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"chatid"})) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, normalizeName(name));

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "createSessionWithoutDateUpdated", e);
        }

        return null;
    }

    public ArrayList<ChatSessionModel> getSessionsByUserId(Integer userId) {
        ArrayList<ChatSessionModel> sessions = new ArrayList<>();
        String sql = """
                SELECT chatid, userid, name, datestart, dateupdated
                FROM chatsession
                WHERE userid = ?
                ORDER BY NVL(dateupdated, datestart) DESC, chatid DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    sessions.add(mapSession(rs));
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "getSessionsByUserId", e);
            return getSessionsByUserIdWithoutDateUpdated(userId);
        }

        return sessions;
    }

    private ArrayList<ChatSessionModel> getSessionsByUserIdWithoutDateUpdated(Integer userId) {
        ArrayList<ChatSessionModel> sessions = new ArrayList<>();
        String sql = """
                SELECT chatid, userid, name, datestart
                FROM chatsession
                WHERE userid = ?
                ORDER BY datestart DESC, chatid DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChatSessionModel session = mapSessionWithoutDateUpdated(rs);
                    sessions.add(session);
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "getSessionsByUserIdWithoutDateUpdated", e);
        }

        return sessions;
    }

    public ChatSessionModel getSessionByIdAndUserId(Integer chatId, Integer userId) {
        String sql = """
                SELECT chatid, userid, name, datestart, dateupdated
                FROM chatsession
                WHERE chatid = ? AND userid = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapSession(rs);
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "getSessionByIdAndUserId", e);
            return getSessionByIdAndUserIdWithoutDateUpdated(chatId, userId);
        }

        return null;
    }

    private ChatSessionModel getSessionByIdAndUserIdWithoutDateUpdated(Integer chatId, Integer userId) {
        String sql = """
                SELECT chatid, userid, name, datestart
                FROM chatsession
                WHERE chatid = ? AND userid = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapSessionWithoutDateUpdated(rs);
                }
            }
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "getSessionByIdAndUserIdWithoutDateUpdated", e);
        }

        return null;
    }

    public boolean updateSessionName(Integer chatId, String name) {
        String sql = "UPDATE chatsession SET name = ?, dateupdated = SYSTIMESTAMP WHERE chatid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, normalizeName(name));
            pstmt.setInt(2, chatId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "updateSessionName", e);
            return updateSessionNameWithoutDateUpdated(chatId, name);
        }
    }

    private boolean updateSessionNameWithoutDateUpdated(Integer chatId, String name) {
        String sql = "UPDATE chatsession SET name = ? WHERE chatid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, normalizeName(name));
            pstmt.setInt(2, chatId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "updateSessionNameWithoutDateUpdated", e);
        }

        return false;
    }

    public boolean touchSession(Integer chatId) {
        String sql = "UPDATE chatsession SET dateupdated = SYSTIMESTAMP WHERE chatid = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            ErrorUtil.log("ChatSessionDAO.java", "touchSession", e);
        }

        return false;
    }

    private ChatSessionModel mapSession(ResultSet rs) throws Exception {
        return new ChatSessionModel(
                rs.getInt("chatid"),
                rs.getInt("userid"),
                rs.getString("name"),
                toDate(rs.getTimestamp("datestart")),
                toDate(rs.getTimestamp("dateupdated"))
        );
    }

    private ChatSessionModel mapSessionWithoutDateUpdated(ResultSet rs) throws SQLException {
        return new ChatSessionModel(
                rs.getInt("chatid"),
                rs.getInt("userid"),
                rs.getString("name"),
                toDate(rs.getTimestamp("datestart")),
                null
        );
    }

    private java.util.Date toDate(Timestamp timestamp) {
        return timestamp == null ? null : new java.util.Date(timestamp.getTime());
    }

    private String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "New advisory chat";
        }
        String trimmed = name.trim();
        return trimmed.length() > 80 ? trimmed.substring(0, 80) : trimmed;
    }
}
