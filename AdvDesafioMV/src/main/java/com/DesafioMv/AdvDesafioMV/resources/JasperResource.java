package com.DesafioMv.AdvDesafioMV.resources;

import com.DesafioMv.AdvDesafioMV.services.JasperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class JasperResource {
    @Autowired
    private JasperService jasperService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/reports")
    public void exibirRelatorio(@RequestParam("code") String code, @RequestParam("acao") String acao, HttpServletResponse response) throws IOException {
        byte[] bytes = jasperService.exportarPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        if (acao.equals("v")){
            response.setHeader("Content-disposition", "inline; filename=relatorio-"+code+".pdf");
        }else{
            response.setHeader("Content-disposition", "attachment; filename=relatorio-"+code+".pdf");
        }
        response.getOutputStream().write(bytes);
    }
}
