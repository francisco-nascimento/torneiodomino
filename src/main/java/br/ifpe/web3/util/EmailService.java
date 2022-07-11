package br.ifpe.web3.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void enviarEmailConfirmacaoCadastro(String emailTo) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailTo);
			helper.setSubject("Torneio de Dominó - Confirmação de cadastro");
			helper.setText("<p>Solicitação de cadastro realizada com sucesso. <br/>Aguarde novas instruções.</p>",
					true);
			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void enviarEmailEsqueceuSenha(String emailTo, String senha) {

		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo(emailTo);
			helper.setSubject("Torneio de Dominó - Lembrete de senha");
			helper.setText("<p>Você clicou no Esqueceu a senha. Peço que decore, por favor: <br/>"
					+ senha + "</p>",
					true);
			mailSender.send(mail);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void enviarEmailText() {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo("teste@gmail.com");
		email.setSubject("Teste envio de e-mail");
		email.setText("Enviei este e-mail usando Spring Boot.");
		mailSender.send(email);
	}

	public String enviarEmailMime() {
		try {
			MimeMessage mail = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mail);
			helper.setTo("wolmirgarbin@gmail.com");
			helper.setSubject("Teste Envio de e-mail");
			helper.setText("<p>Hello from Spring Boot Application</p>", true);
			mailSender.send(mail);

			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return "Erro ao enviar e-mail";
		}
	}
}
