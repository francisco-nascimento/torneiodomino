package br.ifpe.web3.util;

import javax.persistence.EntityNotFoundException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalController {

	@ExceptionHandler(EntityNotFoundException.class)
	protected String handleEntityNotFound(EntityNotFoundException ex, WebRequest request, Model model) {
		model.addAttribute("msg", ex.getMessage());
		return "redirect:/erro";
		
	}
}
