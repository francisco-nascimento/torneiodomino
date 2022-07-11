package br.ifpe.web3.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ifpe.web3.model.Usuario;
import br.ifpe.web3.services.UsuarioService;
import br.ifpe.web3.util.EmailService;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/")
	public String index(Usuario usuario) {
		return "index";
	}
	
	@PostMapping("/novoUsuario")
	public String solicitarCadastroUsuario(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			return index(usuario);
		}
		try {
			this.usuarioService.solicitarCadastro(usuario);
			this.emailService.enviarEmailConfirmacaoCadastro(usuario.getEmail());
			ra.addFlashAttribute("msg", "Solicitação enviada com sucesso. Aguarde confirmação por e-mail");
			return "redirect:/";
		} catch (Exception e) {
            ObjectError erro = new ObjectError("usuario",e.getMessage());
			br.addError(erro);
			return index(usuario);
		}
		
	}
	
	@PostMapping("/admin/alterarUsuario")
	public String alterarUsuario(@Valid Usuario usuario, BindingResult br, RedirectAttributes ra) {
		if (br.hasErrors()) {
			return editarUsuario(usuario.getCodigo(), ra);
		}
		try {
			this.usuarioService.salvarAlteracoes(usuario);
			ra.addFlashAttribute("msg", "Alteração realizada com sucesso");
			return "redirect:/admin/listarUsuarios";
		} catch (Exception e) {
            ObjectError erro = new ObjectError("usuario",e.getMessage());
			br.addError(erro);
			return editarUsuario(usuario.getCodigo(), ra);
		}
		
	}
	@GetMapping("/admin/listarUsuarios")
	public String exibirListaUsuarios(@RequestParam(defaultValue = "email") String ordem, Model model) {
		model.addAttribute("colecao", this.usuarioService.listarUsuarios(ordem));
		return "/usuario/usuario-listar";
	}
	
	@GetMapping("/admin/editarUsuario")
	public String editarUsuario(Integer codigo, Model model) {
		Usuario user = this.usuarioService.consultarUsuarioPeloId(codigo);
		model.addAttribute("usuario", user);
		return "/usuario/usuario-editar";
	}

	@GetMapping("/admin/removerUsuario")
	public String removerUsuario(Integer codigo) {
		this.usuarioService.removerUsuario(codigo);
		return "redirect:/admin/listarUsuarios";

	}
	
	@GetMapping("/esqueceuSenha")
	public String exibirEsqueceuSenha() {
		return "/esqueceu-senha";

	}
	
	@PostMapping("/lembrarSenha")
	public String lembrarSenha(String login, RedirectAttributes ra) {
		try {
			this.usuarioService.enviarEmailEsqueceuSenha(login);
			ra.addFlashAttribute("msg", "E-mail enviado com a solicitação.");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ra.addFlashAttribute("msg", e.getMessage());
		}
		return "redirect:/";

	}
	
}
