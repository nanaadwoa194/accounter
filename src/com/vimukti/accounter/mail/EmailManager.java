package com.vimukti.accounter.mail;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import com.vimukti.accounter.main.ServerConfiguration;

public class EmailManager extends Thread {

	static LinkedBlockingQueue<EMailJob> queue = new LinkedBlockingQueue<EMailJob>();
	private static EmailManager manager;
	public static long INTERVAL_TIME = 1000 * 60 * 1;
	private boolean shutdown;
	private String error;

	private Logger log = Logger.getLogger(EmailManager.class);

	public void run() {
		try {

			sleep(INTERVAL_TIME * 1);

			while (!shutdown) {
				try {
					EMailJob mail = getQueue().take();
					sendMail(mail);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendMail(final EMailJob mail) {
		// Set the host smtp address
		final EMailSenderAccount sender = mail.getSender();
		Properties p = getProperties(mail);

		Session session = null;
		if (sender.isSmtpAuthentication()) {
			Authenticator authenticator = new javax.mail.Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(
							sender.getSenderEmailID(),
							sender.getSenderPassword());
				}
			};
			session = Session.getInstance(p, authenticator);
		} else {
			session = Session.getInstance(p);
		}
		session.setDebug(false);

		for (EMailMessage emsg : mail.getMailMessages()) {
			try {
				MimeMessage msg = createMimeMessage(sender, emsg, session,
						mail.companyName);
				Transport transport = session.getTransport("smtp");
				transport.connect(sender.getOutGoingMailServer(),
						Integer.parseInt(sender.getSslPort()),
						sender.getSenderEmailID(), sender.getSenderPassword());
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
				// Transport.send(msg);

			}

			catch (Exception ex) {
				ex.printStackTrace();
				if (ex instanceof MessagingException) {
					error = "connection to host: \""
							+ mail.getSender().getOutGoingMailServer()
							+ "\" failed";
				}
				break;
			}

		}

	}

	private Properties getProperties(EMailJob mail) {
		final EMailSenderAccount sender = mail.getSender();
		Properties p = new Properties();
		if (sender.isSslAutheticationRequired()) {
			p.put("mail.smtps.auth", true);
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.user", sender.getSenderEmailID());

		} else {
			p.put("mail.smtps.auth", false);

		}

		if (sender.isStartTtlsEnables()) {
			p.put("mail.smtp.starttls.enable", true);
			p.put("mail.smtp.socketFactory.port", "465");
			p.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		}
		p.put("mail.smtp.host", sender.getOutGoingMailServer());
		p.put("mail.smtp.port", sender.getPortNumber());
		p.put("mail.smtp.8BITMIME", "true");
		p.put("mail.smtp.PIPELINING", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.fallback", "false");
		p.put("protocol", "smtp");
		return p;
	}

	private String createErrorMessage(EMailJob mail) {

		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append("The despatch of campaign ");
		errorMessage.append(mail.getCampaignName() + " ( Sent on " + new Date()
				+ " )");
		errorMessage.append(" failed because ");
		errorMessage.append(error);
		errorMessage
				.append(". Please try and change your email account settings.  "
						+ "The following recipients did not receive their email :");
		for (EMailMessage mesg : mail.getMailMessages()) {
			for (String to : mesg.getRecipeants()) {
				errorMessage.append(to);
				errorMessage.append(", ");
			}

		}
		int index = errorMessage.lastIndexOf(", ");
		if (index != -1) {
			errorMessage.replace(index, index + 2, ".");
		}

		return errorMessage.toString();

	}

	private MimeMessage createMimeMessage(EMailSenderAccount acc,
			EMailMessage emsg, Session session, String comapanyDBName) {
		// create a message
		MimeMessage msg = new MimeMessage(session);

		InternetAddress addressTo[] = new InternetAddress[emsg.getRecipeants()
				.size()];
		int i = 0;
		try {
			for (String email : emsg.getRecipeants()) {
				InternetAddress address = new InternetAddress(email);
				addressTo[i] = address;
				log.info(acc.getSenderEmailID() + " sending mail To " + email);
			}

			msg.setRecipients(Message.RecipientType.TO, addressTo);
			if (emsg.getReplayTO() != null) {
				msg.setReplyTo(new InternetAddress[] { new InternetAddress(emsg
						.getReplayTO()) });
			}
			String from = "";
			if (!acc.getAccoutName().equals("")) {
				from = acc.getAccoutName() + "<" + acc.getSenderEmailID() + ">";
			} else {
				from = acc.getSenderEmailID();
			}
			msg.setFrom(new InternetAddress(from));

			// MultiPart body part

			Multipart multipart = new MimeMultipart();

			// This is the message body content part

			MimeBodyPart messageContentPart = new MimeBodyPart();

			String content = processContent(emsg.content, multipart,
					comapanyDBName);
			if (emsg.isPlain) {
				messageContentPart.setContent(content,
						"text/plain; charset=UTF-8");
			} else {
				messageContentPart.setContent(content,
						"text/html; charset=UTF-8");
			}
			multipart.addBodyPart(messageContentPart);

			// This is the template Attachment part
			if (emsg.getAttachment() != null) {
				for (File file : emsg.getAttachment()) {
					MimeBodyPart messageAttachmentBodyPart = new MimeBodyPart();
					messageAttachmentBodyPart = new MimeBodyPart();

					DataSource source = new FileDataSource(file);

					messageAttachmentBodyPart.setDataHandler(new DataHandler(
							source));
					messageAttachmentBodyPart.setFileName(file.getName());
					multipart.addBodyPart(messageAttachmentBodyPart);
				}
			}

			// Put parts in message
			msg.setContent(multipart);

			// end

			// Optional : You can also set your custom headers in the Email
			// if
			// you
			// Want
			msg.addHeader("MyHeaderName", "myHeaderValue");

			// Setting the Subject and Content Type
			msg.setSubject(emsg.subject, "UTF-8");
			// msg.setSubject(emsg.subject);//text/plain; charset=ISO-8859-1
			// msg.setContent( map.getValue().getContent(), "text/html");
		} catch (AddressException e) {

			e.printStackTrace();
		} catch (MessagingException e) {

			e.printStackTrace();
		}
		return msg;
	}

	private String processContent(String content, Multipart multipart,
			String comapanyDBName) {

		String emailImagesDomain = ServerConfiguration.getEmailImagesUrl();

		// // Pattern imgTagPattern = Pattern
		// // .compile(
		// //
		// "<img src=[^>]*attachmentid=([a-zA-Z0-9]+)[^>]*parentId=([a-zA-Z0-9]+)[^>]* (style=[^>]*)>",
		// // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
		// // | Pattern.DOTALL);
		//
		// Pattern imgTagPattern = Pattern
		// .compile(
		// "<img src=[^>]*attachmentid=([a-zA-Z0-9]+)[^>]* (style=[^>]*)>",
		// Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
		// | Pattern.DOTALL);
		//
		// Matcher matcher = imgTagPattern.matcher(content);
		// while (matcher.find()) {
		// String attachmentID = matcher.group(1);
		// log.info("in Email manager id: " + attachmentID);
		// // String spaceID = matcher.group(2);
		// matcher.group(2);
		// // try {
		// // uploadEmailImagesToCloud(/* spaceID + */attachmentID,
		// // comapanyDBName);
		// // } catch (UnableToUploadException e) {
		// // e.printStackTrace();
		// // }
		// }
		// StringBuilder replaceString = new StringBuilder();
		// replaceString.append("<img src='http://");
		// replaceString.append(emailImagesDomain);
		// replaceString.append('/');
		// // replaceString.append("$2");
		// replaceString.append("$1");
		// replaceString.append("' ");
		// replaceString.append("$2");
		// replaceString.append(">");
		// return matcher.replaceAll(replaceString.toString());

		HashMap<String, String> keyValue = new HashMap<String, String>();
		Pattern imgTagPattern = Pattern
				.compile(
						"<img( style=[^>]*)? src=[^>]*attachmentid=([a-zA-Z0-9]+)[^ ^>]*( style=[^>]*)?>",
						Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
								| Pattern.DOTALL);
		Matcher matcher = imgTagPattern.matcher(content);
		while (matcher.find()) {
			// System.out.println("Match= " + matcher.group(0));
			// System.out.println("group1= " + matcher.group(1));
			// System.out.println("group2= " + matcher.group(2));
			// System.out.println("group3= " + matcher.group(3));
			// System.out.println("group4= " + matcher.group(4));
			String group1 = matcher.group(1);
			if (group1 != null) {
				StringBuilder original = new StringBuilder();
				original.append("<img src='http://");
				original.append(emailImagesDomain);
				original.append('/');
				original.append(matcher.group(2));
				original.append("' ");
				original.append(group1);
				original.append("/>");
				keyValue.put(matcher.group(0), original.toString());
			} else {
				StringBuilder original = new StringBuilder();
				original.append("<img src='http://");
				original.append(emailImagesDomain);
				original.append('/');
				original.append(matcher.group(2));
				original.append("' ");
				String group3 = matcher.group(3).trim();
				original.append(group3);
				if (group3.endsWith("/")) {
					original.append(">");
				} else {
					original.append("/>");
				}
				keyValue.put(matcher.group(0), original.toString());
			}

		}
		for (String old : keyValue.keySet()) {
			System.out.println("Old= " + old);
			System.out.println("New= " + keyValue.get(old));
			content = content.replace(old, keyValue.get(old));
		}
		return content;

	}

	private LinkedBlockingQueue<EMailJob> getQueue() {
		return queue;
	}

	public static EmailManager getInstance() {
		if (manager == null) {
			manager = new EmailManager();
		}
		return manager;
	}

	public void addJob(EMailJob job) {
		queue.add(job);

	}

	public void stopThread() {
		shutdown = true;
		this.interrupt();
	}

}
