package residencia.vendas;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import residencia.vendas.entidade.Item;
import residencia.vendas.entidade.Pedido;
import residencia.vendas.entidade.TipoItem;
import residencia.vendas.entidade.Usuario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import residencia.vendas.servico.ServicoVendas;

public class ServicoTestVendas {
	
	
	Usuario usuario;
	
	@BeforeAll
	public static void setTestes() {
		
		Usuario usuario = new Usuario("Kaique","Arthur","0000000");
		
	}
	
	@Test
    public void pesoMaiorQue2() {
    	ServicoVendas totaldoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(200.0),BigDecimal.valueOf(4.0),TipoItem.ROUPAS);
      	itens.add(roupaC);
    	
       	BigDecimal valorTotal = totaldoPedido.finalizarVenda(usuario, itens);
       	           	
    	assertEquals(BigDecimal.valueOf(208.00),valorTotal);
    
    }
	
	@Test
    public void pesoMaiorQue10() {
    	ServicoVendas totaldoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(200.0),BigDecimal.valueOf(15.0),TipoItem.ROUPAS);
      	itens.add(roupaC);
    	BigDecimal valorTotal = totaldoPedido.finalizarVenda(usuario, itens);
    	
    	assertEquals(valorTotal,BigDecimal.valueOf(260.0));
    
    }
	
	@Test
    public void pesoMenorQue2() {
		
    	ServicoVendas totalDoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(200.0),BigDecimal.valueOf(1.0),TipoItem.ROUPAS);
    	itens.add(roupaC);
    	
    	BigDecimal valorTotal = totalDoPedido.finalizarVenda(usuario, itens);
    	    	
    	assertEquals(valorTotal,BigDecimal.valueOf(200.0));
    
    }
	
	
	@Test
    public void umItemMenorQue500() {
		ServicoVendas totalDoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(200.0),BigDecimal.valueOf(100.0),TipoItem.ROUPAS);
    	itens.add(roupaC);
    	
    	BigDecimal valorTotal = totalDoPedido.finalizarVenda(usuario, itens);
    	    	    	  	
    	assertEquals(valorTotal,BigDecimal.valueOf(900.0));
    
    }
	
  
    
    @Test
    public void precoMaiorQue1000() {
    	ServicoVendas totalDoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(1000.0),BigDecimal.valueOf(5.0),TipoItem.ROUPAS);
    	itens.add(roupaC);
    	
    	BigDecimal valorTotal = totalDoPedido.finalizarVenda(usuario, itens);
    	
    	System.out.println(valorTotal);
    	    	    	  	
    	assertEquals(valorTotal,BigDecimal.valueOf(540.00));
    
    }
    
    @Test
    public void precoMaiorEntre500e1000() {
    	ServicoVendas totalDoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(600.0),BigDecimal.valueOf(1.0),TipoItem.ROUPAS);
    	itens.add(roupaC);
    	
    	BigDecimal valorTotal = totalDoPedido.finalizarVenda(usuario, itens);
    	
    	System.out.println(valorTotal);
    	    	
    	  	
    	assertEquals(valorTotal,BigDecimal.valueOf(810.00));
    
    }
    
    @Test
    public void maisDeCincoItens() {
    	ServicoVendas totalDoPedido = new ServicoVendas();
    	
    	List<Item> itens = new ArrayList<>();
    	
    	Item roupaA = new Item("Roupa","Roupa",BigDecimal.valueOf(600.0),BigDecimal.valueOf(1.0),TipoItem.ROUPAS);
    	itens.add(roupaA);
    	Item roupaB = new Item("Roupa","Roupa",BigDecimal.valueOf(100.0),BigDecimal.valueOf(3.0),TipoItem.ROUPAS);
    	itens.add(roupaB);
    	Item roupaC = new Item("Roupa","Roupa",BigDecimal.valueOf(500.0),BigDecimal.valueOf(10.0),TipoItem.ROUPAS);
    	itens.add(roupaC);
    	Item roupaD = new Item("Roupa","Roupa",BigDecimal.valueOf(600.0),BigDecimal.valueOf(11.0),TipoItem.ROUPAS);
    	itens.add(roupaD);
    	Item roupaE = new Item("Roupa","Roupa",BigDecimal.valueOf(800.0),BigDecimal.valueOf(20.0),TipoItem.ROUPAS);
    	itens.add(roupaE);
    	Item roupaF = new Item("Roupa","Roupa",BigDecimal.valueOf(600.0),BigDecimal.valueOf(5.0),TipoItem.ROUPAS);
    	itens.add(roupaF);
    	Item roupaG = new Item("Roupa","Roupa",BigDecimal.valueOf(30.0),BigDecimal.valueOf(7.0),TipoItem.ROUPAS);
    	itens.add(roupaG);
    	
    	BigDecimal valorTotal = totalDoPedido.finalizarVenda(usuario, itens);
    	
    	System.out.println(valorTotal);
    	    	
    	  	
    	assertEquals(valorTotal,BigDecimal.valueOf(2993.00));
    
    }
    

}
