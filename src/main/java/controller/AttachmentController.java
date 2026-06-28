package controller;

import dao.AttachmentDAO;
import dao.TransactionDAO;
import helper.RoleHelper;
import helper.SessionHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AttachmentModel;
import model.TransactionModel;
import model.UserModel;
import util.ErrorUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/AttachmentController")
public class AttachmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("download".equalsIgnoreCase(action)) {
			downloadAttachment(request, response);
			return;
		}

		response.sendError(HttpServletResponse.SC_BAD_REQUEST,
				ErrorUtil.format("AttachmentController.java", "doGet", "Invalid action"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

		if ("delete".equalsIgnoreCase(action)) {
			deleteAttachment(request, response);
			return;
		}

		response.sendError(HttpServletResponse.SC_BAD_REQUEST,
				ErrorUtil.format("AttachmentController.java", "doPost", "Invalid action"));
	}

	private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int attachmentId = Integer.parseInt(request.getParameter("attachmentId"));
		AttachmentModel attachment = new AttachmentDAO().getAttachmentById(attachmentId);

		if (attachment == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					ErrorUtil.format("AttachmentController.java", "downloadAttachment", "Attachment not found"));
			return;
		}
		if (!canViewAttachment(request, attachment)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					ErrorUtil.format("AttachmentController.java", "downloadAttachment",
							"You do not have permission to download this attachment."));
			return;
		}

		Path file = Paths.get(attachment.getFilePath()).normalize();
		if (!Files.exists(file) || !Files.isRegularFile(file)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					ErrorUtil.format("AttachmentController.java", "downloadAttachment", "Attachment file not found"));
			return;
		}

		String contentType = attachment.getFileType();
		if (contentType == null || contentType.isBlank()) {
			contentType = Files.probeContentType(file);
		}
		if (contentType == null || contentType.isBlank()) {
			contentType = "application/octet-stream";
		}

		String encodedFileName = URLEncoder.encode(attachment.getName(), StandardCharsets.UTF_8).replace("+", "%20");
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
		response.setContentLengthLong(Files.size(file));

		try (OutputStream outputStream = response.getOutputStream()) {
			Files.copy(file, outputStream);
		}
	}

	private void deleteAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int attachmentId = Integer.parseInt(request.getParameter("attachmentId"));
		AttachmentDAO attachmentDAO = new AttachmentDAO();
		AttachmentModel attachment = attachmentDAO.getAttachmentById(attachmentId);
		if (attachment == null) {
			response.setContentType("application/json");
			response.getWriter().write("{\"success\":false}");
			return;
		}
		if (!canDeleteAttachment(request, attachment)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN,
					ErrorUtil.format("AttachmentController.java", "deleteAttachment",
							"You do not have permission to delete this attachment."));
			return;
		}
		boolean success = attachmentDAO.deleteAttachment(attachmentId);

		if (success && attachment != null && attachment.getFilePath() != null) {
			try {
				Files.deleteIfExists(Paths.get(attachment.getFilePath()).normalize());
			} catch (IOException e) {
				ErrorUtil.log("AttachmentController.java", "deleteAttachment", e);
			}
		}

		response.setContentType("application/json");
		response.getWriter().write("{\"success\":" + success + "}");
	}

	private boolean canViewAttachment(HttpServletRequest request, AttachmentModel attachment) {
		UserModel user = SessionHelper.getCurrentUser(request);
		TransactionModel transaction = new TransactionDAO().getTransactionById(attachment.getTransactionId());

		if (user == null || transaction == null) {
			return false;
		}

		return RoleHelper.isFinancialManager(user) || user.getDepartmentId() == transaction.getDepartmentId();
	}

	private boolean canDeleteAttachment(HttpServletRequest request, AttachmentModel attachment) {
		UserModel user = SessionHelper.getCurrentUser(request);
		TransactionModel transaction = new TransactionDAO().getTransactionById(attachment.getTransactionId());

		if (user == null || transaction == null || user.getDepartmentId() != transaction.getDepartmentId()) {
			return false;
		}

		return "draft".equalsIgnoreCase(transaction.getStatus()) || "rejected".equalsIgnoreCase(transaction.getStatus());
	}
}
