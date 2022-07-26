package residencia.vendas.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import residencia.vendas.entidade.Item;
import residencia.vendas.entidade.Pedido;
import residencia.vendas.entidade.Usuario;

public class ServicoVendas {
	
	public BigDecimal finalizarVenda(final Usuario usuario, final List<Item> itens) {

		BigDecimal precoInicial = BigDecimal.ZERO;
		BigDecimal desconto = BigDecimal.ZERO;
		BigDecimal precoFinal = BigDecimal.ZERO;
		BigDecimal acrescimo = BigDecimal.ZERO;
		BigDecimal valorFinal = BigDecimal.ZERO;
				
						
		for(Item i : itens) {
			precoInicial = precoInicial.add(i.preco());
		}
				
		if(precoInicial.doubleValue()>500 && precoInicial.doubleValue()<1000) {
			desconto = precoInicial.multiply(BigDecimal.valueOf(0.1));
		}
		else if(precoInicial.doubleValue()>=1000) {
			desconto = precoInicial.multiply(BigDecimal.valueOf(0.2));
		}else {
			desconto = BigDecimal.valueOf(0.0);
		}
		
		precoFinal = precoInicial.subtract(desconto);
		
				 				
		BigDecimal valorFrete = calculaFrete(itens);
		valorFinal = precoFinal.add(valorFrete);
			
		
		return valorFinal;
		
	}
	
	public BigDecimal calculaFrete(List<Item> itens) {
		
		BigDecimal peso = BigDecimal.ZERO;
		BigDecimal frete =  BigDecimal.ZERO;
		BigDecimal acrescimo = BigDecimal.ZERO;
		BigDecimal desconto = BigDecimal.ZERO;
		BigDecimal valorFinal = BigDecimal.ZERO;
		
		for(Item i : itens) {
			peso = peso.add(i.peso());
				
		}
		
		if(peso.doubleValue() < 2.0)
			frete = peso.multiply(BigDecimal.valueOf(0.0));
			
		else if(peso.doubleValue() > 2.0 && peso.doubleValue() <= 10) 
			frete = peso.multiply(BigDecimal.valueOf(2.0));
		
		else if(peso.doubleValue() > 10 && peso.doubleValue() <=50) 
			frete = peso.multiply(BigDecimal.valueOf(4.0));
		
		else if(peso.doubleValue() > 50) 
			frete = peso.multiply(BigDecimal.valueOf(7.0));
		
								
		if(itens.size()>5) 
			acrescimo = BigDecimal.valueOf(10.0);
		
		double valorTotal = frete.doubleValue() + acrescimo.doubleValue() - desconto.doubleValue();
		
									
		return BigDecimal.valueOf(valorTotal);
	}
	}
	

