package br.ifpe.web3.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ifpe.web3.model.Perfil;
import br.ifpe.web3.model.Usuario;

@WebFilter("/admin/*")
public class FiltroLogado implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    
		HttpSession sessao = ((HttpServletRequest) request).getSession();
		
		if (sessao != null && sessao.getAttribute("logado") != null) {
			System.out.println("Logado!");
			
			Usuario usuario = (Usuario) sessao.getAttribute("logado");
			if (usuario.getPerfil() == Perfil.ADMIN) {
				chain.doFilter(request, response);				
			} else {
				System.out.println("Não é do perfil Administrador.");
				res.sendRedirect("/acesso-negado");				
			}
		} else {
			System.out.println(" Não-logado");
			res.sendRedirect("/acesso-negado");
		}
		
	}

}
